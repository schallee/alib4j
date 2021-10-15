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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

class FourCharHexMaker extends StrMaker.PreCachedSingletonAbstract
{
	@SuppressWarnings("unused")
	private static final Class<FourCharHexMaker> CLASS = FourCharHexMaker.class;
	private static final FourCharHexMaker SINGLETON = new FourCharHexMaker();

	private FourCharHexMaker()
	{
		makeCache();
	}

	static FourCharHexMaker instance()
	{
		return SINGLETON;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		if(ch < 0)
			throw new IllegalArgumentException("Code point " + ch + " was negative.");
		if(ch > 0xFFFF)
			throw new IllegalArgumentException("Char point " + ch + " is too large for four hex digits.");
		if(ch < 0x10)
			appendable.append("000");
		else if(ch < 0x100)
			appendable.append("00");
		else if(ch < 0x1000)
			appendable.append('0');
		return appendable.append(Integer.toHexString(ch));
	}
}
