package net.darkmist.alib.escape;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

class Charsets
{
	public static final Charset UTF8;

	static
	{	// why can't the platform just provide some constants for required charsets?
		try
		{
			UTF8 = Charset.forName("UTF-8");
		}
		catch(UnsupportedCharsetException e)
		{
			throw new IllegalStateException("Java platform requires UTF-8 support but a UnsupportedCharsetException was thrown when trying to use it.", e);
		}
	}
}
