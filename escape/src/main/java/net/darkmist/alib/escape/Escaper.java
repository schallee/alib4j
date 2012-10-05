package net.darkmist.alib.escape;

import java.io.IOException;

public interface Escaper
{
	public String escape(int ch);
	public String escape(String str);
	public StringBuilder escape(StringBuilder sb, String str);
	public StringBuilder escape(StringBuilder sb, int ch);
	public StringBuffer escape(StringBuffer sb, String str);
	public StringBuffer escape(StringBuffer sb, int ch);
	public Appendable escape(Appendable appendable, int ch) throws IOException;
	public Appendable escape(Appendable appendable, String str) throws IOException;

	public abstract class Abstract implements Escaper
	{
		@Override
		public String escape(int ch)
		{
			return escape(new StringBuilder(ch).toString());
		}

		@Override
		public String escape(String str)
		{
			return escape(new StringBuilder(str.length()), str).toString();
		}

		@Override
		public StringBuilder escape(StringBuilder sb, String str)
		{
			try
			{
				escape((Appendable)sb, str);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuilder cast to Appendable?", e);
			}
		}

		@Override
		public StringBuilder escape(StringBuilder sb, int ch)
		{
			try
			{
				escape((Appendable)sb, ch);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuilder cast to Appendable?", e);
			}
		}

		@Override
		public StringBuffer escape(StringBuffer sb, String str)
		{
			try
			{
				escape((Appendable)sb, str);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuilder cast to Appendable?", e);
			}
		}

		@Override
		public StringBuffer escape(StringBuffer sb, int ch)
		{
			try
			{
				escape((Appendable)sb, ch);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuilder cast to Appendable?", e);
			}
		}

		@Override
		public Appendable escape(Appendable appendable, int ch) throws IOException
		{
			return appendable.append(escape(ch));
		}

		@Override
		public Appendable escape(Appendable appendable, String str) throws IOException
		{
			int codePoints = str.codePointCount(0, str.length());

			for(int i=0; i<codePoints; i++)
				escape(appendable, str.codePointAt(i));
			return appendable;
		}
	}
}
