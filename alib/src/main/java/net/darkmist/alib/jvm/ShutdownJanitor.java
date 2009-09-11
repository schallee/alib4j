package net.darkmist.alib.jvm;

import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ShutdownJanitor implements Runnable
{
	private static final Class CLASS = ShutdownJanitor.class;
	private static final String CLASS_NAME = CLASS.getName();
	private static final Log logger = LogFactory.getLog(CLASS);
	private static Thread thread = null;
	private static final Object lock = new Object();
	private static boolean open = true;
	private static final WeakHashMap<NeedsCleanup,Boolean> objects = new WeakHashMap<NeedsCleanup,Boolean>();

	private ShutdownJanitor()
	{
	}

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
