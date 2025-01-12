/*
 *  Copyright (C) 2014 Ed Schaller <schallee@darkmist.net>
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

package net.darkmist.alib.reflect;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class Reflections
{
	private static final Map<Class<?>,Class<?>> PRIMITIVE_TO_WRAPPER = mkPrimitiveToWrapper();
	private static final Map<Class<?>,Class<?>> WRAPPER_TO_PRIMITIVE = mkWrapperToPrimitive();
	private static final Map<Class<?>,Object> PRIMITIVE_ZERO = mkPrimitiveZero();

	private Reflections()
	{
	}

	private static Map<Class<?>,Class<?>> mkPrimitiveToWrapper()
	{
		Map<Class<?>,Class<?>> map = new HashMap<Class<?>,Class<?>>();

		map.put(Byte.TYPE, Byte.class);
		map.put(Integer.TYPE, Integer.class);
		map.put(Short.TYPE, Short.class);
		map.put(Long.TYPE, Long.class);
		map.put(Float.TYPE, Float.class);
		map.put(Double.TYPE, Double.class);
		map.put(Character.TYPE, Character.class);
		map.put(Boolean.TYPE, Boolean.class);
		return Collections.unmodifiableMap(map);
	}

	private static Map<Class<?>,Class<?>> mkWrapperToPrimitive()
	{
		Map<Class<?>,Class<?>> map = new HashMap<Class<?>,Class<?>>();

		map.put(Byte.class, Byte.TYPE);
		map.put(Integer.class, Integer.TYPE);
		map.put(Short.class, Short.TYPE);
		map.put(Long.class, Long.TYPE);
		map.put(Float.class, Float.TYPE);
		map.put(Double.class, Double.TYPE);
		map.put(Character.class, Character.TYPE);
		map.put(Boolean.class, Boolean.TYPE);
		return Collections.unmodifiableMap(map);
	}

	private static Map<Class<?>,Object> mkPrimitiveZero()
	{
		Map<Class<?>,Object> map = new HashMap<Class<?>,Object>();

		map.put(Boolean.TYPE, Boolean.FALSE);
		map.put(Character.TYPE, Character.valueOf((char)0));
		map.put(Byte.TYPE, Byte.valueOf((byte)0));
		map.put(Short.TYPE, Short.valueOf((short)0));
		map.put(Integer.TYPE, Integer.valueOf(0));
		map.put(Long.TYPE, Long.valueOf((long)0));
		map.put(Float.TYPE, Float.valueOf((float)0));
		map.put(Double.TYPE, Double.valueOf((double)0));

		return Collections.unmodifiableMap(map);
	}

	public static Class<?> wrapperIfPrimitive(Class<?> cls)
	{
		Class<?> wrapper;

		if((wrapper=PRIMITIVE_TO_WRAPPER.get(cls))==null)
			return cls;
		return wrapper;
	}

	public boolean isPrimitive(Class<?> cls)
	{
		return PRIMITIVE_TO_WRAPPER.get(cls)!=null;
	}

	public static Class<?> primitiveIfWrapper(Class<?> cls)
	{
		Class<?> ret;

		if((ret=WRAPPER_TO_PRIMITIVE.get(cls))!=null)
			return ret;
		return cls;
	}

	/**
	 * @Deprecated Use {@link #isPrimitiveWrapper} instead.
	 */
	@Deprecated
	public boolean isWrapper(Class<?> cls)
	{
		return isPrimitiveWrapper(cls);
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public boolean isPrimitiveWrapper(Class<?> cls)
	{
		return WRAPPER_TO_PRIMITIVE.get(cls)!=null;
	}

	@Nullable
	public static Object zeroIfPrimitiveNullOtherwise(Class<?> cls)
	{
		Object ret;

		if((ret=PRIMITIVE_ZERO.get(cls))==null)
			return null;
		return ret;
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
	public static StackTraceElement getStackTraceElement(int index)
	{
		return new Throwable().fillInStackTrace().getStackTrace()[index];
	}

	public static String getMethodName()
	{
		return getStackTraceElement(2).getMethodName();
	}

	public static String getClassName()
	{
		return getStackTraceElement(2).getClassName();
	}

	public static String getFileName()
	{
		return getStackTraceElement(2).getFileName();
	}

	public static int getLineNumber()
	{
		return getStackTraceElement(2).getLineNumber();
	}
}
