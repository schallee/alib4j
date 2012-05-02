package net.darkmist.alib.str;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.darkmist.alib.str.Hex.hexByte;
import static net.darkmist.alib.str.Hex.hexShort;
import static net.darkmist.alib.str.Hex.unhexByte;
import static net.darkmist.alib.str.Hex.unhexShort;
import static net.darkmist.alib.str.Octal.isOctal;
import static net.darkmist.alib.str.Octal.unoctByte;
import static net.darkmist.alib.str.Octal.unoctShort;

public class Escape
{
	private static final char DEFAULT_ESCAPE = '\\';
	private static final boolean DEFAULT_IGNORE_ERROR = true;
	private static final char[] EMPTY_CHAR_ARRAY = new char[0];
	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	public static Appendable unescape(CharSequence src, Appendable dst, char escapeChar, boolean ignoreError) throws IOException, EscapeException
	{
		int srcLen = src.length();
		char ch;		// char from src we're looking at
		int left = srcLen;	// amount left in src
		int i;

		srcloop: for(i=0;i<srcLen;i++)	// i also incremented in loop!
		{
			ch=src.charAt(i);

			if(ch!=escapeChar)
			{
				dst.append(ch);
				continue;
			}

			// we hit the escape char... so get the next one
			i++;
			if(i>=srcLen)
				if(ignoreError)
					return dst;
				else
					throw new EscapeException("String to escape ends in unescaped escape char.");
			ch=src.charAt(i);
			switch(ch)
			{
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
					// octal bleh...
					left = srcLen - i;

					// check for just one octal
					if(!(left>=2 && isOctal(src.charAt(i+1))))
					{	// only one octal
						dst.append((char)unoctByte(src,i,1));
						continue srcloop;
					}

					// check for two octals
					if(!(left>=3 && isOctal(src.charAt(i+2))))
					{	// two octal chars
						dst.append((char)unoctByte(src,i,2));
						i++;
						continue srcloop;
					}

					// three octal chars (ha ha ha)
					//System.err.println("escaped=" + src.subSequence(i,i+3));
					dst.append((char)unoctShort(src,i,3));
					i+=2;
					continue srcloop;
				case 'a':
					dst.append('\u0007');
					continue srcloop;
				case 'b':
					dst.append('\b');
					continue srcloop;
				case 't':
					dst.append('\t');
					continue srcloop;
				case 'n':
					dst.append('\n');
					continue srcloop;
				case 'v':
					dst.append('\u000b');
					continue srcloop;
				case 'f':
					dst.append('\f');
					continue srcloop;
				case 'r':
					dst.append('\r');
					continue srcloop;
				case 'x':
				case 'X':
					// hex escape
					if(srcLen - i < 3)
						if(ignoreError)
							return dst;
						else
							throw new EscapeException("String to escape ends in truncated hex escape");
					i++;
					dst.append((char)(unhexByte(src,i,2)&0xff));
					i++;
					continue srcloop;
				case 'u':
				case 'U':
					// unicode hex escape
					if(srcLen - i < 5)
						if(ignoreError)
							return dst;
						else
							throw new EscapeException("String to escape ends in truncated unicode hex escape");
					i++;
					//System.err.println("unescaped=" + src.subSequence(i,i+4));
					//System.err.println("unhexShort=" + Integer.toHexString(unhexShort(src,i,4)) + '=');
					dst.append((char)(unhexShort(src,i,4)&0xffff));
					i+=3;
					continue srcloop;
				default:
					dst.append(ch);
					continue srcloop;
			}
		}
		return dst;
	}

	public static Appendable unescape(CharSequence src, Appendable dst, char escapeChar) throws IOException, EscapeException
	{
		return unescape(src,dst,escapeChar,DEFAULT_IGNORE_ERROR);
	}

	public static Appendable unescape(CharSequence src, Appendable dst, boolean ignoreError) throws IOException, EscapeException
	{
		return unescape(src,dst, DEFAULT_ESCAPE, ignoreError);
	}

	public static Appendable unescape(CharSequence src, Appendable dst) throws IOException, EscapeException
	{
		return unescape(src,dst, DEFAULT_ESCAPE, DEFAULT_IGNORE_ERROR);
	}

	public static String unescape(CharSequence src, char escapeChar,  boolean ignoreError) throws EscapeException
	{
		try
		{
			return unescape(src, new StringBuilder(), escapeChar, ignoreError).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String unescape(CharSequence src, char escapeChar) throws EscapeException
	{
		return unescape(src, escapeChar, DEFAULT_IGNORE_ERROR);
	}

	public static String unescape(CharSequence src, boolean ignoreError) throws EscapeException
	{
		return unescape(src, DEFAULT_ESCAPE, ignoreError);
	}

	public static String unescape(CharSequence src) throws EscapeException
	{
		return unescape(src, DEFAULT_ESCAPE, DEFAULT_IGNORE_ERROR);
	}

	public static Appendable escape(CharSequence src, Appendable dst, char[] alsoEscape, char escapeChar) throws IOException
	{
		int srcLen = src.length();
		char ch;	// char from src we're looking at
		int c;		// postive int form of ch
		int i;		// src position
		int j;		// alsoEscape position

		srcloop: for(i=0;i<srcLen;i++)
		{
			ch = src.charAt(i);

			switch(ch)
			{
				case '\007':
					dst.append(escapeChar);
					dst.append('a');
					continue srcloop;
				case '\b':
					dst.append(escapeChar);
					dst.append('b');
					continue srcloop;
				case '\t':
					dst.append(escapeChar);
					dst.append('t');
					continue srcloop;
				case '\n':
					dst.append(escapeChar);
					dst.append('n');
					continue srcloop;
				case '\013':
					dst.append(escapeChar);
					dst.append('v');
					continue srcloop;
				case '\f':
					dst.append(escapeChar);
					dst.append('f');
					continue srcloop;
				case '\r':
					dst.append(escapeChar);
					dst.append('r');
					continue srcloop;
			}
			c=((int)ch)&0xffff;	// positive ch
			if(c>0xff)
			{
				dst.append(escapeChar);
				dst.append("u");
				hexShort(dst, c);
				continue srcloop;
			}
			if(c>=0x7f||c<0x20)
			{
				dst.append(escapeChar);
				dst.append('x');
				hexByte(dst,c);
				continue srcloop;
			}
			if(ch==escapeChar)
			{
				dst.append(escapeChar);
				dst.append(escapeChar);
				continue srcloop;
			}
			for(j=0;j<alsoEscape.length;j++)
				if(ch == alsoEscape[j])
				{
					dst.append(escapeChar);
					dst.append(ch);
					continue srcloop;
				}
			dst.append(ch);
		}
		return dst;
	}

	public static Appendable escape(CharSequence src, Appendable dst, char[] alsoEscape) throws IOException
	{
		return escape(src, dst, alsoEscape, DEFAULT_ESCAPE);
	}

	public static Appendable escape(CharSequence src, Appendable dst, char escapeChar) throws IOException
	{
		return escape(src, dst, EMPTY_CHAR_ARRAY, escapeChar);
	}

	public static String escape(CharSequence src, char[] alsoEscape, char escapeChar)
	{
		try
		{
			return escape(src, new StringBuilder(), alsoEscape, escapeChar).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String escape(CharSequence src, char[] alsoEscape)
	{
		return escape(src, alsoEscape, DEFAULT_ESCAPE);
	}

	public static String escape(CharSequence src, char escapeChar)
	{
		return escape(src, EMPTY_CHAR_ARRAY, escapeChar);
	}

	public static String escape(CharSequence src)
	{
		return escape(src, EMPTY_CHAR_ARRAY, DEFAULT_ESCAPE);
	}

	public static String[] splitUnescaped(CharSequence src, int off, int len, char delim, char escapeChar)
	{
		boolean lastWasEscape = false;
		int start=off;
		String part;
		List<String> parts = new ArrayList<String>(len/2);
		for(int end=off+len;off<end;off++)
		{
			int c = src.charAt(off);
			//System.err.println("c=" + (char)c + "= lastWasEscaped=" + lastWasEscape + '=');
			if(lastWasEscape)
				lastWasEscape = false;
			else if(c==escapeChar)
				lastWasEscape = true;
			else if(c==delim)
			{
				if(off - start > 0)
					part = src.subSequence(start, off).toString();
				else
					part = "";
				parts.add(part);
				start = off+1;
			}
		}
		if(off - start > 0)
			part = src.subSequence(start, off).toString();
		else
			part = "";
		parts.add(part);
		return parts.toArray(EMPTY_STRING_ARRAY);
	}

	public static String[] splitUnescaped(CharSequence src, int off, int len, char delim)
	{
		return splitUnescaped(src, off, len, delim, DEFAULT_ESCAPE);
	}

	public static String[] splitUnescaped(CharSequence src, int off, char delim)
	{
		return splitUnescaped(src, off, src.length()-off, delim, DEFAULT_ESCAPE);
	}

	public static String[] splitUnescaped(CharSequence src, char delim)
	{
		return splitUnescaped(src, 0, src.length(), delim, DEFAULT_ESCAPE);
	}

	public static Appendable joinEscaped(Appendable dst, char delim, char escapeChar, String... strs) throws IOException
	{
		char[] alsoEscape;
		int i;

		if(strs == null || strs.length==0)
			return dst;
		alsoEscape = new char[1];
		alsoEscape[0] = delim;
		for(i=0;i<strs.length-1;i++)
			dst.append(escape(strs[i],alsoEscape,escapeChar)).append(delim);
		dst.append(escape(strs[i],alsoEscape,escapeChar));
		return dst;
	}

	public static Appendable joinEscaped(Appendable dst, char delim, String... strs) throws IOException
	{
		return joinEscaped(dst,delim,DEFAULT_ESCAPE,strs);
	}

	public static String joinEscaped(char delim, char escapeChar, String...strs)
	{
		if(strs==null||strs.length==0)
			return "";
		try
		{
			return joinEscaped(new StringBuilder(),delim, escapeChar,strs).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String joinEscaped(char delim, String...strs)
	{
		return joinEscaped(delim, DEFAULT_ESCAPE, strs);
	}
}
