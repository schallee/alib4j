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

/**
 * Generic reference to another object interface.
 */
public interface Ref<T>
{
	public void set(T obj) throws RefException;
	public void setReferent(T obj) throws RefException;
	public T get() throws RefException;
	public T getReferent() throws RefException;
	public void clear() throws RefException;
	public boolean isSetSupported();

	/**
	 * Reference interface that includes @{link ReferenceQueue}s.
	 */
	public interface Queued<T> extends Ref<T>
	{
		public boolean enqueue();
		public boolean isEnqueued();
	}
}
