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
			public void write(int i) throws IOException
			{
				toWrap.write(i);
			}

			public void write(byte[] b) throws IOException
			{
				toWrap.write(b);
			}

			public void write(byte[] b, int off, int len) throws IOException
			{
				toWrap.write(b,off,len);
			}

			public void writeBoolean(boolean b) throws IOException
			{
				toWrap.writeBoolean(b);
			}

			public void writeByte(int i) throws IOException
			{
				toWrap.writeByte(i);
			}

			public void writeShort(int i) throws IOException
			{
				toWrap.writeShort(Short.reverseBytes((short)i));
			}

			public void writeChar(int i) throws IOException
			{
				toWrap.writeChar(i);
			}

			public void writeInt(int i) throws IOException
			{
				toWrap.writeInt(Integer.reverseBytes(i));
			}

			public void writeLong(long l) throws IOException
			{
				toWrap.writeLong(Long.reverseBytes(l));
			}

			public void writeFloat(float f) throws IOException
			{
				throw new UnsupportedOperationException();
			}

			public void writeDouble(double d) throws IOException
			{
				throw new UnsupportedOperationException();
			}

			public void writeBytes(String s) throws IOException
			{
				toWrap.writeBytes(s);
			}

			public void writeChars(String s) throws IOException
			{
				toWrap.writeChars(s);
			}

			public void writeUTF(String s) throws IOException
			{
				toWrap.writeUTF(s);
			}

			@SuppressWarnings("unused")
			public DataOutput getWrapped()
			{
				return toWrap;
			}

			protected Object clone() throws CloneNotSupportedException
			{
				throw new CloneNotSupportedException();
			}
	
			public String toString()
			{
				return "Little endian wrapped " + toWrap.toString();
			}

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
			public void readFully(byte[] b) throws IOException
			{
				toWrap.readFully(b);
			}
	
			public void readFully(byte[] b, int off, int len) throws IOException
			{
				toWrap.readFully(b,off,len);
			}
	
			public int skipBytes(int n) throws IOException
			{
				return toWrap.skipBytes(n);
			}
	
			public boolean readBoolean() throws IOException
			{
				return toWrap.readBoolean();
			}
	
			public byte readByte() throws IOException
			{
				return toWrap.readByte();
			}
	
			public int readUnsignedByte() throws IOException
			{
				return toWrap.readUnsignedByte();
			}
	
			public short readShort() throws IOException
			{
				return Short.reverseBytes(toWrap.readShort());
			}
	
			public int readUnsignedShort() throws IOException
			{
				int a;
				int b;
	
				a = readByte();
				b = readByte();
	
				// reverse of what the java doc says:
				return (((b & 0xff) << 8) | (a & 0xff));
			}
	
			public char readChar() throws IOException
			{
				return toWrap.readChar();
			}
	
			public int readInt() throws IOException
			{
				return Integer.reverseBytes(toWrap.readInt());
			}
	
			public long readLong() throws IOException
			{
				return Long.reverseBytes(toWrap.readLong());
			}
	
			public float readFloat() throws IOException
			{
				throw new UnsupportedOperationException();
			}
	
			public double readDouble() throws IOException
			{
				throw new UnsupportedOperationException();
			}

			public String readLine() throws IOException
			{
				return toWrap.readLine();
			}
	
			public String readUTF() throws IOException
			{
				return toWrap.readUTF();
			}
	
			public String toString()
			{
				return "Little endian wrapped " + toWrap.toString();
			}

			public int hashCode()
			{
				return ~(toWrap.hashCode());
			}

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
