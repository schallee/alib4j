package net.darkmist.alib.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.darkmist.alib.lang.NullSafe;

import static net.darkmist.alib.lang.NullSafe.requireNonNull;

public class SimpleInvocationHandler<T> implements InvocationHandler
{
	private final T target;

	public SimpleInvocationHandler(Class<T> iface, T target)
	{
		this.target = requireNonNull(target, "target");
		if(!requireNonNull(iface, "iface").isInstance(target))
			throw new IllegalArgumentException("Target is not an instance of " + iface.getName() + '.');
	}

	@Override
	public Object invoke(Object proxy, Method meth, Object[] args) throws Throwable
	{
		return meth.invoke(target, args);
	}

	@Override
	public String toString()
	{
		return SimpleInvocationHandler.class.getSimpleName() + " targetting " + target + '.';
	}

	@Override
	public int hashCode()
	{
		return target.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		SimpleInvocationHandler<?> other;

		if(o == this)
			return true;
		if(o == null)
			return false;
		if(!(o instanceof SimpleInvocationHandler))
			return false;
		other = (SimpleInvocationHandler<?>)o;
		return NullSafe.equals(other.target, target);
	}
}
