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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link ExceptionHandler} that just logs the exception.
 */
public class LoggingExceptionHandler implements ExceptionHandler
{
	private static final Class<LoggingExceptionHandler> CLASS = LoggingExceptionHandler.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private volatile Logger log = null;

	/**
	 * Set the Logger to log exceptions to.
	 * @param log_ the log to log exceptions to.
	 */
	// final as constructor calls this.
	@SuppressFBWarnings(value="LO_SUSPECT_LOG_PARAMETER",justification="Purpose of class")
	public final void setLogger(Logger log_)
	{
		log = log_;
	}

	/**
	 * Get the current logger.
	 * @return The current logger.
	 */
	public Logger getLogger()
	{
		return log;
	}

	/**
	 * Create using the given logger.
	 * @param log_ The logger to use.
	 */
	@SuppressFBWarnings(value="LO_SUSPECT_LOG_PARAMETER",justification="Purpose of class")
	public LoggingExceptionHandler(Logger log_)
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
		Logger localLog;

		if((localLog = getLogger())==null)
			localLog = logger;
		localLog.error("Handling exception", t);
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof LoggingExceptionHandler))
			return false;
		LoggingExceptionHandler that = (LoggingExceptionHandler)o;
		return NullSafe.equals(this.log, that.log);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(log);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": log=" + log;
	}
}
