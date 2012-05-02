/*
 *  Copyright (C) 2012 Ed Schaller <schallee@darkmist.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.darkmist.alib.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * {@link ExceptionHandler} that just logs the exception.
 */
public class LoggingExceptionHandler implements ExceptionHandler
{
	private static final Class<LoggingExceptionHandler> CLASS = LoggingExceptionHandler.class;
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

	@Override
	public void handleException(Throwable t)
	{
		Log localLog;

		if((localLog = getLogger())==null)
			localLog = logger;
		localLog.error(t);
	}
}
