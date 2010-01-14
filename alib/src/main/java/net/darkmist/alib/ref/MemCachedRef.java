package net.darkmist.alib.ref;

import java.lang.ref.WeakReference;

public abstract class MemCachedRef<T> extends AbstractRef<T>
{
	private WeakReference<T> valRef = null;

	/**
	 * (Re)Create the object.
	 */
	protected abstract T make();

	/**
	 * Get the cached object. If the object is not in memory, it is recreated.
	 */
	public synchronized T get()
	{
		T val;

		synchronized(this)
		{
			if(valRef==null || (val = valRef.get())==null)
			{
				val = make();
				valRef = new WeakReference<T>(val);
			}
		}
		return val;
	}
}
