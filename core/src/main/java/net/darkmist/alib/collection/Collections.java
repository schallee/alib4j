package net.darkmist.alib.collection;

import java.util.Collection;

/**
 * Static utils for {@link Collection}s.
 */
public class Collections
{
	Collections()
	{
	}

	/**
	 * Add elements to a collection and return the collection.
	 * This only differs from
	 * {@link java.util.Collections#addAll(Collection,Object[])}
	 * in that it returns the collection instead of a boolean.
	 * @param c The collection to add to.
	 * @param elements The elements to add.
	 * @return c
	 * @see java.util.Collections#addAll(Collection,Object[])
	 */
	public static <T> Collection<? super T> addAll(Collection<? super T> c, T... elements)
	{
		java.util.Collections.addAll(c,elements);
		return c;
	}
}
