package net.darkmist.alib.ref;

/**
 * Simple reference implementation. If you need a reference that
 * implements {@link Ref.Queued} see {@link StrongRef}.
 */
public class SimpleRef<T> implements Ref<T>
{
	private T target;

	public SimpleRef(T target)
	{
		this.target = target;
	}

	@Override
	public void set(T obj) throws RefException
	{
		this.target = obj;
	}

	@Override
	public void setReferent(T obj) throws RefException
	{
		this.target = obj;
	}

	@Override
	public T get() throws RefException
	{
		return target;
	}

	@Override
	public T getReferent() throws RefException
	{
		return target;
	}

	@Override
	public void clear() throws RefException
	{
		target = null;
	}

	@Override
	public boolean isSetSupported()
	{
		return true;
	}
}
