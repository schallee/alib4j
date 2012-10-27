package net.darkmist.alib.escape;

import java.io.IOException;

class CSSEscapeMaker extends StrMaker.PreCachedSingletonAbstract
{
	@SuppressWarnings("unused")
	private static final Class<CSSEscapeMaker> CLASS = CSSEscapeMaker.class;
	private static final CSSEscapeMaker SINGLETON = new CSSEscapeMaker();

	private CSSEscapeMaker()
	{
		makeCache();
	}

	static CSSEscapeMaker instance()
	{
		return SINGLETON;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		// FUTURE: if we knew the next char wasn't white space
		// or a hex digit we could avoid appending it here.
		return appendable.append("\\").append(Integer.toHexString(ch)).append(' ');
	}
}
