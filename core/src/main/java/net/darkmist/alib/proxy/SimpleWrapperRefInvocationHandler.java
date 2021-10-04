package net.darkmist.alib.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;
import net.darkmist.alib.ref.Ref;
import net.darkmist.alib.ref.WrapperRef;

public class SimpleWrapperRefInvocationHandler<T> extends WrapperRef<T> implements InvocationHandler
{
	public SimpleWrapperRefInvocationHandler(Class<T> iface, Ref<T> target)
	{
		super(NullSafe.requireNonNull(target,"target"));
		NullSafe.requireNonNull(iface);
		if(!iface.isInstance(target))
			throw new IllegalArgumentException("Target is not an instance of " + iface.getName() + '.');
	}

	@Override
	public Object invoke(Object proxy, Method meth, Object[] args) throws Throwable
	{
		return meth.invoke(getReferent(), args);
	}
}
