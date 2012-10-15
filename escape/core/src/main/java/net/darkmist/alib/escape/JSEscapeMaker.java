package net.darkmist.alib.escape;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JSEscapeMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<JSEscapeMaker> CLASS = JSEscapeMaker.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final JSEscapeMaker SINGLETON = new JSEscapeMaker();
	// not static to avoid initialization races...
	private final StrMaker BACKSLASH_X_HEX = BackSlashXHexMaker.instance();
	// not static to avoid initialization races...
	private final StrMaker BACKSLASH_U_HEX = BackSlashUHexMaker.instance();

	private JSEscapeMaker()
	{
		makeCache();
	}

	static JSEscapeMaker instance()
	{
		return SINGLETON;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		if(ch < 0)
			throw new IllegalArgumentException("Negative code point");
		if(ch < 0x100)
			return BACKSLASH_X_HEX.appendStr(appendable, ch);
		if(ch < 0x10000)
			return BACKSLASH_U_HEX.appendStr(appendable, ch);
		throw new IllegalArgumentException("JavaScript/ECMAScript does not define how code points greater than 0xFFFF are handled.");
	}
}
