package net.darkmist.alib.collection;

// not changed since qcomm

import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import org.apache.log4j.Logger;

public class IteratorUtilTest extends TestCase
{
	private static final Logger logger = Logger.getLogger(IteratorUtilTest.class);

	public void testSimple() throws Exception
	{
		Set<Integer> set = new HashSet<Integer>();
		Set<Integer> set2;

		set.add(1);
		set.add(2);
		set.add(3);

		set2 = IteratorUtil.toSet(set.iterator());

		assertFalse("0 appeared", set2.contains(0));
		assertTrue("1 is missing", set2.contains(1));
		assertTrue("2 is missing", set2.contains(2));
		assertTrue("3 is missing", set2.contains(3));
		assertFalse("4 appeared", set2.contains(4));
	}
	
	public static Test suite()
	{
		return new TestSuite(IteratorUtilTest.class);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
