package net.darkmist.alib.exception;

/**
 * {@link ExceptionHandler} that does nothing.
 */
public class NullExceptionHandler implements ExceptionHandler
{
	private static final NullExceptionHandler singleton = new NullExceptionHandler();

	/**
	 * Private constructor as this is a singleton.
	 */
	private NullExceptionHandler()
	{
	}

	/**
	 * Get singleton instance.
	 * @return singleton instance.
	 */
	public static NullExceptionHandler instance()
	{
		return singleton;
	}

	/**
	 * Handle an {@link Exception} by doing nothing.
	 * @param t The exception to handle.
	 */
	public void handleException(Throwable t)
	{
		// drop it on the ground!
	}
}
