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

import java.nio.charset.Charset;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
public abstract class URLEscape
{
	private static final Class<URLEscape> CLASS = URLEscape.class;
        private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final Charset UTF8 = getUTF8Charset();

	@SuppressFBWarnings(value={"WEM_WEAK_EXCEPTION_MESSAGING","EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS"}, justification="Boolean state")
	private static Charset getUTF8Charset()
	{
		return Charset.forName("UTF-8");
	}

	private static class URLByte2StrHolder
	{
		private static final Class<URLByte2StrHolder> CLASS = URLByte2StrHolder.class;
        	private static final Logger logger = LoggerFactory.getLogger(CLASS);
		static final List<String> urlByte2Str = mkUrlByte2Str();

		private static List<String> mkUrlByte2Str()
		{
			String[] array = new String[256];

			for(int ch=0;ch<256;ch++)
			{
				if(logger.isDebugEnabled())
					logger.debug("ch={}", ch);
				if(('A' <= ch && ch <= 'Z')||
					('a' <= ch && ch <= 'z') ||
					('0' <= ch && ch <= '9') ||
					ch == '_' ||
					ch == '-' ||
					ch == '.' ||
					ch == '~')
				{
					array[ch&0xff] = Character.toString((char)ch);
					if(logger.isDebugEnabled())
						logger.debug("ch={} didn't need escapoing array[ch]={}", ch, array[ch]);
				}
				else if(ch == ' ')
				{
					array[ch&0xff] = "+";
					if(logger.isDebugEnabled())
						logger.debug("ch={} space replacement array[ch]={}", ch, array[ch]);
				}
				else
				{
					array[ch&0xff] = String.format("%%%02X", ch);
					if(logger.isDebugEnabled())
						logger.debug("ch={} hex replacement array[ch]={}", ch, array[ch]);
				}
			}
			return Collections.unmodifiableList(Arrays.asList(array));
		}
	}

	private static class URLByte2StrNotSlashHolder
	{
		static final List<String> urlByte2StrNotSlash = mkUrlByte2StrNotSlash();

		private static List<String> mkUrlByte2StrNotSlash()
		{
			String[] array = URLByte2StrHolder.urlByte2Str.toArray(new String[256]);

			array['/'&0xff]="/";
			return Collections.unmodifiableList(Arrays.asList(array));
		}
	}

	private URLEscape()
	{
	}

	public static final String escape(int b)
	{
		return URLByte2StrHolder.urlByte2Str.get(b&0xff);
	}

	public static final String escape(byte[] in)
	{
		StringBuilder ret = new StringBuilder(in.length);
		for(int i=0;i<in.length;i++)
		{
			ret.append(escape(in[i]));
			logger.debug("ret={}", ret);
		}
		return ret.toString();
	}

	private static final byte[] str2Bytes(String in)
	{
		return in.getBytes(UTF8);
	}

	public static final String escape(String in)
	{
		return escape(str2Bytes(in));
	}

	public static final String URLEscapeNotSlash(int b)
	{
		return URLByte2StrNotSlashHolder.urlByte2StrNotSlash.get(b&0xff);
	}

	public static final String URLEscapeNotSlash(byte[] in)
	{
		StringBuilder ret = new StringBuilder(in.length);
		for(int i=0;i<in.length;i++)
		{
			ret.append(URLEscapeNotSlash(in[i]));
			logger.debug("ret={}", ret);
		}
		return ret.toString();
	}

	public static final String URLEscapeNotSlash(String in)
	{
		return URLEscapeNotSlash(str2Bytes(in));
	}
}
