package net.darkmist.alib.collection;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Iterator;

public final class Enumerations
{
	private Enumerations()
	{
	}

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

		// in 1.6: @Override
		public boolean hasMoreElements()
		{
			return false;
		}

		// in 1.6: @Override
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
		private Enumeration<T> e;

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

	public <T> Iterator<T> asIterator(Enumeration<T> e)
	{
		if(e == null || !e.hasMoreElements())
			return Iterators.getEmptyIterator();
		return new EnumerationIterator<T>(e);
	}
}
