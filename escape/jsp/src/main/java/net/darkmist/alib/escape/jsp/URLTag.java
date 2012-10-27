package net.darkmist.alib.escape.jsp;

import net.darkmist.alib.escape.URL;

public class URLTag extends BaseTag
{
	private static final URL escaper = URL.instance();

	public URLTag()
	{
		super(escaper);
	}

	public static String escape(String in)
	{
		return escaper.escape(in);
	}
}
