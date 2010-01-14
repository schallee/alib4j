package net.darkmist.alib.collection;

import java.util.Iterator;

/**
 * Iterator that iterates over an array.
 */
public class ArrayIterator<T> extends NonRemovingIterator<T>
{
	private T[] array;
	private int i;

	/**
	 * Given an array, create an iterator.
	 * @param array The array to iterate over.
	 */
	public ArrayIterator(T[] array)
	{
		this.array = array;
		this.i = 0;
	}

	public boolean hasNext()
	{
		return (i<array.length);
	}

	public T next()
	{
		return array[i++];
	}
}
