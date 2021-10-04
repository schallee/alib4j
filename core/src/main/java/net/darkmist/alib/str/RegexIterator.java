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

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RegexIterator<T> implements Iterator<T>
{
	private static final Class<?> CLASS = RegexIterator.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	protected final Matcher matcher;
	protected T next;
	protected boolean hasNext;

	@Nullable
	protected abstract T getObj(Matcher matcher_param);

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

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public RegexIterator(Pattern pat, CharSequence data)
	{
		matcher = pat.matcher(data);
		hasNext = false;
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public RegexIterator(CharSequence regex, CharSequence data)
	{
		this(Pattern.compile(regex.toString()), data);
	}

	@Deprecated	// Not our job to decode bytes
	public RegexIterator(CharSequence regex, byte[] data)
	{
		this(regex, new String(data, Charset.defaultCharset()));
	}

	@Override
	public boolean hasNext()
	{
		if(hasNext)
			return true;
		return advance();
	}

	@Override
	public T next()
	{
		T ret;

		if(!hasNext && !advance())
			throw new NoSuchElementException();
		ret = next;
		next = null;	// don't keep a reference
		hasNext = false;
		return ret;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
