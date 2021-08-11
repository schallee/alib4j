package net.darkmist.alib.osgi.slf4j_log_listener;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import net.darkmist.alib.osgi.ServiceUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogBridge implements BundleActivator, LogListener, ServiceListener
{
	private static final Class<LogBridge> CLASS = LogBridge.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final String MDC_SERVICE_ID = Constants.class.getName() + '.' + Constants.SERVICE_ID;
	private BundleContext ctx;
	private Set<ServiceReference<LogReaderService>> servRefs;

	@Override	// BundleActivator
	public synchronized void start(BundleContext ctx_)
	{
		this.ctx = ctx_;
		servRefs = new HashSet<ServiceReference<LogReaderService>>();
		try
		{
			ctx.addServiceListener(this, "(" + Constants.OBJECTCLASS + '=' + LogReaderService.class.getName() + ')');
		}
		catch(InvalidSyntaxException e)
		{
			throw new IllegalStateException("Match for object class is invalid?", e);
		}
	}

	@Override	// BundleActivator
	public synchronized void stop(BundleContext ctx_)
	{
		LogReaderService lrs;

		this.ctx = ctx_;
		for(ServiceReference<LogReaderService> servRef : servRefs)
		{
			if((lrs = ctx.getService(servRef))==null)
				continue;
			try
			{
				lrs.removeLogListener(this);
			}
			catch(Exception e)
			{
				logger.warn("Exception removing ourselves as a log listener", e);
			}
		}
		servRefs=null;
	}

	/**
	 * Utility method to reduce unchecked area.
	 */
	@SuppressWarnings("unchecked")
	private static Enumeration<LogEntry> getLogEntries(LogReaderService lrs)
	{
		return (Enumeration<LogEntry>)lrs.getLog();
	}

	@Override	// ServiceListener
	public synchronized void serviceChanged(ServiceEvent event)
	{
		ServiceReference<LogReaderService> ref;
		LogReaderService lrs;
		Enumeration<LogEntry> logEntries = null;

		if((ref = ServiceUtil.getServiceRefAs(event.getServiceReference(), LogReaderService.class))==null)
		{
			logger.debug("Ignoring non-LogReaderService event");
			return;
		}
		switch(event.getType())
		{
			case ServiceEvent.MODIFIED:
				// Hasn't modfied our match...
				logger.debug("Modified but still a log service");
				return;
			case ServiceEvent.REGISTERED:
				// Register us
				if((lrs = ctx.getService(ref))==null)
				{
					logger.debug("service disappeared before we could use it");
					return;
				}

				try
				{
					lrs.addLogListener(this);
					servRefs.add(ref);
				}
				catch(Exception e)
				{
					logger.warn("error adding log listener", e);
				}
				try
				{
					logEntries = getLogEntries(lrs);
				}
				catch(Exception e)
				{
					logger.warn("error getting log entries", e);
				}
				if(logEntries != null)
					while(logEntries.hasMoreElements())
						logged(logEntries.nextElement());
				break;
			case ServiceEvent.MODIFIED_ENDMATCH:
				// We no longer match so it should ignore our listener
				servRefs.remove(ref);
				break;
			case ServiceEvent.UNREGISTERING:
				// remove it from the cache
				servRefs.remove(ref);
				break;
			default:
				logger.warn("Ignoring unknown event type {}", event.getType());
		}
	}

	private static String getLoggerName(Bundle bundle)
	{
		StringBuilder sb = new StringBuilder();
		String str;
		Version version;

		if(bundle == null)
			return "null_bundle";
		if((str=bundle.getSymbolicName())==null)
			sb.append("null_bundle_symbolic_name");
		else
			sb.append(str);
		if((version = bundle.getVersion())!=null)
			sb.append('.').append(version.toString());
		else
			sb.append(".null_bundle_version");
		return sb.toString();
	}

	@Override	// LogListener
	public synchronized void logged(LogEntry entry)
	{
		ServiceReference<?> servRef = entry.getServiceReference();
		String loggerName = getLoggerName(entry.getBundle());
		Logger eventLogger = LoggerFactory.getLogger(loggerName);
		Throwable exception = entry.getException();
		String msg = entry.getMessage();
		String serviceId=null;

		if(servRef != null)
			serviceId = ServiceUtil.getPropertyAs(servRef, Constants.SERVICE_ID, String.class);
		if(serviceId != null)
			MDC.put(MDC_SERVICE_ID, serviceId);
		switch(entry.getLevel())
		{
			case LogService.LOG_ERROR:
				if(exception == null)
					eventLogger.error(msg);
				else
					eventLogger.error(msg, exception);
				break;
			case LogService.LOG_WARNING:
				if(exception == null)
					eventLogger.warn(msg);
				else
					eventLogger.warn(msg, exception);
				break;
			case LogService.LOG_INFO:
				if(exception == null)
					eventLogger.info(msg);
				else
					eventLogger.info(msg, exception);
				break;
			case LogService.LOG_DEBUG:
				if(exception == null)
					eventLogger.debug(msg);
				else
					eventLogger.debug(msg, exception);
				break;
			default:
				logger.warn("Unknown log level {} ignored", entry.getLevel());
				break;
		}
		if(serviceId != null)
			MDC.remove(MDC_SERVICE_ID);
	}
}
