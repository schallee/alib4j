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

public class URLEscapeTest extends TestCase
{

	public void testEmpty() throws Exception
	{
		String in = "";
		String expected = in;
		String out = URLEscape.escape(in);
		assertEquals("Empty string not returned as is.", expected, out);
	}

	public void testNothingToEncode() throws Exception
	{
		String in = "toast";
		String expected = in;
		String out = URLEscape.escape(in);
		assertEquals("String not needing encoding not returned as is", expected, out);
	}

	public void testEncodeSpace() throws Exception
	{
		String in = "toast is yummy";
		String expected = "toast+is+yummy";
		String out = URLEscape.escape(in);
		assertEquals("Space not encoded as expected", expected, out);
	}

	public void testNull() throws Exception
	{
		String in = "one\u0000null";
		String expected = "one%00null";
		String out = URLEscape.escape(in);
		assertEquals("One null not encoded properly", expected, out);
	}

	public void testHighBit() throws Exception
	{
		String in = "one\uFFFFhighbit";
		String expected = "one%EF%BF%BFhighbit";
		String out = URLEscape.escape(in);
		assertEquals("One high bit not escaped properly", expected, out);
	}

	public void testSlash() throws Exception
	{
		String in = "slash/between";
		String expected = "slash%2Fbetween";
		String out = URLEscape.escape(in);
		assertEquals("Slash not escaped", expected, out);
	}

	public void testNoSlash() throws Exception
	{
		String in = "slash/between";
		String expected = in;
		String out = URLEscape.URLEscapeNotSlash(in);
		assertEquals("Slash escaped when it shouldn't have been", expected, out);
	}
}
