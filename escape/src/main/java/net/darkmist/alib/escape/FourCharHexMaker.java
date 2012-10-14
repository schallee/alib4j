package net.darkmist.alib.escape;

import java.io.IOException;

class FourCharHexMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<FourCharHexMaker> CLASS = FourCharHexMaker.class;
	private static final FourCharHexMaker SINGLETON = new FourCharHexMaker();

	private FourCharHexMaker()
	{
	}

	static FourCharHexMaker instance()
	{
		return SINGLETON;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		if(ch < 0)
			throw new IllegalArgumentException("Negative code point.");
		if(ch > 0xFF)
			throw new IllegalArgumentException("Char code is too large for two hex digits.");
		if(ch < 0x10)
			appendable.append("000");
		else if(ch < 0x100)
			appendable.append("00");
		else if(ch < 0x1000)
			appendable.append('0');
		return appendable.append(Integer.toHexString(ch));
	}
}
