package net.darkmist.alib.escape.jsp;

import net.darkmist.alib.escape.XMLAttribute;

public class XMLAttributeTag extends BaseTag
{
	private static final XMLAttribute escaper = XMLAttribute.instance();

	public XMLAttributeTag()
	{
		super(escaper);
	}

	public static String escape(String in)
	{
		return escaper.escape(in);
	}
}
