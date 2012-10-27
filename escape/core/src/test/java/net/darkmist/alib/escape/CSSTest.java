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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CSSTest extends TestCase
{
	private static final Class<CSSTest> CLASS = CSSTest.class;

	private CSS css = CSS.instance();

	private static int testAndDecodeChar(String encoded)
	{
		assertTrue(encoded.length() >= 3);
		assertEquals('\\', encoded.charAt(0));
		assertEquals(' ', encoded.charAt(encoded.length()-1));
		return Integer.parseInt(encoded.substring(1, encoded.length()-1), 16);
	}

	public void testTest()
	{
		assertEquals("test", css.escape("test"));
	}
	
	public void testTwoByteCharsWork()
	{
		String encoded = "";
		String charStr;

		for(int i=Character.MIN_VALUE;i<=Character.MAX_VALUE;i++)
		{
			charStr = Character.toString((char)i);

			try
			{
				encoded = css.escape(charStr);
				// zero is invalid in css
				assertTrue(0!=i);
			}
			catch(IllegalArgumentException e)
			{
				// zero is invalid in css
				assertEquals(0,i);
				continue;
			}

			if(charStr.equals(encoded))
			{
				assertTrue(Util.isAlphaNumericOrWhiteSpace(i));
				continue;
			}
			assertFalse(Util.isAlphaNumeric(i));
			assertEquals(i, testAndDecodeChar(encoded));
		}
	}

	public void testSurrogatesWork()
	{
		char array[] = new char[2];
		String str;
		String encoded;
		int codepoint;

		for(char high=0xD800;high<=0xDBFF;high++)
		{
			array[0] = high;
			for(char low=0xDC00;low<=0xDFFF;low++)
			{
				array[1] = low;
				str = String.valueOf(array);
				// make sure we built a proper char...
				assertEquals(1, str.codePointCount(0,2));
				encoded = css.escape(str);
				codepoint = str.codePointAt(0);
				assertEquals(codepoint, testAndDecodeChar(encoded));
			}
		}
	}
}
