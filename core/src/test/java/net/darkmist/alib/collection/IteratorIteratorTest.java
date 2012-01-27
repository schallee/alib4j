package net.darkmist.alib.collection;

import java.util.Iterator;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import org.apache.log4j.Logger;

public class IteratorIteratorTest extends TestCase
{
	private static final Logger logger = Logger.getLogger(IteratorIteratorTest.class);

	public void testTwoSubIterators() throws Exception
	{
		Iterator<Integer> a = new ArrayIterator<Integer>(new Integer[]{1,2,3,4});
		Iterator<Integer> b = new ArrayIterator<Integer>(new Integer[]{5,6,7,8});
		IteratorIterator<Integer,Iterator<Integer>> ii = new IteratorIterator<Integer, Iterator<Integer>>(a,b);

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
