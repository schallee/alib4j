/*
 *  Copyright (C) 2012 Ed Schaller <schallee@darkmist.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

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
