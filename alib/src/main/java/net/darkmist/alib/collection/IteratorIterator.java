package net.darkmist.alib.collection;

// new since qcomm

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;

public class IteratorIterator<T,U extends Iterator<T>> extends NonRemovingIterator<T>
{
	private static final Logger logger = Logger.getLogger(IteratorIterator.class);
	private LinkedList<U> iterators = new LinkedList<U>();
	private U iterator = null;

	protected U nextIterator()
	{
		if(iterators.isEmpty())
			return (iterator = null);
		return (iterator = iterators.remove());
	}

	protected U getCurrentIterator()
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

	public void prependIterator(U newIterator)
	{
		iterators.addFirst(iterator);
		iterator = newIterator;
	}

	public void appendIterator(U newIterator)
	{
		iterators.addLast(newIterator);
	}

	public void prependIterators(U...newIterators)
	{
		for(int i=newIterators.length-1;i>=0;i--)
			prependIterator(newIterators[i]);
	}

	public void appendIterators(U...newIterators)
	{
		for(U i : newIterators)
			appendIterator(i);
	}

	public IteratorIterator()
	{
	}

	public IteratorIterator(U newIterator)
	{
		appendIterator(newIterator);
	}

	public IteratorIterator(U...newIterators)
	{
		appendIterators(newIterators);
	}
}
