package net.darkmist.alib.escape;

import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Escaper
{
	public String escape(int ch);
	public String escape(String str);

	// StringBuilder like methods
	public StringBuilder escape(StringBuilder sb, int ch);
	public StringBuilder escape(StringBuilder sb, String str);
	public StringBuilder escape(StringBuilder sb, String str, int off, int len);
	public StringBuilder escape(StringBuilder sb, CharSequence str);
	public StringBuilder escape(StringBuilder sb, CharSequence str, int off, int len);
	public StringBuilder escape(StringBuilder sb, char[] chars);
	public StringBuilder escape(StringBuilder sb, char[] chars, int off, int len);

	// StringBuffer like methods
	public StringBuffer escape(StringBuffer sb, int ch);
	public StringBuffer escape(StringBuffer sb, String str);
	public StringBuffer escape(StringBuffer sb, String str, int off, int len);
	public StringBuffer escape(StringBuffer sb, CharSequence str);
	public StringBuffer escape(StringBuffer sb, CharSequence str, int off, int len);
	public StringBuffer escape(StringBuffer sb, char[] chars);
	public StringBuffer escape(StringBuffer sb, char[] chars, int off, int len);

	// Appendable like methods
	public Appendable escape(Appendable appendable, int ch) throws IOException;
	public Appendable escape(Appendable appendable, String str) throws IOException;
	public Appendable escape(Appendable appendable, String str, int off, int len) throws IOException;
	public Appendable escape(Appendable appendable, CharSequence str) throws IOException;
	public Appendable escape(Appendable appendable, CharSequence str, int start, int end) throws IOException;
	public Appendable escape(Appendable appendable, char[] chars) throws IOException;
	public Appendable escape(Appendable appendable, char[] chars, int off, int len) throws IOException;


	/**
	 * Wrap a writer escaping all output
	 * @param out The writer to wrap.
	 * @return A writer that escapes all output and then writes it
	 * 	to out.
	 */
	public Writer escape(Writer out);

	public abstract class Abstract implements Escaper
	{
		private static final Class<Abstract> CLASS = Abstract.class;
		@SuppressWarnings("unused")
		private static final Logger logger = LoggerFactory.getLogger(CLASS);

		@Override
		public String escape(int ch)
		{
			return escape(new StringBuilder(ch).toString());
		}

		@Override
		public String escape(String str)
		{
			return escape(new StringBuilder(), str).toString();
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
		public StringBuilder escape(StringBuilder sb, String str, int off, int len)
		{
			try
			{
				escape((Appendable)sb, str, off, len);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuilder cast to Appendable?", e);
			}
		}

		@Override
		public StringBuilder escape(StringBuilder sb, CharSequence str)
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
		public StringBuilder escape(StringBuilder sb, CharSequence str, int off, int len)
		{
			try
			{
				escape((Appendable)sb, str, off, len);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuilder cast to Appendable?", e);
			}
		}

		@Override
		public StringBuilder escape(StringBuilder sb, char[] chars)
		{
			try
			{
				escape((Appendable)sb, chars);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuilder cast to Appendable?", e);
			}
		}

		@Override
		public StringBuilder escape(StringBuilder sb, char[] chars, int off, int len)
		{
			try
			{
				escape((Appendable)sb, chars, off, len);
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
				throw new IllegalStateException("IOException appending to StringBuffer cast to Appendable?", e);
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
				throw new IllegalStateException("IOException appending to StringBuffer cast to Appendable?", e);
			}
		}

		@Override
		public StringBuffer escape(StringBuffer sb, String str, int off, int len)
		{
			try
			{
				escape((Appendable)sb, str, off, len);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuffer cast to Appendable?", e);
			}
		}

		@Override
		public StringBuffer escape(StringBuffer sb, CharSequence str)
		{
			try
			{
				escape((Appendable)sb, str);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuffer cast to Appendable?", e);
			}
		}

		@Override
		public StringBuffer escape(StringBuffer sb, CharSequence str, int off, int len)
		{
			try
			{
				escape((Appendable)sb, str, off, len);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuffer cast to Appendable?", e);
			}
		}

		@Override
		public StringBuffer escape(StringBuffer sb, char[] chars)
		{
			try
			{
				escape((Appendable)sb, chars);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuffer cast to Appendable?", e);
			}
		}

		@Override
		public StringBuffer escape(StringBuffer sb, char[] chars, int off, int len)
		{
			try
			{
				escape((Appendable)sb, chars, off, len);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException("IOException appending to StringBuffer cast to Appendable?", e);
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
			return escape(appendable, str, 0, str.length());
		}

		@Override
		public Appendable escape(Appendable appendable, String str, int off, int len) throws IOException
		{
			int end = off + len;
			int codePoints = str.codePointCount(off, end);
			int codePointsBegin = str.codePointCount(0,off);
			int codePointsEnd = codePointsBegin + codePoints;

			//if(logger.isDebugEnabled())
				//logger.debug("off=" + off + " len=" + len + " end=" + end);
			for(int i=codePointsBegin; i<codePointsEnd; i++)
				escape(appendable, str.codePointAt(i));
			return appendable;
		}

		@Override
		public Appendable escape(Appendable appendable, CharSequence str) throws IOException
		{
			// we need codePointAt so to string it
			return escape(appendable, str.toString());
		}

		@Override
		public Appendable escape(Appendable appendable, CharSequence str, int start, int end) throws IOException
		{
			// we need codePointAt so to string it
			return escape(appendable, str.toString(), start, end);
		}

		@Override
		public Appendable escape(Appendable appendable, char[] chars) throws IOException
		{
			// we need codePointAt so to string it
			return escape(appendable, String.valueOf(chars));
		}

		@Override
		public Appendable escape(Appendable appendable, char[] chars, int off, int len) throws IOException
		{
			// we need codePointAt so to string it
			return escape(appendable, String.valueOf(chars, off, len));
		}

		@Override
		public Writer escape(Writer out)
		{
			return new EscapingWriter(out, this);
		}
	}
}
