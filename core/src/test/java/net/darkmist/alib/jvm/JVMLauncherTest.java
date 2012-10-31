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

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JVMLauncherTest extends TestCase
{
	private static final Class<JVMLauncherTest> CLASS = JVMLauncherTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	public static class Main
	{
		public static void main(String[] args)
		{
		}
	}

	public void testLaunch() throws Exception
	{
		Process proc;
		
		proc = JVMLauncher.getProcessBuilder(Main.class).start();
		proc.waitFor();
		assertEquals(0, proc.exitValue());
	}
}
