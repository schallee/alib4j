package net.darkmist.alib.collection;

import java.util.Collection;
import java.util.Set;

/**
 * Set that allows additions that will be weak references.
 *
 * <b>Note:</b> that presently it is an implemenation detail as to
 * what happens when a object is added both strongly and weakly. @{link
 * Set#remove(Object)} and related are still guaranteed to remove the
 * element, whether strongly or weakly referenced, so that the Set
 * contract continues to be valid.
 */
public interface StrongWeakSet<T> extends Set<T>
{
	/**
	 * Add an element that will be weakly referenced.
	 * @param e Element to add
	 * @return true if the set was modified
	 */
	public boolean addWeak(T e);

	/**
	 * Add a collection of elements that will be weakly
	 * referenced.
	 * @param c Collection of elements to add
	 * @return true if the set was modified
	 */
	public boolean addAllWeak(Collection<? extends T> c);
}
