package net.darkmist.alib.collection;

import java.util.Collection;
import java.util.Collections;

/**
 * Static utils for {@link Collection}s.
 */
public class CollectionUtil
{
	private CollectionUtil()
	{
	}

	/**
	 * Add elements to a collection and return the collection.
	 * This only differs from
	 * {@link Collections#addAll(Collection,Object[])} in that it
	 * returns the collection instead of a boolean.
	 * @param c The collection to add to.
	 * @param elements The elements to add.
	 * @return c
	 * @see Collections#addAll(Collection,Object[])
	 */
	public static <T> Collection<? super T> addAll(Collection<? super T> c, T... elements)
	{
		
		Collections.addAll(c,elements);
		return c;
	}
}
