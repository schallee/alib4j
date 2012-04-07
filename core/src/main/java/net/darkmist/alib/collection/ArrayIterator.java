package net.darkmist.alib.collection;

import java.util.Iterator;

/**
 * Iterator that iterates over an array. Note that this does NOT make
 * a copy of the array so changes to the passed array may cause issues
 * with iteration.
 */
final class ArrayIterator<T> extends NonRemovingIterator<T>
{
	private T[] array;
	private int i;

	/**
	 * Given an array, create an iterator.
	 * @param array The array to iterate over.
	 * @throws NullPointerException if array is null.
	 */
	public ArrayIterator(T[] array)
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
