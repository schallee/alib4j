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

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public final class Slurp
{
	private Slurp()
	{
	}

	/**
	 * @deprecated Use {@link org.apache.commons.io.IOUtils#toByteArray(java.io.InputStream)} instead.
	 */
	@Deprecated
	public static byte[] slurp(InputStream stream) throws IOException
	{
		return IOUtils.toByteArray(stream);
	}

	public static byte[] slurp(InputStream stream, int amount) throws IOException
	{
		byte data[] = new byte[amount];
		int total_read = 0;
		int amount_read;

		while(total_read < amount)
		{
			if((amount_read = stream.read(data, total_read, amount))<0)
				break;
			amount -= amount_read;
			total_read += amount_read;
		}
		return data;
	}

	public static byte[] slurp(InputStream stream, long amount) throws IOException
	{
		if(amount > Integer.MAX_VALUE)
			throw new IOException("Size larger than max int");
		return slurp(stream, (int)amount);
	}

	public static byte[] slurp(DataInput din) throws IOException
	{
		ByteArrayOutputStream baos;
		int b;

		if(din instanceof InputStream)
			return IOUtils.toByteArray((InputStream)din);
		baos = new ByteArrayOutputStream();
		while(true)
		{
			try
			{
				b = din.readByte();
			}
			catch(EOFException e)
			{
				return baos.toByteArray();
			}
			baos.write(b);
		}
	}

	public static byte[] slurp(DataInput din, int amount) throws IOException
	{
		byte data[] = new byte[amount];

		din.readFully(data);
		return data;
	}

	public static byte[] slurp(DataInput din, long amount) throws IOException
	{
		if(amount > Integer.MAX_VALUE)
			throw new IOException("Size larger than max int");
		return slurp(din, (int)amount);
	}

	/**
	 * @deprecated Use {@link org.apache.commons.io.FileUtils#readFileToByteArray(java.io.File)} instead
	 */
	@Deprecated
	public static byte[] slurp(File file) throws IOException
	{
		return FileUtils.readFileToByteArray(file);
	}

	public static byte[] slurp(String file) throws IOException
	{
		return FileUtils.readFileToByteArray(new File(file));
		//return slurp(new File(file));
	}

	private static class ReaderToStringCallable implements Callable<String>
	{
		private Reader in = null;

		public ReaderToStringCallable(Reader in)
		{
			setReader(in);
		}

		public void setReader(Reader in)
		{
			this.in = in;
		}

		@Override
		public String call() throws Exception
		{
			String ret;

			if(in==null)
				return null;
			ret = IOUtils.toString(in);
			in.close();
			return ret;
		}
	}

	public static Callable<String> callableToString(InputStream in)
	{
		return new ReaderToStringCallable(new InputStreamReader(in,Charset.defaultCharset()));
	}

	public static Callable<String> callableToString(Reader in)
	{
		return new ReaderToStringCallable(in);
	}
}
