package net.darkmist.alib.ref;

public abstract class AbstractRef<T> implements Ref<T>
{
	/**
	 * Unsupported Operation
	 * @param obj unsed
	 * @throws RefException always.
	 */
	@Override
	public void set(@SuppressWarnings("unused") T obj) throws RefException
	{
		throw new RefException(new UnsupportedOperationException());
	}

	@Override
	public void setReferent(T obj) throws RefException
	{
		set(obj);
	}

	@Override
	public boolean isSetSupported()
	{
		return false;
	}

	@Override
	public T get() throws RefException
	{
		return null;
	}

	@Override
	public T getReferent() throws RefException
	{
		return get();
	}

	@Override
	public void clear() throws RefException
	{
		set(null);
	}
}
