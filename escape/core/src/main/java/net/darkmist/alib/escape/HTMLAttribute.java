package net.darkmist.alib.escape;

import java.io.IOException;

public class HTMLAttribute extends XMLAttribute
{
	private static final HTMLAttribute SINGLETON = new HTMLAttribute();
 
	private HTMLAttribute()
	{
	}

	public static HTMLAttribute instance()
	{
		return SINGLETON;
	}
}
