package net.darkmist.alib.ref;

import java.lang.ref.SoftReference;
import java.lang.ref.ReferenceQueue;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

/**
 * Simple wrapper of @{link SoftReference} that implements {@link Ref}.
 */
@SuppressFBWarnings(value="SCII_SPOILED_CHILD_INTERFACE_IMPLEMENTOR",justification="Yup")
public class SoftRef<T> extends SoftReference<T> implements Ref.Queued<T>
{
	private final int hashCode;

	public SoftRef(@Nullable T referent)
	{
		super(referent);
		this.hashCode = NullSafe.hashCode(referent);
	}

	public SoftRef(@Nullable T referent, ReferenceQueue<? super T> q)
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

	@Nullable
	@Override
	public T getReferent()
	{
		return get();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof SoftRef))
			return false;
		SoftRef<?> that = (SoftRef<?>)o;
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
