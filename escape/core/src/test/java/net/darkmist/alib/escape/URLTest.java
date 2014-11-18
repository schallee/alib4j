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

package net.darkmist.alib.escape;

import java.util.concurrent.Callable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLTest extends BaseTest
{
	private static final Class<URLTest> CLASS = URLTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private URL url = URL.instance();

	private static int testAndDecodeChar(String encoded, int off, int len)
	{
		assertEquals('%', encoded.charAt(off));
		if(len != 3)
			fail("Encoded character had length of " + encoded.length() + " which is not 3.");
		return Integer.parseInt(encoded.substring(off+1, off+len), 16);
	}

	private static int testAndDecodeChar(String encoded)
	{
		return testAndDecodeChar(encoded, 0, encoded.length());
	}

	public void testTest()
	{
		assertEquals("test", url.escape("test"));
	}
	
	public void testOneByteCharsWork()
	{
		String encoded = "";
		String charStr;
		for(int i=0x00;i<0x100;i++)
		{
			charStr = Character.toString((char)i);

			encoded = url.escape(charStr);
			switch(i)
			{
				case '-':
				case '_':
				case '.':
					assertEquals(charStr, encoded);
					continue;
				default:
					if(Util.isAlphaNumeric(i))
						assertEquals(charStr, encoded);
					else
						assertEquals(i, testAndDecodeChar(encoded));
			}
		}
	}

	private int testDecodeMultiByte(String encoded)
	{
		byte[] decodedBytes;
		String decoded;
		int escapedOff;
		int byteOff;

		assertEquals(0,encoded.length() % 3);
		decodedBytes = new byte[encoded.length()/3];
		for(byteOff=0,escapedOff=0;escapedOff<encoded.length();byteOff++,escapedOff+=3)
			decodedBytes[byteOff] = (byte)testAndDecodeChar(encoded, escapedOff, 3);
		decoded = new String(decodedBytes, Charsets.UTF8);
		assertEquals(1, decoded.codePointCount(0, decoded.length()));
		return decoded.codePointAt(0);
	}

	public void testTwoByteCharsWork() throws Exception
	{
		executeForRange(0x100, (int)Character.MAX_VALUE + 1, new RangedCallableFactory()
		{
			@Override
			public Callable<Boolean> createCallableForRange(final int start, final int stop)
			{
				return new Callable<Boolean>()
				{
					@Override
					public Boolean call()
					{
						String encoded;
						String charStr;
						int decoded;

						for(int i=start;i<stop;i++)
						{
							if(0xD800 <= i && i <= 0xDFFF)
								continue;	// skip surrogates...
							charStr = Character.toString((char)i);
							encoded = url.escape(charStr);
							decoded = testDecodeMultiByte(encoded);
							assertEquals(i, decoded);
						}
						return true;
					}
				};
			}
		});
	}

	public void testSurrogatesWork() throws Exception
	{
		executeForRange(0xD800, 0xDBFF + 1, new RangedCallableFactory()
		{
			@Override
			public Callable<Boolean> createCallableForRange(final int start, final int stop)
			{
				return new Callable<Boolean>()
				{
					@Override
					public Boolean call()
					{
						char array[] = new char[2];
						String str;
						String encoded;
						int decoded;

						for(char high=(char)(start&0xFFFF);high<(char)(stop&0xFFFF);high++)
						{
							array[0] = high;
							for(char low=0xDC00;low<=0xDFFF;low++)
							{
								array[1] = low;
								str = String.valueOf(array);
								// make sure we built a proper char...
								assertEquals(1, str.codePointCount(0,2));
								encoded = url.escape(str);
								decoded = testDecodeMultiByte(encoded);
								assertEquals(str.codePointAt(0), decoded);
							}
						}

						return true;
					}
				};
			}
		});
	}
}
