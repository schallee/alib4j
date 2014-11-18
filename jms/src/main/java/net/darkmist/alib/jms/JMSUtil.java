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

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSUtil
{
	private static final Class<JMSUtil> CLASS = JMSUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private JMSUtil()
	{
	}

	public static ExceptionListener loggingExceptionListener(final Logger logExceptionTo, final String msg)
	{
		return new ExceptionListener()
		{
			@Override
			public void onException(JMSException e)
			{
				(logExceptionTo==null?logger:logExceptionTo).warn((msg==null?"Asynchronous JMSException thrown":msg), e);
			}
		};
	}

	public static ExceptionListener loggingExceptionListener(String msg)
	{
		return loggingExceptionListener(null,msg);
	}

	public static ExceptionListener loggingExceptionListener()
	{
		return loggingExceptionListener(null,null);
	}

	public static Connection setLoggingExceptionListener(Connection conn, Logger logExceptionTo, String msg) throws JMSException
	{
		conn.setExceptionListener(loggingExceptionListener(logExceptionTo, msg));
		return conn;
	}
	
	public static Connection setLoggingExceptionListener(Connection conn, String msg) throws JMSException
	{
		conn.setExceptionListener(loggingExceptionListener(msg));
		return conn;
	}

	public static Connection setLoggingExceptionListener(Connection conn) throws JMSException
	{
		conn.setExceptionListener(loggingExceptionListener());
		return conn;
	}

	public static Connection close(Connection toClose, Logger logExceptionTo, Object name)
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

	public static Connection close(Connection toClose, Object name)
	{
		return close(toClose, null, name);
	}

	public static Connection close(Connection toClose)
	{
		return close(toClose, null, null);
	}

	public static MessageConsumer close(MessageConsumer toClose, Logger logExceptionTo, Object name)
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

	public static MessageConsumer close(MessageConsumer toClose, Object name)
	{
		return close(toClose, null, name);
	}

	public static MessageConsumer close(MessageConsumer toClose)
	{
		return close(toClose, null, null);
	}

	public static MessageProducer close(MessageProducer toClose, Logger logExceptionTo, Object name)
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

	public static MessageProducer close(MessageProducer toClose, Object name)
	{
		return close(toClose, null, name);
	}

	public static MessageProducer close(MessageProducer toClose)
	{
		return close(toClose, null, null);
	}

	public static QueueBrowser close(QueueBrowser toClose, Logger logExceptionTo, Object name)
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

	public static QueueBrowser close(QueueBrowser toClose, Object name)
	{
		return close(toClose, null, name);
	}

	public static QueueBrowser close(QueueBrowser toClose)
	{
		return close(toClose, null, null);
	}

	public static Session close(Session toClose, Logger logExceptionTo, Object name)
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

	public static Session close(Session toClose, Object name)
	{
		return close(toClose, null, name);
	}

	public static Session close(Session toClose)
	{
		return close(toClose, null, null);
	}

}
