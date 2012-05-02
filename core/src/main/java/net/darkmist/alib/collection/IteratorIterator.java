package net.darkmist.alib.collection;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class IteratorIterator<T> extends NonRemovingIterator<T>
{
	@SuppressWarnings("rawtypes")
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
