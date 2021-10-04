package net.darkmist.alib.ref;

import java.lang.ref.SoftReference;
import java.lang.ref.Reference;

import javax.annotation.Nullable;

import net.darkmist.alib.lang.NullSafe;

/**
 * Concrete wrapper of @{link Reference} that implements {@link Ref}.
 *
 * Design note: Non-java.lang.ref classes cannot inherit {@link Reference}
 * because it's constructor is package private. As such this actually
 * extends {@link SoftReference} but keeps it's own hard reference to
 * the target object. Usage of this class should <em>not</em> depend on
 * it inheriting from SoftReference as this could potentially change.
 */
public class StrongRef<T> extends SoftReference<T> implements Ref.Queued<T>
{
	private @Nullable T target;

	public StrongRef(@Nullable T referent)
	{
		super(referent);
		this.target = referent;
	}

	@Override
	public void set(@Nullable T obj)
	{
		throw new RefException(new UnsupportedOperationException());
	}

	@Override
	public void setReferent(@Nullable T obj)
	{
		set(obj);
	}

	@Override
	public boolean isSetSupported()
	{
		return false;
	}

	@Nullable
	@Override
	public T get()
	{
		return target;
	}

	@Nullable
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
	public boolean enqueue()
	{
		return false;
	}

	@Override
	public boolean isEnqueued()
	{
		return false;
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof StrongRef))
			return false;
		StrongRef<?> that = (StrongRef<?>)o;
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
