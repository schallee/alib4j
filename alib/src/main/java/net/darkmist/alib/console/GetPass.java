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
