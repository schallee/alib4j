package net.darkmist.alib.generics;

public class GenericFudge
{
	@SuppressWarnings("unchecked")
	static public <T> Class<? extends T> getClass(T obj)
	{
		return (Class<? extends T>)obj.getClass();
	}
}
