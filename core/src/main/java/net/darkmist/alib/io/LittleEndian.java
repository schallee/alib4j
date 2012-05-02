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
import java.io.DataOutput;
import java.io.IOException;

class LittleEndian
{
	/**
	 * Wraps a DataOutput so that short, int &amp; long are written
	 * in little endian. Note that the float &amp; double throw
	 * {@link java.lang.UnsupportedOperationException}.
	 */
	public static DataOutput wrap(final DataOutput toWrap)
	{
		return new DataOutput()
		{
			@Override
			public void write(int i) throws IOException
			{
				toWrap.write(i);
			}

			@Override
			public void write(byte[] b) throws IOException
			{
				toWrap.write(b);
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException
			{
				toWrap.write(b,off,len);
			}

			@Override
			public void writeBoolean(boolean b) throws IOException
			{
				toWrap.writeBoolean(b);
			}

			@Override
			public void writeByte(int i) throws IOException
			{
				toWrap.writeByte(i);
			}

			@Override
			public void writeShort(int i) throws IOException
			{
				toWrap.writeShort(Short.reverseBytes((short)i));
			}

			@Override
			public void writeChar(int i) throws IOException
			{
				toWrap.writeChar(i);
			}

			@Override
			public void writeInt(int i) throws IOException
			{
				toWrap.writeInt(Integer.reverseBytes(i));
			}

			@Override
			public void writeLong(long l) throws IOException
			{
				toWrap.writeLong(Long.reverseBytes(l));
			}

			/**
			 * @throws UnsupportedOperationException always.
			 */
			@Override
			public void writeFloat(@SuppressWarnings("unused") float f) throws IOException
			{
				throw new UnsupportedOperationException();
			}

			/**
			 * @throws UnsupportedOperationException always
			 */
			@Override
			public void writeDouble(@SuppressWarnings("unused") double d) throws IOException
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public void writeBytes(String s) throws IOException
			{
				toWrap.writeBytes(s);
			}

			@Override
			public void writeChars(String s) throws IOException
			{
				toWrap.writeChars(s);
			}

			@Override
			public void writeUTF(String s) throws IOException
			{
				toWrap.writeUTF(s);
			}

			@SuppressWarnings("unused")
			public DataOutput getWrapped()
			{
				return toWrap;
			}

			@Override
			protected Object clone() throws CloneNotSupportedException
			{
				throw new CloneNotSupportedException();
			}
	
			@Override
			public String toString()
			{
				return "Little endian wrapped " + toWrap.toString();
			}

			@Override
			public int hashCode()
			{
				return ~(toWrap.hashCode());
			}
		};
	}

	public static DataInput wrap(final DataInput toWrap)
	{
		return new DataInput()
		{
			@Override
			public void readFully(byte[] b) throws IOException
			{
				toWrap.readFully(b);
			}
	
			@Override
			public void readFully(byte[] b, int off, int len) throws IOException
			{
				toWrap.readFully(b,off,len);
			}
	
			@Override
			public int skipBytes(int n) throws IOException
			{
				return toWrap.skipBytes(n);
			}
	
			@Override
			public boolean readBoolean() throws IOException
			{
				return toWrap.readBoolean();
			}
	
			@Override
			public byte readByte() throws IOException
			{
				return toWrap.readByte();
			}
	
			@Override
			public int readUnsignedByte() throws IOException
			{
				return toWrap.readUnsignedByte();
			}
	
			@Override
			public short readShort() throws IOException
			{
				return Short.reverseBytes(toWrap.readShort());
			}
	
			@Override
			public int readUnsignedShort() throws IOException
			{
				int a;
				int b;
	
				a = readByte();
				b = readByte();
	
				// reverse of what the java doc says:
				return (((b & 0xff) << 8) | (a & 0xff));
			}
	
			@Override
			public char readChar() throws IOException
			{
				return toWrap.readChar();
			}
	
			@Override
			public int readInt() throws IOException
			{
				return Integer.reverseBytes(toWrap.readInt());
			}
	
			@Override
			public long readLong() throws IOException
			{
				return Long.reverseBytes(toWrap.readLong());
			}
	
			@Override
			public float readFloat() throws IOException
			{
				throw new UnsupportedOperationException();
			}
	
			@Override
			public double readDouble() throws IOException
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public String readLine() throws IOException
			{
				return toWrap.readLine();
			}
	
			@Override
			public String readUTF() throws IOException
			{
				return toWrap.readUTF();
			}
	
			@Override
			public String toString()
			{
				return "Little endian wrapped " + toWrap.toString();
			}

			@Override
			public int hashCode()
			{
				return ~(toWrap.hashCode());
			}

			/**
			 * @throws CloneNotSupportedException always
			 */
			@Override
			protected Object clone() throws CloneNotSupportedException
			{
				throw new CloneNotSupportedException();
			}

			@SuppressWarnings("unused")
			public DataInput getWrapped()
			{
				return toWrap;
			}
		};
	}
}
