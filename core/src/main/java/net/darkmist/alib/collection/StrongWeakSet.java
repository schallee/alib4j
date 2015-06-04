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
import java.util.Set;

/**
 * Set that allows additions that will be weak references.
 *
 * <b>Note:</b> that presently it is an implemenation detail as to
 * what happens when a object is added both strongly and weakly. @{link
 * Set#remove(Object)} and related are still guaranteed to remove the
 * element, whether strongly or weakly referenced, so that the Set
 * contract continues to be valid.
 */
public interface StrongWeakSet<T> extends Set<T>
{
	/**
	 * Add an element that will be weakly referenced.
	 * @param e Element to add
	 * @return true if the set was modified
	 */
	public boolean addWeak(T e);

	/**
	 * Add a collection of elements that will be weakly
	 * referenced.
	 * @param c Collection of elements to add
	 * @return true if the set was modified
	 */
	public boolean addAllWeak(Collection<? extends T> c);
}
