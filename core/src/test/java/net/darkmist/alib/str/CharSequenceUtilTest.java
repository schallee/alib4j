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

public class CharSequenceUtilTest extends TestCase
{

	public void testLTrimEmpty() throws Exception
	{
		String in = "";
		String expected = "";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimStr() throws Exception
	{
		String in = "toast is yummy";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimSpaceStr() throws Exception
	{
		String in = " toast is yummy";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimTabStr() throws Exception
	{
		String in = "\ttoast is yummy";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimSpaceSpaceStr() throws Exception
	{
		String in = "  toast is yummy";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimTabTabStr() throws Exception
	{
		String in = "\t\ttoast is yummy";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimSpaceTabStr() throws Exception
	{
		String in = " \ttoast is yummy";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimTabSpaceStr() throws Exception
	{
		String in = "\t toast is yummy";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimSpace() throws Exception
	{
		String in = " ";
		String expected = "";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimTab() throws Exception
	{
		String in = "\t";
		String expected = "";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimSpaceSpace() throws Exception
	{
		String in = "  ";
		String expected = "";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimTabTab() throws Exception
	{
		String in = "\t\t";
		String expected = "";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimSpaceTab() throws Exception
	{
		String in = " \t";
		String expected = "";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testLTrimTabSpace() throws Exception
	{
		String in = "\t ";
		String expected = "";
		String actual = CharSequenceUtil.ltrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimEmpty() throws Exception
	{
		String in = "";
		String expected = "";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimStr() throws Exception
	{
		String in = "toast is yummy";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimStrSpace() throws Exception
	{
		String in = "toast is yummy ";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimStrTab() throws Exception
	{
		String in = "toast is yummy\t";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimStrSpaceSpace() throws Exception
	{
		String in = "toast is yummy  ";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimStrTabTab() throws Exception
	{
		String in = "toast is yummy\t\t";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimStrSpaceTab() throws Exception
	{
		String in = "toast is yummy \t";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimStrTabSpace() throws Exception
	{
		String in = "toast is yummy\t ";
		String expected = "toast is yummy";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimSpace() throws Exception
	{
		String in = " ";
		String expected = "";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimTab() throws Exception
	{
		String in = "\t";
		String expected = "";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimSpaceSpace() throws Exception
	{
		String in = "  ";
		String expected = "";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimTabTab() throws Exception
	{
		String in = "\t\t";
		String expected = "";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimSpaceTab() throws Exception
	{
		String in = " \t";
		String expected = "";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testRTrimTabSpace() throws Exception
	{
		String in = "\t ";
		String expected = "";
		String actual = CharSequenceUtil.rtrim(in).toString();
		assertEquals(expected, actual);
	}

	public void testIndexOfEmpty() throws Exception
	{
		String in = "";
		int expected = -1;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

	public void testIndexOfOneNotFound() throws Exception
	{
		String in = "A";
		int expected = -1;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

	public void testIndexOfOneFound() throws Exception
	{
		String in = ":";
		int expected = 0;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

	public void testIndexOfTwoNotFound() throws Exception
	{
		String in = "AA";
		int expected = -1;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

	public void testIndexOfTwoFoundOne() throws Exception
	{
		String in = ":A";
		int expected = 0;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

	public void testIndexOfTwoFoundTwo() throws Exception
	{
		String in = "A:";
		int expected = 1;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

	public void testIndexOfThreeNotFound() throws Exception
	{
		String in = "AAA";
		int expected = -1;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

	public void testIndexOfThreeFoundOne() throws Exception
	{
		String in = ":AA";
		int expected = 0;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

	public void testIndexOfThreeFoundTwo() throws Exception
	{
		String in = "A:A";
		int expected = 1;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

	public void testIndexOfThreeFoundThree() throws Exception
	{
		String in = "AA:";
		int expected = 2;
		int actual = CharSequenceUtil.indexOf(in, ':');
		assertEquals(expected, actual);
	}

}
