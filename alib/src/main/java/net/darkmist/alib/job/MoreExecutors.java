package net.darkmist.alib.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MoreExecutors
{
	public static ExecutorService newNumProcThreadPool()
	{
		return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}

	public static ExecutorService newCurrentThreadPool()
	{
		return CurrentThreadExecutor.instance();
	}
}
