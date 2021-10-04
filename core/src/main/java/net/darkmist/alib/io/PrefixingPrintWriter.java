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

package net.darkmist.alib.io;

import java.io.PrintWriter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

/** A {@link PrintWriter} wrapper that outputs a prefix before the first output. The prefix is not written until the first output method is called. */
public class PrefixingPrintWriter extends PrintWriterWrapper
{
	/** The prefix that will be written. */
	private String prefix = null;

	/** Set the prefix string.
	 * @throws IllegalStateException If the first output has already occured.
	 */
	protected void setPrefix(String prefix_)
	{
		if(hasFirstOutputHappened())
			throw new IllegalStateException("Attempt to set prefix to \"" + prefix_ + "\" when the first output has already occured");
		prefix = prefix_;
	}

	/** Get the prefix that is to be written */
	public String getPrefix()
	{
		return prefix;
	}

	/** Constructs a PrefixingPrintWriter
	 * @param prefix_ the prefix to output before anything else.
	 * @param pw_ The {@link PrintWriter} to wrap.
	 */
	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API Method")
	public PrefixingPrintWriter(String prefix_, PrintWriter pw_)
	{
		super(pw_);
		prefix = prefix_;
	}

	/** Reversed argument constructor for dislexics.
	 * @see #PrefixingPrintWriter(String, PrintWriter)
	 */
	public PrefixingPrintWriter(PrintWriter pw_, String prefix_)
	{
		this(prefix_,pw_);
	}

	/** Writes out the prefix.
	 */
	@Override
	protected void onFirstOutput()
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
		if(!(o instanceof PrefixingPrintWriter))
			return false;
		if(!NullSafe.equals(prefix, ((PrefixingPrintWriter)o).prefix))
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
