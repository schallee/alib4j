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

	private void init(BufferedReader in)
	{
		this.in = in;
		line = null;
		previousException = null;
	}

	private void init(InputStream in)
	{
		init(new BufferedReader(new InputStreamReader(in)));
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
	public void remove() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("Remove is not supported");
	}
}
