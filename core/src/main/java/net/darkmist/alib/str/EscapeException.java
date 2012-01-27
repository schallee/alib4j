package net.darkmist.alib.str;

public class EscapeException extends Exception
{
	private static final long serialVersionUID = 0;

	public EscapeException()
	{
		super();
	}

	public EscapeException(String msg)
	{
		super(msg);
	}

	public EscapeException(String msg, Throwable cause)
	{
		super(msg, cause);
	}

	public EscapeException(Throwable cause)
	{
		super(cause);
	}
}
