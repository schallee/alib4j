/*
 *  Copyright (C) 2015 Ed Schaller <schallee@darkmist.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.darkmist.alib.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class CollectionWrapper<T,I extends Collection<T>> extends IterableWrapper<T,I> implements Collection<T>
{
	public CollectionWrapper(I target)
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
	@SuppressWarnings("PMD.UseVarargs")	// part of interface
	public <S> S[] toArray(S[] array)
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
	 * As {@link CollectionWrapper} but with bulk mutation operations
	 * are implemented in terms of single operations to simplify
	 * overloading. Mutation operations include iterator() and clear().
	 */
	public static class SimplifiedCollectionWrapper<T,I extends Collection<T>> extends CollectionWrapper<T,I>
	{
		public SimplifiedCollectionWrapper(I target)
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

			for(Iterator<T> i = iterator(); i.hasNext();)
			{
				T e = i.next();

				if(!s.contains(e))
				{
					i.remove();
					ret = true;
				}
			}
			return ret;
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
			return new IteratorWrapper<T, Iterator<T>>(super.iterator())
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
					SimplifiedCollectionWrapper.this.remove(last);
				}
			};
		}
	}

}
