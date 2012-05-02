package net.darkmist.alib.res;

import java.io.InputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import net.darkmist.alib.io.Closer;

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

	public PkgRes(Class<?> cls)
	{
		if(cls == null)
			throw new NullPointerException("cls is null");
		this.loader = cls.getClassLoader();
		prefix = appendResourcePathPrefixFor(null,cls).toString();
	}

	public PkgRes(Object obj)
	{
		Class<?> cls;

		if(obj == null)
			throw new NullPointerException("obj is null");
		cls = obj.getClass();
		loader = cls.getClassLoader();
		prefix = appendResourcePathPrefixFor(null,cls).toString();
	}

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
	 * @returns the resource path prefix used.
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
	 * @returns the class's pacakge name.
	 * @throws NullPointerException if cls is null.
	 */
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
	 * @returns the object's class's pacakge name.
	 * @throws NullPointerException if obj is null.
	 */
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
	protected static StringBuilder appendResourcePathPrefixFor(StringBuilder sb, String pkgName)
	{
		if(pkgName == null)
			throw new NullPointerException("pkgName is null");
		if(sb == null)
			sb = new StringBuilder(pkgName.length() + 2);
		sb.append('/');
		if(pkgName.length() == 0)
			return sb;
		sb.append(pkgName.replace('.','/'));
		pkgName = null;
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
	protected static StringBuilder appendResourcePathPrefixFor(StringBuilder sb, Class<?> cls)
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
	protected static StringBuilder appendResourcePathPrefixFor(StringBuilder sb, Object obj)
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
		cls = null;
		if(name.charAt(0)!='/')
			sb.append(name, 1, nameLen);
		else
			sb.append(name);
		name = null;
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
			throw new NullPointerException("obj is null");
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
	public static String getStringFor(String name, Class<?> cls)
	{
		InputStream in = null;

		try
		{
			if((in = getFor(name, cls))==null)
				throw new ResourceException("Unablet to find package resource for " + name + " and " + cls);
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
	 * @param cls the class to use for the package name
	 * @return The contents of the resource converted to a string
	 * 	with the default encoding.
	 * @throws NullPointerException if name or obj are null.
	 * 	ResourceException if the resource cannot be found.
	 */
	public static String getStringFor(String name, Object obj)
	{
		if(obj == null)
			throw new NullPointerException("obj is null");
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
	public static byte[] getBytesFor(String name, Class<?> cls)
	{
		InputStream in = null;

		try
		{
			if((in = getFor(name, cls))==null)
				throw new ResourceException("Unablet to find package resource for " + name + " and " + cls);
			return IOUtils.toByteArray(in);
		}
		catch(IOException e)
		{
			throw new ResourceException("IOException reading resource");
		}
		finally
		{
			Closer.close(in,logger,"resource InputStream");
		}
	}

	/**
	 * Get a resource as a byte array.
	 * @param name The name of the resource
	 * @param cls the class to use for the package name
	 * @return The contents of the resource as a byte array.
	 * @throws NullPointerException if name or obj are null.
	 * 	ResourceException if the resource cannot be found.
	 */
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
}
