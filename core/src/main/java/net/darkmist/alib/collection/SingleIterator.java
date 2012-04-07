package net.darkmist.alib.collection;

import java.util.NoSuchElementException;

/**
 * Iterator for a single item. Why? So items can be trivially added to
 * a IteratorIterator.
 * @deprecated Use {@link Iterators#getSingletonIterator(T)} instead.
 */
@Deprecated
public final class SingleIterator<U> extends Iterators.SingleIterator<U>
{

	public SingleIterator(U single)
	{
		super(single);
	}

}
