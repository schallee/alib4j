/*
 *  Copyright (C) 2012 Ed Schaller <schallee@darkmist.net>
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

package net.darkmist.alib.res;

import java.io.InputStream;
import java.io.IOException;

import javax.annotation.Nullable;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.io.Closer;
import net.darkmist.alib.lang.NullSafe;

import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource acquisition using paths based on package names.
 */
public class PkgRes
{
	private static final Class<PkgRes> CLASS = PkgRes.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private ClassLoader loader;
	private String prefix;

	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public PkgRes(Class<?> cls)
	{
		if(cls == null)
			throw new NullPointerException("cls is null");
		this.loader = cls.getClassLoader();
		prefix = appendResourcePathPrefixFor(null,cls).toString();
	}

	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public PkgRes(Object obj)
	{
		Class<?> cls;

		if(obj == null)
			throw new NullPointerException("obj is null");
		cls = obj.getClass();
		loader = cls.getClassLoader();
		prefix = appendResourcePathPrefixFor(null,cls).toString();
	}

	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public PkgRes(String pkg, ClassLoader loader)
	{
		if(pkg == null)
			throw new NullPointerException("pkg is null");
		if(loader == null)
			throw new NullPointerException("loader is null");
		prefix = appendResourcePathPrefixFor(null,pkg).toString();
		this.loader = loader;
	}

	/**
	 * Retrieve the prefix used for resource paths.
	 * @return the resource path prefix used.
	 */
	public String getResourcePathPrefix()
	{
		return prefix;
	}

	/**
	 * Retrieve the ClassLoader used to locate resources.
	 */
	public ClassLoader getClassLoader()
	{
		return loader;
	}

	/**
	 * Get the package name for a class.
	 * @param cls The class to get the package name for.
	 * @return the class's pacakge name.
	 * @throws NullPointerException if cls is null.
	 */
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public static String getPackageName(Class<?> cls)
	{
		Package pkg;
		String str;
		int pos;

		if(cls == null)
			throw new NullPointerException("cls is null");
		if((pkg = cls.getPackage())!=null)
			return pkg.getName();
		str = cls.getName();
		if((pos = str.lastIndexOf('.'))>=0)
			return str.substring(0,pos);
		return "";	// default package
	}

	/**
	 * Get the package name for a object.
	 * @param obj The object to get the package name for.
	 * @return the object's class's pacakge name.
	 * @throws NullPointerException if obj is null.
	 */
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public static String getPackageName(Object obj)
	{
		if(obj == null)
			throw new NullPointerException("obj is null");
		return getPackageName(obj.getClass());
	}

	/**
	 * Apend a package name converted to a resource path prefix.
	 * @param sb what to append to. If this is null, a new
	 * 	StringBuilder is created.
	 * @param pkgName The name of the package.
	 * @return Path, starting and ending with a slash, for resources
	 * 	prefixed by pkgName.
	 * @throws NullPointerException if pkgName is null.
	 */
	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	protected static StringBuilder appendResourcePathPrefixFor(@Nullable StringBuilder sb, String pkgName)
	{
		if(pkgName == null)
			throw new NullPointerException("pkgName is null");
		if(sb == null)
			sb = new StringBuilder(pkgName.length() + 2);
		sb.append('/');
		if(pkgName.length() == 0)
			return sb;
		sb.append(pkgName.replace('.','/'));
		sb.append('/');
		return sb;
	}

	/**
	 * Apend a package name converted to a resource path prefix.
	 * @param sb what to append to. If this is null, a new
	 * 	StringBuilder is created.
	 * @param cls The class to get the package name from.
	 * @return Path, starting and ending with a slash, for resources
	 * 	prefixed by the package name.
	 * @throws NullPointerException if cls is null.
	 */
	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	protected static StringBuilder appendResourcePathPrefixFor(@Nullable StringBuilder sb, Class<?> cls)
	{
		if(cls == null)
			throw new NullPointerException("cls is null");
		return appendResourcePathPrefixFor(sb, getPackageName(cls));
	}

	/**
	 * Apend a package name converted to a resource path prefix.
	 * @param sb what to append to. If this is null, a new
	 * 	StringBuilder is created.
	 * @param obj The object to get the package name from.
	 * @return Path, starting and ending with a slash, for resources
	 * 	prefixed by the package name.
	 * @throws NullPointerException if obj is null.
	 */
	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	protected static StringBuilder appendResourcePathPrefixFor(@Nullable StringBuilder sb, Object obj)
	{
		if(obj == null)
			throw new NullPointerException("obj is null");
		return appendResourcePathPrefixFor(sb, getPackageName(obj));
	}

	/**
	 * Gets a resource path using cls's package name as the prefix.
	 * @param name The name of the resource.
	 * @param cls The class to get the package prefix from.
	 * @return Path of a resource prefixed by the class package name.
	 * @throws NullPointerException if name or cls are null.
	 */
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public static String getResourcePathFor(CharSequence name, Class<?> cls)
	{
		int nameLen;
		StringBuilder sb;

		if(name==null)
			throw new NullPointerException("name is null");
		if(cls==null)
			throw new NullPointerException("cls is null");
		nameLen = name.length();
		sb = new StringBuilder(cls.getName().length() + nameLen + 2);
		appendResourcePathPrefixFor(sb,cls);
		if(name.charAt(0)!='/')
			sb.append(name, 1, nameLen);
		else
			sb.append(name);
		return sb.toString();
	}

	/**
	 * Gets a resource path using obj's class's package name as
	 * the prefix.
	 * @param name The name of the resource.
	 * @param obj The object to use for the package name
	 * @return Path of a resource prefixed by obj's class package
	 * 	name.
	 * @throws NullPointerException if name or obj are null.
	 */
	public static String getResourcePathFor(CharSequence name, Object obj)
	{
		if(obj == null)
			throw new NullPointerException("obj named " + name + " is null");
		return getResourcePathFor(name,obj.getClass());
	}

	/**
	 * Gets a InputStream for a class's package.
	 * @param name The name of the resource.
	 * @param cls The class to use for the package name
	 * @return InputStream for the resource.
	 * @throws NullPointerException if name or cls are null.
	 * 	ResourceException if the resource cannot be found.
	 */
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public static InputStream getFor(String name, Class<?> cls)
	{
		InputStream ret;

		if(cls == null)
			throw new NullPointerException("cls is null");
		if((ret = cls.getResourceAsStream(getResourcePathFor(name,cls)))==null)
			throw new ResourceException("Unable to find resource for " + name + " and " + cls);
		return ret;
	}

	/**
	 * Gets a InputStream for a objects's package.
	 * @param name The name of the resource.
	 * @param obj The obj to use for the package name
	 * @return InputStream for the resource.
	 * @throws NullPointerException if name or obj are null.
	 * 	ResourceException if the resource cannot be found.
	 */
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public static InputStream getFor(String name, Object obj)
	{
		if(obj == null)
			throw new NullPointerException("obj is null");
		return getFor(name,obj.getClass());
	}

	/**
	 * Gets a InputStream for a named resource
	 * @param name The name of the resource.
	 * @return InputStream for the resource.
	 * @throws NullPointerException if name is null.
	 * 	ResourceException if the resource cannot be found.
	 */
	@Nullable
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public InputStream get(String name)
	{
		InputStream ret;

		if(name == null)
			throw new NullPointerException("name is null");
		if((ret = loader.getResourceAsStream(prefix + name))==null)
			throw new ResourceException("Unable to find resource for " + name);
		return ret;
	}

	/**
	 * Get a resource as a String.
	 * @param name The name of the resource
	 * @param cls the class to use for the package name
	 * @return The contents of the resource converted to a string
	 * 	with the default encoding.
	 * @throws NullPointerException if name or cls are null.
	 * 	ResourceException if the resource cannot be found.
	 */
	@SuppressFBWarnings(value={"OPM_OVERLY_PERMISSIVE_METHOD","EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS"}, justification="API method, Missing resource is usually fatal")
	public static String getStringFor(String name, Class<?> cls)
	{
		InputStream in = null;

		try
		{
			in = getFor(name, cls);
			return IOUtils.toString(in);
		}
		catch(IOException e)
		{
			throw new ResourceException("IOException reading resource " + name, e);
		}
		finally
		{
			Closer.close(in,logger,"resource InputStream for resource " + name);
		}
	}

	/**
	 * Get a resource as a String.
	 * @param name The name of the resource
	 * @return The contents of the resource converted to a string
	 * 	with the default encoding.
	 * @throws NullPointerException if name or obj are null.
	 * 	ResourceException if the resource cannot be found.
	 */
	public static String getStringFor(String name, Object obj)
	{
		if(obj == null)
			throw new NullPointerException("obj named " + name + " is null");
		return getStringFor(name,obj.getClass());
	}

	/**
	 * Gets a resource as a String
	 * @param name The name of the resource.
	 * @return The contents of the resource converted to a string
	 * 	with the default encoding.
	 * @throws NullPointerException if name is null.
	 *	ResourceException if the resource cannot be found or
	 * 	there is a error reading it.
	 */
	@SuppressFBWarnings(value={"WEM_WEAK_EXCEPTION_MESSAGING","EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS"},justification="Boolean state, Missing resource is usually fatal")
	public String getString(String name)
	{
		InputStream in = null;

		if(name == null)
			throw new NullPointerException("name is null");
		try
		{
			if((in = loader.getResourceAsStream(prefix + name))==null)
				throw new ResourceException("Unable to find resource for " + name);
			return IOUtils.toString(in);
		}
		catch(IOException e)
		{
			throw new ResourceException("IOException reading resource " + name, e);
		}
		finally
		{
			Closer.close(in,logger,"resource InputStream for " + name);
		}
	}

	/**
	 * Get a resource as a byte array.
	 * @param name The name of the resource
	 * @param cls the class to use for the package name
	 * @return The contents of the resource as a byte array.
	 * @throws NullPointerException if name or cls are null.
	 * 	ResourceException if the resource cannot be found.
	 */
	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS",justification="Missing resource is usually fatal")
	public static byte[] getBytesFor(String name, Class<?> cls)
	{
		InputStream in = null;

		try
		{
			in = getFor(name, cls);
			return IOUtils.toByteArray(in);
		}
		catch(IOException e)
		{
			throw new ResourceException("IOException reading resource " + name + " for class " + cls + '.', e);
		}
		finally
		{
			Closer.close(in,logger,"resource InputStream");
		}
	}

	/**
	 * Get a resource as a byte array.
	 * @param name The name of the resource
	 * @return The contents of the resource as a byte array.
	 * @throws NullPointerException if name or obj are null.
	 * 	ResourceException if the resource cannot be found.
	 */
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING",justification="Boolean state")
	public static byte[] getBytesFor(String name, Object obj)
	{
		if(obj == null)
			throw new NullPointerException("obj is null");
		return getBytesFor(name,obj.getClass());
	}

	/**
	 * Gets a resource as a byte array.
	 * @param name The name of the resource.
	 * @return The contents of the resource as a byte array.
	 * @throws NullPointerException if name is null.
	 *	ResourceException if the resource cannot be found or
	 * 	there is a error reading it.
	 */
	@SuppressFBWarnings(value={"WEM_WEAK_EXCEPTION_MESSAGING","EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS"},justification="Boolean state")
	public byte[] getBytes(String name)
	{
		InputStream in = null;

		if(name == null)
			throw new NullPointerException("name is null");
		try
		{
			if((in = loader.getResourceAsStream(prefix + name))==null)
				throw new ResourceException("Unable to find resource for " + name);
			return IOUtils.toByteArray(in);
		}
		catch(IOException e)
		{
			throw new ResourceException("IOException reading resource " + name, e);
		}
		finally
		{
			Closer.close(in,logger,"resource InputStream for " + name);
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof PkgRes))
			return false;
		PkgRes that = (PkgRes)o;
		if(!NullSafe.equals(this.loader, that.loader))
			return false;
		return NullSafe.equals(this.prefix, that.prefix);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(loader,prefix);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": prefix=" + prefix + " loader=" + loader;
	}
}
