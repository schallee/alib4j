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

package net.darkmist.alib.str;

public class Octal
{
	public static final int OCT_CHAR_SIZE = 3;
	public static final int BYTE_OCT_CHARS = Byte.SIZE / OCT_CHAR_SIZE + 1;
	public static final int SHORT_OCT_CHARS = Short.SIZE / OCT_CHAR_SIZE + 1;
	public static final int INT_OCT_CHARS = Integer.SIZE / OCT_CHAR_SIZE + 1;
	public static final int LONG_OCT_CHARS = Long.SIZE / OCT_CHAR_SIZE + 1;

	public static boolean isOctal(int c)
	{
		return ('0'<=c&&c<='7');
	}

	public static byte unoctTriplet(int c)
	{
		if(isOctal(c))
			return (byte)(c-'0');
		throw new NumberFormatException("Non-octal character '" + (char)c + "' encountered.");
	}

	public static byte unoctByte(CharSequence src, int off, int len)
	{
		return (byte)unoctInt(src,off,Math.min(len,BYTE_OCT_CHARS));
	}

	public static byte unoctByte(CharSequence src, int off)
	{
		return (byte)unoctInt(src,off,Math.min(src.length() - off,BYTE_OCT_CHARS));
	}

	public static byte unoctByte(CharSequence src)
	{
		return (byte)unoctInt(src,0,Math.min(src.length(),BYTE_OCT_CHARS));
	}

	public static short unoctShort(CharSequence src, int off, int len)
	{
		return (short)unoctInt(src,off,Math.min(len,SHORT_OCT_CHARS));
	}

	public static short unoctShort(CharSequence src, int off)
	{
		return (short)unoctInt(src,off,Math.min(src.length() - off, SHORT_OCT_CHARS));
	}

	public static short unoctShort(CharSequence src)
	{
		return (short)unoctInt(src,0,Math.min(src.length(),SHORT_OCT_CHARS));
	}

	public static int unoctInt(CharSequence src, int off, int len)
	{
		int ret = 0;

		len = Math.min(len, INT_OCT_CHARS);
		for(int end=off+len;off<end;off++)
			ret= (ret<<3) | unoctTriplet(src.charAt(off));
		return ret;
	}

	public static int unoctInt(CharSequence src, int off)
	{
		return unoctInt(src, off, src.length()-off);
	}

	public static int unoctInt(CharSequence src)
	{
		return unoctInt(src, 0, src.length());
	}
}
