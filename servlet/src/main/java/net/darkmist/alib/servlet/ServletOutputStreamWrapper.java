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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

/** Simple wrapper around a {@link ServletOutputStream}. */
public class ServletOutputStreamWrapper extends ServletOutputStream
{
	private boolean firstOutputHappened = false;
	private ServletOutputStream out;

	/** Subclass constructor for when {@link #out} is not immediately available. {@link #setServletOutputStream} should be called before other methods or a {@link java.lang.NullPointerException} may result. */
	protected ServletOutputStreamWrapper()
	{
	}

	/** Constructor.
	 * @param out_ The {@link ServletOutputStream} to wrap.
	 */
	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public ServletOutputStreamWrapper(ServletOutputStream out_)
	{
		setServletOutputStream(out_);
	}

	/** Sets the {@link ServletOutputStream} to wrap.
	 * @param out_ The {@link ServletOutputStream} to wrap.
	 */
	protected final void setServletOutputStream(ServletOutputStream out_)
	{
		out = out_;
	}

	/** Called before the first output is written.
	 * The default version does nothing.
	 * @throws IOException Never thrown unless this method is overriden.
	 */
	protected void onFirstOutput() throws IOException
	{
	}

	/** Has the first output occured?
	 * @return whether the first output has occured or not.
	 */
	public boolean hasFirstOutputHappened()
	{
		return firstOutputHappened;
	}

	/** Check whether first output calls should be made.
	 * If the first output has not occured, {@link #onFirstOutput} is called.
	 * @throws IOException only if {@link #onFirstOutput} is called and throws an {@link IOException}.
	 * @see #onFirstOutput
	 */
	protected void checkFirstOutput() throws IOException
	{
		if(firstOutputHappened)
			return;
		// set first so we don't have infinite recursion...
		firstOutputHappened = true;
		onFirstOutput();
	}

	// OutputStream overrides
	@Override
	public void write(int b) throws IOException
	{
		checkFirstOutput();
		out.write(b);
	}

	@Override
	public void write(byte[] bytes) throws IOException
	{
		checkFirstOutput();
		out.write(bytes);
	}

	@Override
	public void write(byte[] bytes, int off, int len) throws IOException
	{
		checkFirstOutput();
		out.write(bytes, off, len);
	}

	@Override
	public void flush() throws IOException
	{
		checkFirstOutput();
		out.flush();
	}

	@Override
	public void close() throws IOException
	{
		checkFirstOutput();
		out.close();
	}

	// ServletOutputStream overrides
	@Override
	public void print(String s) throws IOException
	{
		checkFirstOutput();
		out.print(s);
	}

	@Override
	public void print(boolean bool) throws IOException
	{
		checkFirstOutput();
		out.print(bool);
	}

	@Override
	public void print(char ch) throws IOException
	{
		checkFirstOutput();
		out.print(ch);
	}

	@Override
	public void print(int i) throws IOException
	{
		checkFirstOutput();
		out.print(i);
	}

	@Override
	public void print(long l) throws IOException
	{
		checkFirstOutput();
		out.print(l);
	}

	@Override
	public void print(float f) throws IOException
	{
		checkFirstOutput();
		out.print(f);
	}

	@Override
	public void print(double d) throws IOException
	{
		checkFirstOutput();
		out.print(d);
	}

	@Override
	public void println() throws IOException
	{
		checkFirstOutput();
		out.println();
	}

	@Override
	public void println(String s) throws IOException
	{
		checkFirstOutput();
		out.println(s);
	}

	@Override
	public void println(boolean bool) throws IOException
	{
		checkFirstOutput();
		out.println(bool);
	}

	@Override
	public void println(char ch) throws IOException
	{
		checkFirstOutput();
		out.println(ch);
	}

	@Override
	public void println(int i) throws IOException
	{
		checkFirstOutput();
		out.println(i);
	}

	@Override
	public void println(long l) throws IOException
	{
		checkFirstOutput();
		out.println(l);
	}

	@Override
	public void println(float f) throws IOException
	{
		checkFirstOutput();
		out.println(f);
	}

	@Override
	public void println(double d) throws IOException
	{
		checkFirstOutput();
		out.println(d);
	}

	@Override
	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof ServletOutputStreamWrapper))
			return false;
		ServletOutputStreamWrapper that = (ServletOutputStreamWrapper)o;
		if(this.firstOutputHappened != that.firstOutputHappened)
			return false;
		if(!NullSafe.equals(this.out, that.out))
			return false;
		return super.equals(that);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(super.hashCode(), firstOutputHappened, out);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": firstOutputHappened=" + firstOutputHappened + " out=" + out + " super=" + super.toString();
	}
}
