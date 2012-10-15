package net.darkmist.alib.escape;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BackSlashXHexMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<BackSlashXHexMaker> CLASS = BackSlashXHexMaker.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final BackSlashXHexMaker SINGLETON = new BackSlashXHexMaker();
	// not static to avoid initialization races...
	private final StrMaker TWO_CHAR_HEX = TwoCharHexMaker.instance();

	private BackSlashXHexMaker()
	{
		makeCache();
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
