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

import javax.annotation.Nullable;

import net.darkmist.alib.lang.NullSafe;

public class Past<T> implements Future<T>
{
	private T returnValue;
	private Exception exception = null;

	/** 
	 * @param value The value to return.
	 * @param e The exception to wrap and throw
	 */
	public Past(@Nullable T value, @Nullable Exception e)
	{
		returnValue = value;
		exception = e;
	}

	/** 
	 * @param value The value to return.
	 */
	public Past(@Nullable T value)
	{
		returnValue = value;
	}
	
	/** 
	 * @param e The exception to wrap and throw
	 */
	public Past(@Nullable Exception e)
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

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof Past))
			return false;
		Past<?> that = (Past<?>)o;
		if(!NullSafe.equals(this.exception, that.exception))
			return false;
		return NullSafe.equals(this.returnValue, that.returnValue);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(returnValue, exception);

	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": returnValue=" + returnValue  + " exception=" + exception;
	}
}
