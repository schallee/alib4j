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
