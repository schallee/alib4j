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

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

public class IteratorsTest extends TestCase
{
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(IteratorsTest.class);

	public void testSimple() throws Exception
	{
		Set<Integer> set = new HashSet<Integer>();
		Set<Integer> set2;

		set.add(1);
		set.add(2);
		set.add(3);

		set2 = Iterators.toSet(set.iterator());

		assertFalse("0 appeared", set2.contains(0));
		assertTrue("1 is missing", set2.contains(1));
		assertTrue("2 is missing", set2.contains(2));
		assertTrue("3 is missing", set2.contains(3));
		assertFalse("4 appeared", set2.contains(4));
	}
	
	public static Test suite()
	{
		return new TestSuite(IteratorsTest.class);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
