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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** Wrapper around a stream to iterate through the stream one line at a time.
 * @deprecated Use {@link org.apache.commons.io.LineIterator} instead.
 */

@Deprecated
public class LineIterator implements Iterator<String>
{
	private BufferedReader in;
	private String line;
	private IOException previousException;

	private void init(BufferedReader init_in)
	{
		this.in = init_in;
		line = null;
		previousException = null;
	}

	private void init(InputStream init_in)
	{
		init(new BufferedReader(new InputStreamReader(init_in)));
	}

	public LineIterator(InputStream in)
	{
		init(in);
	}

	public LineIterator(BufferedReader in)
	{
		init(in);
	}

	/** Get any {@link java.io.IOException IOException} caught but unreportable due to the
	  * {@link java.util.Iterator Interator} interface. 
	  * @return IOException Any previous caught  {@link java.io.IOException IOException} or null.
	  */
	public IOException getIOException()
	{
		return previousException;
	}

	/** {@link java.util.Iterator#hasNext()} implementation.
	  * @return True if more lines are availible, false if not.
	  * @note As this can cause the underlying {@link java.io.InputStream InputStream} to throw
	  * a {@link java.io.IOException IOException} but the {@link java.util.Iterator#hasNext()}
	  * interface does not, false is returned instead of it being rethrown. The exception can be
	  * retrieved via {@link #getIOException()}.
	  */
	@Override
	public boolean hasNext()
	{
		if(previousException != null)
			return false;
		if(line != null)
			return true;
		try
		{
			line = in.readLine();
			if(line == null)
				return false;
			return true;
		}
		catch(IOException e)
		{
			previousException = e;
			return false;
		}
	}

	/** {@link java.util.Iterator#next()} implementation.
	  * @return Next line of the source stream.
	  * @throws NoSuchElementException in the case of a end of file or a {@link java.io.IOException}
	  * 	by the underlying {@link java.io.IOStream IOStream}. In the latter case the exception can
	  *	be retrieved via {@link #getIOException()}.
	  */
	@Override
	public String next() throws NoSuchElementException
	{
		String ret;

		if(previousException != null)
			throw new NoSuchElementException("Previous IOException reading from stream");
		if(line == null)
		{
			try
			{
				if((line = in.readLine()) == null)
					throw new NoSuchElementException("No more lines available");
			}
			catch(IOException e)
			{
				previousException = e;
				throw new NoSuchElementException("IOException reading next line.");
			}
		}
		ret = line;
		line = null;
		return ret;
	}

	/** Not supported.
	  * @throws UnsupportedOperationException Always thrown.
	  */
	@Override
	public void remove() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("Remove is not supported");
	}
}
