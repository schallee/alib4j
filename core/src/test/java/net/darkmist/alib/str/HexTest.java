/*
 *  Copyright (C) 2012 Ed Schaller <schallee@darkmist.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.darkmist.alib.str;

import java.nio.ByteBuffer;
import java.security.SecureRandom;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HexTest extends TestCase
{
	private static final Class<HexTest> CLASS = HexTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final SecureRandom rand = new SecureRandom();
	private static final int NUM_RAND_TESTS = 100;

	private static String zeroPad(String str, int len)
	{
		while(str.length()<len)
			str = "0" + str;
		return str;
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

	public void testHexIntStringBuilder()
	{
		String actual;
		String expected;
		int n;

		for(int i=0;i<NUM_RAND_TESTS;i++)
		{
			StringBuilder sb = new StringBuilder(8);
			n = rand.nextInt();
			expected = zeroPad(Integer.toHexString(n),8);
			Hex.hex(sb,n);
			actual = sb.toString();
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

	public void testDumpEmpty() throws Exception
	{
		ByteBuffer in = ByteBuffer.wrap(new byte[0]);
		String actual = Hex.dump(in);
		String expected =	"          0011 2233 4455 6677  8899 aabb ccdd eeff  0123456789abcdef\n";
		logger.debug("expected:\n{}", expected);
		logger.debug("actual:\n{}", actual);
		assertEquals(expected, actual);
	}

	public void testDumpMultiPartialLine() throws Exception
	{
		ByteBuffer in = ByteBuffer.wrap(ASCII.toBytes("abcdefghijklmnopqrstuvwxyz \u0000"));
		String actual = Hex.dump(in);
		String expected =	"          0011 2233 4455 6677  8899 aabb ccdd eeff  0123456789abcdef\n"
			+ 		"00000000: 6162 6364 6566 6768  696a 6b6c 6d6e 6f70  abcdefghijklmnop\n"
			+ 		"00000010: 7172 7374 7576 7778  797a 2000            qrstuvwxyz..\n";
		logger.debug("expected:\n{}", expected);
		logger.debug("actual:\n{}", actual);
		assertEquals(expected, actual);
	}

	public void testDumpMultiLine() throws Exception
	{
		ByteBuffer in = ByteBuffer.wrap(ASCII.toBytes("abcdefghijklmnopqrstuvwxyz0123 \u0000"));
		String actual = Hex.dump(in);
		String expected =	"          0011 2233 4455 6677  8899 aabb ccdd eeff  0123456789abcdef\n"
			+ 		"00000000: 6162 6364 6566 6768  696a 6b6c 6d6e 6f70  abcdefghijklmnop\n"
			+ 		"00000010: 7172 7374 7576 7778  797a 3031 3233 2000  qrstuvwxyz0123..\n";
		logger.debug("expected:\n{}", expected);
		logger.debug("actual:\n{}", actual);
		assertEquals(expected, actual);
	}

	public void testDumpSingleLine() throws Exception
	{
		ByteBuffer in = ByteBuffer.wrap(ASCII.toBytes("abcdefghijklmnop"));
		String actual = Hex.dump(in);
		String expected =	"          0011 2233 4455 6677  8899 aabb ccdd eeff  0123456789abcdef\n"
			+ 		"00000000: 6162 6364 6566 6768  696a 6b6c 6d6e 6f70  abcdefghijklmnop\n";
		logger.debug("expected:\n{}", expected);
		logger.debug("actual:\n{}", actual);
		assertEquals(expected, actual);
	}

	public void testDumpPartialLine() throws Exception
	{
		ByteBuffer in = ByteBuffer.wrap(ASCII.toBytes("abcdefghijklm"));
		String actual = Hex.dump(in);
		String expected =	"          0011 2233 4455 6677  8899 aabb ccdd eeff  0123456789abcdef\n"
			+ 		"00000000: 6162 6364 6566 6768  696a 6b6c 6d         abcdefghijklm\n";
		logger.debug("expected:\n{}", expected);
		logger.debug("actual:\n{}", actual);
		assertEquals(expected, actual);
	}
}
