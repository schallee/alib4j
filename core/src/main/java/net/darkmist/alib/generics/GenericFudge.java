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

package net.darkmist.alib.generics;

import java.util.Map;

/**
 * Static methods to get around issues with generics... 
 */
@SuppressWarnings("unchecked")
public class GenericFudge
{
	/** Only static methods. */
	private GenericFudge()
	{
	}

	/**
	 * Fudge the return from getClass to include the generic.
	 */
	static public <T> Class<? extends T> getClass(T obj)
	{
		return (Class<? extends T>)obj.getClass();
	}

	/**
	 * Force a non generic map to be one.
	 */
	static public <K,V> Map<K,V> map(Map<?,?> map)
	{
		return (Map<K,V>)map;
	}
}
