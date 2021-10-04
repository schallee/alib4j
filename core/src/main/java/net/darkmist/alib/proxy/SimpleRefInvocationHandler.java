package net.darkmist.alib.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.darkmist.alib.ref.SimpleRef;

import static net.darkmist.alib.lang.NullSafe.requireNonNull;

public class SimpleRefInvocationHandler<T> extends SimpleRef<T> implements InvocationHandler
{
	public SimpleRefInvocationHandler(Class<T> iface, T target)
	{
		super(requireNonNull(target,"target"));
		if(!requireNonNull(iface,"iface").isInstance(target))
			throw new IllegalArgumentException("Target is not an instance of " + iface.getName() + '.');
	}

	@Override
	public Object invoke(Object proxy, Method meth, Object[] args) throws Throwable
	{
		return meth.invoke(getReferent(), args);
	}
}
