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

package net.darkmist.alib.collection;

import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

public class ArrayIteratorTest extends TestCase
{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(ArrayIteratorTest.class);

	public void testSimple() throws Exception
	{
		Integer[] array = new Integer[]{1,2,3,4};
		Iterator<Integer> i = Iterators.getArrayIterator(array);

		assertEquals("1 is incorrect", 1, i.next().intValue());
		assertEquals("2 is incorrect", 2, i.next().intValue());
		assertEquals("3 is incorrect", 3, i.next().intValue());
		assertEquals("4 is incorrect", 4, i.next().intValue());
		assertFalse("More in iterator than should be", i.hasNext());
	}
	
	public static Test suite()
	{
		return new TestSuite(ArrayIteratorTest.class);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
