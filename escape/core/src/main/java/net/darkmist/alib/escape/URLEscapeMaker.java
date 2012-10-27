package net.darkmist.alib.escape;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class URLEscapeMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<URLEscapeMaker> CLASS = URLEscapeMaker.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final URLEscapeMaker SINGLETON = new URLEscapeMaker();
	// not static to avoid initialization races...
	private final StrMaker HEXER = TwoCharHexMaker.instance();

	private URLEscapeMaker()
	{
		makeCache();
	}

	static URLEscapeMaker instance()
	{
		return SINGLETON;
	}

	private Appendable appendSingleByteEscape(Appendable appendable, int ch) throws IOException
	{
		return HEXER.appendStr(appendable.append('%'), ch);
	}

	private Appendable appendMultiByteEscape(Appendable appendable, int ch) throws IOException
	{
		int[] codePointStr = new int[1];
		String str;

		codePointStr[0] = ch;
		str = new String(codePointStr,0,1);
		for(int b : str.getBytes(Charsets.UTF8))
			appendSingleByteEscape(appendable, b & 0xFF);
		return appendable;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		if(ch < 0)
			throw new IllegalArgumentException("Negative valued character is invalid.");
		if(ch < 0x100)
			return appendSingleByteEscape(appendable, ch);
		return appendMultiByteEscape(appendable, ch);
	}
}
