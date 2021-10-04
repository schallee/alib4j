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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="API methods")
public class ValueFits
{
	private ValueFits()
	{
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsByte(@SuppressWarnings("unused") byte b)
	{
		return true;
	}

	public static boolean fitsByte(short s)
	{
		return Byte.MIN_VALUE <= s && s <= Byte.MAX_VALUE;
	}

	public static boolean fitsByte(int i)
	{
		return Byte.MIN_VALUE <= i && i <= Byte.MAX_VALUE;
	}

	public static boolean fitsByte(long l)
	{
		return Byte.MIN_VALUE <= l && l <= Byte.MAX_VALUE;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsByte(@SuppressWarnings("unused") Byte b)
	{
		return true;
	}

	public static boolean fitsByte(Short s)
	{
		return fitsShort(s.shortValue());
	}

	public static boolean fitsByte(Integer i)
	{
		return fitsShort(i.intValue());
	}

	public static boolean fitsByte(Long l)
	{
		return fitsShort(l.longValue());
	}

	@SuppressFBWarnings(value="ITC_INHERITANCE_TYPE_CHECKING", justification="Less complex than alternatives")
	public static boolean fitsByte(Object o)
	{
		if(o instanceof Byte)
			return true;
		if(o instanceof Short)
			return fitsByte(((Short)o).shortValue());
		if(o instanceof Integer)
			return fitsByte(((Integer)o).intValue());
		if(o instanceof Long)
			return fitsByte(((Long)o).longValue());
		return false;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsShort(@SuppressWarnings("unused") byte b)
	{
		return true;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsShort(@SuppressWarnings("unused") short s)
	{
		return true;
	}

	public static boolean fitsShort(int i)
	{
		return Short.MIN_VALUE <= i && i <= Short.MAX_VALUE;
	}

	public static boolean fitsShort(long l)
	{
		return Short.MIN_VALUE <= l && l <= Short.MAX_VALUE;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsShort(@SuppressWarnings("unused") Byte b)
	{
		return true;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsShort(@SuppressWarnings("unused") Short s)
	{
		return true;
	}

	public static boolean fitsShort(Integer i)
	{
		return fitsShort(i.intValue());
	}

	public static boolean fitsShort(Long l)
	{
		return fitsShort(l.longValue());
	}

	@SuppressFBWarnings(value="ITC_INHERITANCE_TYPE_CHECKING", justification="Less complex than alternatives")
	public static boolean fitsShort(Object o)
	{
		if(o instanceof Byte)
			return true;
		if(o instanceof Short)
			return true;
		if(o instanceof Integer)
			return fitsShort(((Integer)o).intValue());
		if(o instanceof Long)
			return fitsShort(((Long)o).longValue());
		return false;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsInt(@SuppressWarnings("unused") byte b)
	{
		return true;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsInt(@SuppressWarnings("unused") short s)
	{
		return true;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsInt(@SuppressWarnings("unused") int i)
	{
		return true;
	}

	public static boolean fitsInt(long l)
	{
		return Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsInt(@SuppressWarnings("unused") Byte b)
	{
		return true;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsInt(@SuppressWarnings("unused") Short s)
	{
		return true;
	}

	@SuppressFBWarnings(value="UP_UNUSED_PARAMETER", justification="Symetry of API")
	public static boolean fitsInt(@SuppressWarnings("unused") Integer i)
	{
		return true;
	}

	public static boolean fitsInt(Long l)
	{
		return fitsInt(l.longValue());
	}

	@SuppressFBWarnings(value="ITC_INHERITANCE_TYPE_CHECKING", justification="Less complex than alternatives")
	public static boolean fitsInt(Object o)
	{
		if(o instanceof Byte)
			return true;
		if(o instanceof Short)
			return true;
		if(o instanceof Integer)
			return true;
		if(o instanceof Long)
			return fitsInt(((Long)o).longValue());
		return false;
	}
}
