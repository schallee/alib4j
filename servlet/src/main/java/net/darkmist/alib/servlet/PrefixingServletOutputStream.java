package net.darkmist.alib.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

/** A {@link ServletOutputStream} wrapper that outputs a prefix before the first output. The prefix is not written until the first output method is called. */
public class PrefixingServletOutputStream extends ServletOutputStreamWrapper
{
	/** The prefix that will be written. */
	private String prefix = null;

	/** Set the prefix string.
	 * @throws IllegalStateException If the first output has already occured.
	 */
	protected void setPrefix(String prefix_)
	{
		if(hasFirstOutputHappened())
			throw new IllegalStateException("Attempt to set prefix when the first output has already occured");
		prefix = prefix_;
	}

	/** Get the prefix that is to be written */
	public String getPrefix()
	{
		return prefix;
	}

	/** Constructs a PrefixingServletOutputStream
	 * @param prefix_ the prefix to output before anything else.
	 * @param out_ The {@link ServletOutputStream} to wrap.
	 */
	public PrefixingServletOutputStream(String prefix_, ServletOutputStream out_)
	{
		super(out_);
		setPrefix(prefix_);
	}

	/** Reversed argument constructor for dislexics.
	 * @see #PrefixingServletOutputStream(String, ServletOutputStream)
	 */
	public PrefixingServletOutputStream(ServletOutputStream out_, String prefix_)
	{
		this(prefix_,out_);
	}

	/** Writes out the prefix.
	 * @throws IOException if {@link #print(String)} does.
	 */
	@Override
	protected void onFirstOutput() throws IOException
	{
		if(prefix != null && prefix.length() > 0)
		{
			print(prefix);
			prefix = null;	// no need to keep a ref
		}
	}
}
