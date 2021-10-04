package net.darkmist.alib.ref;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.annotation.Nullable;

import net.darkmist.alib.lang.NullSafe;

public class WrapperRef<T> implements Ref<T>
{
	private final Ref<T> target;

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="API method")
	public WrapperRef(Ref<T> target)
	{
		this.target = target;
	}

	@Override
	public void set(@Nullable T obj)
	{
		target.set(obj);
	}

	@Override
	public void setReferent(@Nullable T obj)
	{
		target.setReferent(obj);
	}

	@Nullable
	@Override
	public T get()
	{
		return target.get();
	}

	@Nullable
	@Override
	public T getReferent()
	{
		return target.getReferent();
	}

	@Override
	public void clear()
	{
		target.clear();
	}

	@Override
	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="API method")
	public boolean isSetSupported()
	{
		return target.isSetSupported();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof WrapperRef))
			return false;
		WrapperRef<?> that = (WrapperRef<?>)o;
		return NullSafe.equals(this.target, that.target);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(target);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " target=" + target;
	}
}
