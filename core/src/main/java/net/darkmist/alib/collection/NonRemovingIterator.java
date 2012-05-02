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

import java.util.Iterator;

/**
 * Abstract {@link Iterator} that does not support
 * {@link Iterator#remove}.
 */
public abstract class NonRemovingIterator<T> implements Iterator<T>
{
	@Override
	public abstract boolean hasNext();

	@Override
	public abstract T next();

	/**
	 * remove method that always trows an exception.
	 * @throws UnsupportedOperationException always.
	 */
	@Override
	public final void remove()
	{
		throw new UnsupportedOperationException(this.getClass().getName() + " does not support remove()");
	}
}
