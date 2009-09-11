package net.darkmist.alib.collection;

// new since qcomm

import java.util.Iterator;

public abstract class NonRemovingIterator<T> implements Iterator<T>
{
	public abstract boolean hasNext();

	public abstract T next();

	public final void remove()
	{
		throw new UnsupportedOperationException(this.getClass().getName() + " does not support remove()");
	}
}
