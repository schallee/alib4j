package net.darkmist.alib.ref;

import java.lang.ref.WeakReference;
import java.lang.ref.ReferenceQueue;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

/**
 * Simple wrapper of @{link WeakReference} that implements {@link Ref}.
 */
@SuppressFBWarnings(value="SCII_SPOILED_CHILD_INTERFACE_IMPLEMENTOR",justification="Yup")
public class WeakRef<T> extends WeakReference<T> implements Ref.Queued<T>
{
	private final int hashCode;

	public WeakRef(@Nullable T referent)
	{
		super(referent);
		this.hashCode = NullSafe.hashCode(referent);
	}

	public WeakRef(@Nullable T referent, ReferenceQueue<? super T> q)
	{
		super(referent, q);
		this.hashCode = NullSafe.hashCode(referent);
	}

	@Override
	public void set(@Nullable T Obj)
	{
		throw new RefException(new UnsupportedOperationException());
	}

	@Override
	public void setReferent(@Nullable T Obj)
	{
		throw new RefException(new UnsupportedOperationException());
	}

	@Override
	public boolean isSetSupported()
	{
		return false;
	}

	@Override
	@Nullable
	public T getReferent()
	{
		return get();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof WeakRef))
			return false;
		WeakRef<?> that = (WeakRef<?>)o;
		if(this.hashCode != that.hashCode)
			return false;
		return NullSafe.equals(this.get(),that.get());
	}

	@Override
	public int hashCode()
	{
		return hashCode;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " target=" + getReferent();
	}
}
