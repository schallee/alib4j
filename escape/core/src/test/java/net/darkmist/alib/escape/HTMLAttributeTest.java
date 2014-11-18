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

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.concurrent.Callable;
import java.util.zip.GZIPInputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLAttributeTest extends BaseTest
{
	private static final Class<HTMLAttributeTest> CLASS = HTMLAttributeTest.class;
	private HTMLAttribute htmlAttr = HTMLAttribute.instance();

	private static int testAndDecodeChar(String encoded)
	{
		String hex;

		assertTrue(encoded.length() >= 4);
		assertEquals('&', encoded.charAt(0));
		assertEquals('#', encoded.charAt(1));
		assertEquals(';', encoded.charAt(encoded.length()-1));
		if(encoded.charAt(2) == 'x')
			return Integer.parseInt(encoded.substring(3, encoded.length()-1), 16);
		else
			return Integer.parseInt(encoded.substring(2, encoded.length()-1), 10);
	}

	public void testTest()
	{
		assertEquals("test", htmlAttr.escape("test"));
	}
	
	public void testTwoByteChars() throws Exception
	{
		executeForRange(Character.MIN_VALUE, (int)Character.MAX_VALUE + 1, new RangedCallableFactory()
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

						for(int i=start;i<stop;i++)
						{
							charStr = Character.toString((char)i);
							encoded = htmlAttr.escape(charStr);
				
							if(charStr.equals(encoded))
							{
								assertTrue(Util.isAlphaNumericOrWhiteSpace(i));
								continue;
							}
							assertEquals(i, testAndDecodeChar(encoded));
						}
						return true;
					}
				};
			}
		});
	}

	public void testSurrogates() throws Exception
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
						int codepoint;

						for(char high=(char)(start&0xFFFF);high<(char)(stop&0xFFFF);high++)
						{
							array[0] = high;
							for(char low=0xDC00;low<=0xDFFF;low++)
							{
								array[1] = low;
								str = String.valueOf(array);
								assertEquals(1, str.codePointCount(0,2));
								encoded = htmlAttr.escape(str);
								codepoint = str.codePointAt(0);
								assertEquals(codepoint, testAndDecodeChar(encoded));
							}
						}

						return true;
					}
				};
			}
		});
	}

}
