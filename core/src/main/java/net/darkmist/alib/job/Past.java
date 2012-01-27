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
	 * @param exception The exception to wrap and throw
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
	 * @param exception The exception to wrap and throw
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
	public boolean cancel(boolean mayInterruptIfRunning)
	{
		return false;
	}

	/** Always returns false as the task has always completed.
	 * @returns false
	 */
	@Override
	public boolean isCancelled()
	{
		return false;
	}

	/** Always returns true as the task has always completed.
	 * @returns true
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

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException
	{
		if(exception != null)
			throw new ExecutionException(exception);
		return returnValue;
	}
}
