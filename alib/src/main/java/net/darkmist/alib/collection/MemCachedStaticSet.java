package net.darkmist.alib.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.darkmist.alib.ref.MemCachedRef;

public class MemCachedStaticSet<T> extends MemCachedRef<Set<T>> implements Set<T>
{
	protected final T[] array;

	public MemCachedStaticSet(T...array)
	{
		this.array = array;
	}

	protected Set<T> make()
	{
		Set<T> set = new HashSet<T>(array.length);

		for(T entry : array)
			set.add(entry);
		return Collections.unmodifiableSet(set);
	}

	// set
	public int size()
	{
		return get().size();
	}

	public boolean isEmpty()
	{
		return get().isEmpty();
	}

	public boolean contains(Object o)
	{
		return get().contains(o);
	}

	public Iterator<T> iterator()
	{
		return get().iterator();
	}

	public boolean add(T e)
	{
		return get().add(e);
	}

	public Object[] toArray()
	{
		return get().toArray();
	}

	public <U> U[] toArray(U[] a)
	{
		return get().toArray(a);
	}

	public boolean remove(Object o)
	{
		return get().remove(o);
	}

	public boolean containsAll(Collection<?> c)
	{
		return get().containsAll(c);
	}

	public boolean addAll(Collection<? extends T> c)
	{
		return get().addAll(c);
	}

	public boolean retainAll(Collection<?> c)
	{
		return get().retainAll(c);
	}

	public boolean removeAll(Collection<?> c)
	{
		return get().removeAll(c);
	}

	// public void clear()
}

