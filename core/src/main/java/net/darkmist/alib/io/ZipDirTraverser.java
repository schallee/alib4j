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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.collection.Sets;
import net.darkmist.alib.lang.NullSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipDirTraverser extends DirTraverser
{
	private static final Class<ZipDirTraverser> CLASS = ZipDirTraverser.class;
	@SuppressWarnings("unused")
	private static final String CLASS_NAME = CLASS.getName();
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final Set<String> ZIP_EXTS = Sets.newUnmodifiableSet("jar", "war", "ear", "zip", "rar", "car", "pak");
	private NamedInputStreamHandler inputStreamHandler;

	public ZipDirTraverser(Queue<File> frontier, FileHandler fileHandler, NamedInputStreamHandler inputStreamHandler)
	{
		super(frontier,fileHandler);
		this.inputStreamHandler = inputStreamHandler;
	}

	public ZipDirTraverser(File start, FileHandler fileHandler, NamedInputStreamHandler inputStreamHandler)
	{
		super(start,fileHandler);
		this.inputStreamHandler = inputStreamHandler;
	}

	@Nullable
	private static final String getExtension(String name)
	{
		int pos = name.lastIndexOf('.');

		if(pos < -1)
			return null;
		pos++;
		if(pos == name.length())
			return "";
		return name.substring(pos);
	}

	protected static final boolean isZip(String name)
	{
		String ext;

		ext = getExtension(name);
		logger.debug("name={} ext={}", name, ext);
		if(ext == null || ext.length() <= 0)
			return false;
		return ZIP_EXTS.contains(ext.toLowerCase(Locale.getDefault()));
	}

	protected void onZipInputStream(String path, InputStream in) throws IOException
	{
		ZipInputStream zin = null;
		CloseIgnoringInputStream zinNoClose;
		ZipEntry entry;

		zin = new ZipInputStream(in);
		zinNoClose = new CloseIgnoringInputStream(zin);
		while((entry = zin.getNextEntry()) != null)
		{
			if(!entry.isDirectory())
			{	// directories in zip files are useless...
				String entryName =  entry.getName();
				String entryPath = path + '/' + entryName;

				if(isZip(entryName))
					onZipInputStream(entryPath, zinNoClose);
				else	// would never be here if inputStreamHandler was null
					inputStreamHandler.handleInputStream(entryPath, zinNoClose);
			}
			zin.closeEntry();
		}
	}

	@SuppressFBWarnings(value="IOI_USE_OF_FILE_STREAM_CONSTRUCTORS", justification="Supports 1.6")
	protected void onZipFile(File file)
	{
		FileInputStream fin = null;

		try
		{
			fin = new FileInputStream(file);
			onZipInputStream(file.getPath(), fin);
		}
		catch(IOException e)
		{
			logger.warn("Error handling file {}", file, e);
		}
		finally
		{
			fin = Closer.close(fin,logger,file);
		}
	}

	@Override
	protected void onFile(File file)
	{
		logger.debug("file={} inputStreamHandler={}", file, inputStreamHandler);
		if(inputStreamHandler != null && isZip(file.getName()))
		{
			logger.debug("file is zip");
			onZipFile(file);
		}
		else
			super.onFile(file);
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof ZipDirTraverser))
			return false;
		ZipDirTraverser that = (ZipDirTraverser)o;
		if(!NullSafe.equals(this.inputStreamHandler, that.inputStreamHandler))
			return false;
		return super.equals(o);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(inputStreamHandler);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": inputStreamHandler=" + inputStreamHandler;
	}
}
