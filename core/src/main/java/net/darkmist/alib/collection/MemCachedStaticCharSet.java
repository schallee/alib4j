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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MemCachedStaticCharSet extends AbstractMemCachedStaticSet<Character>
{
	protected final char[] array;

	public MemCachedStaticCharSet(char...array)
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

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": array=" + Arrays.toString(array);
	}

}

