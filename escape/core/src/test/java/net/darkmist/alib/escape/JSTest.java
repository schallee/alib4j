package net.darkmist.alib.escape;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSTest extends TestCase
{
	private static final Class<JSTest> CLASS = JSTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private JS js = JS.instance();

	private static int testAndDecodeChar(String encoded)
	{
		assertEquals('\\', encoded.charAt(0));
		switch(encoded.length())
		{
			case 2:
				switch(encoded.charAt(1))
				{
					case 't':
						return '\t';
					case 'r':
						return '\r';
					case 'n':
						return '\n';
					case '\\':
						return '\\';
					default:
						fail("Backslash single character followed by " + encoded.charAt(1));
				}
			case 4:
				assertEquals('x', encoded.charAt(1));
				break;
			case 6:
				assertEquals('u', encoded.charAt(1));
				break;
			default:
				fail("Encoded character had length of " + encoded.length() + " which is not one of 2, 4 or 6.");
		}
		return Integer.parseInt(encoded.substring(2, encoded.length()), 16);
	}

	public void testTest()
	{
		assertEquals("test", js.escape("test"));
	}
	
	public void testTwoByteCharsWork()
	{
		String encoded = "";
		String charStr;

		for(int i=Character.MIN_VALUE;i<=Character.MAX_VALUE;i++)
		{
			charStr = Character.toString((char)i);

			encoded = js.escape(charStr);
			if(charStr.equals(encoded))
			{
				assertTrue(Util.isAlphaNumericOrWhiteSpace(i));
				continue;
			}
			assertFalse(Util.isAlphaNumeric(i));
			assertEquals(i, testAndDecodeChar(encoded));
		}
	}

	public void testSurrogatesWork()
	{
		char array[] = new char[2];
		String str;
		String encoded;

		for(char high=0xD800;high<=0xDBFF;high++)
		{
			array[0] = high;
			for(char low=0xDC00;low<=0xDFFF;low++)
			{
				array[1] = low;
				str = String.valueOf(array);
				// make sure we built a proper char...
				assertEquals(1, str.codePointCount(0,2));
				try
				{
					encoded = js.escape(str);
					fail();
				}
				catch(IllegalArgumentException e)
				{
					assertTrue(true);
				}
			}
		}
	}
}
