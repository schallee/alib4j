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
import java.io.OutputStream;

import net.darkmist.alib.lang.NullSafe;
import static net.darkmist.alib.lang.NullSafe.requireNonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link BitsOutput} implementation that writes to a
 * {@link OutputStream}.
 */
public class OutputStreamBitsOutput implements BitsOutput
{
	private static final Class<OutputStreamBitsOutput> CLASS = OutputStreamBitsOutput.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/** Stream to write to. */
	private OutputStream out;
	/**
	 * Any buffered partial byte. Unused bits are on the least
	 * significant end.
	 */
	private int currentBits = 0;
	/**
	 * The number of bits left in the current partial byte.
	 */
	private int numBitsLeft = Byte.SIZE;
	/** Flag for whether we're closed. */
	private boolean closed = false;
	/** Flag for if an error occured. */
	private boolean error = false;

	/**
	 * Construct using the given stream.
	 * @param out The output stream to write to.
	 */
	public OutputStreamBitsOutput(OutputStream out)
	{
		this.out = requireNonNull(out, "out");
	}

	/**
	 * Validate that we are not closed or have experianced errors.
	 * @throws IOException if closed or errors have occured.
	 */
	protected void checkState() throws IOException
	{
		if(closed)
			throw new IOException("Operation on closed output " + out + " attempted.");
		if(error)
			throw new IOException("Operation on output " + out + " that has already had an error attempted.");
	}

	/**
	 * Write one byte, setting error flag on exception.
	 * @param b byte to write
	 * @throws IOException is rethrown after setting {@link #error}
	 *	if the underlying {@link OutputStream#write(int)}
	 *	threw one.
	 */
	protected void wrappedWrite(int b) throws IOException
	{
		checkState();
		try
		{
			out.write(b);
		}
		catch(IOException e)
		{
			error = true;
			throw e;
		}
	}

	/**
	 * Write the current byte and reset state.
	 * @throws IOException if the underlying {@link
	 * 	OutputStream#write(int)} does.
	 */
	private void writeCurrent() throws IOException
	{
		wrappedWrite(currentBits);
		currentBits=0;
		numBitsLeft=Byte.SIZE;
	}

	/**
	 * Writes bits to the output without checking arguments.
	 * Any currently incomplete byte is first completed and
	 * written. Bits left after last full byte is writeen are buffered
	 * until the next write.
	 * @param numAddBits The number of bits to write
	 * @param addBits The integer containing the bits to write.
	 * @throws IOException if the underlying
	 * 	{@link OutputStream#write(int)} does.
	 */
	protected void uncheckedWriteBits(int numAddBits, int addBits) throws IOException
	{
		int chunks;

		if(numAddBits < numBitsLeft)	// not equal
		{	// all added bits fit in currentBits

			// clean other bits
			addBits &= (0xFF >>> (8-numAddBits));

			// move addBits to position
			addBits <<= numBitsLeft - numAddBits;

			// add addBits to currentBits
			currentBits |= addBits;

			// fixup numBitsLeft
			numBitsLeft -= numAddBits;
			return;
		}

		// all bits left, also cleans any high bits
		addBits <<= Integer.SIZE - numAddBits;

		// shift numBitsLeft bits to right, add to current bits
		currentBits |= addBits >>> (Integer.SIZE - numBitsLeft);

		// remove used bits from addBits
		numAddBits -= numBitsLeft;
		addBits <<= numBitsLeft;

		// write the now full current byte
		writeCurrent();

		// how many byte sized chunks? numAddBits/8
		chunks = numAddBits >> 3;

		// write out each byte directly
		for(int i=0;i<chunks;i++)
		{
			// next byte to lsb
			int tmpBits = addBits >>> (Integer.SIZE - Byte.SIZE);

			// write out byte
			wrappedWrite(tmpBits);

			// clear out byte
			addBits <<= Byte.SIZE;
		}

		// figure out what we have left. numAddBits%8
		if((numAddBits &= 0x7)==0)
			return;	// nothing left.

		// set current bits to what is left
		currentBits = addBits >>> (Integer.SIZE - Byte.SIZE);

		// set to however many bits are left. %8
		numBitsLeft = Byte.SIZE - numAddBits;
	}

	/** {@inheritDoc} */
	@Override
	public void writeBits(int num, int bits) throws IOException
	{
		if(num > Integer.SIZE)
			throw new IllegalArgumentException("Num is larger than the number of bits that can be held in bits. (" + num + " > " + Integer.SIZE + ')');
		if(num <= 0)
			throw new IllegalArgumentException("Num (" + num + ") is less than or equal to zero.");
		checkState();
		uncheckedWriteBits(num, bits);
	}

	/** {@inheritDoc} */
	@Override
	public void writeBit(boolean bit) throws IOException
	{
		writeBits(1,bit ? 1 : 0);
	}

	/**
	 * Internal version of {@link #writeTillByte(boolean)} that does
	 * not check flag state before operating.
	 * @param bit to fill any partial byte with.
	 */
	protected void uncheckedWriteTillByte(boolean bit) throws IOException
	{
		if(numBitsLeft != Byte.SIZE)
			writeBits(numBitsLeft, bit ? ~0 : 0);
	}

	/** {@inheritDoc} */
	@Override
	public void writeTillByte(boolean bit) throws IOException
	{
		checkState();
		uncheckedWriteTillByte(bit);
	}

	/**
	 * {@inheritDoc}
	 * @return true always as this implementation writes to a byte
	 * 	stream and always knows the alignment.
	 */
	@Override
	public boolean isWriteTillByteSupported()
	{
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void flush() throws IOException
	{
		checkState();
		uncheckedWriteTillByte(false);
		try
		{
			out.flush();
		}
		catch(IOException e)
		{
			error = true;
			throw e;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void close() throws IOException
	{
		if(closed)
			return;
		checkState();
		uncheckedWriteTillByte(false);
		closed = true;
		try
		{
			out.close();
		}
		catch(IOException e)
		{
			error = true;
			throw e;
		}
		finally
		{
			out = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @return true always as this implementation writes to a byte
	 * 	stream and always has to write full bytes.
	 */
	 @Override
	 public boolean isByteAligning()
	 {
	 	return true;
	 }

	 @Override
	 public boolean equals(Object o)
	 {
		 if(this==o)
			 return true;
		 if(!(o instanceof OutputStreamBitsOutput))
			 return false;
		 OutputStreamBitsOutput that = (OutputStreamBitsOutput)o;
		 if(this.currentBits != that.currentBits)
			 return false;
		 if(this.numBitsLeft != that.numBitsLeft)
			 return false;
		 if(this.closed != that.closed)
			 return false;
		 if(this.error != that.error)
			 return false;
		 return NullSafe.equals(this.out, that.out);
	 }

	 @Override
	 public int hashCode()
	 {
		 return NullSafe.hashCode(currentBits, numBitsLeft, closed, error, out);
	 }

	 @Override
	 public String toString()
	 {
		 return getClass().getSimpleName() + ": out=" + out + " currentBits=" + currentBits + " numBitsLeft=" + numBitsLeft + " closed=" + closed + " error=" + error;
	 }
}
