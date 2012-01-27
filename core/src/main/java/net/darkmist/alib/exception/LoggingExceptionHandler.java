package net.darkmist.alib.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * {@link ExceptionHandler} that just logs the exception.
 */
public class LoggingExceptionHandler implements ExceptionHandler
{
	private static final Class CLASS = LoggingExceptionHandler.class;
	private static final String CLASS_NAME = CLASS.getName();
	private static final Log logger = LogFactory.getLog(CLASS);

	private volatile Log log = null;

	/**
	 * Set the Log to log exceptions to.
	 * @param log_ the log to log exceptions to.
	 */
	// final as constructor calls this.
	public final void setLogger(Log log_)
	{
		log = log_;
	}

	/**
	 * Get the current logger.
	 * @return The current logger.
	 */
	public Log getLogger()
	{
		return log;
	}

	/**
	 * Create using the given logger.
	 * @param log_ The logger to use.
	 */
	public LoggingExceptionHandler(Log log_)
	{
		setLogger(log_);
	}

	/**
	 * Create using the logger for this class.
	 */
	 public LoggingExceptionHandler()
	 {
	 }

	public void handleException(Throwable t)
	{
		Log localLog;

		if((localLog = getLogger())==null)
			localLog = logger;
		localLog.error(t);
	}
}
