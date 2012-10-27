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

class XMLEntityMaker extends StrMaker.PreCachedSingletonAbstract
{
	@SuppressWarnings("unused")
	private static final Class<XMLEntityMaker> CLASS = XMLEntityMaker.class;
	private static final XMLEntityMaker SINGLETON = new XMLEntityMaker();

	private XMLEntityMaker()
	{
		makeCache();
	}

	static XMLEntityMaker instance()
	{
		return SINGLETON;
	}

	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		// for this range the result is one char shorter as hex even though the x is added...
		if(1000000 <= ch && ch <= 0xfffff)
			appendable.append("&#x").append(Integer.toHexString(ch));
		else
			appendable.append("&#").append(Integer.toString(ch));
		return appendable.append(';');
	}
}
