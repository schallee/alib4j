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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class Enumerations
{
	private Enumerations()
	{
	}

	@SuppressWarnings("PMD.ReplaceEnumerationWithIterator")
	private static final class EmptyEnumeration<T> implements Enumeration<T>
	{
		@SuppressWarnings("rawtypes")
		private static EmptyEnumeration SINGLETON = new EmptyEnumeration();

		private EmptyEnumeration()
		{
		}

		@SuppressWarnings("unchecked")
		static <T> EmptyEnumeration<T> instance()
		{
			return SINGLETON;
		}

		@Override
		public boolean hasMoreElements()
		{
			return false;
		}

		@Override
		public T nextElement()
		{
			throw new NoSuchElementException("This enumeration is empty");
		}
	}

	public static <T> Enumeration<T> getEmptyEnumeration()
	{
		return EmptyEnumeration.instance();
	}

	private static final class EnumerationIterator<T> extends NonRemovingIterator<T>
	{
		private final Enumeration<T> e;

		/**
		 * @param e The enumeration to back the iterator with.
		 * @throws NullPointerException if e is null
		 */
		public EnumerationIterator(Enumeration<T> e)
		{
			if(e == null)
				throw new NullPointerException("Backing enumeration cannot be null");
			this.e = e;
		}

		@Override
		public boolean hasNext()
		{
			return e.hasMoreElements();
		}

		@Override
		public T next()
		{
			return e.nextElement();
		}
	}

	public static <T> Iterator<T> asIterator(Enumeration<T> e)
	{
		if(e == null || !e.hasMoreElements())
			return Iterators.getEmptyIterator();
		return new EnumerationIterator<T>(e);
	}

	public static <T> Iterable<T> asSingleIterable(final Enumeration<T> e)
	{
		return new Iterable<T>()
		{
			private boolean initial=true;

			@Override
			public Iterator<T> iterator()
			{
				if(!initial)
					throw new UnsupportedOperationException("Calling iterator multiple times is not supported.");
				initial=false;
				return asIterator(e);
			}
		};
	}

	public static <T> Iterable<T> asMultiIterable(final Enumeration<T> e)
	{
		List<T> list = new ArrayList<T>();

		if(e==null || !e.hasMoreElements())
			return Collections.emptyList();
		while(e.hasMoreElements())
			list.add(e.nextElement());
		return list;
	}

	public static <T> List<T> toList(final Enumeration<T> e)
	{
		List<T> list = new ArrayList<T>();

		if(e==null)
			return list;
		while(e.hasMoreElements())
			list.add(e.nextElement());
		return list;
	}
}
