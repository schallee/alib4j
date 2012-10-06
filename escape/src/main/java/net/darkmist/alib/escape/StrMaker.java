package net.darkmist.alib.escape;

import java.io.IOException;

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

	abstract class CachingAbstract extends Abstract
	{
		private final StrCache cache;

		protected CachingAbstract(StrMaker maker)
		{
			cache = new StrCache(maker);
		}

		protected StrCache getCache()
		{
			return cache;
		}

		@Override
		public String makeStr(int code)
		{
			return cache.getOrMake(code);
		}

		@Override
		public Appendable appendStr(Appendable appendable, int code) throws IOException
		{
			return appendable.append(cache.getOrMake(code));
		}
	}
}
