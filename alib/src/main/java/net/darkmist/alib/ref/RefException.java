
package net.darkmist.alib.ref;

public class RefException extends RuntimeException
{
	private static final long serialVersionUID = 1;

	public RefException()
	{
		super();
	}

	public RefException(String msg)
	{
		super(msg);
	}

	public RefException(String msg, Throwable cause)
	{
		super(msg,cause);
	}

	public RefException(Throwable cause)
	{
		super(cause);
	}
}

