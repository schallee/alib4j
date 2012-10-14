package net.darkmist.alib.escape;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

interface StrMaker
{
	public String makeStr(int code);
	public Appendable appendStr(Appendable appendable, int code) throws IOException;
	public StringBuilder appendStr(StringBuilder sb, int code);
	public StringBuffer appendStr(StringBuffer sb, int code);

	abstract class Abstract implements StrMaker
	{
		@Override
		public String makeStr(int code)
		{
			return appendStr(new StringBuilder(), code).toString();
		}

		@Override
		public StringBuilder appendStr(StringBuilder sb, int code)
		{
			try
			{
				appendStr((Appendable)sb, code);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException(Util.STRING_BUILDER_IO_EXCEPTION, e);
			}
		}

		@Override
		public StringBuffer appendStr(StringBuffer sb, int code)
		{
			try
			{
				appendStr((Appendable)sb, code);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException(Util.STRING_BUFFER_IO_EXCEPTION, e);
			}
		}
	}

	// note cache is on the instance level not static!
	abstract class PreCachedSingletonAbstract extends Abstract
	{
		private final List<String> cache;

		protected PreCachedSingletonAbstract()
		{
			int size;
			String[] cacheArray;

			// we want the class name of the descentant so use this
			if((size = Util.getPositiveIntProp(this.getClass(), Util.getCacheSizePropName(), Util.getCacheSizeDefault()))==0)
			{
				cache = Collections.emptyList();
				return;
			}
			cacheArray = new String[size];
			for(int i=0;i<size;i++)
				cacheArray[i] = makeStrNoCache(i).intern();
			cache = Collections.unmodifiableList(Arrays.asList(cacheArray));
		}

		protected abstract Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException;

		protected String makeStrNoCache(int ch)
		{
			try
			{
				return appendStrNoCache(new StringBuilder(), ch).toString();
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
}
