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

package net.darkmist.alib.escape;

import java.io.Writer;
import java.io.IOException;
import java.io.FilterWriter;

class EscapingWriter extends FilterWriter
{
	private final Escaper escaper;

	EscapingWriter(Writer out, Escaper esc)
	{
		super(out);
		escaper = esc;
	}

	@Override
	public void write(int ch) throws IOException
	{
		escaper.escape(out, ch);
	}

	@Override
	public void write(char[] chars) throws IOException
	{
		escaper.escape(out, chars);
	}

	@Override
	public void write(char[] chars, int off, int len) throws IOException
	{
		escaper.escape(out, chars, off, len);
	}

	@Override
	public void write(String str) throws IOException
	{
		escaper.escape(out, str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException
	{
		escaper.escape(out, str, off, len);
	}

	@Override
	public Writer append(char ch) throws IOException
	{
		escaper.escape(out, ch);
		return this;
	}

	@Override
	public Writer append(CharSequence str) throws IOException
	{
		escaper.escape(out, str);
		return this;
	}

	@Override
	public Writer append(CharSequence str, int off, int len) throws IOException
	{
		escaper.escape(out, str, off, len);
		return this;
	}

	@Override
	public void flush() throws IOException
	{
		out.flush();
	}

	@Override
	public void close() throws IOException
	{
		out.close();
	}
}
