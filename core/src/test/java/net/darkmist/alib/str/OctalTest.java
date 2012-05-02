package net.darkmist.alib.str;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

public class OctalTest extends TestCase
{
	private static final Class<OctalTest> CLASS = OctalTest.class;


	protected void setUp()
	{
	}

	public void testIsOctalTrue() throws Exception
	{
		for(char ch='0';ch<='7';ch++)
			assertTrue("" + ch + " should be octal but wasn't", Octal.isOctal(ch));
	}

	public void testIsOctalFalse() throws Exception
	{
		for(char ch='a';ch<='z';ch++)
			assertFalse("" + ch + " should not be octal but was", Octal.isOctal(ch));
		for(char ch='A';ch<='Z';ch++)
			assertFalse("" + ch + " should not be octal but was", Octal.isOctal(ch));
		for(char ch='8';ch<='9';ch++)
			assertFalse("" + ch + " should not be octal but was", Octal.isOctal(ch));
	}

	public void testUnoctTripletValid() throws Exception
	{
		for(int c=0;c<=7;c++)
		{
			char ch  = Integer.toString(c).charAt(0);
			assertEquals("Invalid octal triplet conversion", c, Octal.unoctTriplet(ch));
		}
	}

	public void testUnoctTripletInvalid() throws Exception
	{
		for(int c=8;c<=9;c++)
		{
			try
			{
				char ch  = Integer.toString(c).charAt(0);
				int result = Octal.unoctTriplet(ch);
				assertTrue("No exception for invalid octal character " + ch + ". Got " + result + '.', false);
			}
			catch(NumberFormatException e)
			{
				assertTrue(true);
			}
		}
	}
	
	public void testUnoctByte() throws Exception
	{
		int expected = 042;
		int result = Octal.unoctInt("042");
		assertEquals(expected, result);
	}

	protected void tearDown()
	{
	}

	public static Test suite()
	{
		return new TestSuite(CLASS);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
