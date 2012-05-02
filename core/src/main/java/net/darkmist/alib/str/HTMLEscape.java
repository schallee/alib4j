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

import org.apache.commons.lang.StringEscapeUtils;

/** @deprecated Use {@link org.apache.commons.lang.StringEscapeUtils} instead. */
@Deprecated
public abstract class HTMLEscape
{
	private HTMLEscape()
	{
	}

	/** @deprecated Use {@link org.apache.commons.lang.StringEscapeUtils#escapeHtml(java.lang.String)} instead. */
	// FIXME: we don't trust either of these...
	@Deprecated
	public static String escape(String in)
	{
		return StringEscapeUtils.escapeHtml(in);
		/*
		int i;
		StringBuilder ret = new StringBuilder(in.length());

		for(i=0;i<in.length();i++)
		{
			char ch = in.charAt(i);
			if('A' <= ch && ch <= 'Z')
				ret.append(ch);
			else if('a' <= ch && ch <= 'z')
				ret.append(ch);
			else if('0' <= ch && ch <= '9')
				ret.append(ch);
			else
				switch(ch)
				{
					case '_':
					case '-':
					case ' ':
					case '\t':
						ret.append(ch);
						break;
					default:
						ret.append(String.format("&#%04X;", Character.codePointAt(in,i)));
				}
		}
		return ret.toString();
		*/
	}
}
