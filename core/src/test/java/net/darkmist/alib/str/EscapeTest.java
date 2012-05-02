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

import java.util.Random;

import junit.framework.TestCase;

import static net.darkmist.alib.str.Escape.escape;
import static net.darkmist.alib.str.Escape.unescape;

public class EscapeTest extends TestCase
{
	@SuppressWarnings("unused")
	private static final Class<EscapeTest> CLASS = EscapeTest.class;
	private static final Random rand = new Random();
	private static final int NUM_RAND_TESTS = 100;

	private static String zeroPad(String str, int len)
	{
		while(str.length()<len)
			str = "0" + str;
		return str;
	}

	public void testEscapeSingles() throws Exception
	{
		for(int c=0;c<0x07;c++)
			assertEquals("\\x" + zeroPad(Integer.toHexString(c),2), escape(Character.toString((char)c)));
		assertEquals("\\a", escape("\007"));
		assertEquals("\\b", escape("\b"));
		assertEquals("\\t", escape("\t"));
		assertEquals("\\n", escape("\n"));
		assertEquals("\\v", escape("\013"));
		assertEquals("\\f", escape("\f"));
		assertEquals("\\r", escape("\r"));
		for(int c=0xE;c<0x20;c++)
			assertEquals("\\x" + zeroPad(Integer.toHexString(c),2), escape(Character.toString((char)c)));
		for(int c=0x20;c<0x5c;c++)
			assertEquals(Character.toString((char)c), escape(Character.toString((char)c)));
		assertEquals("\\\\", escape("\\"));
		for(int c=0x5D;c<0x7f;c++)
			assertEquals(Character.toString((char)c), escape(Character.toString((char)c)));
		for(int c=0x7f;c<0x100;c++)
			assertEquals("\\x" + zeroPad(Integer.toHexString(c),2), escape(Character.toString((char)c)));
		for(int c=0x100;c<0x10000;c++)
			assertEquals("\\u" + zeroPad(Integer.toHexString(c),4), escape(Character.toString((char)c)));
	}

	public void testUnescapeSingles() throws Exception
	{
		String unescaped;
		String escaped;

		for(int c=0;c<0x07;c++)
		{
			unescaped=Character.toString((char)c);
			escaped = "\\x" + zeroPad(Integer.toHexString(c),2);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
		assertEquals("\007", unescape("\\a"));
		assertEquals("\b", unescape("\\b"));
		assertEquals("\t", unescape("\\t"));
		assertEquals("\n", unescape("\\n"));
		assertEquals("\013", unescape("\\v"));
		assertEquals("\f", unescape("\\f"));
		assertEquals("\r", unescape("\\r"));
		for(int c=0xE;c<0x20;c++)
		{
			unescaped=Character.toString((char)c);
			escaped = "\\x" + zeroPad(Integer.toHexString(c),2);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
		for(int c=0x20;c<0x5c;c++)
		{
			unescaped=escaped=Character.toString((char)c);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
		assertEquals("\\", unescape("\\\\"));
		for(int c=0x5D;c<0x7f;c++)
		{
			unescaped=escaped=Character.toString((char)c);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
		for(int c=0x7f;c<0x100;c++)
		{
			unescaped=Character.toString((char)c);
			escaped = "\\x" + zeroPad(Integer.toHexString(c),2);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
		for(int i=0;i<NUM_RAND_TESTS;i++)
		{
			int c;
			do
			{
				c = rand.nextInt() & 0xffff;
			}
			while(c < 0x100);
			unescaped=Character.toString((char)c);
			escaped = "\\u" + zeroPad(Integer.toHexString(c),4);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
	}

	public void testUnescapeSingleAllHex() throws Exception
	{
		for(int c=0;c<0x100;c++)
		{
			String unescaped=Character.toString((char)c);
			String escaped = "\\x" + zeroPad(Integer.toHexString(c),2);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
	}

	public void testUnescapeSingleAllUnicode() throws Exception
	{
		for(int c=0;c<0x10000;c++)
		{
			String unescaped=Character.toString((char)c);
			String escaped = "\\u" + zeroPad(Integer.toHexString(c),4);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
	}

	public void testUnescapeSingleAllTripleOctal() throws Exception
	{
		for(int c=0;c<01000;c++)
		{
			String unescaped=Character.toString((char)c);
			String escaped = "\\" + zeroPad(Integer.toOctalString(c),3);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
	}

	public void testUnescapeSingleAllDoubleOctal() throws Exception
	{
		for(int c=0;c<0100;c++)
		{
			String unescaped=Character.toString((char)c);
			String escaped = "\\" + zeroPad(Integer.toOctalString(c),2);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
	}

	public void testUnescapeSingleAllSingleOctal() throws Exception
	{
		for(int c=0;c<010;c++)
		{
			String unescaped=Character.toString((char)c);
			String escaped = "\\" + zeroPad(Integer.toOctalString(c),1);
			assertEquals("escaped=" + escaped, unescaped, unescape(escaped));
		}
	}

	public void testSplitUnescapedNoEscapes() throws Exception
	{
		String s1 = "abc";
		String s2 = "def";
		char delim = ':';
		String[] result;

		result = Escape.splitUnescaped(s1+delim+s2, delim);
		assertEquals(2,result.length);
		assertEquals(s1, result[0]);
		assertEquals(s2, result[1]);
	}

	public void testSplitUnescapedEscapes() throws Exception
	{
		String s1 = "ab\\:c";
		String s2 = "de\\:f";
		char delim = ':';
		String[] result;

		result = Escape.splitUnescaped(s1+delim+s2, delim);
		assertEquals(2,result.length);
		assertEquals(s1, result[0]);
		assertEquals(s2, result[1]);
	}

	public void testSplitUnescapedEscapesAndEmpties() throws Exception
	{
		String s1 = "ab\\:c";
		String s2 = "de\\:f";
		char delim = ':';
		String[] result;

		result = Escape.splitUnescaped("" +  delim + s1+delim+delim + s2 + delim, delim);
		assertEquals(5,result.length);
		assertEquals("", result[0]);
		assertEquals(s1, result[1]);
		assertEquals("", result[2]);
		assertEquals(s2, result[3]);
		assertEquals("", result[4]);
	}

	public void testJoinEscaped() throws Exception
	{
		String expected = ":ab\\:c::de\\:f:";
		String result = Escape.joinEscaped(':', "", "ab:c", "", "de:f", "");
		assertEquals(expected, result);
	}
	
}
