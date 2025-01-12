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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class MemCachedStaticSet<T> extends AbstractMemCachedStaticSet<T>
{
	protected final T[] array;

	@SafeVarargs
	@SuppressWarnings("varargs")
	public MemCachedStaticSet(T...array)
	{
		this.array = array;
	}

	@Override
	protected Set<T> make()
	{
		Set<T> set = new HashSet<T>(array.length);

		for(T entry : array)
			set.add(entry);
		return Collections.unmodifiableSet(set);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": array.length=" + array.length + " super=" + super.toString();
	}
}

