package net.darkmist.alib.proxy;

import java.util.Set;

public class DefaultProxy<T> extends NonReflectiveProxyBase<T>
{
	private T target;

	public DefaultProxy(T target)
	{
		super(target);
		this.target = target;
	}

	public DefaultProxy(T target, Set<ProxyFlags> flags)
	{
		super(target, flags);
		this.target = target;
	}

	@Override
	protected T getProxyTargetInternal()
	{
		return target;
	}

	/**
	 * Called by setProxyTarget to set subclass's target field.
	 */
	protected void setProxyTargetInternal(T target)
	{
		this.target = ProxyFlags.validate(proxyFlags, target);
	}

	public static class Accessible<T> extends DefaultProxy<T> implements AccessibleProxy<T>
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

	public static class Retargetable<T> extends DefaultProxy<T> implements RetargetableProxy<T>
	{
		public Retargetable(T target)
		{
			super(target);
		}
	
		public Retargetable(T target, Set<ProxyFlags> flags)
		{
			super(target,flags);
		}
		
		@Override
		public void setProxyTarget(T target)
		{
			setProxyTargetInternal(target);
		}
	}

	public static class AccessibleRetargetable<T> extends DefaultProxy<T> implements AccessibleProxy<T>, RetargetableProxy<T>
	{
		public AccessibleRetargetable(T target)
		{
			super(target);
		}
	
		public AccessibleRetargetable(T target, Set<ProxyFlags> flags)
		{
			super(target, flags);
		}

		@Override
		public T getProxyTarget()
		{
			return getProxyTargetInternal();
		}
		
		@Override
		public void setProxyTarget(T target)
		{
			setProxyTargetInternal(target);
		}
	}

}
