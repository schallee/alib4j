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

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class URLEscape
{
	private static final Class<URLEscape> CLASS = URLEscape.class;
        private static final Log logger = LogFactory.getLog(CLASS);

	private static String urlByte2Str[] = new String[256];
	private URLEscape()
	{
	}

	public static final String escape(int b)
	{
		String ret;

		while(b<0)
			b+=256;
		if((ret = urlByte2Str[b]) != null)
			return ret;
		synchronized(URLEscape.class)
		{
			// try it again now that we are synced
			if((ret = urlByte2Str[b])!=null)
				return ret;	// got changed before we synced...
			// there has to be a better way to do this...
			//ch = new String(new byte[]{b}, "US-ASCII").charAt(0);
			// This seems to work... worries me though
			char ch = (char)b;
			logger.debug("b=" + b + "= ch=" + ch + "=");
			if(('A' <= ch && ch <= 'Z')||
				('a' <= ch && ch <= 'z') ||
				('0' <= ch && ch <= '9') ||
				ch == '_' ||
				ch == '-' ||
				ch == '.' ||
				ch == '~')
			{
				ret = new String(Character.toString(ch));
				logger.debug("didn't need escaping ret=" + ret + "=");
			}
			else if(ch == ' ')
			{
				ret = "+";
				logger.debug("Space needing replacement ret=" + ret + "=");
			}
			else
			{
				ret = String.format("%%%02X", b);
				logger.debug("Needied percent escape ret=" + ret + "=");
			}
			return (urlByte2Str[b] = ret);
		}
	}

	public static final String escape(byte[] in)
	{
		StringBuilder ret = new StringBuilder(in.length);
		for(int i=0;i<in.length;i++)
		{
			ret.append(escape(in[i]));
			logger.debug("ret=" + ret + "=");
		}
		return ret.toString();
	}

	private static final byte[] str2Bytes(String in)
	{
		try
		{
			return in.getBytes("UTF-8");
		}
		catch(UnsupportedEncodingException e)
		{
			throw new IllegalStateException("JVM Spec requires support for UTF-8 but getBytes(\"UTF-8\") threw a UnsupportedEncodingException",e);
		}
	}

	public static final String escape(String in)
	{
		return escape(str2Bytes(in));
	}

	public static final String URLEscapeNotSlash(int b)
	{
		if((char)b == '/')
			return "/";
		return escape(b);
	}

	public static final String URLEscapeNotSlash(byte[] in)
	{
		StringBuilder ret = new StringBuilder(in.length);
		for(int i=0;i<in.length;i++)
		{
			ret.append(URLEscapeNotSlash(in[i]));
			logger.debug("ret=" + ret + "=");
		}
		return ret.toString();
	}

	public static final String URLEscapeNotSlash(String in)
	{
		return URLEscapeNotSlash(str2Bytes(in));
	}
}
