package net.darkmist.alib.escape;

import java.io.IOException;

class BackSlashXHexMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<BackSlashXHexMaker> CLASS = BackSlashXHexMaker.class;
	private static final BackSlashXHexMaker SINGLETON = new BackSlashXHexMaker();
	private static final StrMaker TWO_CHAR_HEX = TwoCharHexMaker.instance();

	private BackSlashXHexMaker()
	{
	}

	static BackSlashXHexMaker instance()
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
		return TWO_CHAR_HEX.appendStr(appendable.append("\\x"), ch);
	}
}
