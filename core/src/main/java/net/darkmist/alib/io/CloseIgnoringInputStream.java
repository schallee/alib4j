package net.darkmist.alib.io;

import java.io.InputStream;

import org.apache.commons.io.input.ProxyInputStream;

/**
 * An {@link InputStream} that ignores {@link InputStream#close()}.
 * This differes from
 * {@link org.apache.commons.io.input.CloseShieldInputStream} in that
 * it does not fake the closing of the underlying InputStream.
 */
public class CloseIgnoringInputStream extends ProxyInputStream
{
	/**
	 * Wrap InputStream.
	 * @param in The InputStream to wrap.
	 */
	public CloseIgnoringInputStream(InputStream in)
	{
		super(in);
	}

	/**
	 * Do nothing.
	 */
	@Override
	public void close()
	{
	}
}
