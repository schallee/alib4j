package net.darkmist.alib.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LoggingExceptionHandler implements ExceptionHandler
{
	private static final Class CLASS = LoggingExceptionHandler.class;
	private static final String CLASS_NAME = CLASS.getName();
	private static final Log logger = LogFactory.getLog(CLASS);

	private Log log;

	protected void setLogger(Log log_)
	{
		log = log_;
	}

	protected Log getLogger()
	{
		return log;
	}

	public LoggingExceptionHandler(Log log_)
	{
		setLogger(log_);
	}

	public void handleException(Throwable t)
	{
		log.error(t);
	}
}
