package net.darkmist.alib.escape;

import java.io.IOException;

public class HTML extends XML
{
	private static final HTML SINGLETON = new HTML();
 
	private HTML()
	{
	}

	public static HTML instance()
	{
		return SINGLETON;
	}
}
