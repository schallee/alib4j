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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

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

	private static class PipeReader implements Runnable
	{
		private final String name;
		private final BufferedReader in;

		PipeReader(String name, InputStream is)
		{
			this.name = name;
			in = new BufferedReader(new InputStreamReader(is));
		}

		@Override
		public void run()
		{
			String line;

			try
			{
				while((line = in.readLine())!=null)
					logger.debug("[{}] {}", name, line);
			}
			catch(IOException ioe)
			{
				logger.debug("[{}] Error reading line", name, ioe);
			}
		}
	}

	public void testLaunch() throws Exception
	{
		Process proc;
		
		proc = JVMLauncher.getProcessBuilder(Main.class).start();
		if(logger.isDebugEnabled())
		{
			Thread stdout = new Thread(new PipeReader("stdout", proc.getInputStream()));
			Thread stderr = new Thread(new PipeReader("stderr", proc.getErrorStream()));

			stdout.setDaemon(true);
			stderr.setDaemon(true);

			stdout.setName("stdout");
			stderr.setName("stderr");

			stdout.start();
			stderr.start();
		}
		proc.waitFor();
		assertEquals(0, proc.exitValue());
	}
}
