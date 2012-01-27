package net.darkmist.alib.res;

public class ResourceException extends RuntimeException
{
	private static final long serialVersionUID = 1l;

	public ResourceException()
	{
	}

	public ResourceException(String msg)
	{
		super(msg);
	}

	public ResourceException(String msg, Throwable cause)
	{
		super(msg,cause);
	}

	public ResourceException(Throwable cause)
	{
		super(cause);
	}
}
