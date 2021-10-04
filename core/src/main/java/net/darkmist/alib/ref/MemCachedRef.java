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

package net.darkmist.alib.ref;

import java.lang.ref.WeakReference;

public abstract class MemCachedRef<T> extends AbstractRef<T>
{
	private final Object lock = new Object();
	private WeakReference<T> valRef = null;

	/**
	 * (Re)Create the object.
	 */
	protected abstract T make();

	/**
	 * Get the cached object. If the object is not in memory, it is recreated.
	 */
	@Override
	public synchronized T get()
	{
		T val;

		synchronized(lock)
		{
			if(valRef==null || (val = valRef.get())==null)
			{
				val = make();
				valRef = new WeakReference<T>(val);
			}
		}
		return val;
	}
}
