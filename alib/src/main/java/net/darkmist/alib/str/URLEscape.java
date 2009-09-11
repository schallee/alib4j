package net.darkmist.alib.str;

// never in qcomm

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

	public static final String URLEscape(int b)
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

	public static final String URLEscape(byte[] in)
	{
		StringBuilder ret = new StringBuilder(in.length);
		for(int i=0;i<in.length;i++)
		{
			ret.append(URLEscape(in[i]));
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

	public static final String URLEscape(String in)
	{
		return URLEscape(str2Bytes(in));
	}

	public static final String URLEscapeNotSlash(int b)
	{
		if((char)b == '/')
			return "/";
		return URLEscape(b);
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
