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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class URLEscapeMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<URLEscapeMaker> CLASS = URLEscapeMaker.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final URLEscapeMaker SINGLETON = new URLEscapeMaker();
	// not static to avoid initialization races...
	private final StrMaker HEXER = TwoCharHexMaker.instance();

	private URLEscapeMaker()
	{
		makeCache();
	}

	static URLEscapeMaker instance()
	{
		return SINGLETON;
	}

	private Appendable appendSingleByteEscape(Appendable appendable, int ch) throws IOException
	{
		return HEXER.appendStr(appendable.append('%'), ch);
	}

	private Appendable appendMultiByteEscape(Appendable appendable, int ch) throws IOException
	{
		int[] codePointStr = new int[1];
		String str;

		codePointStr[0] = ch;
		str = new String(codePointStr,0,1);
		for(int b : str.getBytes(Charsets.UTF8))
			appendSingleByteEscape(appendable, b & 0xFF);
		return appendable;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		if(ch < 0)
			throw new IllegalArgumentException("Character value " + ch + " is negative.");
		if(ch < 0x100)
			return appendSingleByteEscape(appendable, ch);
		return appendMultiByteEscape(appendable, ch);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " SINGLETON";
	}
}
