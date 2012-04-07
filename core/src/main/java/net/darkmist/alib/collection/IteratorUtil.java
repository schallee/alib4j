package net.darkmist.alib.collection;

import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class IteratorUtil
{
	private static final Class<IteratorUtil> CLASS = IteratorUtil.class;
        private static final Log logger = LogFactory.getLog(CLASS);

	private IteratorUtil()
	{
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

	public static <T> LinkedHashSet<T> toSet(Iterator<T> i)
	{
		LinkedHashSet<T> set = new LinkedHashSet<T>();
		addToSet(set, i);
		return set;
	}

	public static <T> LinkedHashSet<T> toSet(Iterator<T> i, int sizeGuess)
	{
		LinkedHashSet<T> set = new LinkedHashSet<T>(sizeGuess);
		addToSet(set, i);
		return set;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toSetArray(Iterator<T> i)
	{
		return (T[])toSet(i).toArray();
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toSetArray(Iterator<T> i, int sizeGuess)
	{
		return (T[])toSet(i, sizeGuess).toArray();
	}

	/**
	 * Empty iterator singleton.
	 */
	private static final class EmptyIterator<T> extends NonRemovingIterator<T>
	{
		private static EmptyIterator SINGLETON = new EmptyIterator();

		private EmptyIterator()
		{
		}

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
			return !((Iterator)o).hasNext();
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
	 * Create an non-modifiable iterator that iterates over an
	 * array. Note that this does NOT make a copy of the array so
	 * changes to the passed array may cause issues with iteration.
	 * @param array to iterate over
	 * @return Iterator that iterates over array. If array is null,
	 * 	a empty iterator is returned.
	 */
	public static <T> Iterator<T> getArrayIterator(T...array)
	{
		if(array == null || array.length == 0)
			return getEmptyIterator();
		return new ArrayIterator<T>(array);
	}
}
