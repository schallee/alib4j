package net.darkmist.alib.escape;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class XMLEntityMaker extends StrMaker.Abstract
{
	private static final Class<XMLEntityMaker> CLASS = XMLEntityMaker.class;
	private static final XMLEntityMaker SINGLETON = new XMLEntityMaker();
	private final List<String> cache;

	private XMLEntityMaker()
	{
		int size;
		String[] cacheArray;

		if((size = Util.getPositiveIntProp(CLASS, Util.getCacheSizePropName(), Util.getCacheSizeDefault()))==0)
		{
			cache = Collections.emptyList();
			return;
		}
		cacheArray = new String[size];
		for(int i=0;i<size;i++)
			cacheArray[i] = makeStrNoCache(i).intern();
		cache = Collections.unmodifiableList(Arrays.asList(cacheArray));
	}

	static XMLEntityMaker instance()
	{
		return SINGLETON;
	}

	private static String makeStrNoCache(int ch)
	{
		return appendStrNoCache(new StringBuilder(), ch).toString();
	}

	private static Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		if(1000000 <= ch && ch <= 0xfffff)
			appendable.append("&#x").append(Integer.toHexString(ch));
		else
			appendable.append("&#").append(Integer.toString(ch));
		return appendable.append(';');
	}

	private static StringBuilder appendStrNoCache(StringBuilder sb, int ch)
	{
		try
		{
			appendStrNoCache((Appendable)sb, ch);
			return sb;
		}
		catch(IOException e)
		{
			throw new IllegalStateException(Util.STRING_BUILDER_IO_EXCEPTION, e);
		}
	}

	@Override
	public String makeStr(int ch)
	{
		if(ch < 0)
			throw new IllegalArgumentException("Character code was less than zero");
		if(ch<cache.size())
			return cache.get(ch);
		return makeStrNoCache(ch);
	}

	@Override
	public Appendable appendStr(Appendable appendable, int ch) throws IOException
	{
		if(ch < 0)
			throw new IllegalArgumentException("Character code was less than zero");
		if(ch<cache.size())
			return appendable.append(cache.get(ch));
		return appendStrNoCache(appendable, ch);
	}
}
