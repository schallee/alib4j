package net.darkmist.alib.proxy;

import java.util.Set;

public class FinalProxy<T> extends NonReflectiveProxyBase<T>
{
	private final T target;

	public FinalProxy(T target)
	{
		super(target);
		this.target = target;
	}

	public FinalProxy(T target, Set<ProxyFlags> flags)
	{
		super(target, flags);
		this.target = target;
	}

	@Override
	protected T getProxyTargetInternal()
	{
		return target;
	}

	public static class Accessible<T> extends FinalProxy<T> implements AccessibleProxy<T>
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
