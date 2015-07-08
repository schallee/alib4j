package net.darkmist.alib.beans;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import net.darkmist.alib.collection.SetWrapper;
import net.darkmist.alib.proxy.MultiplexingProxies;

public class Listeners<T> extends SetWrapper<T, Set<T>>
{
	private final T proxy;

	public Listeners(Class<T> iface)
	{
		super(new CopyOnWriteArraySet<T>());
		proxy = MultiplexingProxies.getProxy(iface, this);
	}

	public T fire()
	{
		return proxy;
	}
}
