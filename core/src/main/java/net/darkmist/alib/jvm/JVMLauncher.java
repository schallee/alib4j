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
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JVMLauncher
{
	private static final Class<JVMLauncher> CLASS = JVMLauncher.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	public static String getJavaPath() throws LauncherException
	{
		String home = System.getProperty("java.home");
		String sep = System.getProperty("file.separator");
		StringBuilder sb = new StringBuilder();
		String exe;
		File file;

		sb.append(home);
		if(!home.endsWith(sep))
			sb.append(sep);
		sb.append("bin");
		sb.append(sep);
		sb.append("java");
		exe = sb.toString();
		logger.debug("exe=" + exe + '=');

		file = new File(exe);
		if(!file.exists())
			throw new LauncherException("Java executable " + exe + " does not exist.");
		if(!file.isFile())
			throw new LauncherException("Java executable " + exe + " is not a file.");
		if(!file.canExecute())
			throw new LauncherException("Java executable " + exe + " cannot be executed.");
		return exe;
	}

	public static String getClassPath() throws LauncherException
	{
		return System.getProperty("java.class.path");
	}

	public static ProcessBuilder getProcessBuilder(Class<?> mainClass, String... args) throws LauncherException
	{
		return getProcessBuilder(mainClass.getName(), args);
	}

	public static ProcessBuilder getProcessBuilder(String mainClass, String... args) throws LauncherException
	{
		String[] finalArgs;
		
		if(args==null || args.length==0)
			finalArgs = new String[4];
		else
		{
			finalArgs = new String[4 + args.length];
			System.arraycopy(args, 0, finalArgs, 4, args.length);
		}
		args = null;
		finalArgs[0] = getJavaPath();
		finalArgs[1] = "-cp";
		finalArgs[2] = getClassPath();
		finalArgs[3] = mainClass;
		if(logger.isDebugEnabled())
			logger.debug("finalArgs=" + Arrays.toString(finalArgs));
		return new ProcessBuilder(finalArgs);
	}

	public static Process getProcess(String mainClass, String... args) throws LauncherException, IOException
	{
		return getProcessBuilder(mainClass, args).start();
	}

	public static Process getProcess(Class<?> mainClass, String... args) throws LauncherException, IOException
	{
		return getProcess(mainClass.getName(), args);
	}

	public static void main(String[] args) throws Exception
	{
		System.out.println("exe=" + getJavaPath());
		System.out.println("classpath=" + getClassPath());
	}
}
