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

import javax.servlet.ServletOutputStream;

import net.darkmist.alib.lang.NullSafe;

/** A {@link ServletOutputStream} wrapper that outputs a prefix before the first output. The prefix is not written until the first output method is called. */
public class PrefixingServletOutputStream extends ServletOutputStreamWrapper
{
	/** The prefix that will be written. */
	private String prefix = null;

	/** Set the prefix string.
	 * @throws IllegalStateException If the first output has already occured.
	 */
	protected final void setPrefix(String prefix_)
	{
		if(hasFirstOutputHappened())
			throw new IllegalStateException("Attempt to set prefix from " + prefix + " to " + prefix_ + " when the first output has already occured");
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

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof PrefixingServletOutputStream))
			return false;
		PrefixingServletOutputStream that = (PrefixingServletOutputStream)o;
		if(!NullSafe.equals(this.prefix, that.prefix))
			return false;
		return super.equals(o);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(super.hashCode(), prefix);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": prefix=" + prefix + " super=" + super.toString();
	}
}
