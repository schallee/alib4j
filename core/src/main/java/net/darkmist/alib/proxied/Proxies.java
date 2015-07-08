package net.darkmist.alib.proxied;

import java.lang.reflect.Proxy;

public final class Proxies
{
	private Proxies()
	{
		throw new IllegalStateException(getClass().getName() + " is not instantiable.");
	}

	public static <T> T getProxy(Class<T> iface, T target)
	{
		return iface.cast(Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[]{iface}, new SimpleInvocationHandler<T>(iface, target)));
	}
}
