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

package net.darkmist.alib.str;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class ASCII
{
	public static final String CHARSET_NAME = "US-ASCII";
	public static final Charset CHARSET = getCharSet();

	@SuppressFBWarnings(value={"EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS","WEM_WEAK_EXCEPTION_MESSAGING"},justification="Boolean state, shouldn't happen for ASCII")
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

	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public static String fromBytes(byte[] bytes)
	{
		return new String(bytes,CHARSET);
	}

	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public static byte[] toBytes(String str)
	{
		return str.getBytes(CHARSET);
	}

	/**
	 * Does a char have non-whitespace graphics
	 * @param ch The char to check
	 * @return true if it is printable. false otherwise.
	 */
	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
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

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public static <T extends Appendable> T printable(T sb, byte[] bin, int off, int len) throws IOException
	{
		for(len+=off;off<len;off++)
			sb.append(printable(bin[off]));
		return sb;
	}

	@SuppressFBWarnings(value={"OPM_OVERLY_PERMISSIVE_METHOD","EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS"},justification="API method, appending to StringBuilder")
	public static StringBuilder printable(StringBuilder sb, byte[] bin, int off, int len)
	{
		try
		{
			printable((Appendable)sb, bin, off, len);
			return sb;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("Caught IOException appending to a StringBuilder " + sb + '.', e);
		}
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
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
