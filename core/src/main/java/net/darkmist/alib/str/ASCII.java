package net.darkmist.alib.str;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

class ASCII
{
	public static final String CHARSET_NAME = "US-ASCII";
	public static final Charset CHARSET = getCharSet();

	private static Charset getCharSet()
	{
		try
		{
			return Charset.forName(CHARSET_NAME);
		}
		catch(UnsupportedCharsetException e)
		{
			throw new IllegalStateException("JVM spec requires " + CHARSET_NAME + " but I got an exception using it.", e);
		}
	}

	private ASCII()
	{
	}

	public static String fromBytes(byte[] bytes)
	{
		try
		{
			return new String(bytes,CHARSET_NAME);
		}
		catch(UnsupportedEncodingException e)
		{
			throw new IllegalStateException("JVM spec requires " + CHARSET_NAME + " but I got an exception using it.", e);
		}
	}

	public static byte[] toBytes(String str)
	{
		try
		{
			return str.getBytes(CHARSET_NAME);
		}
		catch(UnsupportedEncodingException e)
		{
			throw new IllegalStateException("JVM spec requires " + CHARSET_NAME + " but I got an exception using it.", e);
		}
	}

	/**
	 * Does a char have non-whitespace graphics
	 * @param ch The char to check
	 * @return true if it is printable. false otherwise.
	 */
	public static boolean isPrintable(int ch)
	{
		switch(ch)
		{
			//case ' ':
			case '!':
			case '"':
			case '#':
			case '$':
			case '%':
			case '&':
			case '\'':
			case '(':
			case ')':
			case '*':
			case '+':
			case ',':
			case '-':
			case '.':
			case '/':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case ':':
			case ';':
			case '<':
			case '=':
			case '>':
			case '?':
			case '@':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case '[':
			case '\\':
			case ']':
			case '^':
			case '_':
			case '`':
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
			case '{':
			case '|':
			case '}':
			case '~':
				return true;
			default:
				return false;
		}
	}

	/**
	 * Char or '.' if not printable
	 * @param b character to return or replace
	 * @return b if it is printable, '.' otherwise.
	 */
	public static char printable(int b)
	{
		if(isPrintable(b))
			return (char)b;
		return '.';
	}

	public static <T extends Appendable> T printable(T sb, byte[] bin, int off, int len) throws IOException
	{
		for(len+=off;off<len;off++)
			sb.append(printable(bin[off]));
		return sb;
	}

	public static StringBuilder printable(StringBuilder sb, byte[] bin, int off, int len)
	{
		try
		{
			printable((Appendable)sb, bin, off, len);
			return sb;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("Caught IOException appending to a StringBuilder", e);
		}
	}

	public static String printable(byte[] bin, int off, int len)
	{
		return printable(new StringBuilder(bin.length), bin, off, len).toString();
	}

	public static <T extends Appendable> T printable(T sb, byte[] bin) throws IOException
	{
		return printable(sb, bin, 0, bin.length);
	}

	public static StringBuilder printable(StringBuilder sb, byte[] bin)
	{
		return printable(sb, bin, 0, bin.length);
	}

	public static String printable(byte[] bin)
	{
		return printable(bin, 0, bin.length);
	}
}