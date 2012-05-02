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

import junit.framework.TestCase;

public class OctalTest extends TestCase
{
	@SuppressWarnings("unused")
	private static final Class<OctalTest> CLASS = OctalTest.class;

	public void testIsOctalTrue() throws Exception
	{
		for(char ch='0';ch<='7';ch++)
			assertTrue("" + ch + " should be octal but wasn't", Octal.isOctal(ch));
	}

	public void testIsOctalFalse() throws Exception
	{
		for(char ch='a';ch<='z';ch++)
			assertFalse("" + ch + " should not be octal but was", Octal.isOctal(ch));
		for(char ch='A';ch<='Z';ch++)
			assertFalse("" + ch + " should not be octal but was", Octal.isOctal(ch));
		for(char ch='8';ch<='9';ch++)
			assertFalse("" + ch + " should not be octal but was", Octal.isOctal(ch));
	}

	public void testUnoctTripletValid() throws Exception
	{
		for(int c=0;c<=7;c++)
		{
			char ch  = Integer.toString(c).charAt(0);
			assertEquals("Invalid octal triplet conversion", c, Octal.unoctTriplet(ch));
		}
	}

	public void testUnoctTripletInvalid() throws Exception
	{
		for(int c=8;c<=9;c++)
		{
			try
			{
				char ch  = Integer.toString(c).charAt(0);
				int result = Octal.unoctTriplet(ch);
				assertTrue("No exception for invalid octal character " + ch + ". Got " + result + '.', false);
			}
			catch(NumberFormatException e)
			{
				assertTrue(true);
			}
		}
	}
	
	public void testUnoctByte() throws Exception
	{
		int expected = 042;
		int result = Octal.unoctInt("042");
		assertEquals(expected, result);
	}
}
