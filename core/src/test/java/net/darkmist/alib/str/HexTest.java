package net.darkmist.alib.str;

import java.security.SecureRandom;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

public class HexTest extends TestCase
{
	private static final Class CLASS = HexTest.class;
	private static final SecureRandom rand = new SecureRandom();
	private static final int NUM_RAND_TESTS = 100;

	private static String zeroPad(String str, int len)
	{
		while(str.length()<len)
			str = "0" + str;
		return str;
	}

	protected void setUp()
	{
	}

	public void testIsHexTrue() throws Exception
	{
		for(char ch='0';ch<='9';ch++)
			assertTrue("" + ch + " should be hex but wasn't", Hex.isHex(ch));
		for(char ch='a';ch<='f';ch++)
			assertTrue("" + ch + " should be hex but wasn't", Hex.isHex(ch));
		for(char ch='A';ch<='F';ch++)
			assertTrue("" + ch + " should be hex but wasn't", Hex.isHex(ch));
	}

	public void testIsHexFalse() throws Exception
	{
		for(char ch='g';ch<='z';ch++)
			assertFalse("" + ch + " should not be hex but was", Hex.isHex(ch));
		for(char ch='G';ch<='Z';ch++)
			assertFalse("" + ch + " should not be hex but was", Hex.isHex(ch));
	}

	public void testHexNybble() throws Exception
	{
		StringBuilder sb = new StringBuilder(1);

		for(int c=0;c<=0xf;c++)
			assertEquals("c=" + c, Integer.toHexString(c).charAt(0), Hex.hexNybble(c));
		for(int c=0;c<=0xf;c++)
		{
			sb.delete(0,sb.length());
			assertEquals(Integer.toHexString(c), Hex.hexNybble(sb, c).toString());
		}
	}

	public void testHexByte() throws Exception
	{
		String actual;
		String expected;

		for(int i=0;i<0x10;i++)
		{
			expected = "0" + Integer.toHexString(i);
			actual = Hex.hexByte(i);
			assertEquals(expected, actual);
		}
		for(int i=0x10;i<0x100;i++)
		{
			expected = Integer.toHexString(i);
			actual = Hex.hexByte(i);
			assertEquals(expected, actual);
		}
	}

	public void testHexInt() throws Exception
	{
		String actual;
		String expected;
		int n;

		for(int i=0;i<NUM_RAND_TESTS;i++)
		{
			n = rand.nextInt();
			expected = zeroPad(Integer.toHexString(n),8);
			actual = Hex.hexInt(n);
			assertEquals(expected, actual);
		}
	}

	public void testHexLong() throws Exception
	{
		String actual;
		String expected;
		long l;

		for(int i=0;i<NUM_RAND_TESTS;i++)
		{
			l = rand.nextLong();
			expected = zeroPad(Long.toHexString(l),16);
			actual = Hex.hexLong(l);
			assertEquals(expected, actual);
		}
	}

	public void testUnhexNybble() throws Exception
	{
		for(int i=0;i<=9;i++)
			assertEquals(Hex.unhexNybble(Integer.toHexString(i).charAt(0)),i);
		for(int i=0xa;i<=0xf;i++)
			assertEquals(Hex.unhexNybble(Integer.toHexString(i).toLowerCase().charAt(0)),i);
		for(int i=0xa;i<=0xf;i++)
			assertEquals(Hex.unhexNybble(Integer.toHexString(i).toUpperCase().charAt(0)),i);
	}

	public void testUnhexByte() throws Exception
	{
		for(int i=0;i<=0xff;i++)
			assertEquals(Hex.unhexByte(zeroPad(Integer.toHexString(i).toLowerCase(),2)),(byte)i);
		for(int i=0;i<=0xff;i++)
			assertEquals(Hex.unhexByte(zeroPad(Integer.toHexString(i).toUpperCase(),2)),(byte)i);
	}

	public void testUnhexInt() throws Exception
	{
		for(int c=0;c<NUM_RAND_TESTS;c++)
		{
			int i = rand.nextInt();
			assertEquals(Hex.unhexInt(zeroPad(Integer.toHexString(i).toLowerCase(),8)),i);
		}
		for(int c=0;c<NUM_RAND_TESTS;c++)
		{
			int i = rand.nextInt();
			assertEquals(Hex.unhexInt(zeroPad(Integer.toHexString(i).toUpperCase(),8)),i);
		}
	}

	public void testUnhexLong() throws Exception
	{
		for(int c=0;c<NUM_RAND_TESTS;c++)
		{
			long l = rand.nextLong();
			assertEquals(Hex.unhexLong(zeroPad(Long.toHexString(l).toLowerCase(),16)),l);
		}
		for(int c=0;c<NUM_RAND_TESTS;c++)
		{
			long l = rand.nextLong();
			assertEquals(Hex.unhexLong(zeroPad(Long.toHexString(l).toUpperCase(),16)),l);
		}
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
