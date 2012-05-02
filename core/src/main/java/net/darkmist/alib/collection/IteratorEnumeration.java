package net.darkmist.alib.collection;

import java.util.Iterator;

/**
 * @deprecated Use {@link Iterators#asEnumeration(Iterator)} instead.
 */
@Deprecated
public final class IteratorEnumeration<E> extends Iterators.IteratorEnumeration<E>
{
	public IteratorEnumeration(Iterator<? extends E> i)
	{
		super(i);
	}
}
