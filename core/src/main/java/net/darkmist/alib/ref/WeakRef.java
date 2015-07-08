package net.darkmist.alib.ref;

import java.lang.ref.WeakReference;
import java.lang.ref.ReferenceQueue;

/**
 * Simple wrapper of @{link WeakReference} that implements {@link Ref}.
 */
public class WeakRef<T> extends WeakReference<T> implements Ref.Queued<T>
{
	public WeakRef(T referent)
	{
		super(referent);
	}

	public WeakRef(T referent, ReferenceQueue<? super T> q)
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
