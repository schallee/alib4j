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
