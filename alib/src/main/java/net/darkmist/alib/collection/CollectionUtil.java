package net.darkmist.alib.collection;

import java.util.Collection;

public abstract class CollectionUtil
{
	public static <U, T extends Collection<U>> T addAll(T collection, U[] array)
	{
		for(U i : array)
			collection.add(i);
		return collection;
	}
}
