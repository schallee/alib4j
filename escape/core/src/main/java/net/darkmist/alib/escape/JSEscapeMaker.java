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

class JSEscapeMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<JSEscapeMaker> CLASS = JSEscapeMaker.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final JSEscapeMaker SINGLETON = new JSEscapeMaker();
	// not static to avoid initialization races...
	private final StrMaker BACKSLASH_X_HEX = BackSlashXHexMaker.instance();
	// not static to avoid initialization races...
	private final StrMaker BACKSLASH_U_HEX = BackSlashUHexMaker.instance();

	private JSEscapeMaker()
	{
		makeCache();
	}

	static JSEscapeMaker instance()
	{
		return SINGLETON;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		if(ch < 0)
			throw new IllegalArgumentException("Code point " + ch + " is negative.");
		if(ch < 0x100)
			return BACKSLASH_X_HEX.appendStr(appendable, ch);
		if(ch < 0x10000)
			return BACKSLASH_U_HEX.appendStr(appendable, ch);
		throw new IllegalArgumentException("JavaScript/ECMAScript does not define how code points greater than 0xFFFF are handled and code point provided was " + ch + '.');
	}

	public String toString()
	{
		return "Singleton JSEscapeMaker";
	}
}
