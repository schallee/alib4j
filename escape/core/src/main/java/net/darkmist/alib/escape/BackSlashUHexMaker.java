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

class BackSlashUHexMaker extends StrMaker.PreCachedSingletonAbstract
{
	private static final Class<BackSlashUHexMaker> CLASS = BackSlashUHexMaker.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final BackSlashUHexMaker SINGLETON = new BackSlashUHexMaker();
	// not static to avoid initialization races...
	private final StrMaker FOUR_CHAR_HEX = FourCharHexMaker.instance();

	private BackSlashUHexMaker()
	{
		makeCache();
	}

	static BackSlashUHexMaker instance()
	{
		return SINGLETON;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		if(ch < 0)
			throw new IllegalArgumentException("Code point " + ch + " is negative.");
		if(ch > 0xFFFF)
			throw new IllegalArgumentException("Char code " + ch + " is too large for four hex digits.");
		return FOUR_CHAR_HEX.appendStr(appendable.append("\\u"), ch);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName();	// singleton
	}
}
