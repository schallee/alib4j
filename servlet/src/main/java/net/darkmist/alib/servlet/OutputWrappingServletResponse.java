package net.darkmist.alib.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;

/** Extended ServletResponseWrapper that allows easy wrapping of servlet output */
public abstract class OutputWrappingServletResponse extends ServletResponseWrapper implements OutputMethodServletResponse
{
	/** {@link ServletOutputStream} if requested. Kept track of so we don't wrap multiple times.*/
	private ServletOutputStream os = null;
	/** {@link PrintWriter} if requested. Kept track of so we don't wrap multiple times.*/
	private PrintWriter pw = null;

	/** Construct wrapper of response.
	 * @param resp The response to wrap.
	 */
	public OutputWrappingServletResponse(ServletResponse resp)
	{
		super(resp);
	}
	
	/** Returns the wrapped output from a subclass.
	 * @param orig The origional to wrap.
	 * @return The wrapped output stream. Unless overriden, orig is returned.
	 * @throws IOException Only when overridding method throws one.
	 */
	public ServletOutputStream getWrappedOutputStream(ServletOutputStream orig) throws IOException
	{
		return orig;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException
	{
		if(os == null)
			os = getWrappedOutputStream(super.getOutputStream());
		return os;
	}

	/** Returns the wrapped output from a subclass.
	 * @param orig The origional to wrap.
	 * @return The wrapped output stream. Unless overriden, orig is returned.
	 * @throws IOException Only when overridding method throws one.
	 */
	public PrintWriter getWrappedWriter(PrintWriter orig) throws IOException
	{
		return orig;
	}

	@Override
	public PrintWriter getWriter() throws IOException
	{
		if(pw == null)
			pw = getWrappedWriter(super.getWriter());
		return pw;
	}

	/** Check for {@link #getWriter()} having been called.
	 * @return true if {@link #getWriter()} was called, false otherwise.
	 */
	@Override
	public boolean isUsingWriter()
	{
		return (pw != null);
	}

	/** Check for {@link #getOutputStream()} having been called.
	 * @return true if {@link #getOutputStream()} was called, false otherwise.
	 */
	@Override
	public boolean isUsingOutputStream()
	{
		return (os != null);
	}
}
