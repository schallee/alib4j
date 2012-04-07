package net.darkmist.alib.collection;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Iterators
{
	private static final Class<Iterators> CLASS = Iterators.class;
        private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/**
	 * Package private so deprecated IteratorUtil can subclass...
	 */
	Iterators()
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

	public static <T> LinkedHashSet<T> toLinkedHashSet(Iterator<T> i)
	{
		LinkedHashSet<T> set = new LinkedHashSet<T>();
		addToSet(set, i);
		return set;
	}
	
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
	 * Iterator that iterates over an array. Note that this does
	 * NOT make a copy of the array so changes to the passed array
	 * may cause issues with iteration.
	 */
	static class ArrayIterator<T> extends NonRemovingIterator<T>
	{
		private T[] array;
		private int i;

		/**
		 * Given an array, create an iterator.
		 * @param array The array to iterate over.
		 * @throws NullPointerException if array is null.
		 */
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
	public static <T> Iterator<T> getArrayIterator(T...array)
	{
		if(array == null || array.length == 0)
			return getEmptyIterator();
		return new ArrayIterator<T>(array);
	}
	
	static class IteratorEnumeration<E> implements Enumeration<E>
	{
		private Iterator<? extends E> i;

		IteratorEnumeration(Iterator<? extends E> i)
		{
			this.i = i;
		}

		public final boolean hasMoreElements()
		{
			return i.hasNext();
		}

		public final E nextElement()
		{
			return i.next();
		}
	}

	public static <T> Enumeration<T> asEnumeration(Iterator<T> i)
	{
		if(i == null || !i.hasNext())
			return Enumerations.getEmptyEnumeration();
		return new IteratorEnumeration(i);
	}

	/** Iterator for a single item. Why? So items can be trivially added to a IteratorIterator. */
	static class SingleIterator<U> extends NonRemovingIterator<U>
	{
		private U single;
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
			U ret;
	
			if(done)
				throw new NoSuchElementException("Single element already iterated over");
			ret = single;
			single = null;
			return ret;
		}
	}

	public static <T> Iterator<T> getSingletonIterator(T singleton)
	{
		return new SingleIterator<T>(singleton);
	}
}
