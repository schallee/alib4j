package net.darkmist.alib.ref;

public interface Ref<T>
{
	void set(T obj) throws RefException;
	void setReferent(T obj) throws RefException;
	T get() throws RefException;
	T getReferent() throws RefException;
	void clear() throws RefException;
	boolean isSetSupported();
}
