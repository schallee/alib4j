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

import java.util.Iterator;

public class IteratorProxy<T, I extends Iterator<T>> implements Iterator<T>
{
	protected final I target;

	public IteratorProxy(I target)
	{
		if((this.target=target)==null)
			throw new NullPointerException();
	}

	@Override
	public boolean hasNext()
	{
		return target.hasNext();
	}

	@Override
	public T next()
	{
		return target.next();
	}

	@Override
	public void remove()
	{
		target.remove();
	}
}
