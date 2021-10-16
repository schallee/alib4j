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

package net.darkmist.alib.spring;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.springframework.context.support.FileSystemXmlApplicationContext;


public class Main
{
	private static final Class<Main> CLASS = Main.class;
	private static final String CLASS_NAME = CLASS.getName();
	private static final int ERROR_EXIT = 1;
	private static final String MAIN_BEAN = "main";

	@SuppressFBWarnings(value="DM_EXIT", justification="Main class")
	private static void usage()
	{
		System.err.println("Usage: " + CLASS_NAME + " spring-config [args]");
		System.exit(ERROR_EXIT);
	}

	public static void main(String[] args)
	{
		FileSystemXmlApplicationContext ctx;
		Runnable mb;
		boolean doExit = false;
		int exitCode = 0;
		
		if(args.length < 1)
			usage();
		ctx = new FileSystemXmlApplicationContext(args[0]);
		mb = (Runnable)ctx.getBean(MAIN_BEAN);
		if(mb instanceof MainBean)
			((MainBean)mb).setArgs(args, 1, args.length-1);
		else if(args.length > 1)
			throw new IllegalArgumentException("main bean " + mb + " does not take arguments");
		mb.run();
		if(mb instanceof MainBean)
		{
			exitCode = ((MainBean)mb).getExitCode();
			doExit = true;
		}
		ctx.close();
		if(doExit)
			System.exit(exitCode);
	}
}
