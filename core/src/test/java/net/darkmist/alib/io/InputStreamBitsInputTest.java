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

package net.darkmist.alib.io;


import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;
import net.darkmist.alib.str.Binary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputStreamBitsInputTest extends TestCase
{
	private static final Class<InputStreamBitsInputTest> CLASS = InputStreamBitsInputTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	public void testOneBitFF20() throws IOException
	{
		byte[] bytes = new byte[20];
		ByteArrayInputStream bais;
		InputStreamBitsInput bits;

		Arrays.fill(bytes, (byte)0xFF);
		bais = new ByteArrayInputStream(bytes);
		bits = new InputStreamBitsInput(bais);
		for(int i=0;i<(Byte.SIZE*bytes.length);i++)
			assertEquals(1, bits.readBits(1));
		try
		{
			bits.readBits(1);
			fail("no EOFException received");
		}
		catch(EOFException e)
		{
			//expected;
		}
	}

	public void testOneBit0020() throws IOException
	{
		byte[] bytes = new byte[20];
		ByteArrayInputStream bais;
		InputStreamBitsInput bits;

		Arrays.fill(bytes, (byte)0x00);
		bais = new ByteArrayInputStream(bytes);
		bits = new InputStreamBitsInput(bais);
		for(int i=0;i<(Byte.SIZE*bytes.length);i++)
			assertEquals(0, bits.readBits(1));
		try
		{
			bits.readBits(1);
			fail("no EOFException received");
		}
		catch(EOFException e)
		{
			//expected;
		}
	}

	public void testOneBitAA20() throws IOException
	{
		byte[] bytes = new byte[20];
		ByteArrayInputStream bais;
		InputStreamBitsInput bits;

		Arrays.fill(bytes, (byte)0xAA);
		bais = new ByteArrayInputStream(bytes);
		bits = new InputStreamBitsInput(bais);
		for(int i=0;i<(Byte.SIZE*bytes.length);i++)
			if((i&0x1)==0x0)
			{
				logger.debug("i={} expecting 1", i);
				assertEquals(1, bits.readBits(1));
			}
			else
			{
				logger.debug("i={} expecting 0", i);
				assertEquals(0, bits.readBits(1));
			}
		try
		{
			bits.readBits(1);
			fail("no EOFException received");
		}
		catch(EOFException e)
		{
			//expected;
		}
	}

	public void testOneBit5520() throws IOException
	{
		byte[] bytes = new byte[20];
		ByteArrayInputStream bais;
		InputStreamBitsInput bits;

		Arrays.fill(bytes, (byte)0x55);
		bais = new ByteArrayInputStream(bytes);
		bits = new InputStreamBitsInput(bais);
		for(int i=0;i<(Byte.SIZE*bytes.length);i++)
			if((i&0x1)==0x1)
			{
				logger.debug("i={} expecting 1", i);
				assertEquals(1, bits.readBits(1));
			}
			else
			{
				logger.debug("i={} expecting 0", i);
				assertEquals(0, bits.readBits(1));
			}
		try
		{
			bits.readBits(1);
			fail("no EOFException received");
		}
		catch(EOFException e)
		{
			//expected;
		}
	}

	public void testOneBitA520() throws IOException
	{
		byte[] bytes = new byte[20];
		ByteArrayInputStream bais;
		InputStreamBitsInput bits;

		Arrays.fill(bytes, (byte)0xA5);
		bais = new ByteArrayInputStream(bytes);
		bits = new InputStreamBitsInput(bais);
		for(int i=0;i<(Byte.SIZE*bytes.length);i++)
			if((i&0x5)==0||(i&0x5)==0x5)
			{
				if(logger.isDebugEnabled())
					logger.debug("i={} ({}) expecting 1", i, Binary.toString(i));
				assertEquals(1, bits.readBits(1));
			}
			else
			{
				if(logger.isDebugEnabled())
					logger.debug("i={} ({}) expecting 0", i, Binary.toString(i));
				assertEquals(0, bits.readBits(1));
			}
		try
		{
			bits.readBits(1);
			fail("no EOFException received");
		}
		catch(EOFException e)
		{
			//expected;
		}
	}

	public void testThreeBitFF() throws IOException
	{
		byte[] bytes = new byte[20];
		ByteArrayInputStream bais;
		InputStreamBitsInput bits;

		Arrays.fill(bytes, (byte)0xFF);
		bais = new ByteArrayInputStream(bytes);
		bits = new InputStreamBitsInput(bais);
		for(int i=0;i<(Byte.SIZE*bytes.length)/3;i++)
			assertEquals(7, bits.readBits(3));
		try
		{
			bits.readBits(3);
			fail("no EOFException received");
		}
		catch(EOFException e)
		{
			//expected;
		}
	}

	public void testThreeBit00() throws IOException
	{
		byte[] bytes = new byte[20];
		ByteArrayInputStream bais;
		InputStreamBitsInput bits;

		Arrays.fill(bytes, (byte)0x00);
		bais = new ByteArrayInputStream(bytes);
		bits = new InputStreamBitsInput(bais);
		for(int i=0;i<(Byte.SIZE*bytes.length)/3;i++)
			assertEquals(0, bits.readBits(3));
		try
		{
			bits.readBits(3);
			fail("no EOFException received");
		}
		catch(EOFException e)
		{
			//expected;
		}
	}

	public void testThreeBitAA() throws IOException
	{
		byte[] bytes = new byte[20];
		ByteArrayInputStream bais;
		InputStreamBitsInput bits;

		Arrays.fill(bytes, (byte)0xAA);
		bais = new ByteArrayInputStream(bytes);
		bits = new InputStreamBitsInput(bais);
		for(int i=0;i<(Byte.SIZE*bytes.length)/3;i++)
		{
			if((i&0x1)==0x0)
				assertEquals(5, bits.readBits(3));
			else
				assertEquals(2, bits.readBits(3));
		}
		try
		{
			bits.readBits(3);
			fail("no EOFException received");
		}
		catch(EOFException e)
		{
			//expected;
		}
	}

	public void testThreeBit55() throws IOException
	{
		byte[] bytes = new byte[20];
		ByteArrayInputStream bais;
		InputStreamBitsInput bits;

		Arrays.fill(bytes, (byte)0x55);
		bais = new ByteArrayInputStream(bytes);
		bits = new InputStreamBitsInput(bais);
		for(int i=0;i<(Byte.SIZE*bytes.length)/3;i++)
		{
			if((i&0x1)==0x1)
				assertEquals(5, bits.readBits(3));
			else
				assertEquals(2, bits.readBits(3));
		}
		try
		{
			bits.readBits(3);
			fail("no EOFException received");
		}
		catch(EOFException e)
		{
			//expected;
		}
	}
}
