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

package net.darkmist.alib.jvm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JVMLauncher
{
	private static final Class<JVMLauncher> CLASS = JVMLauncher.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final boolean HAVE_EXTENSION_CLASS_LOADER = System.getProperty("java.ext.dirs")!=null;
	private static final String PATH_SEPARATOR = System.getProperty("path.separator", ":");
	private static File JAVA;

	private static Set<String> getPossibleExecutableExtensions()
	{
		String pathext;
			
		if((pathext = System.getenv("PATHEXT"))== null)
			return Collections.singleton("");
		return new LinkedHashSet(Arrays.asList(pathext.split(";")));
	}

	private static final File getJavaHome() throws LauncherException
	{
		String homePath;
		File home;

		if((homePath = System.getProperty("java.home"))==null)
			throw new LauncherException("Standard java property java.home does not exist.");
		home = new File(homePath);
		if(!home.exists())
			throw new LauncherException("Java home directory " + homePath + " does not exist.");
		if(!home.isDirectory())
			throw new LauncherException("Java home directory " + homePath + " exists but is not a directory.");
		return home;
	}

	private static final File getJavaHomeBin() throws LauncherException
	{
		File bin;

		bin = new File(getJavaHome(), "bin");
		if(!bin.exists())
			throw new LauncherException("Java home bin directory " + bin.getPath() + " does not exist.");
		if(!bin.isDirectory())
			throw new LauncherException("Java home bin directory " + bin.getPath() + " exists but is not a directory.");
		return bin;
	}

	public static final synchronized File getJavaFile() throws LauncherException
	{
		File bin;
		File java;

		if(JAVA != null)
			return JAVA;
		bin = getJavaHomeBin();
		for(String ext : getPossibleExecutableExtensions())
		{
			java = new File(bin, "java" + ext);
			if(java.isFile() && java.canExecute())
				return (JAVA = java);
		}
		throw new LauncherException("Unable to find java executable in " + bin.getPath());
	}

	public static String getJavaPath() throws LauncherException
	{
		return getJavaFile().getPath();
	}

	private static Set<URL> addClassPathURLsFor(Set<URL> set, ClassLoader clsLoader)
	{
		ClassLoader parent;

		if((parent = clsLoader.getParent())==null)
		{
			if(HAVE_EXTENSION_CLASS_LOADER)	// java <= 8
				return set;	// we are the extension class loader
		}
		else
			addClassPathURLsFor(set, parent);
		if(clsLoader instanceof URLClassLoader)
		{
			URL[] urls = ((URLClassLoader)clsLoader).getURLs();
			if(logger.isDebugEnabled())
			{
				logger.debug("clsLoader {} has urls:", clsLoader);
				for(int i=0;i<urls.length;i++)
					logger.debug("\t[{}] {}", i, urls[i]);
			}
			set.addAll(Arrays.asList(urls));
		}
		else
			logger.warn("ClassLoader {} is not URLClassLoader but {}", clsLoader, clsLoader.getClass());
		return set;
	}

	private static Set<URL> getClassPathURLsFor(Class<?> cls)
	{
		Set<URL> urls = addClassPathURLsFor(new LinkedHashSet<URL>(), cls.getClassLoader());
		logger.debug("Computed urls for {}: {}", cls, urls);
		return urls;
	}

	private static String mkPath(Collection<URL> urls)
	{
		StringBuilder sb = new StringBuilder();

		for(URL url : urls)
		{
			if("file".equalsIgnoreCase(url.getProtocol()))
			{	// For some idiotic reason winderz can't find it if this is a url...
				File file;
				String path;

				try
				{
					file = new File(url.toURI());
				}
				catch(URISyntaxException use)
				{
					logger.warn("Unable to convert {} to URI. Ignoring.", url, use);
					continue;
				}
				if(!file.exists())
				{
					logger.debug("File {} does not exist. Ignoring.", file);
					continue;
				}
				try
				{
					path = file.getCanonicalPath();
				}
				catch(IOException ioe)
				{
					logger.debug("File {} could not be cannonicalized. Using absolute instead.", file, ioe);
					path = file.getAbsolutePath();
				}
				sb.append(path);
			}
			else
				sb.append(url.toString());
			sb.append(PATH_SEPARATOR);
		}
		return sb.substring(0, sb.length() - PATH_SEPARATOR.length());
	}

	/**
	 * Get the system property java.class.path.
	 * @return The value of the system property java.class.path
	 * @deprecated Use {@link System#getProperty(String)} directly.
	 */
	@Deprecated
	public static String getClassPath() throws LauncherException
	{
		return System.getProperty("java.class.path");
	}

	/**
	 * Tries to find a class by name using the thread context class
	 * loader and this class's class loader.
	 * @param name Fully qualified name of the class to find
	 * @return The class found.
	 * @throws LauncherException if the class was not found.
	 */
	private static Class<?> findClass(String name) throws LauncherException
	{
		for(ClassLoader clsLoader : new ClassLoader[]{Thread.currentThread().getContextClassLoader(), CLASS.getClassLoader()})
			try
			{
				return clsLoader.loadClass(name);
			}
			catch(ClassNotFoundException ignored)
			{
				logger.debug("Ignoring ClassNotFound exception looking for class.", ignored);
			}
		throw new LauncherException("Unable to find class " + name + " via either the thread context class loader nor our own class loader.");
	}

	/**
	 * Get a process loader for a JVM. The class path for the JVM
	 * is computed based on the class loaders for the main class.
	 * @param mainClass Fully qualified class name of the main class
	 * @param args Additional command line parameters
	 * @return ProcessBuilder that has not been started.
	 * @deprecated This acquires the mainClass class by using the
	 * {@link Thread#getContextClassLoader() thread context class loader}
	 * or this class's class loader. Depending on where mainClass
	 * was loaded from neither of these may work.
	 * @see #getProcessBuilder(Class, String[])
	 */
	@Deprecated
	public static ProcessBuilder getProcessBuilder(String mainClass, String...args) throws LauncherException
	{
		return getProcessBuilder(findClass(mainClass), Arrays.asList(args));
	}

	/**
	 * Get a process loader for a JVM. The class path for the JVM
	 * is computed based on the class loaders for the main class.
	 * @param mainClass Main class to run
	 * @param args Additional command line parameters
	 * @return ProcessBuilder that has not been started.
	 */
	public static ProcessBuilder getProcessBuilder(Class<?> mainClass, String...args) throws LauncherException
	{
		return getProcessBuilder(mainClass, Arrays.asList(args));
	}

	/**
	 * Get a process loader for a JVM. The class path for the JVM
	 * is computed based on the class loaders for the main class.
	 * @param mainClass Main class to run
	 * @param args Additional command line parameters
	 * @return ProcessBuilder that has not been started.
	 */
	public static ProcessBuilder getProcessBuilder(Class<?> mainClass, List<String> args) throws LauncherException
	{
		URL mainUrl;
		String mainName = mainClass.getName();
		String mainPath = mainName.replace('.','/') + ".class";
		Set<URL> classPath = getClassPathURLsFor(mainClass);

		if((mainUrl = mainClass.getClassLoader().getResource(mainPath))!=null)
		{	// So, at least when run from surefire, the actual location of the class is not in the provided URLs.
			String urlPath = mainUrl.getPath();
			String proto = mainUrl.getProtocol();

			if(logger.isDebugEnabled())
			{
				logger.debug("mainClass={} => {}", mainClass, mainPath);
				logger.debug("\tclassLoader={}", mainClass.getClassLoader());
				logger.debug("\tproto={} path={}", proto, urlPath);
			}
			if(urlPath.endsWith(mainPath))
			{
				String mainClsPath = urlPath.substring(0, urlPath.length() - mainPath.length());
				try
				{
					mainUrl = new URL(proto, mainUrl.getHost(), mainUrl.getPort(), mainClsPath);
					logger.debug("\tadding {} to class path", mainUrl);
					classPath.add(mainUrl);
				}
				catch(MalformedURLException mue)
				{	// Log but keep going
					logger.warn("Failed to create url for class path from url for class", mue);
				}
			}
		}

		return getProcessBuilder(mainName, classPath, args);
	}

	/**
	 * Get a process loader for a JVM.
	 * @param mainClass Main class to run
	 * @param classPath List of urls to use for the new JVM's
	 * 	class path.
	 * @param args Additional command line parameters
	 * @return ProcessBuilder that has not been started.
	 */
	public static ProcessBuilder getProcessBuilder(String mainClass, Set<URL> classPath, String...args) throws LauncherException
	{
		return getProcessBuilder(mainClass, classPath, Arrays.asList(args));
	}

	/**
	 * Get a process loader for a JVM.
	 * @param mainClass Main class to run
	 * @param classPath Set of urls to use for the new JVM's
	 * 	class path.
	 * @param args Additional command line parameters
	 * @return ProcessBuilder that has not been started.
	 */
	public static ProcessBuilder getProcessBuilder(String mainClass, Set<URL> classPath, List<String> args) throws LauncherException
	{
		List<String> cmdList = new ArrayList<String>();
		String[] cmdArray;
		
		cmdList.add(getJavaPath());
		if(classPath != null && classPath.size() > 0)
		{
			cmdList.add("-cp");
			if(logger.isDebugEnabled())
			{
				int i=0;

				logger.debug("classPath:");
				for(URL url : classPath)
					logger.debug("\t[{}]: {}", i++, url);
			}
			cmdList.add(mkPath(classPath));
		}
		cmdList.add(mainClass);
		if(args!=null && args.size()>0)
			cmdList.addAll(args);
		cmdArray = cmdList.toArray(new String[cmdList.size()]);
		if(logger.isDebugEnabled())
		{
			logger.debug("cmdArray:");
			for(int i=0;i<cmdArray.length;i++)
				logger.debug("\t[{}]={}", i, cmdArray[i]);
		}
		return new ProcessBuilder(cmdArray);
	}

	/**
	 * Get a process for a JVM. The class path for the JVM is computed
	 * based on the class loaders for the main class.
	 * @param mainClass Fully qualified class name of the main class
	 * @param args Additional command line parameters
	 * @return ProcessBuilder that has not been started.
	 * @deprecated This acquires the mainClass class by using the
	 * {@link Thread#getContextClassLoader() thread context class loader}
	 * or this class's class loader. Depending on where mainClass
	 * was loaded from neither of these may work.
	 * @see #getProcessBuilder(Class, String[])
	 */
	@Deprecated
	public static Process getProcess(String mainClass, String... args) throws LauncherException, IOException
	{
		return getProcessBuilder(mainClass, args).start();
	}

	/**
	 * Get a process for a JVM. The class path for the JVM is computed
	 * based on the class loaders for the main class.
	 * @param mainClass Fully qualified class name of the main class
	 * @param args Additional command line parameters
	 * @return ProcessBuilder that has not been started.
	 * @deprecated Use {@link #getProcessBuilder(Class,String[])}
	 * 	instead.
	 */
	@Deprecated
	public static Process getProcess(Class<?> mainClass, String... args) throws LauncherException, IOException
	{
		return getProcessBuilder(mainClass, args).start();
	}
}
