package net.darkmist.alib.exception;

public class NullExceptionHandler implements ExceptionHandler
{
	private static final NullExceptionHandler singleton = new NullExceptionHandler();

	protected NullExceptionHandler()
	{
	}

	public static NullExceptionHandler instance()
	{
		return singleton;
	}

	public void handleException(Throwable t)
	{
		// drop it on the ground!
	}
}
