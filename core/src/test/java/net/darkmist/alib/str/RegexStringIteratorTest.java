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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexStringIteratorTest extends TestCase
{
	private static final Class<RegexStringIteratorTest> CLASS = RegexStringIteratorTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	static private class RegIt extends RegexStringIterator 
	{
		RegIt(CharSequence data)
		{
			super(data);
		}

		@Override
		protected String getRegex()
		{
			return "a";
		}
	}

	static private class GroupRegIt extends RegexStringIterator 
	{
		GroupRegIt(CharSequence data)
		{
			super(data);
		}

		@Override
		protected String getRegex()
		{
			return "a(.)";
		}

		@Override
		protected int getGroup()
		{
			return 1;
		}
	}

	public void testSimple() throws Exception
	{
		RegexStringIterator i = new RegIt("xyzabcxyzabc");
		int expected = 2;
		int actual = 0;
		String tmp;

		logger.debug("Looping on i.hasNext");
		while(i.hasNext())
		{
			tmp = i.next();
			logger.debug("Got: " + tmp);
			assertEquals("a", tmp);
			actual++;
		}
		assertEquals(expected,actual);
	}

	public void testGrouping() throws Exception
	{
		RegexStringIterator i = new GroupRegIt("xyzabcxyzabc");
		int expected = 2;
		int actual = 0;
		String tmp;

		logger.debug("Looping on i.hasNext");
		while(i.hasNext())
		{
			tmp = i.next();
			logger.debug("Got: " + tmp);
			assertEquals("b", tmp);
			actual++;
		}
		assertEquals(expected,actual);
	}

}
