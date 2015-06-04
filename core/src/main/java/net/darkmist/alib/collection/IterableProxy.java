package net.darkmist.alib.collection;

import java.util.Iterator;

public class IterableProxy<T,I extends Iterable<T>> implements Iterable<T>
{
	protected final I target;

	public IterableProxy(I target)
	{
		if((this.target = target)==null)
			throw new NullPointerException();
	}

	@Override
	public Iterator<T> iterator()
	{
		return target.iterator();
	}
}
