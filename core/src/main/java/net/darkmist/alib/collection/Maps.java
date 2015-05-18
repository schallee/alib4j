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
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

/**
 * Static utilities for {@link Map}s.
 */
public final class Maps
{
	private Maps()
	{
	}

	private static abstract class AbstractDictioanryView<K,V> extends Dictionary<K,V> implements Serializable
	{
		private static final long serialVersionUID = 1l;

		protected final Map<K,V> map;

		AbstractDictioanryView(Map<K,V> map)
		{
			if((this.map=map)==null)
				throw new NullPointerException();
		}

		@Override
		public int size()
		{
			return map.size();
		}

		@Override
		public boolean isEmpty()
		{
			return map.isEmpty();
		}

		@Override
		public Enumeration<K> keys()
		{
			return Iterators.asEnumeration(map.keySet().iterator());
		}

		@Override
		public Enumeration<V> elements()
		{
			return Iterators.asEnumeration(map.values().iterator());
		}

		@Override
		public V get(Object key)
		{
			if(key==null)
				throw new NullPointerException();
			return map.get(key);
		}

		@Override
		public abstract V put(K key, V val);
	
		@Override
		public abstract V remove(Object key);

		// Method of computation not specified in java docs so we hope that the dictionary base class will do something useful
		// public int hashCode()
		// public String toString()

		@Override
		public boolean equals(Object o)
		{
			Dictionary<?,?> other;

			if(this==o)
				return true;
			if(o==null)
				return false;
			if(!(o instanceof Dictionary))
				return false;
			other = (Dictionary<?,?>)o;

			for(Map.Entry<K,V> entry : map.entrySet())
			{
				if((o=other.get(entry.getKey()))==null)
					return false;
				if(!o.equals(entry.getValue()))
					return false;
			}

			for(Enumeration<?> e=other.keys(); e.hasMoreElements();)
				if(!map.containsKey(e.nextElement()))
					return false;
			return true;
		}
	}

	public static <K,V> Dictionary<K,V> asUnmodifiableDictionary(Map<K,V> map)
	{
		return new AbstractDictioanryView<K,V>(map)
		{
			private static final long serialVersionUID = 1l;

			@Override
			public V put(K key, V val)
			{
				throw new UnsupportedOperationException();
			}
	
			@Override
			public V remove(Object key)
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	public static <K,V> Dictionary<K,V> asDictionary(Map<K,V> map)
	{
		return new AbstractDictioanryView<K,V>(map)
		{
			private static final long serialVersionUID = 1l;

			@Override
			public V put(K key, V val)
			{
				return map.put(key,val);
			}
		
			@Override
			public V remove(Object key)
			{
				return map.remove(key);
			}
		};
	}

}
