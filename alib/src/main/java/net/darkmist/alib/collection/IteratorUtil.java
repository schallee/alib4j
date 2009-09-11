package net.darkmist.alib.collection;

// probably not rewritten since qcomm

import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;

public abstract class IteratorUtil
{
	private static final Logger logger = Logger.getLogger(IteratorUtil.class);

	public static <T> Set<T> addToSet(Set<T> set, Iterator<T> i)
	{
		while(i.hasNext())
		{
			T e = i.next();
			set.add(e);
		}
		return set;
	}

	public static <T> LinkedHashSet<T> toSet(Iterator<T> i)
	{
		LinkedHashSet<T> set = new LinkedHashSet<T>();
		addToSet(set, i);
		return set;
	}

	public static <T> LinkedHashSet<T> toSet(Iterator<T> i, int sizeGuess)
	{
		LinkedHashSet<T> set = new LinkedHashSet<T>(sizeGuess);
		addToSet(set, i);
		return set;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toSetArray(Iterator<T> i)
	{
		return (T[])toSet(i).toArray();
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toSetArray(Iterator<T> i, int sizeGuess)
	{
		return (T[])toSet(i, sizeGuess).toArray();
	}
}