package net.darkmist.alib.ref;

import java.lang.ref.WeakReference;

public abstract class MemCachedRef<T> extends AbstractRef<T>
{
	private volatile WeakReference<T> valRef = null;

	/**
	 * (Re)Create the object.
	 */
	protected abstract T make();

	/**
	 * Get the cached object. If the object is not in memory, it is recreated.
	 */
	// This is not the classic race condition as valRef is volatile.
	public T get()
	{
		WeakReference<T> wref;
		T val;

		if((wref=valRef)!=null && (val = wref.get())!=null)
			return val;
		synchronized(this)
		{
			if((wref=valRef)!=null && (val = wref.get())!=null)
				return val;
			val = make();
			valRef = new WeakReference<T>(val);
		}
		return val;
	}
}
