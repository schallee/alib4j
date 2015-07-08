package net.darkmist.alib.proxied;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import net.darkmist.alib.reflect.Reflections;

public class MultiplexingInvocationHandler<T, C extends Iterable<T>> implements InvocationHandler
{
	private final static Set<MultiplexingFlags> DEFAULT_FLAGS = Collections.unmodifiableSet(EnumSet.of(
		MultiplexingFlags.IGNORE_NULL,
		MultiplexingFlags.IGNORE_EXCEPTIONS,
		MultiplexingFlags.RETURN_LAST
	));
	private final C targets;
	private final Set<MultiplexingFlags> flags;

	public MultiplexingInvocationHandler(Class<T> iface, C targets, Set<MultiplexingFlags> flags)
	{
		if((this.targets=targets)==null)
			throw new NullPointerException("Targets cannot be null.");
		if(iface==null)
			throw new NullPointerException("Iface cannot be null.");
		if(flags==null)
			this.flags = DEFAULT_FLAGS;
		else
			this.flags = Collections.unmodifiableSet(EnumSet.copyOf(flags));
	}

	public MultiplexingInvocationHandler(Class<T> iface, C targets)
	{
		this(iface, targets, null);
	}

	private Object invokeVoidRet(Method meth, Object[] args) throws Throwable
	{
		for(T item : targets)
		{
			if(item==null && flags.contains(MultiplexingFlags.IGNORE_NULL))
				continue;
			try
			{
				meth.invoke(item, args);
			}
			catch(Exception e)
			{
				if(!flags.contains(MultiplexingFlags.IGNORE_EXCEPTIONS))
					throw e;
			}
		}
		return null;
	}

	private Object invokeNonVoidRet(Method meth, Object[] args) throws Throwable
	{
		boolean retSet=false;
		boolean first=true;
		Object ret=null;

		for(T item : targets)
		{
			if(item==null && flags.contains(MultiplexingFlags.IGNORE_NULL))
				continue;
			try
			{
				ret = meth.invoke(item, args);
				retSet = true;
				if(first && flags.contains(MultiplexingFlags.RETURN_FIRST_SKIP))
					return ret;
				else
					first=false;
			}
			catch(Exception e)
			{
				if(!flags.contains(MultiplexingFlags.IGNORE_EXCEPTIONS))
					throw e;
			}
		}

		if(retSet)
			return ret;
		if(flags.contains(MultiplexingFlags.ZERO_ON_NO_RETURN))
		{
			Class<?> retType = meth.getReturnType();

			if(!retType.isPrimitive())
				return null;
			return Reflections.zeroIfPrimitiveNullOtherwise(retType);
		}
		throw new IllegalStateException("No return from any multiplexed items and non-void method.");
	}

	@Override
	public Object invoke(Object proxy, Method meth, Object[] args) throws Throwable
	{
		if(meth.getReturnType()==Void.TYPE)
			return invokeVoidRet(meth, args);
		return invokeNonVoidRet(meth, args);
	}
}

