package net.darkmist.alib.escape;

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
