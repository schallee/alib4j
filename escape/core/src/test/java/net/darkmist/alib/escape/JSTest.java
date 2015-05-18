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

public class JSTest extends BaseTest
{
	private static final Class<JSTest> CLASS = JSTest.class;

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
				break;	// unnecessary but make the compiler with -Xlint happier...
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
	
	public void testTwoByteCharsWork() throws Exception
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
						String encoded = "";
						String charStr;

						for(int i=start;i<stop;i++)
						{
							charStr = Character.toString((char)i);
							encoded = js.escape(charStr);
				
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
						int codepoint;

						for(char high=(char)(start&0xFFFF);high<(char)(stop&0xFFFF);high++)
						{
							array[0] = high;
							for(char low=0xDC00;low<=0xDFFF;low++)
							{
								array[1] = low;
								str = String.valueOf(array);
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

						return true;
					}
				};
			}
		});
	}
}
