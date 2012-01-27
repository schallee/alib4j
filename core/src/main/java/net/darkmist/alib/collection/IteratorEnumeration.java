package net.darkmist.alib.collection;

import java.util.Enumeration;
import java.util.Iterator;

public final class IteratorEnumeration<E> implements Enumeration<E>
{
	private Iterator<? extends E> i;

	public IteratorEnumeration(Iterator<? extends E> i)
	{
		this.i = i;
	}

	public final boolean hasMoreElements()
	{
		return i.hasNext();
	}

	public final E nextElement()
	{
		return i.next();
	}
}
