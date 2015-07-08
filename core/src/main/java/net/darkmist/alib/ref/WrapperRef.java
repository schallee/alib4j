package net.darkmist.alib.ref;

public class WrapperRef<T> implements Ref<T>
{
	private final Ref<T> target;

	public WrapperRef(Ref<T> target)
	{
		this.target = target;
	}

	@Override
	public void set(T obj) throws RefException
	{
		target.set(obj);
	}

	@Override
	public void setReferent(T obj) throws RefException
	{
		target.setReferent(obj);
	}

	@Override
	public T get() throws RefException
	{
		return target.get();
	}

	@Override
	public T getReferent() throws RefException
	{
		return target.getReferent();
	}

	@Override
	public void clear() throws RefException
	{
		target.clear();
	}

	@Override
	public boolean isSetSupported()
	{
		return target.isSetSupported();
	}
}
