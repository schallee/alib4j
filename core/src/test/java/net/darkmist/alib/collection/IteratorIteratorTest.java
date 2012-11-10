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

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IteratorIteratorTest extends TestCase
{
	private static final Class<IteratorIteratorTest> CLASS = IteratorIteratorTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	public void testTwoSubIterators() throws Exception
	{
		Iterator<Integer> a = Iterators.getArrayIterator(new Integer[]{1,2,3,4});
		Iterator<Integer> b = Iterators.getArrayIterator(new Integer[]{5,6,7,8});
		@SuppressWarnings("unchecked")
		IteratorIterator<Integer> ii = IteratorIterator.getInstance(a,b);

		for(int i=1;i<9;i++)
		{
			logger.debug("i=" + i);
			assertEquals("" + i + " is incorrect", i, ii.next().intValue());
		}
		assertFalse("More in iterator than should be", ii.hasNext());
	}
	
	public static Test suite()
	{
		return new TestSuite(IteratorIteratorTest.class);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
