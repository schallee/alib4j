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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// should look at org.apache.commons.lang.SerializationUtils
public abstract class Serializer
{
	private static final Class<Serializer> CLASS = Serializer.class;
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CLASS);

	/** Read one serialized object from a input stream.
	 * @param in InputStream to read from. This will be closed!
	 * @param type The type that is to be read.
	 * @return The serialized object cast as type.
	 */
	public static <T extends Serializable> T deserializeFromStream(InputStream in, Class<T> type) throws ClassNotFoundException, IOException
	{
		ObjectInputStream objIn;
		T obj;

		if(in instanceof ObjectInputStream)
			objIn = (ObjectInputStream)in;
		else
			objIn = new ObjectInputStream(in);

		obj = type.cast(objIn.readObject());
		// to close or not to close... That is the question...
		// we close... It doesn't make too much sense to have a stream with more afterwards...There would be two
		// stream headers...
		objIn.close();
		return obj;
	}

	public static <T extends Serializable> T deserializeFromFile(File file, Class<T> type) throws ClassNotFoundException, IOException
	{
		return deserializeFromStream(new FileInputStream(file), type);
	}

	public static <T extends Serializable> T deserializeFromBytes(byte[] bytes, Class<T> type) throws ClassNotFoundException, IOException
	{
		return deserializeFromStream(new ByteArrayInputStream(bytes), type);
	}

	/** Write one object serilized to a output stream.
	 * @param obj The Object to serilize.
	 * @param out The output stream to write it to. This will be closed!
	 */
	public static <T extends Serializable> void serializeToStream(T obj, OutputStream out) throws IOException
	{
		ObjectOutputStream objOut;

		if(out instanceof ObjectOutputStream)
			objOut = (ObjectOutputStream)out;
		else
			objOut = new ObjectOutputStream(out);
		objOut.writeObject(obj);
		objOut.close();
	}

	public static <T extends Serializable> void serializeToFile(T obj, File file) throws IOException
	{
		serializeToStream(obj, new FileOutputStream(file));
	}

	/** Serializes an object to a temporary file.
	 * @param obj The object to serialize.
	 * @return The file the object is serialized in. This file will already be set to be deleted on process exit.
	 */
        public static <T extends Serializable> File serializeToTempFile(T obj) throws IOException
        {
                File file = File.createTempFile(Serializer.class.getName().replace(".*\\.",""), null);
                file.deleteOnExit();
                serializeToFile(obj,file);
		return file;
        }

	public static <T extends Serializable> byte[] serializeToBytes(T obj) throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		serializeToStream(obj, out);
		return out.toByteArray();
	}
}
