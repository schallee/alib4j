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
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.darkmist.alib.ref.MemCachedRef;

public class MemCachedStaticCharSet extends MemCachedRef<Set<Character>> implements Set<Character>
{
	protected final char[] array;

	public MemCachedStaticCharSet(char[] array)
	{
		this.array = array;
	}

	@Override
	protected Set<Character> make()
	{
		Set<Character> set = new HashSet<Character>(array.length);

		for(char entry : array)
			set.add(entry);
		return Collections.unmodifiableSet(set);
	}

	// set
	@Override
	public int size()
	{
		return get().size();
	}

	@Override
	public boolean isEmpty()
	{
		return get().isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		return get().contains(o);
	}

	@Override
	public Iterator<Character> iterator()
	{
		return get().iterator();
	}

	@Override
	public boolean add(Character e)
	{
		return get().add(e);
	}

	@Override
	public Object[] toArray()
	{
		return get().toArray();
	}

	@Override
	public <U> U[] toArray(U[] a)
	{
		return get().toArray(a);
	}

	@Override
	public boolean remove(Object o)
	{
		return get().remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return get().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Character> c)
	{
		return get().addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return get().retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return get().removeAll(c);
	}

	// public void clear()
}

