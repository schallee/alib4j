package net.darkmist.alib.collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Static utilities for {@link Set}s.
 */
public class Sets
{
	private Sets()
	{
	}

	/**
	 * Create a new set.
	 * This allow's the default Set implementation to be swapped
	 * easily.
	 * @return a new empty set.
	 */
	public static <T> Set<T> newSet()
	{
		return new HashSet<T>();
	}

	/**
	 * Create a new set with an initial size.
	 * This allow's the default Set implementation to be swapped
	 * easily.
	 * @param initialSize hint defining the initial size.
	 * @return a new set with the initial capacity
	 */
	public static <T> Set<T> newSet(int initialSize)
	{
		return new HashSet<T>(initialSize);
	}

	/**
	 * Create a set with contents.
	 * @param contents The contents of the set.
	 * @return A set containing contents.
	 */
	public static <T> Set<T> newSet(T...contents)
	{
		Set<T> set;

		if(contents == null || contents.length==0)
			return newSet();
		set = newSet(contents.length);
		Collections.addAll(set, contents);
		return set;
	}

	/**
	 * Create a unmodifiable set with contents.
	 * @param contents The contents of the set.
	 * @return A unmodifiable set containing contents.
	 */
	public static <T> Set<T> newUnmodifiableSet(T...contents)
	{
		Set<T> set;

		if(contents == null || contents.length==0)
			return Collections.emptySet();
		if(contents.length == 1)
			return Collections.singleton(contents[0]);
		set = newSet(contents.length);
		Collections.addAll(set, contents);
		return Collections.unmodifiableSet(set);
	}
}
