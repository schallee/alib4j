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

public class HTMLEscapeTest extends TestCase
{

	@Deprecated
	public void testEmpty() throws Exception
	{
		String in = "";
		String expected = in;
		String out = HTMLEscape.escape(in);
		assertEquals("Empty string not returned as is.", expected, out);
	}

	@Deprecated
	public void testNothingToEncode() throws Exception
	{
		String in = "toast";
		String expected = in;
		String out = HTMLEscape.escape(in);
		assertEquals("String not needing encoding not returned as is", expected, out);
	}

	@Deprecated
	public void testEncodeSpace() throws Exception
	{
		String in = "toast is yummy";
		String expected = "toast is yummy";
		String out = HTMLEscape.escape(in);
		assertEquals("Space not encoded as expected", expected, out);
	}

	/*
	@Deprecated
	public void testNull() throws Exception
	{
		String in = "one\u0000null";
		String expected = "one&#0000;null";
		String out = HTMLEscape.HTMLEscape(in);
		assertEquals("One null not encoded properly", expected, out);
	}
	*/

	/*
	@Deprecated
	public void testHighBit() throws Exception
	{
		String in = "one\uFFFFhighbit";
		String expected = "one&#FFFF;highbit";
		String out = HTMLEscape.HTMLEscape(in);
		assertEquals("One high bit not escaped properly", expected, out);
	}
	*/
	
}
