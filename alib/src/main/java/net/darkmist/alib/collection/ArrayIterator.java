package net.darkmist.alib.collection;

// new since qcomm

import java.util.Iterator;

public class ArrayIterator<T> extends NonRemovingIterator<T>
{
	private T[] array;
	private int i;

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
