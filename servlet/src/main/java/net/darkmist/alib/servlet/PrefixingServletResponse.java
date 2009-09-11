package net.darkmist.alib.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import net.darkmist.alib.io.PrefixingPrintWriter;

public class PrefixingServletResponse extends OutputWrappingServletResponse
{
	/** The prefix that will be written. */
	private String prefix = null;

	/** Set the prefix string.
	 */
	protected void setPrefix(String prefix_)
	{
		prefix = prefix_;
	}

	/** Get the prefix that is to be written */
	public String getPrefix()
	{
		return prefix;
	}

	/** Wraps resp and sends prefix.
	 * @param prefix_ the prefix to send.
	 * @param resp The response to wrap.
	 */
	public PrefixingServletResponse(String prefix_, ServletResponse resp)
	{
		super(resp);
		setPrefix(prefix_);
	}

	@Override
	public ServletOutputStream getWrappedOutputStream(ServletOutputStream orig) throws IOException
	{
		return new PrefixingServletOutputStream(prefix, orig);
	}

	@Override
	public PrintWriter getWrappedWriter(PrintWriter orig) throws IOException
	{
		return new PrefixingPrintWriter(prefix, orig);
	}
}
