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

import java.util.Arrays;

import junit.framework.TestCase;

public class StrUtilTest extends TestCase
{
	@SuppressWarnings("unused")
	private static final Class<StrUtilTest> CLASS = StrUtilTest.class;

	private void doSimpleSplitTest(String input, char delim, String[] expected)
	{
		String[] actual;

		actual = StrUtil.split(input, delim);
		if(Arrays.equals(expected,actual))
			return;
		fail("Input " + input + " split by " + delim + " returned " + Arrays.toString(actual) + " instead of " + Arrays.toString(expected));
	}

	public void testSplitNull()
	{
		String input = null;
		String[] expected = new String[]{};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplitEmpty()
	{
		String input = "";
		String[] expected = new String[]{""};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit1x1()
	{
		String input = "a";
		String[] expected = new String[]{"a"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit1x2()
	{
		String input = "ab";
		String[] expected = new String[]{"ab"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit2Empty()
	{
		String input = ".";
		String[] expected = new String[]{"",""};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit2x1()
	{
		String input = "x.y";
		String[] expected = new String[]{"x","y"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit2x1EmptyFirst()
	{
		String input = ".y";
		String[] expected = new String[]{"","y"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit2x1EmptySecond()
	{
		String input = "x.";
		String[] expected = new String[]{"x",""};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit2x2()
	{
		String input = "ab.cd";
		String[] expected = new String[]{"ab","cd"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit2x2EmptyFirst()
	{
		String input = ".cd";
		String[] expected = new String[]{"","cd"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit2x2EmptySecond()
	{
		String input = "ab.";
		String[] expected = new String[]{"ab",""};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x1()
	{
		String input = "x.y.z";
		String[] expected = new String[]{"x","y","z"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x1EmptyFirst()
	{
		String input = ".y.z";
		String[] expected = new String[]{"","y","z"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x1EmptyLast()
	{
		String input = "x.y.";
		String[] expected = new String[]{"x","y",""};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x1EmptyMiddle()
	{
		String input = "x..z";
		String[] expected = new String[]{"x","","z"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x1EmptyStartMiddle()
	{
		String input = "..z";
		String[] expected = new String[]{"","","z"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x1EmptyMiddleEnd()
	{
		String input = "x..";
		String[] expected = new String[]{"x","",""};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3EmptyStartMiddleEnd()
	{
		String input = "..";
		String[] expected = new String[]{"","",""};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x2()
	{
		String input = "ab.cd.ef";
		String[] expected = new String[]{"ab","cd","ef"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x2EmptyFirst()
	{
		String input = ".cd.ef";
		String[] expected = new String[]{"","cd","ef"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x2EmptyLast()
	{
		String input = "ab.cd.";
		String[] expected = new String[]{"ab","cd",""};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x2EmptyMiddle()
	{
		String input = "ab..ef";
		String[] expected = new String[]{"ab","","ef"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x2EmptyStartMiddle()
	{
		String input = "..ef";
		String[] expected = new String[]{"","","ef"};

		doSimpleSplitTest(input,'.',expected);
	}

	public void testSplit3x2EmptyMiddleEnd()
	{
		String input = "ab..";
		String[] expected = new String[]{"ab","",""};

		doSimpleSplitTest(input,'.',expected);
	}
}
