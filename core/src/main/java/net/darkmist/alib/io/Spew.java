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
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

public abstract class Spew
{
	private Spew()
	{
	}

	/** @deprecated Use {@link java.io.OutputStream#write(byte[])} instead */
	@Deprecated
	public static void spew(OutputStream stream, byte[] data) throws IOException
	{
		stream.write(data);
	}

	/** @deprecated Use {@link org.apache.commons.io.FileUtils#writeByteArrayToFile(java.io.File, byte[])} instead. */
	@Deprecated
	public static void spew(File file, byte[] data) throws IOException
	{
		FileUtils.writeByteArrayToFile(file,data);
		/*
		OutputStream stream = new FileOutputStream(file);

		spew(stream, data);
		stream.close();
		*/
	}

	public static void spew(String filename, byte[] data) throws IOException
	{
		FileUtils.writeByteArrayToFile(new File(filename),data);
		//spew(new File(filename), data);
	}
}
