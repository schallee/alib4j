package net.darkmist.alib.str;

import junit.framework.TestCase;

public class HTMLEscapeTest extends TestCase
{

	@Deprecated
	public void testEmpty() throws Exception
	{
		String in = "";
		String expected = in;
		String out = HTMLEscape.escape(in);
		assertEquals("Empty string not returned as is.", expected, out);
	}

	@Deprecated
	public void testNothingToEncode() throws Exception
	{
		String in = "toast";
		String expected = in;
		String out = HTMLEscape.escape(in);
		assertEquals("String not needing encoding not returned as is", expected, out);
	}

	@Deprecated
	public void testEncodeSpace() throws Exception
	{
		String in = "toast is yummy";
		String expected = "toast is yummy";
		String out = HTMLEscape.escape(in);
		assertEquals("Space not encoded as expected", expected, out);
	}

	/*
	@Deprecated
	public void testNull() throws Exception
	{
		String in = "one\u0000null";
		String expected = "one&#0000;null";
		String out = HTMLEscape.HTMLEscape(in);
		assertEquals("One null not encoded properly", expected, out);
	}
	*/

	/*
	@Deprecated
	public void testHighBit() throws Exception
	{
		String in = "one\uFFFFhighbit";
		String expected = "one&#FFFF;highbit";
		String out = HTMLEscape.HTMLEscape(in);
		assertEquals("One high bit not escaped properly", expected, out);
	}
	*/
	
}
