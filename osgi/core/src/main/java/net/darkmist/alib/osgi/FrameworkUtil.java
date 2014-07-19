package net.darkmist.alib.osgi;

import org.osgi.framework.FrameworkEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameworkUtil
{
	private static final Class<FrameworkUtil> CLASS = FrameworkUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/**
	 * Static utils only
	 */
	private FrameworkUtil()
	{
	}

	public static String toString(FrameworkEvent event)
	{
		if(event == null)
			return "null FrameworkEvent";
		switch(event.getType())
		{
			case FrameworkEvent.ERROR:
				return "ERROR";
			case FrameworkEvent.INFO:
				return "INFO";
			case FrameworkEvent.PACKAGES_REFRESHED:
				return "PACKAGES_REFRESHED";
			case FrameworkEvent.STARTED:
				return "STARTED";
			case FrameworkEvent.STARTLEVEL_CHANGED:
				return "STARTLEVEL_CHANGED";
			case FrameworkEvent.STOPPED:
				return "STOPPED";
			case FrameworkEvent.STOPPED_BOOTCLASSPATH_MODIFIED:
				return "STOPPED_BOOTCLASSPATH_MODIFIED";
			case FrameworkEvent.STOPPED_UPDATE:
				return "STOPPED_UPDATE";
			case FrameworkEvent.WAIT_TIMEDOUT:
				return "WAIT_TIMEDOUT";
			case FrameworkEvent.WARNING:
				return "WARNING";
			default:
				return "unknown FrameworkEvent type " + event.getType();
		}
	}
	
}
