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
