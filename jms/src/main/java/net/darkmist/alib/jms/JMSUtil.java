/*
 *  Copyright (C) 2014 Ed Schaller <schallee@darkmist.net>
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

package net.darkmist.alib.jms;

import javax.annotation.Nullable;
import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressFBWarnings(value="LO_SUSPECT_LOG_PARAMETER", justification="Logger for exception handler")
	// Can't put this on the implicit constructure for the ExceptionListner inner class
public class JMSUtil
{
	private static final Class<JMSUtil> CLASS = JMSUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private JMSUtil()
	{
	}

	private static <T> T nullOr(@Nullable T val, T def)
	{
		if(val==null)
			return def;
		return val;
	}

	@SuppressFBWarnings(value={"OPM_OVERLY_PERMISSIVE_METHOD","LO_SUSPECT_LOG_PARAMETER"}, justification="Library API, Logger for exception handler")
	public static ExceptionListener loggingExceptionListener(@Nullable Logger logExceptionTo, @Nullable String msg)
	{
		final Logger finalLogger = nullOr(logExceptionTo, logger);
		final String finalMsg = nullOr(msg, "Asynchronous JMSException thrown");

		return new ExceptionListener()
		{
			@Override
			public void onException(JMSException e)
			{
				finalLogger.warn(finalMsg, e);
			}
		};
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public static ExceptionListener loggingExceptionListener(@Nullable String msg)
	{
		return loggingExceptionListener(null,msg);
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public static ExceptionListener loggingExceptionListener()
	{
		return loggingExceptionListener(null,null);
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="CFS_CONFUSING_FUNCTION_SEMANTICS",justification="Method chaining")
	public static Connection setLoggingExceptionListener(Connection conn, @Nullable Logger logExceptionTo, @Nullable String msg) throws JMSException
	{
		conn.setExceptionListener(loggingExceptionListener(logExceptionTo, msg));
		return conn;
	}
	
	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="CFS_CONFUSING_FUNCTION_SEMANTICS",justification="Method chaining")
	public static Connection setLoggingExceptionListener(Connection conn, @Nullable String msg) throws JMSException
	{
		conn.setExceptionListener(loggingExceptionListener(msg));
		return conn;
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="CFS_CONFUSING_FUNCTION_SEMANTICS",justification="Method chaining")
	public static Connection setLoggingExceptionListener(Connection conn) throws JMSException
	{
		conn.setExceptionListener(loggingExceptionListener());
		return conn;
	}

	@CanIgnoreReturnValue
	@Nullable
	public static Connection close(@Nullable Connection toClose, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		if(toClose==null)
			return null;
		try
		{
			toClose.close();
		}
		catch(JMSException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("JMSException closing " + (name == null ? toClose.toString() : name) + " ignored.", e);
		}
		return null;
	}

	@CanIgnoreReturnValue
	@Nullable
	public static Connection close(@Nullable Connection toClose, @Nullable Object name)
	{
		return close(toClose, null, name);
	}

	@CanIgnoreReturnValue
	@Nullable
	public static Connection close(@Nullable Connection toClose)
	{
		return close(toClose, null, null);
	}

	@CanIgnoreReturnValue
	@Nullable
	public static MessageConsumer close(@Nullable MessageConsumer toClose, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		if(toClose==null)
			return null;
		try
		{
			toClose.close();
		}
		catch(JMSException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("JMSException closing " + (name == null ? toClose.toString() : name) + " ignored.", e);
		}
		return null;
	}

	@CanIgnoreReturnValue
	@Nullable
	public static MessageConsumer close(@Nullable MessageConsumer toClose, @Nullable Object name)
	{
		return close(toClose, null, name);
	}

	@CanIgnoreReturnValue
	@Nullable
	public static MessageConsumer close(@Nullable MessageConsumer toClose)
	{
		return close(toClose, null, null);
	}

	@CanIgnoreReturnValue
	@Nullable
	public static MessageProducer close(@Nullable MessageProducer toClose, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		if(toClose==null)
			return null;
		try
		{
			toClose.close();
		}
		catch(JMSException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("JMSException closing " + (name == null ? toClose.toString() : name) + " ignored.", e);
		}
		return null;
	}

	@CanIgnoreReturnValue
	@Nullable
	public static MessageProducer close(@Nullable MessageProducer toClose, @Nullable Object name)
	{
		return close(toClose, null, name);
	}

	@CanIgnoreReturnValue
	@Nullable
	public static MessageProducer close(@Nullable MessageProducer toClose)
	{
		return close(toClose, null, null);
	}

	@CanIgnoreReturnValue
	@Nullable
	public static QueueBrowser close(@Nullable QueueBrowser toClose, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		if(toClose==null)
			return null;
		try
		{
			toClose.close();
		}
		catch(JMSException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("JMSException closing " + (name == null ? toClose.toString() : name) + " ignored.", e);
		}
		return null;
	}

	@CanIgnoreReturnValue
	@Nullable
	public static QueueBrowser close(@Nullable QueueBrowser toClose, @Nullable Object name)
	{
		return close(toClose, null, name);
	}

	@CanIgnoreReturnValue
	@Nullable
	public static QueueBrowser close(@Nullable QueueBrowser toClose)
	{
		return close(toClose, null, null);
	}

	@CanIgnoreReturnValue
	@Nullable
	public static Session close(@Nullable Session toClose, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		if(toClose==null)
			return null;
		try
		{
			toClose.close();
		}
		catch(JMSException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("JMSException closing " + (name == null ? toClose.toString() : name) + " ignored.", e);
		}
		return null;
	}

	@CanIgnoreReturnValue
	@Nullable
	public static Session close(@Nullable Session toClose, @Nullable Object name)
	{
		return close(toClose, null, name);
	}

	@CanIgnoreReturnValue
	@Nullable
	public static Session close(@Nullable Session toClose)
	{
		return close(toClose, null, null);
	}

}
