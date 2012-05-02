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

import java.io.IOException;
import java.io.EOFException;
import java.io.InputStream;

import net.darkmist.alib.str.Binary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputStreamBitsInput implements BitsInput
{
	private static final Class<InputStreamBitsInput> CLASS = InputStreamBitsInput.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private InputStream in;
	private long bits = 0;
	private int numBits = 0;
	private boolean eof = false;

	public InputStreamBitsInput(InputStream in)
	{
		this.in = in;
	}

	private void addBits(int b)
	{
		int finalNumBits;
		if((finalNumBits = numBits + Byte.SIZE) > Long.SIZE)
			throw new IllegalStateException("Attempt to add more bits than can be fit into the current buffer.");
		bits <<= Byte.SIZE;
		bits |= b&0xFF;
		numBits=finalNumBits;
	}

	private void fillBits() throws IOException
	{
		int bytesNeeded = ((Long.SIZE - numBits)/Byte.SIZE);
		byte[] buf;
		int amount;

		if(eof || bytesNeeded == 0)
			return;
		buf = new byte[bytesNeeded];

		if((amount=IOUtils.readFully(in,buf))<buf.length)
			eof = true;
		for(int i=0;i<amount;i++)
			addBits(buf[i]);
	}

	@Override
	public int readBits(int num) throws IOException
	{
		long out;
		int numBitsAfter;

		if(num > Integer.SIZE)
			throw new IllegalArgumentException("Number of bits requested (" + num + " is larger than the number of bits a integer can hold (" + Integer.SIZE + ')');
		if(num <= 0)
			throw new IllegalArgumentException("Number of bits requested (" + num + " is less than or equal to zero.");
		if(numBits < num)
		{
			if(eof)
				throw new EOFException("bits left=" + numBits + " bits needed=" + num);
			fillBits();
		}
		if(logger.isDebugEnabled())
		{
			logger.debug("bits={}", Binary.toString(bits));
			logger.debug("used={}", Binary.toString((~0l)>>>(Long.SIZE - numBits),' ','^'));
		}
		numBitsAfter = numBits - num;
		if(numBitsAfter < 0)
			throw new EOFException("bits left=" + numBits + " bits needed=" + num);

		// get mask for the bits we need
		out = (~(0l)) >>> (Long.SIZE - num);

		// shift mask into position
		out <<= numBitsAfter;

		// get bits
		out &= bits;

		// shift bits back into position
		out >>>= numBitsAfter;

		// fixup the bit count
		numBits = numBitsAfter;

		return (int)out;
	}

	@Override
	public boolean readBit() throws IOException
	{
		return (readBits(1)!=0x0);
	}

	@Override
	public void close()throws IOException
	{
		eof = true;
		numBits = 0;
		in.close();
	}
}
