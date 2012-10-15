package net.darkmist.alib.escape.jsp;

import net.darkmist.alib.escape.HTML;

public class HTMLTag extends BaseTag
{
	private static final HTML escaper = HTML.instance();

	public HTMLTag()
	{
		super(escaper);
	}

	public static String escape(String in)
	{
		return escaper.escape(in);
	}
}
