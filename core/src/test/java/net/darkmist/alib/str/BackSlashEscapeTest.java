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

public class BackSlashEscapeTest extends TestCase
{
	private BackSlashEscape escape;

	@Override
	protected void setUp()
	{
		escape = BackSlashEscape.getInstance();
	}

	public void testNothingToEscape() throws Exception
	{
		String in = "toast is yummy";
		String expected = in;
		String out = escape.escape(in).toString();
		assertEquals("String with nothing to escape was escaped", expected, out);
	}

	public void testEmpty() throws Exception
	{
		String in = "";
		String expected = in;
		String out = escape.escape(in).toString();
		assertEquals("Result not empty", expected, out);
	}

	public void testHexEscape() throws Exception
	{
		String in = "toast is yummy (like cabbage)";
		String expected = "toast is yummy \\x28like cabbage\\x29";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

	public void testNewLineEscape() throws Exception
	{
		String in = "toast is yummy\nlike cabbage";
		String expected = "toast is yummy\\nlike cabbage";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

	public void testTabEscape() throws Exception
	{
		String in = "toast is yummy\tlike cabbage";
		String expected = "toast is yummy\\tlike cabbage";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

	public void testLowValueHexEscape() throws Exception
	{
		String in = "toast is yummy \001 like cabbage";
		String expected = "toast is yummy \\x01 like cabbage";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

	public void testNullHexEscape() throws Exception
	{
		String in = "toast is yummy \000 like cabbage";
		String expected = "toast is yummy \\x00 like cabbage";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

}
