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

class CSSEscapeMaker extends StrMaker.PreCachedSingletonAbstract
{
	@SuppressWarnings("unused")
	private static final Class<CSSEscapeMaker> CLASS = CSSEscapeMaker.class;
	private static final CSSEscapeMaker SINGLETON = new CSSEscapeMaker();

	private CSSEscapeMaker()
	{
		makeCache();
	}

	static CSSEscapeMaker instance()
	{
		return SINGLETON;
	}

	@Override
	protected Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException
	{
		// FUTURE: if we knew the next char wasn't white space
		// or a hex digit we could avoid appending it here.
		return appendable.append("\\").append(Integer.toHexString(ch)).append(' ');
	}
}
