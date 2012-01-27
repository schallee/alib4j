package net.darkmist.alib.exception;

/**
 * Interface to handle {@link Exception}s.
 */
public interface ExceptionHandler
{
	/**
	 * Handle an {@link Exception}.
	 * @param t the exception to handle.
	 */
	public void handleException(Throwable t);
}
