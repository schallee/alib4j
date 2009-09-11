package net.darkmist.alib.collection;

import java.util.NoSuchElementException;

/** Iterator for a single item. Why? So items can be trivially added to a IteratorIterator. */
public class SingleIterator<U> extends NonRemovingIterator<U>
{
	private U single;
	private boolean done;	// so we can actually iterate a null value....

	public SingleIterator(U single)
	{
		this.single=single;
		done=false;
	}

	public boolean hasNext()
	{
		return !done;
	}

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
