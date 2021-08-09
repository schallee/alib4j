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


/**
 * Iterator that iterates over an array. Note that this does NOT make
 * a copy of the array so changes to the passed array may cause issues
 * with iteration.
 * @deprecated Use {@link Iterators#getArrayIterator(Object[])} instead.
 */
@Deprecated
public final class ArrayIterator<T> extends Iterators.ArrayIterator<T>
{
	/**
	 * Given an array, create an iterator.
	 * @param array The array to iterate over.
	 * @throws NullPointerException if array is null.
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public ArrayIterator(@SuppressWarnings("unchecked") T...array)
	{
		super(array);
	}
}
