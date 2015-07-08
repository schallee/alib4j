package net.darkmist.alib.proxied;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.darkmist.alib.ref.SimpleRef;

public class SimpleRefInvocationHandler<T> extends SimpleRef<T> implements InvocationHandler
{
	public SimpleRefInvocationHandler(Class<T> iface, T target)
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
