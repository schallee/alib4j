package net.darkmist.alib.proxy;

public interface RetargetableProxy<T>
{
	/** 
	 * Set the proxy's target.
	 * @param target object to nwo target
	 * @throws UnsupportedOperationException if set is not allowed
	 * @throws NullPointerException is target is null and is not allowed to be.
	 */
	void setProxyTarget(T target);
}
