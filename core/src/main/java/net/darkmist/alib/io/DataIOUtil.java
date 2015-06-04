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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class DataIOUtil
{
	/**
	 * Only stati methods.
	 */
	private DataIOUtil()
	{
	}

	private static class DataInputAsStream extends InputStream
	{
		private DataInput din;

		DataInputAsStream(DataInput din)
		{
			if((this.din = din)==null)
				throw new NullPointerException("DataInput cannot be null.");
		}

		@Override
		public int read() throws IOException
		{
			return din.readByte();
		}

		// note that DataOutput.readFully does NOT follow the same
		// samantics as read(byte[]...) so is not directly wrapped.
	}

	public static InputStream asInputStream(DataInput din)
	{
		if(din instanceof InputStream)
			return (InputStream)din;
		return new DataInputAsStream(din);
	}

	public static Reader asReader(DataInput din)
	{
		return new InputStreamReader(asInputStream(din));
	}

	private static class DataOutputAsStream extends OutputStream
	{
		private DataOutput dout;

		DataOutputAsStream(DataOutput dout)
		{
			if((this.dout = dout)==null)
				throw new NullPointerException("DataOutput cannot be null.");
		}

		@Override
		public void write(int b) throws IOException
		{
			dout.write(b);
		}

		@Override
		public void write(byte[] bytes, int off, int len) throws IOException
		{
			dout.write(bytes, off, len);
		}

		@Override
		public void write(byte[] bytes) throws IOException
		{
			dout.write(bytes);
		}
	}

	public static OutputStream asOutputStream(DataOutput dout)
	{
		if(dout instanceof OutputStream)
			return (OutputStream)dout;
		return new DataOutputAsStream(dout);
	}

	public static Writer asWriter(DataOutput dout)
	{
		return new OutputStreamWriter(asOutputStream(dout));
	}
}
