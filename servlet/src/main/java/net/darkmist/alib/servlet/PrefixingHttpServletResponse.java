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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.darkmist.alib.io.PrefixingPrintWriter;

public class PrefixingHttpServletResponse extends OutputWrappingHttpServletResponse
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
	public PrefixingHttpServletResponse(String prefix_, HttpServletResponse resp)
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
