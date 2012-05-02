package net.darkmist.alib.console;

// not changed since qcomm
// added lock object since then.

public class StatusLine
{
	private static boolean enabled = false;
	private static int lastLineLength = 0;
	private static Object lock = new Object();

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
