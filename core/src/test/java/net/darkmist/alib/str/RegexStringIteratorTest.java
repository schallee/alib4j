package net.darkmist.alib.str;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class RegexStringIteratorTest extends TestCase
{
	private static final Logger logger = Logger.getLogger(RegexStringIteratorTest.class);

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
