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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

class Util
{
	public static final String STRING_BUILDER_IO_EXCEPTION = "IOException appending to StringBuilder cast to Appendable?";
	public static final String STRING_BUFFER_IO_EXCEPTION = "IOException appending to StringBuffer cast to Appendable?";
	private static final int DEFAULT_CACHE_SIZE = 128;
	private static final String CACHE_SIZE_PROP_NAME = "cacheSize";

	private Util()
	{
	}

	@SuppressFBWarnings(value="MRC_METHOD_RETURNS_CONSTANT", justification="For convinence in changing without rebuilting everything")
	static int getCacheSizeDefault()
	{
		return DEFAULT_CACHE_SIZE;
	}

	@SuppressFBWarnings(value="MRC_METHOD_RETURNS_CONSTANT", justification="For convinence in changing without rebuilting everything")
	static String getCacheSizePropName()
	{
		return CACHE_SIZE_PROP_NAME;
	}

	static boolean isAlpha(int ch)
	{
		switch(ch)
		{
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
				return true;
			default:
				return false;
		}
	}

	static boolean isDigit(int ch)
	{
		switch(ch)
		{
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				return true;
			default:
				return false;
		}
	}

	static boolean isAlphaNumeric(int ch)
	{
		switch(ch)
		{
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				return true;
			default:
				return false;
		}
	}

	static boolean isWhiteSpace(int ch)
	{
		switch(ch)
		{
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				return true;
			default:
				return false;
		}
	}

	static boolean isAlphaNumericOrWhiteSpace(int ch)
	{
		switch(ch)
		{
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				return true;
			default:
				return false;
		}
	}

	static int getIntProp(Class<?> cls, String name, int def)
	{
		String prop;

		try
		{
			prop = System.getProperty(cls.getName() + '.' + name);
		}
		catch(SecurityException ignored)
		{
			return def;
		}
		if(prop == null)
			return def;
		try
		{
			return Integer.parseInt(prop);
		}
		catch(NumberFormatException e)
		{
			return def;
		}
	}

	static int getPositiveIntProp(Class<?> cls, String name, int def)
	{
		int val;

		if((val = getIntProp(cls, name, def))<0)
			return def;
		return val;
	}

}
