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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class RegexIterator<T> implements Iterator<T>
{
	@SuppressWarnings("rawtypes")
	private static final Class<RegexIterator> CLASS = RegexIterator.class;
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CLASS);

	protected Matcher matcher;
	protected T next;
	protected boolean hasNext;

	protected abstract T getObj(Matcher matcher_param);
	protected abstract String getRegex();

	protected Pattern getPattern()
	{
		return Pattern.compile(getRegex());
	}

	protected Matcher getMatcher(CharSequence data)
	{
		return getPattern().matcher(data);
	}

	private boolean advance()
	{
		if(matcher.find())
		{
			hasNext = true;
			next = getObj(matcher);
		}
		else
			hasNext = false;
		return hasNext;
	}

	private void init(CharSequence data)
	{
		matcher = getMatcher(data);
		hasNext = false;
	}

	public RegexIterator(byte[] data)
	{
		init(new String(data));
	}

	public RegexIterator(CharSequence data)
	{
		init(data);
	}

	@Override
	public boolean hasNext()
	{
		if(hasNext)
			return true;
		return advance();
	}

	@Override
	public T next() throws NoSuchElementException
	{
		T ret;

		if(!hasNext)
		{
			if(!advance())
				throw new NoSuchElementException();
		}
		ret = next;
		next = null;	// don't keep a reference
		hasNext = false;
		return ret;
	}

	@Override
	public void remove() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
}
