package net.darkmist.alib.collection;

import java.util.Iterator;

public class IteratorProxy<T, I extends Iterator<T>> implements Iterator<T>
{
	protected final I target;

	public IteratorProxy(I target)
	{
		if((this.target=target)==null)
			throw new NullPointerException();
	}

	@Override
	public boolean hasNext()
	{
		return target.hasNext();
	}

	@Override
	public T next()
	{
		return target.next();
	}

	@Override
	public void remove()
	{
		target.remove();
	}
}
