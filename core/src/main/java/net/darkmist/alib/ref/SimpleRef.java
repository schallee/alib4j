package net.darkmist.alib.ref;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

/**
 * Simple reference implementation. If you need a reference that
 * implements {@link Ref.Queued} see {@link StrongRef}.
 */
public class SimpleRef<T> implements Ref<T>
{
	private T target;

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public SimpleRef(T target)
	{
		this.target = target;
	}

	@Override
	public void set(T obj)
	{
		this.target = obj;
	}

	@Override
	public void setReferent(T obj)
	{
		this.target = obj;
	}

	@Override
	public T get()
	{
		return target;
	}

	@Override
	public T getReferent()
	{
		return target;
	}

	@Override
	public void clear()
	{
		target = null;
	}

	@Override
	public boolean isSetSupported()
	{
		return true;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof SimpleRef))
			return false;
		return NullSafe.equals(target, ((SimpleRef<?>)o).target);
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
