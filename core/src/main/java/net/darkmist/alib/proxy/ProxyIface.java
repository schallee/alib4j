package net.darkmist.alib.proxy;

public interface ProxyIface<T>
{
	public T getProxyTarget();
	public void setProxyTarget(T target);
	public void clearProxyTarget();
}
