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

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("PMD.UseUtilityClass")	// yes but we're deprecating IteratorUtil at the same time.
public class Iterators
{
	private static final Class<Iterators> CLASS = Iterators.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/**
	 * Package private so deprecated IteratorUtil can subclass...
	 */
	protected Iterators()
	{
		// for deprecated IteratorUtil
	}

	public static <T> Set<T> addToSet(Set<T> set, Iterator<T> i)
	{
		while(i.hasNext())
		{
			T e = i.next();
			set.add(e);
		}
		return set;
	}

	@SuppressWarnings("PMD.LooseCoupling")	// its supposed to return LinkedHashSet!
	public static <T> LinkedHashSet<T> toLinkedHashSet(Iterator<T> i)
	{
		LinkedHashSet<T> set = new LinkedHashSet<T>();
		addToSet(set, i);
		return set;
	}
	
	@SuppressWarnings("PMD.LooseCoupling")	// its supposed to return LinkedHashSet!
	public static <T> LinkedHashSet<T> toLinkedHashSet(Iterator<T> i, int sizeGuess)
	{
		LinkedHashSet<T> set = new LinkedHashSet<T>(sizeGuess);
		addToSet(set, i);
		return set;
	}

	public static <T> Set<T> toSet(Iterator<T> i)
	{
		Set<T> set = new HashSet<T>();
		addToSet(set, i);
		return set;
	}

	public static <T> Set<T> toSet(Iterator<T> i, int sizeGuess)
	{
		Set<T> set = new HashSet<T>(sizeGuess);
		addToSet(set, i);
		return set;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toSetArray(Iterator<T> i)
	{
		return (T[])toLinkedHashSet(i).toArray();
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toSetArray(Iterator<T> i, int sizeGuess)
	{
		return (T[])toLinkedHashSet(i, sizeGuess).toArray();
	}

	/**
	 * Empty iterator singleton.
	 */
	private static final class EmptyIterator<T> extends NonRemovingIterator<T>
	{
		@SuppressWarnings("rawtypes")
		private static EmptyIterator SINGLETON = new EmptyIterator();

		private EmptyIterator()
		{
		}

		@SuppressWarnings("unchecked")
		static <T> EmptyIterator<T> instance()
		{
			return SINGLETON;
		}

		@Override
		public boolean hasNext()
		{
			return false;
		}

		@Override
		public T next()
		{
			throw new NoSuchElementException("This iterator has no content!");
		}

		/**
		 * Standard equals. This allows comparision of this
		 * iterator with a non-EmptyIterator Iterator. They are
		 * considered equal if the other iterators {@link
		 * #hasNext()} also returns false.
		 * @param o Object to compare with
		 * @return true if o is an iterator and o.hasNext()
		 * 	is false.
		 */
		@Override
		public boolean equals(Object o)
		{
			if(o == null)
				return false;
			if(!(o instanceof Iterator))
				return false;
			return !((Iterator<?>)o).hasNext();
		}
		
		/**
		 *  Always returns zero.
		 */
		@Override
		public int hashCode()
		{
				return 0;
		}
	}

	/**
	 * Get an empty iterator.
	 * @return Iterator who's hasNext() is always false.
	 */
	public static <T> Iterator<T> getEmptyIterator()
	{
		return EmptyIterator.instance();
	}

	/**
	 * Iterator that iterates over an array. Note that this does
	 * NOT make a copy of the array so changes to the passed array
	 * may cause issues with iteration.
	 */
	static class ArrayIterator<T> extends NonRemovingIterator<T>
	{
		private final T[] array;
		private int i;

		/**
		 * Given an array, create an iterator.
		 * @param array The array to iterate over.
		 * @throws NullPointerException if array is null.
		 */
		@SafeVarargs
		@SuppressWarnings("varargs")
		public ArrayIterator(T...array)
		{
			if(array == null)
				throw new NullPointerException("Array was null");
			this.array = array;
			this.i = 0;
		}
	
		@Override
		public boolean hasNext()
		{
			return (i<array.length);
		}
	
		@Override
		public T next()
		{
			return array[i++];
		}
	}

	/**
	 * Create an non-modifiable iterator that iterates over an
	 * array. Note that this does NOT make a copy of the array so
	 * changes to the passed array may cause issues with iteration.
	 * @param array to iterate over
	 * @return Iterator that iterates over array. If array is null,
	 * 	a empty iterator is returned.
	 */
	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <T> Iterator<T> getArrayIterator(T...array)
	{
		if(array == null || array.length == 0)
			return getEmptyIterator();
		return new ArrayIterator<T>(array);
	}

	public static <T> Enumeration<T> asEnumeration(final Iterator<T> i)
	{
		if(i == null || !i.hasNext())
			return Enumerations.getEmptyEnumeration();
		return new Enumeration<T>()
		{
			@Override
			public final boolean hasMoreElements()
			{
				return i.hasNext();
			}
	
			@Override
			public final T nextElement()
			{
				return i.next();
			}
		};
	}

	/** Iterator for a single item. Why? So items can be trivially added to a IteratorIterator. */
	static class SingleIterator<U> extends NonRemovingIterator<U>
	{
		private final U single;
		private boolean done;	// so we can actually iterate a null value....

		public SingleIterator(U single)
		{
			this.single=single;
			done=false;
		}

		@Override
		public boolean hasNext()
		{
			return !done;
		}

		@Override
		public U next()
		{
			if(done)
				throw new NoSuchElementException("Single element already iterated over");
			done = true;
			return single;
		}
	}

	public static <T> Iterator<T> getSingletonIterator(T singleton)
	{
		return new SingleIterator<T>(singleton);
	}
}
