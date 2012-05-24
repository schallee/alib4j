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

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferUtil
{
	private static final Class<BufferUtil> CLASS = BufferUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0).asReadOnlyBuffer();

	public static ByteBuffer getEmptyBuffer()
	{
		return EMPTY_BUFFER;
	}

	/**
	 * Allocate a buffer and fill it from a channel. The returned
	 * buffer will be rewound to the begining.
	 * @return Buffer containing size bytes from the channel.
	 * @throws IOException if the channel read does.
	 * @throws EOFException if a end of stream is encountered
	 * 	before the full size is read.
	 */
	public static ByteBuffer allocateAndReadAll(int size, ReadableByteChannel channel) throws IOException
	{
		ByteBuffer buf = ByteBuffer.allocate(size);
		int justRead;
		int totalRead = 0;

		// FIXME, this will be a tight loop if the channel is non-blocking...
		while(totalRead < size)
		{
			logger.debug("reading totalRead={}", totalRead);
			if((justRead = channel.read(buf))<0)
				throw new EOFException("Unexpected end of stream after reading " + totalRead + " bytes");
			totalRead += justRead;
		}
		buf.rewind();
		return buf;
	}

	public static void writeAll(ByteBuffer buf, WritableByteChannel channel) throws IOException
	{
		while(buf.remaining() > 0)
			channel.write(buf);
	}

	public static byte[] asBytes(ByteBuffer buf)
	{
		buf = buf.duplicate();
		/* To use buf.array() the buffer must:
		 * 	be writable as the array will be writable
		 * 	have arrayOffset() == 0 or the array will not start at the right location
		 * 	the returned array must be the same length as the buffer's limit or it will be the wrong size.
		 */
		if(!buf.isReadOnly() && buf.hasArray() && buf.arrayOffset() == 0)
		{
			logger.debug("read-only, hasArray && offset is 0");
			byte[] ret = buf.array();

			if(ret.length == buf.limit())
				return buf.array();
			logger.debug("length of array !=limit, doing copy...");
		}

		byte[] bytes = new byte[buf.limit()];
		buf.get(bytes,0,buf.limit());
		return bytes;
	}

	/* someday I'll write the readUTF and test this all out...
	private static class ByteBufferDataInput implements DataInput
	{
		private ByteBuffer buf;

		public ByteBufferDataInput(ByteBuffer buf)
		{
			this.buf = buf;
		}

		public void readFully(byte[] bytes) throws IOException
		{
			try
			{
				buf.get(bytes);
			}
			catch(BufferUndeflowException e)
			{
				throw EOFException(e);
			}
		}

		public void readFully(byte[] bytes, int off, int len) throws IOException
		{
			try
			{
				buf.get(bytes, off, len);
			}
			catch(BufferUndeflowException e)
			{
				throw EOFException(e);
			}
		}

		public int skipBytes(int i) throws IOException
		{
			try
			{
				buf.position(buf.position() + i);
			}
			catch(IllegalArgumentException e)
			{
				throw new EOFException(e);
			}
		}

		public boolean readBoolean() throws IOException
		{
			return readByte()!=0;
		}

		public byte readByte() throws IOException
		{
			try
			{
				return buf.get();
			}
			catch(BufferUndeflowException e)
			{
				throw EOFException(e);
			}
		}

		public int readUnsignedByte() throws IOException
		{
			return (int)readByte() & 0xFF;
		}

		public short readShort() throws IOException
		{
			try
			{
				return buf.getShort();
			}
			catch(BufferUndeflowException e)
			{
				throw EOFException(e);
			}
		}

		public int readUnsignedShort() throws IOException
		{
			return (int)readShort() & 0xffff;
		}

		public char readChar() throws IOException
		{
			try
			{
				return buf.getChar();
			}
			catch(BufferUndeflowException e)
			{
				throw EOFException(e);
			}
		}

		public int readInt() throws IOException
		{
			try
			{
				return buf.getInt();
			}
			catch(BufferUndeflowException e)
			{
				throw EOFException(e);
			}
		}

		public long readLong() throws IOException
		{
			try
			{
				return buf.getLong();
			}
			catch(BufferUndeflowException e)
			{
				throw EOFException(e);
			}
		}

		public float readFloat() throws IOException
		{
			try
			{
				return buf.getFloat();
			}
			catch(BufferUndeflowException e)
			{
				throw EOFException(e);
			}
		}

		public double readDouble() throws IOException
		{
			try
			{
				return buf.getDouble();
			}
			catch(BufferUndeflowException e)
			{
				throw EOFException(e);
			}
		}

		public String readLine() throws IOException
		{
			StringBuilder sb = new sb(buf.remaining());
			byte b;
			boolean lastWasCR = false;

			while(buf.hasRemaining())
			{
				switch((b = buf.get()))
				{
					case '\n':
						return sb.toString();
					case '\r':
						if(lastWasCR)
							sb.append('\r');
						else
							lastWasCR = true;
						break;
					default:
						if(lastWasCR)
						{
							sb.append('\r');
							lastWasCR = false;
						}
						sb.append(b);
						break;
				}
			}
			if(sb.length() == 0)
				return null;
			return sb.toString();
		}
		public String readUTF() throws IOException
		{
		}
	}
	*/

	private static class ByteBufferInputStream extends InputStream
	{
		private ByteBuffer buf;
		private int mark = -1;

		ByteBufferInputStream(ByteBuffer buf)
		{
			this.buf = buf;
		}

		@Override
		public int read(byte[] bytes) throws IOException
		{
			int num;

			if(bytes == null)
				throw new NullPointerException("Byte buffer is null");
			if(bytes.length == 0)
				return 0;
			if(!buf.hasRemaining())
				return -1;
			num = Math.min(buf.remaining(), bytes.length);
			buf.get(bytes, 0, num);
			return num;
		}

		@Override
		public int read(byte[] bytes, int off, int len) throws IOException
		{
			if(bytes == null)
				throw new NullPointerException("Byte buffer is null");
			if(off >= bytes.length)
				throw new IllegalArgumentException("Offset is larger than or equal to byte array length");
			if(off < 0)
				throw new IllegalArgumentException("Offset is negative");
			if(len < 0)
				throw new IllegalArgumentException("Length is negative");
			if(len + off > bytes.length)
				throw new IllegalArgumentException("Length + off set is larger than byte array length");

			int num;

			if(len == 0)
				return 0;
			if(!buf.hasRemaining())
				return -1;
			num = Math.min(buf.remaining(), len);
			buf.get(bytes, off, num);
			return num;
		}

		@Override
		public int read() throws IOException
		{
			if(buf.hasRemaining())
				return buf.get() & 0xFF;
			return -1;
		}

		@Override
		public int available() throws IOException
		{
			return buf.remaining();
		}

		@Override
		public long skip(long amount) throws IOException
		{
			int bufRemaining = buf.remaining();

			if(amount <= 0)
				return 0;
			if(bufRemaining == 0)
				return 0;
			if(bufRemaining < 0)
				throw new IllegalStateException("Remaining is negative");
			if(bufRemaining < amount)
			{
				buf.position(buf.position() + bufRemaining);
				return bufRemaining;
			}
			buf.position((int)(buf.position() + amount));
			return amount;
		}

		/**
		 * @param ignored as the mark is taken from the underlying buffer position.
		 */
		@Override
		public synchronized void mark(@SuppressWarnings("unused") int ignored)
		{
			mark = buf.position();
		}

		@Override
		public synchronized void reset() throws IOException
		{
			buf.position(mark);
		}

		@Override
		public boolean markSupported()
		{
			return true;
		}
	}

	public static InputStream asInputStream(ByteBuffer buf)
	{
		return new ByteBufferInputStream(buf);
	}

	public static DataInput asDataInput(ByteBuffer buf)
	{
		return new DataInputStream(asInputStream(buf));
	}

	/**
	 * Sane ByteBuffer slice
	 * @param buf the buffer to slice something out of
	 * @param off The offset into the buffer
	 * @param len the length of the part to slice out
	 */
	public static ByteBuffer slice(ByteBuffer buf, int off, int len)
	{
		ByteBuffer localBuf=buf.duplicate();	// so we don't mess up the position,etc
		logger.debug("off={} len={}", off, len);
		localBuf.position(off);
		localBuf.limit(off+len);
		logger.debug("pre-slice: localBuf.position()={} localBuf.limit()={}", localBuf.position(), localBuf.limit());
		localBuf = localBuf.slice();
		logger.debug("post-slice: localBuf.position()={} localBuf.limit()={}", localBuf.position(), localBuf.limit());
		return localBuf;
	}

	public static ByteBuffer map(File file) throws IOException
	{
		FileInputStream fin=null;
		FileChannel fc=null;

		try
		{
			fin = new FileInputStream(file);
			fc = fin.getChannel();
			return fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		}
		finally
		{
			fc = Closer.close(fc);
			fin = Closer.close(fin);
		}
	}

	public static ByteBuffer mapFile(String path) throws IOException
	{
		return map(new File(path));
	}

	public static boolean isAll(ByteBuffer buf, byte b)
	{
		buf = buf.duplicate();

		while(buf.hasRemaining())
			if(buf.get() != b)
				return false;
		return true;
	}

	public static boolean isAllZero(ByteBuffer buf)
	{
		return isAll(buf, (byte)0);
	}

	public static ByteBuffer asBuffer(InputStream in) throws IOException
	{
		return ByteBuffer.wrap(IOUtils.toByteArray(in));
	}

	public static ByteBuffer asBuffer(InputStream in, int len) throws IOException
	{
		return ByteBuffer.wrap(Slurp.slurp(in,len));
	}

	public static ByteBuffer asBuffer(InputStream in, long len) throws IOException
	{
		return ByteBuffer.wrap(Slurp.slurp(in,len));
	}

	public static ByteBuffer asBuffer(DataInput in) throws IOException
	{
		return ByteBuffer.wrap(Slurp.slurp(in));
	}

	public static ByteBuffer asBuffer(DataInput in, int len) throws IOException
	{
		return ByteBuffer.wrap(Slurp.slurp(in,len));
	}

	public static ByteBuffer asBuffer(DataInput in, long len) throws IOException
	{
		return ByteBuffer.wrap(Slurp.slurp(in,len));
	}

}
