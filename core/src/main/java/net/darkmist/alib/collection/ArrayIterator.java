package net.darkmist.alib.collection;


/**
 * Iterator that iterates over an array. Note that this does NOT make
 * a copy of the array so changes to the passed array may cause issues
 * with iteration.
 * @deprecated Use {@link Iterators#getArrayIterator(T...)} instead.
 */
@Deprecated
public final class ArrayIterator<T> extends Iterators.ArrayIterator<T>
{
	/**
	 * Given an array, create an iterator.
	 * @param array The array to iterate over.
	 * @throws NullPointerException if array is null.
	 */
	public ArrayIterator(T...array)
	{
		super(array);
	}
}
