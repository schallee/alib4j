package net.darkmist.alib.str;

import junit.framework.TestCase;

public class BackSlashEscapeTest extends TestCase
{
	private BackSlashEscape escape;

	@Override
	protected void setUp()
	{
		escape = BackSlashEscape.getInstance();
	}

	public void testNothingToEscape() throws Exception
	{
		String in = "toast is yummy";
		String expected = in;
		String out = escape.escape(in).toString();
		assertEquals("String with nothing to escape was escaped", expected, out);
	}

	public void testEmpty() throws Exception
	{
		String in = "";
		String expected = in;
		String out = escape.escape(in).toString();
		assertEquals("Result not empty", expected, out);
	}

	public void testHexEscape() throws Exception
	{
		String in = "toast is yummy (like cabbage)";
		String expected = "toast is yummy \\x28like cabbage\\x29";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

	public void testNewLineEscape() throws Exception
	{
		String in = "toast is yummy\nlike cabbage";
		String expected = "toast is yummy\\nlike cabbage";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

	public void testTabEscape() throws Exception
	{
		String in = "toast is yummy\tlike cabbage";
		String expected = "toast is yummy\\tlike cabbage";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

	public void testLowValueHexEscape() throws Exception
	{
		String in = "toast is yummy \001 like cabbage";
		String expected = "toast is yummy \\x01 like cabbage";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

	public void testNullHexEscape() throws Exception
	{
		String in = "toast is yummy \000 like cabbage";
		String expected = "toast is yummy \\x00 like cabbage";
		String out = escape.escape(in).toString();
		assertEquals("Improperly escaped", expected, out);
	}

}
