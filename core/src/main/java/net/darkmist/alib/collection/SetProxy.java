package net.darkmist.alib.collection;

import java.util.Set;

public class SetProxy<T,I extends Set<T>> extends CollectionProxy<T,I> implements Set<T>
{
	public SetProxy(I target)
	{
		super(target);
	}

	/**
	 * As {@link SetProxy} but with bulk mutation operations
	 * are implemented in terms of single operations to simplify
	 * overloading. Mutation operations include iterator() and
	 * clear().
	 */
	public static class SimplifiedSetProxy<T,I extends Set<T>> extends SimplifiedCollectionProxy<T,I> implements Set<T>
	{
		public SimplifiedSetProxy(I target)
		{
			super(target);
		}
	}
}
