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

import jline.ConsoleReader;
import java.lang.Character;
import java.io.IOException;

public class GetPass
{
	public static String get() throws IOException
	{
		return get("password: ");
	}

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
			console.setEchoCharacter(new Character((char)0));
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
