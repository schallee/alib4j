package net.darkmist.alib.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.darkmist.alib.ref.Ref;
import net.darkmist.alib.ref.WrapperRef;

public class SimpleWrapperRefInvocationHandler<T> extends WrapperRef<T> implements InvocationHandler
{
	public SimpleWrapperRefInvocationHandler(Class<T> iface, Ref<T> target)
	{
		super(target);
		if(target==null)
			throw new NullPointerException("Target cannot be null.");
		if(iface==null)
			throw new NullPointerException("Iface cannot be null.");
		if(!iface.isInstance(target))
			throw new IllegalArgumentException("Target is not an instance of " + iface.getName() + '.');
	}

	@Override
	public Object invoke(Object proxy, Method meth, Object[] args) throws Throwable
	{
		return meth.invoke(getReferent(), args);
	}
}
