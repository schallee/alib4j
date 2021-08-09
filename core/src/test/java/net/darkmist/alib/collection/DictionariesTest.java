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

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class DictionariesTest extends TestCase
{
	private static final Class<DictionariesTest> CLASS = DictionariesTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private static <K,V> void assertEquals(Dictionary<K,V> dict, Map<K,V> map)
	{
		for(Map.Entry<K,V> entry : map.entrySet())
			assertEquals(entry.getValue(), dict.get(entry.getKey()));
		for(Enumeration<K> e = dict.keys(); e.hasMoreElements();)
			assertTrue(map.containsKey(e.nextElement()));
	}

	private static <K,V> void assertEquals(Map<K,V> map, Dictionary<K,V> dict)
	{
		assertEquals(dict,map);
	}

	private static <T,A extends T> void setTestUnmodifiableAdd(Set<T> set, A[] toAdd)
	{
		for(A addition : toAdd)
			try
			{
				set.add(addition);
				fail("Add to unmodifiable set succeeded.");
			}
			catch(UnsupportedOperationException e)
			{
			}
	}

	private static <T> void setTestUnmodifiableAdd(Set<T> set, Collection<? extends T> toAdd)
	{
		for(T addition : toAdd)
			try
			{
				set.add(addition);
				fail("Add to unmodifiable set succeeded.");
			}
			catch(UnsupportedOperationException e)
			{
			}
	}

	private static <T> void setTestUnmodifiableAddAll(Set<T> set, Collection<? extends T> toAdd)
	{
		try
		{
			set.addAll(toAdd);
			fail("addAll on unmodifiable set succeeded.");
		}
		catch(UnsupportedOperationException e)
		{
		}
	}

	private static <T> void setTestUnmodifiableClear(Set<T> set)
	{
		try
		{
			set.clear();
			fail("clear on unmodifiable set succeeded.");
		}
		catch(UnsupportedOperationException e)
		{
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	private static <T, C> void setTestContains(Set<T> set, Collection<C> toCheck)
	{
		for(C item : toCheck)
			assertTrue("Test set did not contain expected item.", set.contains(item));
	}

	@SuppressWarnings("unlikely-arg-type")
	private static <T, C> void setTestDoesNotContain(Set<T> set, Collection<C> toCheck)
	{
		for(C item : toCheck)
			assertFalse("Test set contained unexpected item.", set.contains(item));
	}

	@SuppressWarnings("unlikely-arg-type")
	private static <T, C> void setTestContainsAll(Set<T> set, Collection<C> toCheck)
	{
		assertTrue("Test set did not contain all expected items.", set.containsAll(toCheck));
	}

	@SuppressWarnings("unlikely-arg-type")
	private static <T, C> void setTestDoesNotContainAll(Set<T> set, Collection<C> toCheck)
	{
		assertFalse("Test set contained unexpected item(s).", set.containsAll(toCheck));
	}

	private static Dictionary<Integer,String> mkTestDict()
	{
		Dictionary<Integer,String> dict = new Hashtable<Integer,String>();

		dict.put(1,"one");
		dict.put(2,"two");
		dict.put(3,"three");
		dict.put(4,"four");
		dict.put(5,"five");

		return dict;
	}

	public void testAsUnmodifiableMapManualsEquals() throws Exception
	{
		Dictionary<Integer,String> dict = mkTestDict();
		Map<Integer,String> map;

		map = Dictionaries.asUnmodifiableMap(dict);
		assertEquals(dict,map);
	}

	public void testAsUnmodifiableMapClear() throws Exception
	{
		Dictionary<Integer,String> dict = mkTestDict();
		Map<Integer,String> map;

		map = Dictionaries.asUnmodifiableMap(dict);
		try
		{
			map.clear();
			fail();
		}
		catch(UnsupportedOperationException e)
		{
		}
	}

	public void testAsUnmodifiableMapContainsKey() throws Exception
	{
		Dictionary<Integer,String> dict = mkTestDict();
		Map<Integer,String> map;

		map = Dictionaries.asUnmodifiableMap(dict);
		for(Enumeration<Integer> e = dict.keys(); e.hasMoreElements();)
			assertTrue(map.containsKey(e.nextElement()));
		assertFalse(map.containsKey(-1));
	}

	@SuppressWarnings("unlikely-arg-type")
	public void testAsUnmodifiableMapContainsValue() throws Exception
	{
		Dictionary<Integer,String> dict = mkTestDict();
		Map<Integer,String> map;

		map = Dictionaries.asUnmodifiableMap(dict);
		for(Enumeration<String> e = dict.elements(); e.hasMoreElements();)
			assertTrue(map.containsValue(e.nextElement()));
		assertFalse(map.containsKey("not there"));
	}

	public void testAsUnmodifiableMapEntrySetGetValueWithModify() throws Exception
	{
		Dictionary<Integer,String> dict = mkTestDict();
		Map<Integer,String> map;

		map = Dictionaries.asUnmodifiableMap(dict);
		for(Map.Entry<Integer,String> entry : map.entrySet())
		{
			Integer key = entry.getKey();
			String val = entry.getValue();
			assertEquals(val,dict.get(key));
			val += " modified";
			dict.put(key, val);
			assertEquals(entry.getValue(), val);
		}
	}

	public void testAsUnmodifiableMapEntrySetRemove() throws Exception
	{
		Dictionary<Integer,String> dict = mkTestDict();
		Map<Integer,String> map;

		map = Dictionaries.asUnmodifiableMap(dict);
		for(Iterator<Map.Entry<Integer,String>> i = map.entrySet().iterator(); i.hasNext();)
		{
			Map.Entry<Integer,String> entry = i.next();

			try
			{
				i.remove();
				fail();
			}
			catch(UnsupportedOperationException e)
			{
			}
		}
	}

	public void testAsUnmodifiableMapEntrySetRemoveNoNext() throws Exception
	{
		Dictionary<Integer,String> dict = mkTestDict();
		Map<Integer,String> map;

		map = Dictionaries.asUnmodifiableMap(dict);
		Iterator<Map.Entry<Integer,String>> i = map.entrySet().iterator();
		try
		{
			i.remove();
			fail();
		}
		catch(UnsupportedOperationException e)
		{
		}
	}

	public void testAsUnmodifiableMapEntrySetSetValue() throws Exception
	{
		Dictionary<Integer,String> dict = mkTestDict();
		Map<Integer,String> map;

		map = Dictionaries.asUnmodifiableMap(dict);
		for(Map.Entry<Integer,String> entry : map.entrySet())
		{
			Integer key = entry.getKey();
			String val = entry.getValue();
			assertEquals(val,dict.get(key));
			try
			{
				entry.setValue(val + "modified");
				fail();
			}
			catch(UnsupportedOperationException e)
			{
			}
			assertEquals(entry.getValue(), val);
		}
	}
	
	public static Test suite()
	{
		return new TestSuite(DictionariesTest.class);
	}

}
