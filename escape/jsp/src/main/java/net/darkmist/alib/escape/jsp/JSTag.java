package net.darkmist.alib.escape.jsp;

import net.darkmist.alib.escape.JS;

public class JSTag extends BaseTag
{
	private static final JS escaper = JS.instance();

	public JSTag()
	{
		super(escaper);
	}

	public static String escape(String in)
	{
		return escaper.escape(in);
	}
}
