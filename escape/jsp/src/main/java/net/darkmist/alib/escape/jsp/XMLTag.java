package net.darkmist.alib.escape.jsp;

import net.darkmist.alib.escape.XML;

public class XMLTag extends BaseTag
{
	private static final XML escaper = XML.instance();

	public XMLTag()
	{
		super(escaper);
	}

	public static String escape(String in)
	{
		return escaper.escape(in);
	}
}
