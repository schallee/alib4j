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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Past<T> implements Future<T>
{
	private T returnValue;
	private Exception exception = null;

	/** 
	 * @param value The value to return.
	 * @param e The exception to wrap and throw
	 */
	public Past(T value, Exception e)
	{
		returnValue = value;
		exception = e;
	}

	/** 
	 * @param value The value to return.
	 */
	public Past(T value)
	{
		returnValue = value;
	}
	
	/** 
	 * @param e The exception to wrap and throw
	 */
	public Past(Exception e)
	{
		exception = e;
	}

	/** Always returns true as task has always completed.
	 * @param mayInterruptIfRunning unused
	 * @return false
	 */
	@Override
	public boolean cancel(@SuppressWarnings("unused") boolean mayInterruptIfRunning)
	{
		return false;
	}

	/** Always returns false as the task has always completed.
	 * @return false
	 */
	@Override
	public boolean isCancelled()
	{
		return false;
	}

	/** Always returns true as the task has always completed.
	 * @return true
	 */
	@Override
	public boolean isDone()
	{
		return true;
	}

	@Override
	public T get() throws InterruptedException, ExecutionException
	{
		if(exception != null)
			throw new ExecutionException(exception);
		return returnValue;
	}

	/**
	 * Returns immediately
	 * @param timeout ignored
	 * @param unit ignored
	 */
	@Override
	public T get(@SuppressWarnings("unused") long timeout, @SuppressWarnings("unused") TimeUnit unit) throws InterruptedException, ExecutionException
	{
		if(exception != null)
			throw new ExecutionException(exception);
		return returnValue;
	}
}
