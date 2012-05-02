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

package net.darkmist.alib.math;

public class Bits
{
	/** Only static methods so private constructor */
	private Bits()
	{
	}

	public static int reverseByte(int in)
	{
		/*
		in &= 0xFF;
		return	  (in & 0x01)<<7
			| (in & 0x02)<<5
			| (in & 0x04)<<3
			| (in & 0x08)<<1
			| (in & 0x10)>>1
			| (in & 0x20)>>3
			| (in & 0x40)>>5
			| (in & 0x80)>>7;
		*/
		int out = 0;
		for(int i=0;i<Byte.SIZE;i++)
		{
			out <<= 1;
			if((in & 0x1)!=0)
				out |= 0x1;
			in >>>= 1;
		}
		return out;
	}

	public static byte reverse(byte in)
	{
		return (byte)reverseByte(in);
	}

	public static int reverseShort(int in)
	{
		int out = 0;
		for(int i=0;i<Short.SIZE;i++)
		{
			out <<= 1;
			if((in & 0x1)!=0)
				out |= 0x1;
			in >>>= 1;
		}
		return out;
	}

	public static short reverse(short in)
	{
		return (short)reverseShort(in);
	}

	public static int reverseInt(int in)
	{
		int out = 0;
		for(int i=0;i<Integer.SIZE;i++)
		{
			out <<= 1;
			if((in & 0x1)!=0)
				out |= 0x1;
			in >>>= 1;
		}
		return out;
	}

	public static int reverse(int in)
	{
		return reverseInt(in);
	}

	public static long reverseLong(long in)
	{
		long out = 0;
		for(int i=0;i<Long.SIZE;i++)
		{
			out <<= 1;
			if((in & 0x1L)!=0)
				out |= 0x1L;
			in >>>= 1;
		}
		return out;
	}

	public static long reverse(long in)
	{
		return reverseLong(in);
	}
}
