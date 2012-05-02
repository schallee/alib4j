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

import java.io.IOException;
import java.io.InputStream;

/**
 * Misc io related utilities.
 */
public class IOUtils
{
	/** 
	 * Private constructor as methods are static.
	 */
	private IOUtils()
	{
	}


	public static int readFully(InputStream in, byte[] bytes) throws IOException
	{
		int off = 0;
		int len = bytes.length;
		int amount;

		while(len>0)
		{
			if((amount = in.read(bytes, off, len))<0)
				return off;
			if(amount > len)
				throw new IllegalStateException("amount returned is larger then the len passed in! (" + amount + " > " + len + ')');
			off += amount;
			len -= amount;
		}
		return off;
	}
}
