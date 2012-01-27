package net.darkmist.alib.io;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static methods to convinently close {@Closeable}s.
 */
public class Closer
{
	private static final Class<Closer> CLASS = Closer.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/** Only static methods so private. */
	private Closer()
	{
	}

	/**
	 * Handle closing.
	 * @param toClose The Closable to close.
	 * @param logExceptionTo The log to log any {@link IOException}
	 *	to. If this is null, the logger for the Closer class
	 *	will be used.
	 * @param name Object to call {@link Object#toString()} on to
	 * 	name the object closed for any logged exception. If this is null
	 *	toClose.toString() will be used.
	 * @return null as a convinence to null out the closed object as in 
	 * 	<code>toClose = Closer.close(toClose,logExceptionTo,name)</code>
	 */
	public static <T extends Closeable> T close(T toClose, Logger logExceptionTo, Object name)
	{
		if(toClose == null)
			return null;
		try
		{
			toClose.close();
		}
		catch(IOException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("IOException closing " + (name == null ? toClose.toString() : name) + " ignored.", e);
		}
		return null;
	}

	/**
	 * Handle closing.
	 * The logger for the Closer class will be used to log any
	 * {@link IOException}s that occur.
	 * @param toClose The Closable to close.
	 * @param name Object to call {@link Object#toString()} on to
	 * 	name the object closed for any logged exception. If this is null
	 *	toClose.toString() will be used.
	 * @return null as a convinence to null out the closed object as in 
	 * 	<code>toClose = Closer.close(toClose,name)</code>
	 */
	public static <T extends Closeable> T close(T toClose, Object name)
	{
		return close(toClose, null, name);
	}

	/**
	 * Handle closing.
	 * The logger for the Closer class will be used to log any
	 * {@link IOException}s that occur. The name of the
	 * object in the log message will be aquired through
	 * <code>toClose.toString()</code>.
	 * @param toClose The Closable to close.
	 * @return null as a convinence to null out the closed object as in 
	 * 	<code>toClose = Closer.close(toClose)</code>
	 */
	public static <T extends Closeable> T close(T toClose)
	{
		return close(toClose, null, null);
	}
}

