package net.darkmist.alib.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;
import net.darkmist.alib.reflect.Reflections;

import static net.darkmist.alib.lang.NullSafe.requireNonNull;

public class MultiplexingInvocationHandler<T, C extends Iterable<T>> implements InvocationHandler
{
	private final static Set<MultiplexingFlags> DEFAULT_FLAGS = Collections.unmodifiableSet(EnumSet.of(
		MultiplexingFlags.RETURN_LAST
	));
	private final C targets;
	private final Set<MultiplexingFlags> flags;

	@SuppressFBWarnings(value="OPN_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public MultiplexingInvocationHandler(Class<T> iface, C targets, @Nullable Set<MultiplexingFlags> flags)
	{
		this.targets = requireNonNull(targets, "targets");
		requireNonNull(iface, "iface");
		if(flags==null)
			this.flags = DEFAULT_FLAGS;
		else
			this.flags = Collections.unmodifiableSet(EnumSet.copyOf(flags));
	}

	public MultiplexingInvocationHandler(Class<T> iface, C targets)
	{
		this(iface, targets, null);
	}

	@Nullable
	private Object invokeVoidRet(Method meth, Object...args) throws Throwable
	{
		for(T item : targets)
		{
			if(item==null && flags.contains(MultiplexingFlags.IGNORE_NULL))
				continue;
			try
			{
				meth.invoke(item, args);
			}
			catch(Throwable e)
			{
				if(!flags.contains(MultiplexingFlags.IGNORE_EXCEPTIONS))
					throw e;
			}
		}
		return null;
	}

	@Nullable
	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS",justification="I don't know why spot bugs is claiming this.")
	private Object invokeNonVoidRet(Method meth, Object...args) throws Throwable
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
			catch(Throwable e)
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
		throw new IllegalStateException("No return from any multiplexed items with method " + meth);
	}

	@Nullable
	@Override
	public Object invoke(Object proxy, Method meth, Object[] args) throws Throwable
	{
		if(meth.getReturnType()==Void.TYPE)
			return invokeVoidRet(meth, args);
		return invokeNonVoidRet(meth, args);
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof MultiplexingInvocationHandler))
			return false;
		MultiplexingInvocationHandler<?,?> that = (MultiplexingInvocationHandler<?,?>)o;
		if(!NullSafe.equals(this.flags, that.flags))
			return false;
		return NullSafe.equals(this.targets, that.targets);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(targets, flags);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": flags=" + flags + " targets=" + targets;
	}

}

