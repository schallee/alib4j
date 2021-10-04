package net.darkmist.alib.proxy;

import java.lang.reflect.Proxy;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.ref.Ref;
import net.darkmist.alib.ref.SoftRef;
import net.darkmist.alib.ref.WeakRef;

public final class ProxiedRefs
{
	private ProxiedRefs()
	{
		throw new IllegalStateException(getClass().getName() + " is not instantiable.");
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public static <T> T getProxy(Class<T> iface, Ref<T> target)
	{
		return iface.cast(
			Proxy.newProxyInstance(
				iface.getClassLoader(),
				new Class<?>[]{iface},
				new SimpleWrapperRefInvocationHandler<T>(iface, target)
			)
		);
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public static <T> T getProxy(Class<T> iface, T target)
	{
		return iface.cast(Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[]{iface}, new SimpleRefInvocationHandler<T>(iface, target)));
	}

	public static <T> T getStrongProxy(Class<T> iface, T target)
	{
		return getProxy(iface, target);
	}

	public static <T> T getWeakProxy(Class<T> iface, T target)
	{
		return getProxy(iface, new WeakRef<T>(target));
	}

	public static <T> T getSoftProxy(Class<T> iface, T target)
	{
		return getProxy(iface, new SoftRef<T>(target));
	}
}
