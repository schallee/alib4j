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
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;

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

	public static BytesMessage toBytesMessage(Session session, byte[] bytes) throws IOException, JMSException
	{
		BytesMessage msg = session.createBytesMessage();

		msg.writeBytes(bytes);
		return msg;
	}

	public static BytesMessage toBytesMessage(Session session, byte[] bytes, int off, int len) throws IOException, JMSException
	{
		BytesMessage msg = session.createBytesMessage();

		msg.writeBytes(bytes, off, len);
		return msg;
	}

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

	public static BytesMessage fileToBytesMessage(Session session, String fileName) throws IOException, JMSException
	{
		return toBytesMessage(session, new File(fileName));
	}

	public static OutputStream writeTo(BytesMessage msg, OutputStream out) throws IOException, JMSException
	{
		byte[] buf = new byte[BUFFER_SIZE];
		int amount;

		while((amount = msg.readBytes(buf))>=0)
			if(amount>0)
				out.write(buf, 0, amount);
		return out;
	}

	public static ByteBuffer writeTo(BytesMessage msg, ByteBuffer buf) throws JMSException
	{
		byte[] bytes = new byte[BUFFER_SIZE];
		int amount;

		while((amount = msg.readBytes(bytes))>=0)
			if(amount>0)
				buf.put(bytes, 0, amount);
		return buf;
	}

	public static File writeTo(BytesMessage msg, File file) throws IOException, JMSException
	{
		FileOutputStream fos = null;
		FileChannel fc = null;
		long size;
		ByteBuffer buf;

		if(file.exists())
			throw new IllegalArgumentException("File " + file + " already exists.");
		size = msg.getBodyLength();
		try
		{
			fos = new FileOutputStream(file);
			try
			{
				fc = fos.getChannel();
				fc.truncate(size);
				buf = fc.map(FileChannel.MapMode.READ_WRITE, 0, size);
				writeTo(msg, buf);
				return file;
			}
			catch(IOException e)
			{
				logger.debug("Ignoring IOException trying to map file {}.", file, e);
			}
			finally
			{
				fc=Closer.close(fc);
			}
			writeTo(msg, fos);
			return file;
		}
		finally
		{
			fos=Closer.close(fos);
		}
	}

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
