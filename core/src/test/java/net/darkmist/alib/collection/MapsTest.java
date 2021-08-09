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

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

public class MapsTest extends TestCase
{
	private static final Class<MapsTest> CLASS = MapsTest.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private static <K,V> void assertPostBuildPutFails(Maps.Builder<K,V> builder, K key, V val)
	{
		try
		{
			builder.put(key,val);
			fail("Able to use builder after build.");
		}
		catch(Exception expected)
		{
		}
	}

	private static <K,V> void assertModifiable(Map<K,V> map, K key, V val)
	{
		try
		{
			map.put(key,val);
		}
		catch(Exception e)
		{
			fail("Unable to put entry into map.");
		}
	}

	private static <K,V> void assertUnmodifiable(Map<K,V> map, K key, V val)
	{
		try
		{
			map.put(key,val);
			fail("Able to put entry into unmodifiable map.");
		}
		catch(Exception e)
		{
		}
	}

	public void testBuildEmptyUnmodifiable() throws Exception
	{
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map;
		
		builder = Maps.Builder.instance(String.class,Integer.class);
		map = builder.unmodifiable();

		assertNotNull(map);
		assertEquals(0, map.size());
		assertUnmodifiable(map, "one",1);
		assertPostBuildPutFails(builder,"two",2);
	}

	public void testBuildInitialEmptyUnmodifiable() throws Exception
	{	// why would anyone ever do this?
		Map<String,Integer> orig = new TreeMap<String,Integer>();
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map;

		builder = Maps.Builder.instance(orig);
		map = builder.unmodifiable();
		assertNotNull(map);
		assertFalse(orig==map);
		assertEquals(0, map.size());
		assertUnmodifiable(map, "one",1);
		assertPostBuildPutFails(builder,"two",2);
	}

	public void testBuildEmpty() throws Exception
	{
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map = Maps.Builder.instance(String.class,Integer.class).build();

		builder = Maps.Builder.instance(String.class,Integer.class);
		map = builder.build();

		assertNotNull(map);
		assertEquals(0, map.size());
		assertModifiable(map, "one",1);
		assertPostBuildPutFails(builder,"two",2);
	}

	public void testBuildInitialEmpty() throws Exception
	{	// why would anyone ever do this?
		Map<String,Integer> orig = new TreeMap<String,Integer>();
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map;

 		builder = Maps.Builder.instance(orig);
		map = builder.build();
		assertNotNull(map);
		assertTrue(orig==map);
		assertEquals(0, map.size());
		assertModifiable(map, "one",1);
		assertPostBuildPutFails(builder,"two",2);
	}

	public void testBuildSingletonUnmodifiable() throws Exception
	{
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map;
		
		builder = Maps.Builder.instance(String.class,Integer.class);
		builder.put("zero",0);
		map = builder.unmodifiable();

		assertNotNull(map);
		assertEquals(1, map.size());
		assertEquals(Integer.valueOf(0), map.get("zero"));
		assertUnmodifiable(map, "one",1);
		assertPostBuildPutFails(builder,"two",2);
	}

	public void testBuildInitialSingletonUnmodifiable() throws Exception
	{	// why would anyone ever do this?
		Map<String,Integer> orig = new TreeMap<String,Integer>();
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map;

		builder = Maps.Builder.instance(orig);
		builder.put("zero",0);
		map = builder.unmodifiable();
		assertNotNull(map);
		assertFalse(orig==map);
		assertEquals(1, map.size());
		assertEquals(Integer.valueOf(0), map.get("zero"));
		assertUnmodifiable(map, "one",1);
		assertPostBuildPutFails(builder,"two",2);
	}

	public void testBuildSingleton() throws Exception
	{
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map = Maps.Builder.instance(String.class,Integer.class).build();

		builder = Maps.Builder.instance(String.class,Integer.class);
		builder.put("zero",0);
		map = builder.build();

		assertNotNull(map);
		assertEquals(1, map.size());
		assertEquals(Integer.valueOf(0), map.get("zero"));
		assertModifiable(map, "one",1);
		assertPostBuildPutFails(builder,"two",2);
	}

	public void testBuildInitialSingleton() throws Exception
	{	// why would anyone ever do this?
		Map<String,Integer> orig = new TreeMap<String,Integer>();
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map;

 		builder = Maps.Builder.instance(orig);
		builder.put("zero",0);
		map = builder.build();
		assertNotNull(map);
		assertTrue(orig==map);
		assertEquals(1, map.size());
		assertEquals(Integer.valueOf(0), map.get("zero"));
		assertModifiable(map, "one",1);
		assertPostBuildPutFails(builder,"two",2);
	}

	public void testBuildMultipleUnmodifiable() throws Exception
	{
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map;
		
		builder = Maps.Builder.instance(String.class,Integer.class);
		builder.put("zero",0);
		builder.put("one",1);
		map = builder.unmodifiable();

		assertNotNull(map);
		assertEquals(2, map.size());
		assertEquals(Integer.valueOf(0), map.get("zero"));
		assertEquals(Integer.valueOf(1), map.get("one"));
		assertUnmodifiable(map, "two",2);
		assertPostBuildPutFails(builder,"three",3);
	}

	public void testBuildInitialMultipleUnmodifiable() throws Exception
	{	// why would anyone ever do this?
		Map<String,Integer> orig = new TreeMap<String,Integer>();
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map;

		builder = Maps.Builder.instance(orig);
		builder.put("zero",0);
		builder.put("one",1);
		map = builder.unmodifiable();
		assertNotNull(map);
		assertFalse(orig==map);
		assertEquals(2, map.size());
		assertEquals(Integer.valueOf(0), map.get("zero"));
		assertEquals(Integer.valueOf(1), map.get("one"));
		assertUnmodifiable(map, "two",2);
		assertPostBuildPutFails(builder,"three",3);
	}

	public void testBuildMultiple() throws Exception
	{
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map = Maps.Builder.instance(String.class,Integer.class).build();

		builder = Maps.Builder.instance(String.class,Integer.class);
		builder.put("zero",0);
		builder.put("one",1);
		map = builder.build();

		assertNotNull(map);
		assertEquals(2, map.size());
		assertEquals(Integer.valueOf(0), map.get("zero"));
		assertEquals(Integer.valueOf(1), map.get("one"));
		assertModifiable(map, "two",2);
		assertPostBuildPutFails(builder,"three",3);
	}

	public void testBuildInitialMultiple() throws Exception
	{
		Map<String,Integer> orig = new TreeMap<String,Integer>();
		Maps.Builder<String,Integer> builder;
		Map<String,Integer> map;

 		builder = Maps.Builder.instance(orig);
		builder.put("zero",0);
		builder.put("one",1);
		map = builder.build();
		assertNotNull(map);
		assertTrue(orig==map);
		assertEquals(2, map.size());
		assertEquals(Integer.valueOf(0), map.get("zero"));
		assertEquals(Integer.valueOf(1), map.get("one"));
		assertModifiable(map, "two",2);
		assertPostBuildPutFails(builder,"three",3);
	}

	public void testBuildThrowOnDuplicateDefault() throws Exception
	{
		Maps.Builder<String,Integer> builder;

 		builder = Maps.Builder.instance(String.class,Integer.class);
		builder.put("zero",0);
		try
		{
			builder.put("zero",0);
			fail("No excpetion thrown when duplicate put attempted.");
		}
		catch(Exception expected)
		{
		}
	}

	public void testBuildThrowOnDuplicateTrue() throws Exception
	{
		Maps.Builder<String,Integer> builder;

 		builder = Maps.Builder.instance(String.class,Integer.class).throwOnDuplicate();
		builder.put("zero",0);
		try
		{
			builder.put("zero",0);
			fail("No excpetion thrown when duplicate put attempted.");
		}
		catch(Exception expected)
		{
		}
	}

	public void testBuildThrowOnDuplicateFalse() throws Exception
	{
		Maps.Builder<String,Integer> builder;

 		builder = Maps.Builder.instance(String.class,Integer.class).noThrowOnDuplicate();
		builder.put("zero",0);
		try
		{
			builder.put("zero",0);
		}
		catch(Exception expected)
		{
			fail("Exception thrown when duplicate put attempted with noThrowOnDuplicate set.");
		}
	}
}
