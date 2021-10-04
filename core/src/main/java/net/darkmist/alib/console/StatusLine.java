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

package net.darkmist.alib.console;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

// not changed since qcomm
// added lock object since then.

public class StatusLine
{
	private static boolean enabled = false;
	private static int lastLineLength = 0;
	private static Object lock = new Object();

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API Method")
	public static void update(CharSequence msg)
	{
		synchronized (lock)
		{
			if(!enabled)
				return;
			System.err.print('\r');
			System.err.print(msg);
			if(msg.length() < lastLineLength)
			{
				int diff = lastLineLength - msg.length();
	
				for(int c=0;c<diff;c++)
					System.err.print(' ');
				for(int c=0;c<diff;c++)
					System.err.print('\b');
			}
			System.err.flush();
			lastLineLength = msg.length();
		}
	}

	public static void end(CharSequence msg)
	{
		synchronized (lock)
		{
			if(!enabled)
				return;
			update(msg);
			System.err.print('\n');
		}
	}

	public static void start(CharSequence msg)
	{
		update(msg);
	}

	public static boolean enabled()
	{
		synchronized (lock)
		{
			return enabled;
		}
	}

	public static void enable()
	{
		synchronized (lock)
		{
			enabled = true;
		}
	}
}
