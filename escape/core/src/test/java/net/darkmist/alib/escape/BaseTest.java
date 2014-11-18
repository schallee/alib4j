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

package net.darkmist.alib.escape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.List;

import junit.framework.TestCase;

abstract class BaseTest extends TestCase
{
	private static final Class<BaseTest> CLASS = BaseTest.class;
	// not likely to change during run time...
	protected final int numProcs = Runtime.getRuntime().availableProcessors();
	
	protected ExecutorService executorService = null;

	protected static interface RangedCallableFactory
	{
		public Callable<Boolean> createCallableForRange(int start, int stop);
	}

	private static boolean nullIsFalse(Boolean b)
	{
		return (b!=null && b.booleanValue());
	}
	
	protected synchronized ExecutorService getExecutor()
	{
		if(executorService == null)
			executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		return executorService;
	}

	private List<Callable<Boolean>> createJobs(int start, int stop, RangedCallableFactory factory)
	{
		int total;
		int chunkSize;
		int chunkStart=start;
		int extra;
		List<Callable<Boolean>> jobs = new ArrayList<Callable<Boolean>>(numProcs+1);

		if(stop < start)
			throw new IllegalArgumentException("Start (" + start + ") is not less than stop (" + stop + ").");
		if((total = stop-start)==0)
			return Collections.emptyList();	// that was easy.
		chunkSize = total / numProcs;
		extra = total % numProcs;
		if(chunkSize > 0)
			for(int proc=0;proc<numProcs;proc++,chunkStart+=chunkSize)
				jobs.add(factory.createCallableForRange(chunkStart, chunkStart+chunkSize));
		if(extra > 0)
			jobs.add(factory.createCallableForRange(chunkStart, stop));
		return jobs;
	}

	private void checkResults(List<Future<Boolean>> results) throws InterruptedException, ExecutionException
	{
		if(results==null || results.isEmpty())
			return;
		for(Future<Boolean> result : results)
			assertTrue(nullIsFalse(result.get()));
	}

	protected void executeForRange(int start, int stop, RangedCallableFactory factory) throws InterruptedException, ExecutionException
	{
		List<Callable<Boolean>> jobs;

		jobs = createJobs(start, stop, factory);
		if(jobs.isEmpty())
			return;
		checkResults(getExecutor().invokeAll(jobs));
	}

	protected void executeForRange(int start, int stop, RangedCallableFactory factory, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException
	{
		List<Callable<Boolean>> jobs;

		jobs = createJobs(start, stop, factory);
		if(jobs.isEmpty())
			return;
		checkResults(getExecutor().invokeAll(jobs, timeout, unit));
	}
	
}
