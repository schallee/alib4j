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

package net.darkmist.alib.str;

import  java.util.regex.Matcher;

import javax.annotation.Nullable;

public class RegexStringIterator extends RegexIterator<String>
{
	protected int getGroup()
	{
		return 0;
	}

	@Nullable
	@Override
	protected String getObj(Matcher matcher_param)
	{
		return matcher_param.group(getGroup());
	}

	@Deprecated	// Not our job to decode bytes.
	public RegexStringIterator(CharSequence regex, byte[] data)
	{
		super(regex, data);
	}

	public RegexStringIterator(CharSequence regex, CharSequence data)
	{
		super(regex, data);
	}
}
