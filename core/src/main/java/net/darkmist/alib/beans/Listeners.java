package net.darkmist.alib.beans;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import net.darkmist.alib.collection.SetWrapper;
import net.darkmist.alib.lang.NullSafe;
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

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof Listeners))
			return false;
		Listeners<?> that = (Listeners<?>)o;
		return NullSafe.equals(this.proxy, that.proxy);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(proxy);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " listeners fireing through " + proxy + '.';
	}
}
