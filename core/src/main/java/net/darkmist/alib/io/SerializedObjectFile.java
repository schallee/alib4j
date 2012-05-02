package net.darkmist.alib.io;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.darkmist.alib.generics.GenericFudge;
import net.darkmist.alib.ref.AbstractRef;
import net.darkmist.alib.ref.RefException;

import static net.darkmist.alib.io.Serializer.deserializeFromFile;
import static net.darkmist.alib.io.Serializer.serializeToTempFile;

public class SerializedObjectFile<T extends Serializable> extends AbstractRef<T>
{
	@SuppressWarnings("rawtypes")
	private static final Class<SerializedObjectFile> CLASS = SerializedObjectFile.class;
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CLASS);
	private File file;
	private Class<? extends T> objType;

	public SerializedObjectFile(T obj) throws RefException
	{
		set(obj);
		objType = GenericFudge.getClass(obj);
	}

	public void set(T obj) throws RefException
	{
		clear();
		try
		{
			if(obj != null)
				file = serializeToTempFile(obj);
		}
		catch(IOException e)
		{
			throw new RefException("Could not serialize object to file.", e);
		}
	}

	public T get() throws RefException
	{
		T obj;

		if(file == null)
			return null;
		try
		{
			obj = deserializeFromFile(file, objType);
		}
		catch(IOException e)
		{
			throw new RefException("IOException reading serialized object from file", e);
		}
		catch(ClassNotFoundException e)
		{
			throw new RefException("Serialized object deserialized to different type!", e);
		}
		return obj;
	}

	public void clear() throws RefException
	{
		try
		{
			if(file != null && file.exists())
			{
				if(!file.delete())
					file.deleteOnExit();
			}
		}
		finally
		{
			file = null;
		}
	}

	protected void finalize() throws Throwable
	{
		clear();
	}
}
