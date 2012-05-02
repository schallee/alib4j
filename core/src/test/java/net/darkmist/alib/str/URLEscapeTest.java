package net.darkmist.alib.str;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

public class URLEscapeTest extends TestCase
{
	protected void setUp()
	{
	}

	public void testEmpty() throws Exception
	{
		String in = "";
		String expected = in;
		String out = URLEscape.escape(in);
		assertEquals("Empty string not returned as is.", expected, out);
	}

	public void testNothingToEncode() throws Exception
	{
		String in = "toast";
		String expected = in;
		String out = URLEscape.escape(in);
		assertEquals("String not needing encoding not returned as is", expected, out);
	}

	public void testEncodeSpace() throws Exception
	{
		String in = "toast is yummy";
		String expected = "toast+is+yummy";
		String out = URLEscape.escape(in);
		assertEquals("Space not encoded as expected", expected, out);
	}

	public void testNull() throws Exception
	{
		String in = "one\u0000null";
		String expected = "one%00null";
		String out = URLEscape.escape(in);
		assertEquals("One null not encoded properly", expected, out);
	}

	public void testHighBit() throws Exception
	{
		String in = "one\uFFFFhighbit";
		String expected = "one%EF%BF%BFhighbit";
		String out = URLEscape.escape(in);
		assertEquals("One high bit not escaped properly", expected, out);
	}

	public void testSlash() throws Exception
	{
		String in = "slash/between";
		String expected = "slash%2Fbetween";
		String out = URLEscape.escape(in);
		assertEquals("Slash not escaped", expected, out);
	}

	public void testNoSlash() throws Exception
	{
		String in = "slash/between";
		String expected = in;
		String out = URLEscape.URLEscapeNotSlash(in);
		assertEquals("Slash escaped when it shouldn't have been", expected, out);
	}

	protected void tearDown()
	{
	}

	public static Test suite()
	{
		return new TestSuite(URLEscapeTest.class);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
