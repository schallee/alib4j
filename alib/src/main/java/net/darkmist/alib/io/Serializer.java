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

import org.apache.log4j.Logger;

// should look at org.apache.commons.lang.SerializationUtils
public abstract class Serializer
{
        private static final Logger logger = Logger.getLogger(Serializer.class);

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
