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

package net.darkmist.alib.beans;

import java.lang.annotation.Annotation;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.darkmist.alib.reflect.Reflections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Beans
{
	private static final Class<Beans> CLASS = Beans.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/*
	private static class BeanProp
	{
		private final String name;
		private Boolean isReadable;
		private Boolean isWritable;
		private final WeakReference<Class<?>> beanClsRef;
		private WeakReference<PropertyDescriptor> propDescRef;
		private WeakReference<Class<?>> typeRef;
		private WeakReference<Method> setterRef;
		private WeakReference<Method> getterRef;

		private BeanProp(Class<?> beanCls, String name)
		{
			this.name = name;
			this.beanCls = new WeakReference<Class<?>>(beanCls);
		}

		public Class<?> getBeanClass()
		{
			Class<?> cls;

			if((cls=beanClsRef.get())==null)
				throw new IllegalStateException("Properties class no longer exists.");
			return cls;
		}

		private PropertyDescriptor getPropertyDescriptor()
		{
			PropertyDescriptor propDesc;

			if(propDescRef==null || (propDesc=propDescRef.get())==null)
			{
				propDesc = getPropertyDescriptor(getBeanClass(), name);
				propDescRef = new WeakReference<PropertyDescriptor>(propDesc);
			}
			return propDesc;
		}

		public Class<?> getType()
		{
			Class<?> typeRef
			HERE
		}
	}
	*/

	private Beans()
	{
	}

	private static PropertyDescriptor getPropertyDescriptor(Class<?> cls, String name) throws IntrospectionException
	{
		PropertyDescriptor[] descriptors;

		if((descriptors = Introspector.getBeanInfo(cls).getPropertyDescriptors())==null)
			throw new IllegalArgumentException("Can't get property descriptors for class " + cls.getName() + '.');
		for(PropertyDescriptor pd : descriptors)
			if(name.equals(pd.getName()))
				return pd;
		throw new IllegalArgumentException("Can't find property descriptor for class " + cls.getName() + " and property " + name + '.');
	}

	/* FIXME: write test code and debug this so we can have interfaces as property tags
	private static boolean isMethOverride(Method parent, Method child)
	{
		int parentMod, childMod;

		// std obj stuff
		if(parent==child)
			return false;
		if(parent==null)
			return false;
		if(child==null)
			return false;

		// java docs aren't clear on whether this equals is just == or more than that
		if(parent.equals(child))
			return true;

		// make sure there is an inheritance relationship
		if(parent.getDeclaringClass().isAssignable(child.getDeclaringClass()))
			return false;

		// check everything else by hand...
		// annotations can be different from super to sub (eg: @Override)
		// exception types cannot differ between super and sub
		if(!Arrays.equals(parent.getExceptionTypes(),child.getExceptionTypes()))
			return false;
		// modifiers...
		parentMod = parent.getModifiers();
		if(Modifiers.isStatic(parentMod))
			return false;
		if(Modifiers.isPrivate(parentMod))
			return false;
		bMod = b.getModifiers();
		if(Modifiers.isStatic(bMod))
			return false;
		if(Modifiers.isPrivate(bMod))
			return false;
		if(aMod != bMod)
		{	// Can be different but optimize if the same.
			if(Modifiers.isProtected(aMod))
			{
				if(!Modifiers.isProtected(bMod))
					return false;
			}
			else if(Modifiers.isPublic(aMod))
			{
				if(!Modifiers.isPublic(bMod))
					return false;
			}
			else	// only overrridable left is package private
			{
				if(Modifiers.isPublic(bMod) || Modifiers.isProtected(bMod))
					return false;
			}
		}
		if(!a.getName().equals(b.getName()))
			return false;
		if(!Arrays.equals(a.getParameterTypes(),b.getParameterTypes()))
			return false;
		if(!a.getReturnType().equals(b.getReturnType()))
			return false;
		return true;
	}

	private static boolean classHasOverridableMethod(Class<?> cls, Method meth)
	{
		Method clsMeth;
		Class methCls;
		int clsMethMods;

		methCls = meth.getDeclaringClass();
		// docs aren't clear on what the "declaring" class is in the case of overridden methods
		if(cls.equals(meth.getDeclaringClass()))
			return true;	// simple case
		// ok, so either meth isn't overriding something in cls OR getDeclaringClass is returning the overriding class. In the latter case, methCls had better be assignable to cls
		if(!cls.isAssignableTo(methCls))
			return false;
		try
		{	// time to look at cls's version of the method...
			return isMethodOverride(cls.getMethod(meth.getName(), meth.getParameterTypes()),meth);
		}
		catch(NoSuchMethodExcepton ignored)
		{	// no such method exists in cls so meth certainly can't be an override of it
			return false;
		}
	}
	*/

	private static void setPropertyAsText(Object bean, String name, String value, PropertyDescriptor pd, Class<?> propCls, Class<? extends Annotation> tagCls) throws IntrospectionException, IllegalAccessException, InvocationTargetException, InstantiationException

	{
		PropertyEditor propEdit;
		Object propVal;
		Method meth;

		// can we use a property editor?
		// OK, for some stupid reason, Integer.class isn't registered but Integer.TYPE is
		logger.debug("finding PropertyEditor for {}", propCls);
		if((propEdit = PropertyEditorManager.findEditor(Reflections.primitiveIfWrapper(propCls)))==null)
			throw new IllegalArgumentException("No property editor for type " + propCls.getName() + '.');

		propEdit.setValue(null);
		propEdit.setAsText(value);
		propVal = propEdit.getValue();

		if((meth=pd.getWriteMethod())==null)
			throw new IllegalArgumentException("Property " + name + " of class " + bean.getClass().getName() + " cannot be written.");
		if(tagCls!=null&&!meth.isAnnotationPresent(tagCls))
			throw new IllegalArgumentException("Write method " + meth + " for property " + name + " of class " + bean.getClass() + " does not have annotation " + tagCls + '.');
		meth.invoke(bean, propVal);
	}

	public static void setPropertyAsText(Object bean, String name, String value, Class<? extends Annotation> tagCls) throws IntrospectionException, IllegalAccessException, InvocationTargetException, InstantiationException

	{
		PropertyDescriptor pd;
		Class<?> beanCls;
		Class<?> propCls;

		beanCls = bean.getClass();
		pd = getPropertyDescriptor(beanCls, name);
		if((propCls=pd.getPropertyType())==null)
			throw new IllegalArgumentException("Property " + name + " of class " + beanCls.getName() + " does not have a type. Is it an indexed property?");
		propCls = Reflections.wrapperIfPrimitive(propCls);
		setPropertyAsText(bean, name, value, pd, propCls, tagCls);
	}

	public static void setPropertyAsText(Object bean, String name, String value) throws IntrospectionException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		setPropertyAsText(bean, name, value, null);
	}

	public static void setProperty(Object bean, String name, Object value, Class<? extends Annotation> tagCls) throws IntrospectionException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		PropertyDescriptor pd;
		Method meth;
		Class<?> beanCls;
		Class<?> propCls;
		Class<?> valCls;

		beanCls = bean.getClass();
		pd = getPropertyDescriptor(beanCls, name);
		if((propCls=pd.getPropertyType())==null)
			throw new IllegalArgumentException("Property " + name + " of class " + beanCls.getName() + " does not have a type. Is it an indexed property?");
		propCls = Reflections.wrapperIfPrimitive(propCls);
		if(value==null)
			valCls = propCls;	// hackish...
		else
			valCls = value.getClass();
		if(!propCls.isAssignableFrom(valCls))
		{
			if(String.class.equals(valCls))
			{
				setPropertyAsText(bean, name, (String)value, pd, propCls, tagCls);
				return;
			}
			throw new IllegalArgumentException("Property " + name + " of class " + beanCls.getName() + " is of type " + propCls.getName() + " which is not assignable from " + valCls.getName() + '.');
		}
		if((meth=pd.getWriteMethod())==null)
			throw new IllegalArgumentException("Property " + name + " of class " + beanCls.getName() + " cannot be written.");
		if(tagCls!=null&&!meth.isAnnotationPresent(tagCls))
			throw new IllegalArgumentException("Write method " + meth + " for property " + name + " of class " + bean.getClass() + " does not have annotation " + tagCls + '.');
		meth.invoke(bean, value);
	}

	public static void setProperty(Object bean, String name, Object value) throws IntrospectionException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		setProperty(bean, name, value, null);
	}

	private static String getPropertyAsText(Object bean, String name, PropertyDescriptor pd, Class<?> propCls, Class<? extends Annotation> tagCls) throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		PropertyEditor propEdit;
		Object o;
		String ret;
		Method meth;

		if((meth=pd.getReadMethod())==null)
			throw new IllegalArgumentException("Property " + name + " of class " + bean.getClass().getName() + " cannot be read.");
		if(tagCls!=null&&!meth.isAnnotationPresent(tagCls))
			throw new IllegalArgumentException("Read method " + meth + " for property " + name + " of class " + bean.getClass() + " does not have annotation " + tagCls + '.');
		o = meth.invoke(bean);
		if(o==null || o instanceof String)
			return (String)o;

		// can we use a property editor?
		if((propEdit = PropertyEditorManager.findEditor(propCls))!=null)
		{
			propEdit.setValue(o);
			if((ret=propEdit.getAsText())!=null)
				return ret;
		}

		// just toString it...
		return o.toString();	// FIXME: is this proper for a bean?
	}

	public static String getPropertyAsText(Object bean, String name, Class<? extends Annotation> tagCls) throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		PropertyDescriptor pd;
		Class<?> propCls;
		Class<?> beanCls;

		beanCls = bean.getClass();
		pd = getPropertyDescriptor(beanCls, name);
		if((propCls=pd.getPropertyType())==null)
			throw new IllegalArgumentException("Property " + name + " of class " + beanCls.getName() + " does not have a type. Is it an indexed property?");
		return getPropertyAsText(bean, name, pd, propCls, tagCls);
	}

	public static String getPropertyAsText(Object bean, String name) throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		return getPropertyAsText(bean, name, null);
	}

	public static <T> T getProperty(Object bean, String name, Class<T> valCls, Class<? extends Annotation> tagCls) throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		PropertyDescriptor pd;
		Method meth;
		Class<?> beanCls;
		Class<?> propCls;
		Object ret;

		beanCls = bean.getClass();
		pd = getPropertyDescriptor(beanCls, name);
		if((propCls=pd.getPropertyType())==null)
			throw new IllegalArgumentException("Property " + name + " of class " + beanCls.getName() + " does not have a type. Is it an indexed property?");
		propCls = Reflections.wrapperIfPrimitive(propCls);
		//valCls = Reflections.wrapperIfPrimitive(valCls);	// I know. Some folks are stupid though...
		if(!valCls.isAssignableFrom(propCls))
		{
			if(String.class.equals(valCls) || CharSequence.class.equals(valCls))
				return valCls.cast(getPropertyAsText(bean, name, pd, propCls, tagCls));
			throw new IllegalArgumentException("Property " + name + " of class " + beanCls.getName() + " is of type " + propCls.getName() + " which is not assignable to " + valCls.getName() + '.');
		}
		if((meth=pd.getReadMethod())==null)
			throw new IllegalArgumentException("Property " + name + " of class " + beanCls.getName() + " cannot be read.");
		if(tagCls!=null&&!meth.isAnnotationPresent(tagCls))
			throw new IllegalArgumentException("Read method " + meth + " for property " + name + " of class " + bean.getClass() + " does not have annotation " + tagCls + '.');
		ret = meth.invoke(bean);
		return valCls.cast(ret);
	}

	public static <T> T getProperty(Object bean, String name, Class<T> valCls) throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		return getProperty(bean,name,valCls, null);
	}

	public static Map<String,?> getProperties(Object bean, Class<? extends Annotation> tagCls) throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		Class<?> beanCls;
		PropertyDescriptor[] descriptors;
		Map<String,Object> map = new HashMap<String,Object>();
		Method meth;
		Object o;
		String name;

		beanCls = bean.getClass();
		if((descriptors = Introspector.getBeanInfo(beanCls).getPropertyDescriptors())==null)
			return map;	// nothing there...
		for(PropertyDescriptor pd : descriptors)
		{

			name = pd.getName();
			if((meth=pd.getReadMethod())==null)
			{
				logger.debug("Unable to get read method for property {} from class {}.", name, beanCls);
				continue;
			}
			if(tagCls!=null&&!meth.isAnnotationPresent(tagCls))
				continue;
			o = meth.invoke(bean);
			map.put(name,o);
		}
		return map;
	}

	public static Map<String,?> getProperties(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException
	{
		return getProperties(bean, null);
	}

	// FIXME: should this gaurantee no changes when tag issues exist?
	public static void setProperties(Object bean, Map<String,?> props, Class<? extends Annotation> tagCls) throws IntrospectionException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		Class<?> beanCls;
		PropertyDescriptor[] descriptors;
		Map<String,PropertyDescriptor> descriptorMap = new HashMap<String,PropertyDescriptor>();
		Method meth;
		String name;
		Object value;
		Class<?> propCls;
		Class<?> valCls;

		beanCls = bean.getClass();
		if((descriptors = Introspector.getBeanInfo(beanCls).getPropertyDescriptors())==null)
			throw new IllegalArgumentException("Unable to get PropertyDescriptors for class " + beanCls.getName() + '.');
		for(PropertyDescriptor pd : descriptors)
			descriptorMap.put(pd.getName(), pd);
		for(Map.Entry<String,?> prop : props.entrySet())
		{
			name = prop.getKey();
			value = prop.getValue();
			PropertyDescriptor pd;

			if((pd=descriptorMap.get(name))==null)
				throw new IllegalArgumentException("Bean class " + beanCls.getName() + " does not have property " + name + '.');
			if((meth=pd.getWriteMethod())==null)
				throw new IllegalArgumentException("Bean class " + beanCls.getName() + " does not have a setter for property " + name + '.');
			if((propCls = pd.getPropertyType())==null)
				throw new IllegalArgumentException("Bean class " + beanCls.getName() + " does not have a type for property " + name + '.');
			propCls = Reflections.wrapperIfPrimitive(propCls);
			if(value==null)
				valCls = propCls;
			valCls = value.getClass();
			if(!propCls.isAssignableFrom(valCls))
			{
				if(String.class.equals(valCls))
				{
					setPropertyAsText(bean, name, (String)value, pd, propCls, tagCls);
					continue;
				}
				throw new IllegalArgumentException("Property " + name + " of class " + beanCls.getName() + " is of type " + propCls.getName() + " which is not assignable from " + valCls.getName() + '.');
			}
			if(tagCls!=null&&!meth.isAnnotationPresent(tagCls))
				throw new IllegalArgumentException("Write method " + meth + " for property " + name + " of class " + bean.getClass() + " does not have annotation " + tagCls + '.');
			meth.invoke(bean, prop.getValue());
		}
	}

	public static void setProperties(Object bean, Map<String,?> props) throws IntrospectionException, IllegalAccessException, InvocationTargetException, InstantiationException
	{
		setProperties(bean, props, null);
	}

}
