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

package net.darkmist.alib.job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/** A Executor that immedately executes in the current thread.
 * In many respects this is a non-Executor
 */
public class CurrentThreadExecutor implements ExecutorService
{
	private static final CurrentThreadExecutor singleton = new CurrentThreadExecutor();

	/** Here in case you need it. Call @{link #instance}
	 * instead as you really only need one instance.
	 */
	public CurrentThreadExecutor()
	{
	}

	/** Get singleton instance. */
	public static CurrentThreadExecutor instance()
	{
		return singleton;
	}

	/** Execute cmd in the current thread immediately and
	 * return when done.
	 * @param cmd The Runnable to run.
	 */
	@Override
	public void execute(Runnable cmd)
	{
		cmd.run();
	}

	/** Does nothing. */
	@Override
	public void shutdown()
	{
	}

	/** Does nothing. 
	 * @return A empty list.
	 */
	@Override
	public List<Runnable> shutdownNow()
	{
		return Collections.emptyList();
	}

	/** Always true.
	 * @return true
	 */
	@Override
	public boolean isShutdown()
	{
		return true;
	}

	/** Always true.
	 * @return true
	 */
	@Override
	public boolean isTerminated()
	{
		return true;
	}

	/** Always immediately true.
	 * @param timeout unused
	 * @param unit unused
	 * @return true
	 */
	@Override
	public boolean awaitTermination(@SuppressWarnings("unused") long timeout, @SuppressWarnings("unused") TimeUnit unit)
	{
		return true;
	}

	protected <T> Future<T> run(Callable<T> task)
	{
		T result = null;
		Exception exception = null;

		try
		{
			result = task.call();
		}
		catch(Exception e)
		{
			exception = e;
		}
		return new Past<T>(result, exception);
	}

	protected <T> Future<T> run(Runnable task, T result)
	{
		return run(Executors.callable(task, result));
	}

	protected Future<?> run(Runnable task)
	{
		return run(Executors.callable(task));
	}

	/** Submit a @{link Callable} task and execute it immediately.
	 * @param task The task to run
	 * @return A @{link Past} containing the result.
	 */
	@Override
	public <T> Future<T> submit(Callable<T> task)
	{
		return run(task);
	}

	/** Submit a @{link Runnable} task and execute it immediately.
	 * @param task The task to run
	 * @param result The result the @{link Past} returns.
	 * @return A @{link Past} containing the result.
	 */
	@Override
	@SuppressFBWarnings(value="NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE",justification="Warning doesn't make sense in context.")
	public <T> Future<T> submit(Runnable task, T result)
	{
		return run(task, result);
	}

	/** Submit a @{link Runnable} task and execute it immediately.
	 * @param task The task to run
	 * @return A @{link Past} containing the result.
	 */
	@Override
	public Future<?> submit(Runnable task)
	{
		return run(task);
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException
	{
		List<Future<T>> results = new ArrayList<Future<T>>(tasks.size());

		for(Callable<T> task : tasks)
			results.add(run(task));
		return results;
	}

	/** Implements @{link Executorservice#InvokeAll(Collection, long, TimeUnit)} by executing each task sequentially in the current thread until all are finished or the timeout has occured. The timeout is <b>NOT</b> at all percise. The current time in milliseconds is chcekced against the timout before each task is run. This has the potential of returning the longest task execution time after the timeout occurs in the worst case. */
	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException
	{
		long end = System.currentTimeMillis() + unit.toMillis(timeout);
		List<Future<T>> results = new ArrayList<Future<T>>(tasks.size());
		Iterator<? extends Callable<T>> i = tasks.iterator();

		while(System.currentTimeMillis() < end && i.hasNext())
			results.add(run(i.next()));
		return results;
	}

	@Override
	@SuppressFBWarnings(value={"WEM_WEAK_EXCEPTION_MESSAGING","EXS_EXCEPTION_SOFTENING_HAS_CHECKED"}, justification="Boolean state, should not happen")
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException
	{
		ExecutionException last = null;

		if(tasks.isEmpty())
			throw new IllegalArgumentException("Empty tasks passed.");
		for(Callable<T> task: tasks)
			try
			{
				return run(task).get();
			}
			catch(ExecutionException e)
			{
				last = e;
			}
		if(last != null)
			throw last;
		throw new IllegalStateException("No task finished and no task threw an exception. Tasks: " + tasks + '.');
	}

	@Override
	@SuppressFBWarnings(value={"WEM_WEAK_EXCEPTION_MESSAGING","EXS_EXCEPTION_SOFTENING_HAS_CHECKED"}, justification="Boolean state, should not happen")
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException
	{
		long end = System.currentTimeMillis() + unit.toMillis(timeout);
		Iterator<? extends Callable<T>> i = tasks.iterator();
		ExecutionException last = null;

		if(tasks.isEmpty())
			throw new IllegalArgumentException("Empty tasks passed.");
		while(System.currentTimeMillis() < end && i.hasNext())
			try
			{
				return run(i.next()).get();
			}
			catch(ExecutionException e)
			{
				last = e;
			}
		if(last != null)
			throw last;
		throw new IllegalStateException("No task finished and no task threw an exception.");
	}
}
