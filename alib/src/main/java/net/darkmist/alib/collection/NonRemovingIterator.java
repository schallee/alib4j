package net.darkmist.alib.collection;

import java.util.Iterator;

/**
 * Abstract {@link Iterator} that does not support
 * {@link Iterator#remove}.
 */
public abstract class NonRemovingIterator<T> implements Iterator<T>
{
	public abstract boolean hasNext();

	public abstract T next();

	/**
	 * remove method that always trows an exception.
	 * @throws UnsupportedOperationException always.
	 */
	public final void remove()
	{
		throw new UnsupportedOperationException(this.getClass().getName() + " does not support remove()");
	}
}
