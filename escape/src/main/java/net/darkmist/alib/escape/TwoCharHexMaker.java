package net.darkmist.alib.escape;

import java.io.IOException;

class TwoCharHexMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<TwoCharHexMaker> CLASS = TwoCharHexMaker.class;
	private static final TwoCharHexMaker SINGLETON = new TwoCharHexMaker();

	private TwoCharHexMaker()
	{
	}

	static TwoCharHexMaker instance()
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
			appendable.append('0');
		return appendable.append(Integer.toHexString(ch));
	}
}
