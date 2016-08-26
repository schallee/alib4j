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

	public static class AccessibleDefaultProxy<T> extends DefaultProxy<T> implements AccessibleProxy<T>
	{
		public AccessibleDefaultProxy(T target)
		{
			super(target);
		}
	
		public AccessibleDefaultProxy(T target, Set<ProxyFlags> flags)
		{
			super(target, flags);
		}

		@Override
		public T getProxyTarget()
		{
			return getProxyTargetInternal();
		}
	}

	public static class RetargetableDefaultProxy<T> extends DefaultProxy<T> implements RetargetableProxy<T>
	{
		public RetargetableDefaultProxy(T target)
		{
			super(target);
		}
	
		public RetargetableDefaultProxy(T target, Set<ProxyFlags> flags)
		{
			super(target,flags);
		}
		
		@Override
		public void setProxyTarget(T target)
		{
			setProxyTargetInternal(target);
		}
	}

	public static class AccessibleRetargetableDefaultProxy<T> extends DefaultProxy<T> implements AccessibleProxy<T>, RetargetableProxy<T>
	{
		public AccessibleRetargetableDefaultProxy(T target)
		{
			super(target);
		}
	
		public AccessibleRetargetableDefaultProxy(T target, Set<ProxyFlags> flags)
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
