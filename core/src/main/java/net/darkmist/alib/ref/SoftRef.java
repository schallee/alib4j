package net.darkmist.alib.ref;

import java.lang.ref.SoftReference;
import java.lang.ref.ReferenceQueue;

/**
 * Simple wrapper of @{link SoftReference} that implements {@link Ref}.
 */
public class SoftRef<T> extends SoftReference<T> implements Ref.Queued<T>
{
	public SoftRef(T referent)
	{
		super(referent);
	}

	public SoftRef(T referent, ReferenceQueue<? super T> q)
	{
		super(referent, q);
	}

	@Override
	public void set(T Obj) throws RefException
	{
		throw new RefException(new UnsupportedOperationException());
	}

	@Override
	public void setReferent(T Obj) throws RefException
	{
		throw new RefException(new UnsupportedOperationException());
	}

	@Override
	public boolean isSetSupported() throws RefException
	{
		return false;
	}

	@Override
	public T getReferent() throws RefException
	{
		return get();
	}
}
