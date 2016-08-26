package net.darkmist.alib.proxy;

public interface AccessibleProxy<T>
{
	/** 
	 * Get the proxy's target.
	 * @return target object the proxy defers to
	 * @throws UnsupportedOperationException if get is not allowed
	 */
	T getProxyTarget();
}
