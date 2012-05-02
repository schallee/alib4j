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
import java.util.Queue;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.darkmist.alib.collection.Sets;

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
		if(logger.isDebugEnabled())
			logger.debug("name=" + name + " ext=" + ext);
		if(ext == null || ext.length() <= 0)
			return false;
		return ZIP_EXTS.contains(ext.toLowerCase());
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
			logger.warn("Error handling file " + file, e);
		}
		finally
		{
			fin = Closer.close(fin,logger,file);
		}
	}

	@Override
	protected void onFile(File file)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("file=" + file);
			logger.debug("inputStreamHandler=" + inputStreamHandler);
		}
		if(inputStreamHandler != null && isZip(file.getName()))
		{
			logger.debug("file is zip");
			onZipFile(file);
		}
		else
			super.onFile(file);
	}
}
