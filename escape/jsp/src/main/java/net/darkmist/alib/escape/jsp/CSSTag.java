package net.darkmist.alib.escape.jsp;

import net.darkmist.alib.escape.CSS;

public class CSSTag extends BaseTag
{
	private static final CSS escaper = CSS.instance();

	public CSSTag()
	{
		super(escaper);
	}

	public static String escape(String in)
	{
		return escaper.escape(in);
	}
}
