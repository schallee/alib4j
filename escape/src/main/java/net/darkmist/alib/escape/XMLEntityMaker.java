package net.darkmist.alib.escape;

import java.io.IOException;

class XMLEntityMaker extends StrMaker.Abstract
{
	private static final XMLEntityMaker SINGLETON = new XMLEntityMaker();

	private XMLEntityMaker()
	{
	}

	static XMLEntityMaker instance()
	{
		return SINGLETON;
	}

	@Override
	public Appendable appendStr(Appendable appendable, int ch) throws IOException
	{
		if(1000000 <= ch && ch <= 0xfffff)
			appendable.append("&#x").append(Integer.toHexString(ch));
		else
			appendable.append("&#").append(Integer.toString(ch));
		return appendable.append(';');
	}
}
