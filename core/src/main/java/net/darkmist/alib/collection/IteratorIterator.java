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
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IteratorIterator<T> extends NonRemovingIterator<T>
{
	private static final Class<IteratorIterator> CLASS = IteratorIterator.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private LinkedList<Iterator<T>> iterators = new LinkedList<Iterator<T>>();
	private Iterator<T> iterator = null;

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
	public T next()
	{
		if(!hasNext())
			throw new NoSuchElementException("Request for next when iterator has hit the end");
		return iterator.next();
	}

	public void prependIterator(Iterator<T> newIterator)
	{
		iterators.addFirst(iterator);
		iterator = newIterator;
	}

	public void appendIterator(Iterator<T> newIterator)
	{
		iterators.addLast(newIterator);
	}

	public void prependIterators(Iterator<T>...newIterators)
	{
		for(int i=newIterators.length-1;i>=0;i--)
			prependIterator(newIterators[i]);
	}

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

	public static <T> IteratorIterator<T> getInstance(Iterator<T>...newIterators)
	{
		return new IteratorIterator<T>(newIterators);
	}
}
