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

	public static int hashCode(Object o)
	{
		return (o==null ? 0 : o.hashCode());
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
