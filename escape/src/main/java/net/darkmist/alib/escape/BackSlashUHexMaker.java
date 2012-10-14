
package net.darkmist.alib.escape;

import java.io.IOException;

class BackSlashUHexMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<BackSlashUHexMaker> CLASS = BackSlashUHexMaker.class;
	private static final BackSlashUHexMaker SINGLETON = new BackSlashUHexMaker();
	private static final StrMaker FOUR_CHAR_HEX = FourCharHexMaker.instance();

	private BackSlashUHexMaker()
	{
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
		if(ch > 0xFF)
			throw new IllegalArgumentException("Char code is too large for two hex digits.");
		return FOUR_CHAR_HEX.appendStr(appendable.append("\\u"), ch);
	}
}
