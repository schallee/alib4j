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

import java.util.Collection;
import java.util.Set;
import net.darkmist.alib.generics.GenericFudge;

/**
 * Static utils for {@link Collection}s.
 */
@SuppressWarnings("PMD.UseUtilityClass")	// handling deprecated CollectionUtils
public class Collections
{
	// protected and not final class as the deprecated CollectionUtils inherits
	protected Collections()
	{
		// support deprecated
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
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <T> Collection<? super T> addAll(Collection<? super T> c, T... elements)
	{
		java.util.Collections.addAll(c,elements);
		return c;
	}

	public static <T> Set<? extends T> cast(Class<T> cls, Set<?> set)
	{
		for(Object o : set)
			cls.cast(o);
		return GenericFudge.set(set);
	}
}
