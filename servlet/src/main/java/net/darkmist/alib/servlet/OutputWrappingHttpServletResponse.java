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

package net.darkmist.alib.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.ServletOutputStream;

/** Extended HttpServletResponseWrapper that allows easy wrapping of servlet output */
public abstract class OutputWrappingHttpServletResponse extends HttpServletResponseWrapper implements OutputMethodServletResponse
{
	/** {@link ServletOutputStream} if requested. Kept track of so we don't wrap multiple times.*/
	private ServletOutputStream os = null;
	/** {@link PrintWriter} if requested. Kept track of so we don't wrap multiple times.*/
	private PrintWriter pw = null;

	/** Construct wrapper of response.
	 * @param resp The response to wrap.
	 */
	public OutputWrappingHttpServletResponse(HttpServletResponse resp)
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

	/**
	 * Override and prevent the content length from being set.
	 * @param len What the content length was to be set to. Ignored.
	 */
	@Override
	public void setContentLength(@SuppressWarnings("unused") int len)
	{
	}
}
