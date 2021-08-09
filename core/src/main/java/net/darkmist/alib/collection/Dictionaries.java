/*
 *  Copyright (C) 2015 Ed Schaller <schallee@darkmist.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.darkmist.alib.collection;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Static utilities for {@link Dictionary}s.
 */
public final class Dictionaries
{
	private Dictionaries()
	{	// only static methods...
	}

	private static class DictionaryMapViewKeySet<K> extends AbstractSet<K> implements Serializable
	{	// Add methods are not supported according to Map#keySet() javadoc.
		private static final long serialVersionUID = 1l;
		private final Dictionary<K,?> dict;

		public DictionaryMapViewKeySet(Dictionary<K,?> dict)
		{
			if((this.dict=dict)==null)
				throw new NullPointerException();
		}

		// Use AbstractSet's throwing variants
		// boolean add(K k);
		// boolean addAll(Collection<? extends k> c);

		@Override
		public void clear()
		{
			for(Enumeration<K> e=dict.keys(); e.hasMoreElements();)
				dict.remove(e.nextElement());
		}

		@Override
		public boolean contains(Object o)
		{
			if(o==null)
				return false;
			return (dict.get(o)!=null);
		}

		// Use AbstratSet's versions:
		// public boolean conatinsAll(Collection<?> c)
		// public boolean equals(java.lang.Object)
		// public int hashCode()

		@Override
		public boolean isEmpty()
		{
			return dict.isEmpty();
		}

		@Override
		public Iterator<K> iterator()
		{
			final Enumeration<K> e = dict.keys();

			return new Iterator<K>()
			{
				private K lastKey = null;

				@Override
				public boolean hasNext()
				{
					return e.hasMoreElements();
				}

				@Override
				public K next()
				{
					lastKey = e.nextElement();
					return lastKey;
				}

				@Override
				public void remove()
				{
					if(lastKey==null)
						throw new IllegalStateException("Either next() has not been called or entry has already been removed.");
					dict.remove(lastKey);
				}

			};
		}

		@Override
		public boolean remove(Object o)
		{
			if(o==null)
				return false;
			return (dict.remove(o)!=null);
		}

		// Use AbstratSet's versions:
		// public boolean removeAll(Collection<?> c)
		// public boolean retainAll(Collection<?> c)
		
		@Override
		public int size()
		{
			return dict.size();
		}

		// Use AbstratSet's versions:
		// public Object[] toArray()
		// public <T> T[] toArray(T[])

	}

	private static class DictionaryMapViewEntry<K,V> implements Map.Entry<K,V>, Serializable
	{
		private static final long serialVersionUID = 1l;
		private final Dictionary<K,V> dict;
		private final K key;

		public DictionaryMapViewEntry(Dictionary<K,V> dict, K key)
		{
			if((this.dict=dict)==null)
				throw new NullPointerException("Dictioanry cannot be null.");
			if((this.key=key)==null)
				throw new NullPointerException("Key cannot be null.");
		}

		@Override
		public K getKey()
		{
			return key;
		}

		@Override
		public V getValue()
		{
			return dict.get(key);
		}

		@Override
		public V setValue(V val)
		{
			return dict.put(key, val);
		}

		@Override
		public boolean equals(Object o)
		{
			Map.Entry<?,?> other;
			Object otherVal;
			V val;

			if(this==o)
				return true;
			if(o==null)
				return false;
			if(!(o instanceof Map.Entry))
				return false;
			other = (Map.Entry<?,?>)o;
			if((otherVal=other.getValue())==null)
				return false;
			if((val=dict.get(key))==null)
				return false;
			return val.equals(otherVal);
		}

		@Override
		public int hashCode()
		{
			V val = dict.get(key);
						
			return (key==null ? 0 : key.hashCode()) ^ (val==null ? 0 : val.hashCode());
		}
	}

	private static class DictionaryMapViewEntrySet<K,V> extends AbstractSet<Map.Entry<K,V>> implements Serializable
	{
		// javadoc for Map#entrySet() says that this does not support add methods
		private static final long serialVersionUID = 1l;
		private final Dictionary<K,V> dict;

		DictionaryMapViewEntrySet(Dictionary<K,V> dict)
		{
			if((this.dict=dict)==null)
				throw new NullPointerException();
		}

		// use AbstractSet's throwing versions
		// public boolean add(E)
		// public boolean addAll(Collection<? extends E>)

		// use AbstractSet's versions:
		// public void clear()

		@Override
		public boolean contains(Object o)
		{
			Map.Entry<?,?> other;
			Object otherVal;
			V val;

			if(o==null)
				return false;
			if(!(o instanceof Map.Entry))
				return false;
			other = (Map.Entry<?,?>)o;
			if((otherVal=other.getValue())==null)
				return false;	// no null in dictionary
			if((val = dict.get(other.getKey()))==null)
				return false;	// our val doesn't exist
			return val.equals(otherVal);	// compare known non-null values
		}

		@Override
		public boolean containsAll(Collection<?> c)
		{
			Map.Entry<?,?> entry;
			Object otherVal;
			V val;
						
			for(Object o : c)
			{
				if(!(o instanceof Map.Entry))
					return false;
				entry = (Map.Entry<?,?>)o;
				if((val=dict.get(entry.getKey()))==null)
					return false;
				if((otherVal=entry.getValue())==null)
					return false;
				if(!val.equals(otherVal))
					return false;
			}
			return false;
		}

		// use AbstractSet's versions:
		// public boolean equals(java.lang.Object)
		// public int hashCode()

		@Override
		public boolean isEmpty()
		{
			return dict.isEmpty();
		}

		@Override
		public Iterator<Map.Entry<K,V>> iterator()
		{
			final Enumeration<K> e = dict.keys();

			return new Iterator<Map.Entry<K,V>>()
			{
				private K lastKey = null;

				@Override
				public boolean hasNext()
				{
					return e.hasMoreElements();
				}

				@Override
				public Map.Entry<K,V> next()
				{
					lastKey = e.nextElement();
					return new DictionaryMapViewEntry<K,V>(dict,lastKey);
				}

				@Override
				public void remove()
				{
					if(lastKey==null)
						throw new IllegalStateException("Either next() has not been called or entry has already been removed.");
					dict.remove(lastKey);
				}

			};
		}

		@Override
		public boolean remove(Object o)
		{
			Map.Entry<?,?> other;
			Object otherKey;
			Object otherVal;
			V ourVal;

			if(o==null)
				return false;
			if(!(o instanceof Map.Entry<?,?>))
				return false;
			other = (Map.Entry<?,?>)o;
			if((otherKey=other.getKey())==null)
				return false;
			if((otherVal=other.getValue())==null)
				return false;
			if((ourVal = dict.get(otherKey))==null)
				return false;
			if(!ourVal.equals(otherVal))
				return false;
			// ok, everything is equal, remove it
			dict.remove(otherKey);
			return true;
		}

		// use AbstractSet's versions:
		// public boolean removeAll(java.util.Collection<?>)
		// public boolean retainAll(java.util.Collection<?>)

		@Override
		public int size()
		{
			return dict.size();
		}

		// use AbstractSet's versions:
		// public Object[] toArray()
		// public <T> T[] toArray(T[])
	}

	private static class DictionaryMapView<K,V> extends AbstractMap<K,V> implements Serializable
	{
		private static final long serialVersionUID = 1l;
		protected final Dictionary<K,V> dict;

		protected DictionaryMapView(Dictionary<K,V> dict)
		{
			if((this.dict=dict)==null)
				throw new NullPointerException();
		}

		@Override
		public void clear()
		{
			for(Enumeration<K> e=dict.keys();e.hasMoreElements();)
				dict.remove(e.nextElement());
		}

		@Override
		public boolean containsKey(Object key)
		{
			return dict.get(key)!=null;
		}

		@Override
		public boolean containsValue(Object val)
		{
			V ourVal;

			// Dictionaries can't contain null values
			if(val==null)
				return false;
			for(Enumeration<V> e=dict.elements(); e.hasMoreElements();)
			{	// ourVal should never be null but play it safe...
				if((ourVal=e.nextElement())!=null && ourVal.equals(val))
					return true;
			}
			return false;
		}

		@Override
		public Set<Map.Entry<K, V>> entrySet()
		{	// FIXME: add read-write support
			return new DictionaryMapViewEntrySet<K,V>(dict);
		}

		// use AbstractMaps's version:
		// public boolean equals(Object)

		@Override
		public V get(Object key)
		{
			return dict.get(key);
		}

		// use AbstractMaps's version:
		// public int hashCode()
	
		@Override
		public boolean isEmpty()
		{
			return dict.isEmpty();
		}

		@Override
		public Set<K> keySet()
		{
			return new DictionaryMapViewKeySet<K>(dict);
		}

		@Override
		public V put(K key, V val)
		{
			if(key==null||val==null)
				throw new NullPointerException();
			return dict.put(key,val);
		}

		// use AbstractMaps's version:
		// public void putAll(Map<? extends K, ? extends V> m)

		@Override
		public V remove(Object key)
		{
			if(key==null)
				throw new NullPointerException();
			return dict.remove(key);
		}
	
		@Override
		public int size()
		{
			return dict.size();
		}
	
		// use AbstractMaps's version:
		// public Collection<V> values()
	}


	public static <K,V> Map<K,V> asUnmodifiableMap(Dictionary<K,V> dict)
	{
		return Collections.unmodifiableMap(asMap(dict));
	}

	public static <K,V> Map<K,V> asMap(Dictionary<K,V> dict)
	{
		return new DictionaryMapView<K, V>(dict);
	}
}
