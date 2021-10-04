package net.darkmist.alib.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

/**
 * Simple wrapper of @{link PhantomReference} that implements {@link Ref}.
 */
@SuppressFBWarnings(value="SCII_SPOILED_CHILD_INTERFACE_IMPLEMENTOR",justification="Yup")
public class PhantomRef<T> extends PhantomReference<T> implements Ref.Queued<T>
{
	private final int hashCode;

	public PhantomRef(@Nullable T referent, ReferenceQueue<? super T> q)
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
		if(!(o instanceof PhantomRef))
			return false;
		PhantomRef<?> that = (PhantomRef<?>)o;
		return this.hashCode == that.hashCode;
	}

	@Override
	public int hashCode()
	{
		return hashCode;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " target=null";
	}
}
