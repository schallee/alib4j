package net.darkmist.alib.escape.jsp;

import net.darkmist.alib.escape.HTMLAttribute;

public class HTMLAttributeTag extends BaseTag
{
	private static final HTMLAttribute escaper = HTMLAttribute.instance();

	public HTMLAttributeTag()
	{
		super(escaper);
	}

	public static String escape(String in)
	{
		return escaper.escape(in);
	}
}
