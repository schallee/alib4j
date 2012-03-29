package net.darkmist.alib.io;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import static java.lang.System.arraycopy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public abstract class Slurp
{
	private static final int buf_size = 1024;
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
		/*
		byte[] read_buf = new byte[buf_size];
		byte[] tmp_buf;
		List<byte[]> bufs = new LinkedList<byte[]>();
		int total=0;
		int amount_read;
		int pos;

		while((amount_read = stream.read(read_buf)) >= 0)
		{
			if(amount_read == 0)
				continue;
			if(amount_read < buf_size)
			{
				tmp_buf = new byte[amount_read];
				arraycopy(read_buf, 0, tmp_buf, 0, amount_read);
				bufs.add(tmp_buf);
			}
			else
			{
				bufs.add(read_buf);
				read_buf = new byte[buf_size];
			}
			total += amount_read;
		}
		switch(bufs.size())
		{
			case 0:
				return new byte[0];
			case 1:
				return bufs.iterator().next();
		}
		read_buf = new byte[total];
		pos = 0;
		for(Iterator<byte[]> i=bufs.iterator();i.hasNext();)
		{
			tmp_buf = i.next();
			arraycopy(tmp_buf, 0, read_buf, pos, tmp_buf.length);
			pos += tmp_buf.length;
		}
		return read_buf;
		*/
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
		/*
		InputStream stream = new FileInputStream(file);

		try
		{
			return slurp(stream,(int)(file.length()));
		}
		finally
		{
			stream.close();
		}
		*/
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

		private void close()
		{
			IOUtils.closeQuietly(in);
			in = null;
		}

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

	public Callable<String> callableToString(InputStream in)
	{
		return new ReaderToStringCallable(new InputStreamReader(in));
	}

	public Callable<String> callableToString(Reader in)
	{
		return new ReaderToStringCallable(in);
	}
}
