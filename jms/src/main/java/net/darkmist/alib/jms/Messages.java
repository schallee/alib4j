/*
 *  Copyright (C) 2014 Ed Schaller <schallee@darkmist.net>
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

package net.darkmist.alib.jms;

import java.io.DataInput;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
// java 7: import java.nio.file.Path;
// java 7: import java.nio.file.StandardOpenOption;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.io.Closer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Messages
{
	private static final Class<Messages> CLASS = Messages.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final int BUFFER_SIZE = 1024;

	private Messages()
	{
	}

	public static BytesMessage toBytesMessage(Session session, byte[] bytes) throws JMSException
	{
		BytesMessage msg = session.createBytesMessage();

		msg.writeBytes(bytes);
		return msg;
	}

	public static BytesMessage toBytesMessage(Session session, byte[] bytes, int off, int len) throws JMSException
	{
		BytesMessage msg = session.createBytesMessage();

		msg.writeBytes(bytes, off, len);
		return msg;
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="Library API")
	public static BytesMessage toBytesMessage(Session session, ByteBuffer bytes) throws IOException, JMSException
	{
		BytesMessage msg = session.createBytesMessage();
		byte[] buf = new byte[BUFFER_SIZE];
		int remaining;
		
		bytes = bytes.duplicate().asReadOnlyBuffer();
		while((remaining=bytes.remaining())>=buf.length)
		{
			bytes.get(buf);
			msg.writeBytes(buf);
		}
		if(remaining>0)
		{
			bytes.get(buf, 0, remaining);
			msg.writeBytes(buf, 0, remaining);
		}
		return msg;
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public static BytesMessage toBytesMessage(Session session, InputStream in) throws IOException, JMSException
	{
		BytesMessage msg = session.createBytesMessage();
		byte[] buf = new byte[BUFFER_SIZE];
		int amount;

		while((amount=in.read(buf))>=0)
			if(amount>0)
				msg.writeBytes(buf, 0, amount);
		return msg;
	}

	public static BytesMessage toBytesMessage(Session session, DataInput din) throws IOException, JMSException
	{
		BytesMessage msg;
		byte b;

		if(din instanceof InputStream)
			return toBytesMessage(session, (InputStream)din);
		msg = session.createBytesMessage();
		while(true)
		{
			try
			{
				b = din.readByte();
			}
			catch(EOFException e)
			{
				return msg;
			}
			msg.writeByte(b);
		}
	}

	@SuppressFBWarnings(value="IOI_USE_OF_FILE_STREAM_CONSTRUCTORS", justification="Pre 1.7")
	public static BytesMessage toBytesMessage(Session session, File file) throws IOException, JMSException
	{
		FileInputStream fin=null;
		FileChannel fc=null;

		try
		{
			fin = new FileInputStream(file);
			try
			{
				fc = fin.getChannel();
				return toBytesMessage(session, fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length()));
			}
			catch(IOException e)
			{
				logger.debug("Ignoring IOException caught trying to map file {}", file, e);
			}
			return toBytesMessage(session, fin);
		}
		finally
		{
			fc = Closer.close(fc);
			fin = Closer.close(fin);
		}
	}

	public static BytesMessage fileToBytesMessage(Session session, File file) throws IOException, JMSException
	{
		return toBytesMessage(session, file);
	}

	@SuppressFBWarnings(value="PATH_TRAVERSAL_IN",justification="API that assumes caller is sane.")
	public static BytesMessage fileToBytesMessage(Session session, String fileName) throws IOException, JMSException
	{
		return toBytesMessage(session, new File(fileName));
	}

	@SuppressFBWarnings(value={"OPM_OVERLY_PERMISSIVE_METHOD","CFS_CONFUSING_FUNCTION_SEMANTICS"},justification="Library API, For chaining methods")
	public static OutputStream writeTo(BytesMessage msg, OutputStream out) throws IOException, JMSException
	{
		byte[] buf = new byte[BUFFER_SIZE];
		int amount;

		while((amount = msg.readBytes(buf))>=0)
			if(amount>0)
				out.write(buf, 0, amount);
		return out;
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="Library API")
	public static ByteBuffer writeTo(BytesMessage msg, ByteBuffer buf) throws JMSException
	{
		byte[] bytes = new byte[BUFFER_SIZE];
		int amount;

		while((amount = msg.readBytes(bytes))>=0)
			if(amount>0)
				buf.put(bytes, 0, amount);
		return buf;
	}

	/* blasted. java 7
	private static Path writeToMapped(BytesMessage msg, Path path) throws IOException, JMSException
	{
		ByteBuffer buf;
		FileChannel fc;

		try
		(
			FileChannel fc = FileChannel.open(path, EnumSet.of(StandardOpenOption.CREATE_NEW));
		)
		{
			logger.debug("Mapping file {}", file);
			buf = fc.map(FileChannel.MapMode.READ_WRITE, 0, msg.getBodyLength());
			logger.debug("Writting message to byte buffer.");
			writeTo(msg, buf);
			logger.debug("Success writing message to file {} using file map.", file);
			return path;
		}
	}
	*/


	private static File writeToMapped(BytesMessage msg, File file) throws IOException, JMSException
	{
		// writeToMapped(msg, file.toPath());

		ByteBuffer buf;
		FileChannel fc=null;
		RandomAccessFile raf=null;

		try
		{
			raf = new RandomAccessFile(file, "rw");
			fc = raf.getChannel();
			buf = fc.map(FileChannel.MapMode.READ_WRITE, 0, msg.getBodyLength());
			logger.debug("Writting message to byte buffer.");
			writeTo(msg, buf);
			logger.debug("Success writing message to file {} using file map. Closing", file);
			fc.close();
			raf.close();
			return file;
		}
		finally
		{
			fc=Closer.close(fc);
			raf=Closer.close(raf);
		}
	}
	
	@SuppressFBWarnings(value="IOI_USE_OF_FILE_STREAM_CONSTRUCTORS", justification="Pre 1.7")
	private static File writeToOutputStream(BytesMessage msg, File file) throws IOException, JMSException
	{
		FileOutputStream fos = null;


		try
		{
			fos = new FileOutputStream(file);
			writeTo(msg, fos);
			fos.close();
			return file;
		}
		finally
		{
			fos = Closer.close(fos);
		}
	}

	public static File writeTo(BytesMessage msg, File file) throws IOException, JMSException
	{
		if(file.exists())
			throw new IllegalArgumentException("File " + file + " already exists.");
		try
		{
			return writeToMapped(msg, file);
		}
		catch(IOException e)
		{
			logger.debug("Ignoring IOException trying to write file through memory mapping.", e);
		}
		return writeToOutputStream(msg, file);
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public static byte[] asBytes(BytesMessage msg) throws JMSException
	{
		long len;
		byte[] buf;
		int amount;

		if((len = msg.getBodyLength())>Integer.MAX_VALUE)
			throw new IllegalArgumentException("Message size " + len + " is larger than Integer.MAX_VALUE " + Integer.MAX_VALUE + '.');
		buf = new byte[(int)len];
		if((amount = msg.readBytes(buf))!=len)
			throw new IllegalStateException("Message size was " + len + " but only " + amount + " bytes were read from it?");
		return buf;
	}

	public static ByteBuffer asBuffer(BytesMessage msg) throws JMSException
	{
		return ByteBuffer.wrap(asBytes(msg));
	}
}
