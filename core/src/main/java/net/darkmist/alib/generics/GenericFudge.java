package net.darkmist.alib.generics;

import java.util.Map;

/**
 * Static methods to get around issues with generics... 
 */
@SuppressWarnings("unchecked")
public class GenericFudge
{
	/** Only static methods. */
	private GenericFudge()
	{
	}

	/**
	 * Fudge the return from getClass to include the generic.
	 */
	static public <T> Class<? extends T> getClass(T obj)
	{
		return (Class<? extends T>)obj.getClass();
	}

	/**
	 * Fudge a ungenericified map to be one.
	 */
	static public <K,V> Map<K,V> map(Map map)
	{
		return map;
	}
}
