/*
 *  Copyright (C) 2015 Ed Schaller <schallee@darkmist.net>
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

import net.darkmist.alib.lang.Wrapper;

public class InputStreamWrapper<I extends InputStream> extends InputStream implements Wrapper<I>
{
	protected final I target;

	public InputStreamWrapper(I target)
	{
		if((this.target = target)==null)
			throw new NullPointerException();
	}

	@Override
	public I unwrap()
	{
		return target;
	}

	@Override
	public int read() throws IOException
	{
		return target.read();
	}

	@Override
	public int read(byte[] bytes) throws IOException
	{
		return target.read(bytes);
	}

	@Override
	public int read(byte[] bytes, int off, int len) throws IOException
	{
		return target.read(bytes, off, len);
	}

	@Override
	public long skip(long l) throws IOException
	{
		return target.skip(l);
	}

	@Override
	public int available() throws IOException
	{
		return target.available();
	}

	@Override
	public void close() throws IOException
	{
		target.close();
	}

	@Override
	public void mark(int i)
	{
		target.mark(i);
	}

	@Override
	public void reset() throws IOException
	{
		target.reset();
	}

	@Override
	public boolean markSupported()
	{
		return target.markSupported();
	}
}
