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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private File file;
	private Class<? extends T> objType;

	public SerializedObjectFile(T obj) throws RefException
	{
		set(obj);
		objType = GenericFudge.getClass(obj);
	}

	@Override
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

	@Override
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

	@Override
	public void clear() throws RefException
	{
		try
		{
			if(file != null && file.exists() && !file.delete())
				file.deleteOnExit();
		}
		finally
		{
			file = null;
		}
	}

	@Override
	protected void finalize() throws Throwable
	{
		clear();
	}
}
