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
import java.io.OutputStream;

import net.darkmist.alib.lang.NullSafe;
import net.darkmist.alib.lang.Wrapper;

public class OutputStreamWrapper<I extends OutputStream> extends OutputStream implements Wrapper<I>
{
	protected final I target;

	public OutputStreamWrapper(I target)
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
	public void write(int b) throws IOException
	{
		target.write(b);
	}

	@Override
	public void write(byte[] bytes) throws IOException
	{
		target.write(bytes);
	}

	@Override
	public void write(byte[] bytes, int off, int len) throws IOException
	{
		target.write(bytes, off, len);
	}

	@Override
	public void flush() throws IOException
	{
		target.flush();
	}

	@Override
	public void close() throws IOException
	{
		target.close();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof OutputStreamWrapper))
			return false;
		return NullSafe.equals(target, ((OutputStreamWrapper<?>)target));
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(target);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " wrapping " + target + '.';
	}
}
