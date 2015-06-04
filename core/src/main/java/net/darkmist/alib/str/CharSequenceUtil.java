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

/**
 * Utils for {@link CharSequence}s.
 */
public class CharSequenceUtil
{
	/**
	 * Only static methods so no instantiation.
	 */
	private CharSequenceUtil()
	{
	}

	/**
	 * Trim horizontal whitespace from left side.
	 * @param str The string to trim
	 * @return str with whitespace from left side trimmed.
	 */
	public static CharSequence ltrim(CharSequence str)
	{
		int len;
		int i;

		if(str == null || (len = str.length()) <= 0)
			return "";
		for(i=0;i<len;i++)
			if(!Character.isWhitespace(str.charAt(i)))
				break;
		if(i>=len)
			return "";
		return str.subSequence(i, len);
	}

	/**
	 * Trim horizontal whitespace from right side.
	 * @param str The string to trim
	 * @return str with whitespace from right side trimmed.
	 */
	public static CharSequence rtrim(CharSequence str)
	{
		int len;
		int i;

		if(str == null || (len = str.length()) <= 0)
			return "";
		for(i=len-1;i>=0;i--)
			if(!Character.isWhitespace(str.charAt(i)))
				break;
		if(i<0)
			return "";
		return str.subSequence(0, i+1);
	}

	/**
	 * Find index of first occurance of a character. This is different
	 * from {@link String#indexOf(String)} in that it works on
	 * {@link CharSequence}s and looks for a single character
	 * instead of a string.
	 * @param str haystack
	 * @param ch needle
	 * @return position of needle in haystack or -1 if not found.
	 */
	public static int indexOf(CharSequence str, char ch)
	{
		for(int i=0; i<str.length(); i++)
			if(str.charAt(i) == ch)
				return i;
		return -1;
	}
}
