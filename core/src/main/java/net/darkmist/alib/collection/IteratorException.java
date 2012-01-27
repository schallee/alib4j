package net.darkmist.alib.collection;

public class IteratorException extends RuntimeException
{
	static final long serialVersionUID = 3735916997356627464L;

	public IteratorException(String msg, Throwable cause)
	{
		super(msg,cause);
	}

	public IteratorException(Throwable cause, String msg)
	{
		this(msg,cause);
	}

	public IteratorException(String msg)
	{
		super(msg);
	}

	public IteratorException()
	{
		super();
	}
}
