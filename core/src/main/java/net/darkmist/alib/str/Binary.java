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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class Binary
{
	private static final Class<Binary> CLASS = Binary.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/**
	 * Currently only static methods so a private constructor.
	 */
	private Binary()
	{
	}

	/**
	 * Convert a long to a binary string.
	 * This is similar to {@link Long#toString(long,int) Long.toString(bits,2)} except that bits
	 * is handled as unsigned and the result is zero padded to the
	 * full number of bits.
	 * @param bits The long to convert
	 * @param zero The character to use for a zero bit.
	 * @param one The character to use for a one bit.
	 * @return a String representing bits in binary.
	 */
	public static String toString(long bits, char zero, char one)
	{
		StringBuilder sb = new StringBuilder(Long.SIZE);
			
		for(long mask = 0x8000000000000000l;mask!=0;mask>>>=1)
			if((mask & bits)==0)
				sb.append(zero);
			else
				sb.append(one);
		return sb.toString();
	}

	/**
	 * Convert a long to a binary string.
	 * This is similar to {@link Long#toString(long,int) Long.toString(bits,2)} except that bits
	 * is handled as unsigned and the result is zero padded to the
	 * full number of bits.
	 * @param bits The long to convert
	 * @return a String representing bits in binary.
	 */
	public static String toString(long bits)
	{
		return toString(bits, '0', '1');
	}

	/**
	 * Convert a int to a binary string.
	 * This is similar to {@link Integer#toString(int,int) Integer.toString(bits,2)} except that bits
	 * is handled as unsigned and the result is zero padded to the
	 * full number of bits.
	 * @param bits The int to convert
	 * @param zero The character to use for a zero bit.
	 * @param one The character to use for a one bit.
	 * @return a String representing bits in binary.
	 */
	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public static String toString(int bits, char zero, char one)
	{
		StringBuilder sb = new StringBuilder(Integer.SIZE);
			
		for(int mask = 0x80000000;mask!=0;mask>>>=1)
			if((mask & bits)==0)
				sb.append(zero);
			else
				sb.append(one);
		return sb.toString();
	}

	/**
	 * Convert a int to a binary string.
	 * This is similar to {@link Integer#toString(int,int) Integer.toString(bits,2)} except that
	 * bits is handled as unsigned and the result is zero padded to
	 * the full number of bits.
	 * @param bits The int to convert
	 * @return a String representing bits in binary.
	 */
	public static String toString(int bits)
	{
		return toString(bits, '0', '1');
	}

	/**
	 * Convert a short to a binary string.
	 * This is similar to {@link Integer#toString(int,int) Integer.toString(bits,2)} except that bits
	 * is handled as unsigned and the result is zero padded to the
	 * full number of bits.
	 * @param bits The short to convert
	 * @param zero The character to use for a zero bit.
	 * @param one The character to use for a one bit.
	 * @return a String representing bits in binary.
	 */
	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public static String toString(short bits, char zero, char one)
	{
		StringBuilder sb = new StringBuilder(Integer.SIZE);
			
		for(short mask = (short)0x8000;mask!=0;mask=(short)((mask&0xffff)>>>1))
			if((mask & bits)==0)
				sb.append(zero);
			else
				sb.append(one);
		return sb.toString();
	}

	/**
	 * Convert a short to a binary string.
	 * This is similar to {@link Integer#toString(int,int) Integer.toString(bits,2)} except that
	 * bits is handled as unsigned and the result is zero padded to
	 * the full number of bits.
	 * @param bits The short to convert
	 * @return a String representing bits in binary.
	 */
	public static String toString(short bits)
	{
		return toString(bits, '0', '1');
	}

	/**
	 * Convert a byte to a binary string.
	 * This is similar to {@link Integer#toString(int,int) Integer.toString(bits,2)} except that bits
	 * is handled as unsigned and the result is zero padded to the
	 * full number of bits.
	 * @param bits The byte to convert
	 * @param zero The character to use for a zero bit.
	 * @param one The character to use for a one bit.
	 * @return a String representing bits in binary.
	 */
	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public static String toString(byte bits, char zero, char one)
	{
		StringBuilder sb = new StringBuilder(Integer.SIZE);
			
		for(byte mask = (byte)0x80;mask!=0;mask=(byte)((mask&0xff)>>>1))
			if((mask & bits)==0)
				sb.append(zero);
			else
				sb.append(one);
		return sb.toString();
	}

	/**
	 * Convert a byte to a binary string.
	 * This is similar to {@link Integer#toString(int,int) Integer.toString(bits,2)} except that
	 * bits is handled as unsigned and the result is zero padded to
	 * the full number of bits.
	 * @param bits The byte to convert
	 * @return a String representing bits in binary.
	 */
	public static String toString(byte bits)
	{
		return toString(bits, '0', '1');
	}
}
