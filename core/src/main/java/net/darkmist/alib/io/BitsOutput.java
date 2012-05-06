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

import java.io.Closeable;
import java.io.IOException;
import java.io.Flushable;

public interface BitsOutput extends Closeable, Flushable
{
	/**
	 * Write bits to output.
	 * @param num the number of bits to write
	 * @param bits the integer containing the bits to write. Bits
	 * to be written should occupy the least significant end of the
	 * int. Extranious bits are ignored.
	 * @throws IOException if writing the bit causes an error. Such
	 *	an error may not actually occur until a full byte is
	 * 	ready to be written to an underlying stream.
	 */
	public void writeBits(int num, int bits) throws IOException;

	/**
	 * Write one bit.
	 * @param bit the bit to write.
	 * @throws IOException if writing the bit causes an error. Such
	 *	an error may not actually occur until a full byte is
	 * 	ready to be written to an underlying stream.
	 */
	public void writeBit(boolean bit) throws IOException;

	/**
	 * Use the given bit for any bits needed to byte align the
	 * current output. If the output is already byte aligned,
	 * nothing is written.
	 * @param bit The bit to fill with
	 * @throws IOException if writing the bit causes an error. Such
	 *	an error may not actually occur until a full byte is
	 * 	ready to be written to an underlying stream.
	 * @throws UnsupportedOperationException if the implementation
	 *	does not support his operation. This may be the case if
	 *	a implementation does not know the initial alignment or
	 * 	does not keep track of alignment.
	 */
	public void writeTillByte(boolean bit) throws IOException;

	/**
	 * Report wether the {@link #writeTillByte(boolean)} method
	 * is supported for this instance (but not necesarily all
	 * instances) of this implementation.
	 * @return true if supported. false otherwise.
	 */
	public boolean isWriteTillByteSupported();

	/**
	 * Writes any buffered data. Instances where
	 * {@link #isByteAligning()} returns true will pad any partial
	 * byte to a full byte with zero bits before writing it.
	 */
	@Override
	public void flush() throws IOException;

	/**
	 * Report wether {@link #flush()} and {@link #close()} pad output
	 * to a full byte using zero bits. This returning true implies
	 * {@link #isWriteTillByteSupported()} also returns true.
	 * @return true if padding will occur. false otherwise.
	 */
	public boolean isByteAligning();

	/**
	 * Closes the output writing any buffered data first. Instances
	 * where {@link #isByteAligning()} returns true will pad any
	 * partial byte to a full byte with zero bits before writing it.
	 */
	@Override
	public void close() throws IOException;
}
