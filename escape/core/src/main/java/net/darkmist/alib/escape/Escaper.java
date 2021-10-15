/*
 *  Copyright (C) 2012 Ed Schaller <schallee@darkmist.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.darkmist.alib.escape;

import java.io.IOException;
import java.io.Writer;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressFBWarnings(value={"EXS_EXCEPTION_SOFTENING_NO_CHECKED","WEM_WEAK_EXCEPTION_MESSAGING"}, justification="Boolean state that shouldn't happen")
public interface Escaper
{
	public String escape(int ch);
	public String escape(String str);

	// StringBuilder like methods
	@CanIgnoreReturnValue
	public StringBuilder escape(StringBuilder sb, int ch);
	@CanIgnoreReturnValue
	public StringBuilder escape(StringBuilder sb, String str);
	@CanIgnoreReturnValue
	public StringBuilder escape(StringBuilder sb, String str, int off, int len);
	@CanIgnoreReturnValue
	public StringBuilder escape(StringBuilder sb, CharSequence str);
	@CanIgnoreReturnValue
	public StringBuilder escape(StringBuilder sb, CharSequence str, int off, int len);
	@CanIgnoreReturnValue
	public StringBuilder escape(StringBuilder sb, char[] chars);
	@CanIgnoreReturnValue
	public StringBuilder escape(StringBuilder sb, char[] chars, int off, int len);

	// StringBuffer like methods
	@CanIgnoreReturnValue
	public StringBuffer escape(StringBuffer sb, int ch);
	@CanIgnoreReturnValue
	public StringBuffer escape(StringBuffer sb, String str);
	@CanIgnoreReturnValue
	public StringBuffer escape(StringBuffer sb, String str, int off, int len);
	@CanIgnoreReturnValue
	public StringBuffer escape(StringBuffer sb, CharSequence str);
	@CanIgnoreReturnValue
	public StringBuffer escape(StringBuffer sb, CharSequence str, int off, int len);
	@CanIgnoreReturnValue
	public StringBuffer escape(StringBuffer sb, char[] chars);
	@CanIgnoreReturnValue
	public StringBuffer escape(StringBuffer sb, char[] chars, int off, int len);

	// Appendable like methods
	@CanIgnoreReturnValue
	public Appendable escape(Appendable appendable, int ch) throws IOException;
	@CanIgnoreReturnValue
	public Appendable escape(Appendable appendable, String str) throws IOException;
	@CanIgnoreReturnValue
	public Appendable escape(Appendable appendable, String str, int off, int len) throws IOException;
	@CanIgnoreReturnValue
	public Appendable escape(Appendable appendable, CharSequence str) throws IOException;
	@CanIgnoreReturnValue
	public Appendable escape(Appendable appendable, CharSequence str, int start, int end) throws IOException;
	@CanIgnoreReturnValue
	public Appendable escape(Appendable appendable, char[] chars) throws IOException;
	@CanIgnoreReturnValue
	public Appendable escape(Appendable appendable, char[] chars, int off, int len) throws IOException;


	/**
	 * Wrap a writer escaping all output
	 * @param out The writer to wrap.
	 * @return A writer that escapes all output and then writes it
	 * 	to out.
	 */
	@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
		@Override
		public Appendable escape(Appendable appendable, int ch) throws IOException
		{
			return appendable.append(escape(ch));
		}

		@CanIgnoreReturnValue
		@Override
		public Appendable escape(Appendable appendable, String str) throws IOException
		{
			return escape(appendable, str, 0, str.length());
		}

		@CanIgnoreReturnValue
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

		@CanIgnoreReturnValue
		@Override
		public Appendable escape(Appendable appendable, CharSequence str) throws IOException
		{
			// we need codePointAt so to string it
			return escape(appendable, str.toString());
		}

		@CanIgnoreReturnValue
		@Override
		public Appendable escape(Appendable appendable, CharSequence str, int start, int end) throws IOException
		{
			// we need codePointAt so to string it
			return escape(appendable, str.toString(), start, end);
		}

		@CanIgnoreReturnValue
		@Override
		public Appendable escape(Appendable appendable, char[] chars) throws IOException
		{
			// we need codePointAt so to string it
			return escape(appendable, String.valueOf(chars));
		}

		@CanIgnoreReturnValue
		@Override
		public Appendable escape(Appendable appendable, char[] chars, int off, int len) throws IOException
		{
			// we need codePointAt so to string it
			return escape(appendable, String.valueOf(chars, off, len));
		}

		@CanIgnoreReturnValue
		@Override
		public Writer escape(Writer out)
		{
			return new EscapingWriter(out, this);
		}
	}
}
