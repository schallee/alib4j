package net.darkmist.alib.proxy;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public final class MultiplexingProxies
{
	private MultiplexingProxies()
	{
		throw new IllegalStateException(getClass().getName() + " is not instantiable.");
	}

	public static <T,C extends Iterable<T>> T getProxy(Class<T> iface, C targets)
	{
		return iface.cast(Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[]{iface}, new MultiplexingInvocationHandler<T,C>(iface, targets)));
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <T> T getProxy(Class<T> iface, T...targets)
	{
		return getProxy(iface, Arrays.asList(targets));
	}
}
