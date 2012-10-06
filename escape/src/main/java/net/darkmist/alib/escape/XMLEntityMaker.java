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
		return appendable.append("&#").append(Integer.toHexString(ch)).append(';');
	}
}
