/*
 *  Copyright (C) 2015 Ed Schaller <schallee@darkmist.net>
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * StrongWeakSet implementation using a {@link WeakHashMap} and a {@link
 * Hashset} internally. Basically this is a proxy for a set created
 * from a WeakHashMap. Strong references are additionaly maintained in
 * a internal strong set. This way weak references to the strong objects
 * in the WeakHashMap will not be garbage collected from the WeakHashMap
 * because strong references are guaranteed to still exist.
 */
class StrongWeakHashSet<T> extends SetProxy.SimplifiedSetProxy<T,Set<T>> implements StrongWeakSet<T>
{
	private final Set<T> strong = new HashSet<T>();

	StrongWeakHashSet()
	{
		super(Collections.newSetFromMap(new WeakHashMap<T,Boolean>()));
	}

	@Override
	public boolean add(T e)
	{
		strong.add(e);
		return super.add(e);
	}

	@Override
	public boolean addWeak(T e)
	{
		return super.add(e);
	}

	@Override
	public boolean addAllWeak(Collection<? extends T> c)
	{
		boolean ret = false;

		for(T t : c)
			ret |= addWeak(t);
		return ret;
	}

	@Override
	public boolean remove(Object o)
	{
		strong.remove(o);
		return super.remove(o);
	}
}

