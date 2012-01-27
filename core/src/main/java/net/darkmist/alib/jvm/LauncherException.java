package net.darkmist.alib.jvm;

public class LauncherException extends Exception
{
	private static final long serialVersionUID = 0;

	public LauncherException()
	{
	}

	public LauncherException(String msg)
	{
		super(msg);
	}

	public LauncherException(String msg, Throwable cause)
	{
		super(msg,cause);
	}

	public LauncherException(Throwable cause)
	{
		super(cause);
	}
}
