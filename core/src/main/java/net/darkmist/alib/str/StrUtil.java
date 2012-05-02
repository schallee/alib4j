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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StrUtil
{
	private static final Class<StrUtil> CLASS = StrUtil.class;
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CLASS);
	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private StrUtil()
	{
	}

	/**
	 * Join strings with a delimiter.
	 * @param delimiter The delimiter to use between strings.
	 * @param strs The Strings to join.
	 */
	// not deprecated because this allows vargs
	public static final String join(CharSequence delimiter, CharSequence... strs)
	{
		return StringUtils.join(strs, delimiter.toString());
	}

	/**
	 * Split a string on a delimiter.
	 * @param str The string to split.
	 * @param delim The delimiter to split on.
	 * @return The substrings of str that were seperated by delim.
	 */
	public static String[] split(String str, char delim)
	{
		List<String> strs;
		int len;
		int start,end;
		
		if(str == null)
			return EMPTY_STRING_ARRAY;
		if((len = str.length())==0)
			return new String[]{""};
		strs = new ArrayList<String>(len);
		for(start=0;start<len && (end = str.indexOf(delim, start))>=0;start=end+1)
			strs.add(str.substring(start,end));
		strs.add(str.substring(start));
		return strs.toArray(EMPTY_STRING_ARRAY);
	}

	/**
	 * Replace occurences of oen string with another.
	 * @param src The string to look for match in
	 * @param match The string to look for
	 * @param replacement The string to replace match with
	 * @return src with occurances of match replaced with replacement
	 */
	public static final String replace(String src, String match, String replacement)
	{
		return replaceToStringBuffer(new StringBuffer(), src, match, replacement).toString();
	}

	/**
	 * Replace occurences of oen string with another appending
	 * 	the result.
	 * @param sb The StringBuffer to append to
	 * @param src The string to look for match in
	 * @param match The string to look for
	 * @param replacement The string to replace match with
	 * @return sb with src appended with occurances of match replaced
	 * 	with replacement
	 * FIXME: convert to Appendable with wrapped StringBuilder &amp; StringBuffer
	 */
	private static final StringBuffer replaceToStringBuffer(StringBuffer sb, String src, String match, String replacement)
	{
		int pos;
		int off=0;

		while((pos=src.indexOf(match,off))>=0)
		{
			sb.append(src.substring(off,pos));
			sb.append(replacement);
			off=pos+match.length();
		}
		if(off<src.length())
			sb.append(src.substring(off));
		return sb;
	}
}
