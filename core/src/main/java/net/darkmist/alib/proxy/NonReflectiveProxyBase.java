package net.darkmist.alib.proxy;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import net.darkmist.alib.lang.NullSafe;

public abstract class NonReflectiveProxyBase<T> extends ProxyBase<T>
{
	protected final Set<ProxyFlags> proxyFlags;

	protected NonReflectiveProxyBase()
	{
		this.proxyFlags = ProxyFlags.getDefaultFlags();
	}

	protected NonReflectiveProxyBase(Set<ProxyFlags> proxyFlags)
	{
		if(proxyFlags==null)
			this.proxyFlags = ProxyFlags.getDefaultFlags();
		else
			this.proxyFlags = Collections.unmodifiableSet(EnumSet.copyOf(proxyFlags));
	}

	/**
	 * Set proxy flags and do target validation according to
	 * flags. This does <em>NOT</em> persist the target. The target
	 * should be persisted in a subclass as the variations of target
	 * storage (eg: final,volatile) cannot be handled by the base
	 * implementation.
	 * @param proxyFlags Configuration flags for the proxy.
	 * @param target Target to validate but <em>NOT</em> persist.
	 */
	protected NonReflectiveProxyBase(T target, Set<ProxyFlags> proxyFlags)
	{
		this(proxyFlags);
		ProxyFlags.validate(proxyFlags, target);
	}

	protected NonReflectiveProxyBase(T target)
	{
		this(target,null);
	}

	/**
	 * Called by base class to use target for various purposes.
	 * @returns proxy target target
	 */
	@Override
	protected abstract T getProxyTargetInternal();

	/*===============OBJECT=================*/

	@Override
	protected Object clone() throws  CloneNotSupportedException
	{
		throw new  CloneNotSupportedException();
	}

	@Override
	public String toString()
	{
		return NullSafe.toString(getProxyTargetInternal());

	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(getProxyTargetInternal());
	}

	@Override
	public boolean equals(Object o)
	{
		return NullSafe.equals(getProxyTargetInternal(), o);
	}

}
