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

// not changed since qcomm

import java.io.IOException;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jline.ConsoleReader;

public class GetPass
{
	private static final Character ZERO = Character.valueOf((char)0);

	public static String get() throws IOException
	{
		return get("password: ");
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="API")
	public static String get(CharSequence prompt) throws IOException
	{
		StringBuilder passwd = new StringBuilder(32);
		int ch;
		Character echoChar;

		ConsoleReader console = new ConsoleReader();

		System.out.print(prompt);
		System.out.flush();
		echoChar = console.getEchoCharacter();
		try
		{
			console.setEchoCharacter(ZERO);
			while(true)
				switch((ch = console.readVirtualKey()))
				{
					case -1:
					case '\n':
					case '\r':
						System.out.println("");
						return passwd.toString();
					default:
						passwd.append((char)ch);
				}
		}
		finally
		{
			console.setEchoCharacter(echoChar);
		}
	}

	public static String getpass(CharSequence prompt) throws IOException
	{
		return get(prompt);
	}

	public static String getpass() throws IOException
	{
		return get();
	}
}
