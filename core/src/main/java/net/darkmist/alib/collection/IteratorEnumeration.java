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

import java.util.Enumeration;
import java.util.Iterator;

/**
 * @deprecated Use {@link Iterators#asEnumeration(Iterator)} instead.
 */
@Deprecated
@SuppressWarnings("PMD.ReplaceEnumerationWithIterator")	// This is an adaptor.
public final class IteratorEnumeration<E> implements Enumeration<E>
{
	private final Iterator<? extends E> i;

	IteratorEnumeration(Iterator<? extends E> i)
	{
		this.i = i;
	}

	@Override
	public boolean hasMoreElements()
	{
		return i.hasNext();
	}

	@Override
	public E nextElement()
	{
		return i.next();
	}
}
