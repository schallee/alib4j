package net.darkmist.alib.collection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CollectionProxy<T,I extends Collection<T>> extends IterableProxy<T,I> implements Collection<T>
{
	public CollectionProxy(I target)
	{
		super(target);
	}

	@Override
	public int size()
	{
		return target.size();
	}

	@Override
	public boolean isEmpty()
	{
		return target.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		return target.contains(o);
	}

	@Override
	public Object[] toArray()
	{
		return target.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array)
	{
		return target.toArray(array);
	}

	@Override
	public boolean add(T e)
	{
		return target.add(e);
	}

	@Override
	public boolean remove(Object o)
	{
		return target.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return target.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c)
	{
		return target.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return target.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return target.retainAll(c);
	}

	@Override
	public void clear()
	{
		target.clear();
	}

	@Override
	public boolean equals(Object o)
	{
		return target.equals(o);
	}

	@Override
	public int hashCode()
	{
		return target.hashCode();
	}

	/**
	 * As {@link CollectionProxy} but with bulk mutation operations
	 * are implemented in terms of single operations to simplify
	 * overloading. Mutation operations include iterator() and clear().
	 */
	public static class SimplifiedCollectionProxy<T,I extends Collection<T>> extends CollectionProxy<T,I>
	{
		public SimplifiedCollectionProxy(I target)
		{
			super(target);
		}

		@Override
		public boolean containsAll(Collection<?> c)
		{
			return target.containsAll(c);
		}

		@Override
		public boolean addAll(Collection<? extends T> c)
		{
			boolean ret = false;

			for(T t : c)
				ret |= add(t);
			return ret;
		}

		@Override
		public boolean removeAll(Collection<?> c)
		{
			boolean ret = false;

			for(Object o : c)
				ret |= remove(o);
			return ret;
		}

		@Override
		public boolean retainAll(Collection<?> c)
		{
			Set<?> s = Sets.asSet(c);
			boolean ret = false;

			c=null;

			for(Iterator<T> i = iterator(); i.hasNext();)
			{
				T e = i.next();

				if(!s.contains(e))
				{
					i.remove();
					ret = true;
				}
			}
			return target.retainAll(c);
		}

		@Override
		public void clear()
		{
			for(Iterator<T> i = iterator(); i.hasNext();)
				i.remove();
		}

		@Override
		public Iterator<T> iterator()
		{
			return new IteratorProxy<T, Iterator<T>>(super.iterator())
			{
				private boolean lastValid = false;
				private T last;

				@Override
				public T next()
				{
					last = target.next();
					lastValid=true;
					return last;
				}

				@Override
				public void remove()
				{
					if(!lastValid)
						throw new IllegalStateException("Next has not been called.");
					SimplifiedCollectionProxy.this.remove(last);
				}
			};
		}
	}

}
