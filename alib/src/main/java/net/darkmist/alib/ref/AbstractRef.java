package net.darkmist.alib.ref;

public abstract class AbstractRef<T> implements Ref<T>
{
	public void set(T obj) throws RefException
	{
		throw new RefException(new UnsupportedOperationException());
	}

	public void setReferent(T obj) throws RefException
	{
		set(obj);
	}

	public boolean isSetSupported()
	{
		return false;
	}

	public T get() throws RefException
	{
		return null;
	}

	public T getReferent() throws RefException
	{
		return get();
	}

	public void clear() throws RefException
	{
		set(null);
	}
}
