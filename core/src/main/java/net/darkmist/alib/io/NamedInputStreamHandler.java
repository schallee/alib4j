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
 * Able to handle a {@link InputStream} that has a name.
 */
public interface NamedInputStreamHandler
{
	/**
	 * Handle a named input stream.
	 * @param name The name for the in. This is frequently a path or the like.
	 * @param in The stream to handle.
	 * @throws IOException if a operation on in does.
	 */
	public void handleInputStream(String name, InputStream in) throws IOException;
}
