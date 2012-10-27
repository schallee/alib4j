package net.darkmist.alib.escape;

import java.io.IOException;

class XMLEntityMaker extends StrMaker.PreCachedSingletonAbstract
{
	@SuppressWarnings("unused")
	private static final Class<XMLEntityMaker> CLASS = XMLEntityMaker.class;
	private static final XMLEntityMaker SINGLETON = new XMLEntityMaker();

	private XMLEntityMaker()
	{
		makeCache();
	}

	static XMLEntityMaker instance()
	{
		return SINGLETON;
	}

	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		// for this range the result is one char shorter as hex even though the x is added...
		if(1000000 <= ch && ch <= 0xfffff)
			appendable.append("&#x").append(Integer.toHexString(ch));
		else
			appendable.append("&#").append(Integer.toString(ch));
		return appendable.append(';');
	}
}
