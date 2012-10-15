
package net.darkmist.alib.escape;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BackSlashUHexMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<BackSlashUHexMaker> CLASS = BackSlashUHexMaker.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final BackSlashUHexMaker SINGLETON = new BackSlashUHexMaker();
	// not static to avoid initialization races...
	private final StrMaker FOUR_CHAR_HEX = FourCharHexMaker.instance();

	private BackSlashUHexMaker()
	{
		makeCache();
	}

	static BackSlashUHexMaker instance()
	{
		return SINGLETON;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		if(ch < 0)
			throw new IllegalArgumentException("Negative code point.");
		if(ch > 0xFFFF)
			throw new IllegalArgumentException("Char code is too large for four hex digits.");
		return FOUR_CHAR_HEX.appendStr(appendable.append("\\u"), ch);
	}
}
