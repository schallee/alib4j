package net.darkmist.alib.ref;

import java.lang.ref.SoftReference;
import java.lang.ref.Reference;

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
	private T target;

	public StrongRef(T referent)
	{
		super(referent);
		this.target = referent;
	}

	@Override
	public void set(T obj) throws RefException
	{
		throw new RefException(new UnsupportedOperationException());
	}

	@Override
	public void setReferent(T obj) throws RefException
	{
		set(obj);
	}

	@Override
	public boolean isSetSupported() throws RefException
	{
		return false;
	}

	@Override
	public T get() throws RefException
	{
		return target;
	}

	@Override
	public T getReferent() throws RefException
	{
		return get();
	}

	@Override
	public void clear() throws RefException
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
}
