package net.darkmist.alib.proxy;

import java.util.Set;

public class VolatileProxy<T> extends NonReflectiveProxyBase<T> implements RetargetableProxy<T>
{
	private volatile T target;

	public VolatileProxy(T target)
	{
		super(target);
		this.target = target;
	}

	public VolatileProxy(T target, Set<ProxyFlags> flags)
	{
		super(target, flags);
		this.target = target;
	}

	@Override
	protected T getProxyTargetInternal()
	{
		return target;
	}

	@Override
	public void setProxyTarget(T target)
	{
		this.target = ProxyFlags.validate(proxyFlags, target);
	}

	public static class Accessible<T> extends VolatileProxy<T> implements AccessibleProxy<T>
	{
		public Accessible(T target)
		{
			super(target);
		}
	
		public Accessible(T target, Set<ProxyFlags> flags)
		{
			super(target, flags);
		}

		@Override
		public T getProxyTarget()
		{
			return getProxyTargetInternal();
		}
	}
}
