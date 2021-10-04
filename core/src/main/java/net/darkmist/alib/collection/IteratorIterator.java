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

package net.darkmist.alib.collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Deque;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IteratorIterator<T> extends NonRemovingIterator<T>
{
	@SuppressWarnings("rawtypes")
	private static final Class<IteratorIterator> CLASS = IteratorIterator.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private final Deque<Iterator<T>> iterators = new LinkedList<Iterator<T>>();
	private Iterator<T> iterator = null;

	@Nullable
	protected Iterator<T> nextIterator()
	{
		if(iterators.isEmpty())
			return (iterator = null);
		return (iterator = iterators.remove());
	}

	protected Iterator<T> getCurrentIterator()
	{
		return iterator;
	}

	@Override
	public boolean hasNext()
	{
		if(iterator==null && nextIterator() == null)
			return false;
		do
		{
			if(iterator.hasNext())
				return true;
			if(nextIterator() == null)
				return false;
		} while(true);
	}

	@Override
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING", justification="Boolean state")
	public T next()
	{
		if(!hasNext())
			throw new NoSuchElementException("Request for next when iterator has hit the end");
		return iterator.next();
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API Method")
	public void prependIterator(Iterator<T> newIterator)
	{
		iterators.addFirst(iterator);
		iterator = newIterator;
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="API method")
	public void appendIterator(Iterator<T> newIterator)
	{
		iterators.addLast(newIterator);
	}

	@SuppressWarnings("unchecked")
	public void prependIterators(Iterator<T>...newIterators)
	{
		for(int i=newIterators.length-1;i>=0;i--)
			prependIterator(newIterators[i]);
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API Method")
	@SuppressWarnings("unchecked")
	public void appendIterators(Iterator<T>...newIterators)
	{
		for(Iterator<T> i : newIterators)
			appendIterator(i);
	}

	private IteratorIterator()
	{
	}

	private IteratorIterator(Iterator<T> newIterator)
	{
		appendIterator(newIterator);
	}

	@SafeVarargs
	private IteratorIterator(Iterator<T>...newIterators)
	{
		appendIterators(newIterators);
	}

	public static <T> IteratorIterator<T> getInstance()
	{
		return new IteratorIterator<T>();
	}

	public static <T> IteratorIterator<T> getInstance(Iterator<T> newIterator)
	{
		return new IteratorIterator<T>(newIterator);
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <T> IteratorIterator<T> getInstance(Iterator<T>...newIterators)
	{
		return new IteratorIterator<T>(newIterators);
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof IteratorIterator))
			return false;
		IteratorIterator<?> that = (IteratorIterator<?>)o;
		if(!NullSafe.equals(this.iterator, that.iterator))
			return false;
		return NullSafe.equals(this.iterators, that.iterators);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(iterator,iterators);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": iterator=" + iterator + " iterators=" + iterators;
	}
}
