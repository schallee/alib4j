package net.darkmist.alib.collection;

import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class IteratorUtil
{
	private static final Class<IteratorUtil> CLASS = IteratorUtil.class;
        private static final Log logger = LogFactory.getLog(CLASS);

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

	private static class EmptyIterator<T> extends NonRemovingIterator<T>
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

	public static <T> Iterator<T> getEmptyIterator()
	{
		return EmptyIterator.instance();
	}
}
