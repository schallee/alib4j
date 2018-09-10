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

package net.darkmist.alib.lang;

public class NullSafe
{
	protected NullSafe()
	{
	}

	/**
	 * Equals that properly handles null values.
	 */
	public static boolean equals(Object a, Object b)
	{
		if(a==b)
			return true;
		if(a==null)
			return false;
		if(b==null)
			return false;
		return a.equals(b);
	}
	
	/**
	 * Compare each odd and even object for equality.
	 * @param objs Pairs of objects. objs.length &gt; 0 and objs.length % 2 == 0.
	 * @return true if each pair is equal, false otherwise.
	 */
	public static boolean pairsEquals(Object...objs)
	{
		if(objs==null || objs.length == 0 || objs.length %2 != 0)
			throw new IllegalArgumentException("Object pairs must be passed.");
		for(int i=0;i<objs.length;i+=2)
			if(!equals(objs[i],objs[i+1]))
				return false;
		return true;
	}

	/**
	 * Get a object's hash code and handle null.
	 * @return 0 if o is null. o.hashCode() otherwise.
	 */
	public static int hashCode(Object o)
	{
		return (o==null ? 0 : o.hashCode());
	}

	/**
	 * Compute a hash code from a combination of each object's
	 * hash code. Null objects will have a zero hash code.
	 * @param objs Objects to get hash codes from.
	 * @return Hashcode appropriate for an object containing each
	 * 	object in objs.
	 */
	public static int hashCode(Object first, Object...objs)
	{
		int ret;

		ret = hashCode(first);
		for(Object o : objs)
			ret = ret*31 + hashCode(o);
		return ret;
	}

	public static String toString(Object o, String ifNull)
	{
		String ret;

		if(o==null)
			return ifNull;
		ret=o.toString();
		if(ret==null)
			return ifNull;
		return ret;
	}

	public static String toString(Object o)
	{
		return toString(o,"");
	}

	public static <T extends Comparable<T>> int compare(T a, T b)
	{
		if(a == b)
			return 0;
		if(a == null)
			return -1;
		if(b == null)
			return 1;
		return a.compareTo(b);
	}
}
