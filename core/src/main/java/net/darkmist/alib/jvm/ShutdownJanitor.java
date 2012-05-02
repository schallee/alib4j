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

package net.darkmist.alib.jvm;

import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShutdownJanitor implements Runnable
{
	private static final Class<ShutdownJanitor> CLASS = ShutdownJanitor.class;
	private static final Log logger = LogFactory.getLog(CLASS);
	private static Thread thread = null;
	private static final Object lock = new Object();
	private static boolean open = true;
	private static final WeakHashMap<NeedsCleanup,Boolean> objects = new WeakHashMap<NeedsCleanup,Boolean>();

	private ShutdownJanitor()
	{
	}

	@Override
	public void run()
	{
		NeedsCleanup[] objs = getObjects();

		for(NeedsCleanup obj : objs)
			try
			{
				obj.cleanup();
			}
			catch(Throwable t)
			{
				logger.error("Received exception when cleaning up " + obj + ". Ignoring...", t);
			}
	}

	protected static void checkRegistered()
	{
		synchronized(lock)	// should be anyway
		{
			if(thread != null)
				return;
			thread = new Thread(new ShutdownJanitor());
			Runtime.getRuntime().addShutdownHook(thread);
		}
	}

	protected static void checkEmpty()
	{
		synchronized(lock)	// should be anyway
		{
			if(thread == null || objects.size() > 0)
				return;
			Runtime.getRuntime().removeShutdownHook(thread);
			thread = null;
		}
	}

	protected NeedsCleanup[] getObjects()
	{
		NeedsCleanup[] empty = new NeedsCleanup[0];

		synchronized(lock)
		{
			return objects.keySet().toArray(empty);
		}
	}

	public static void addObject(NeedsCleanup obj)
	{
		synchronized(lock)
		{
			if(!open)
				throw new IllegalStateException("Attempt to add object to ShutdownJanitor while shutdown is in progress...");
			checkRegistered();
			objects.put(obj,Boolean.TRUE);
		}
	}

	public static void delObject(NeedsCleanup obj)
	{
		synchronized(lock)
		{
			objects.remove(obj);
			checkEmpty();
		}
	}
}
