/*
 *  Copyright (C) 2012 Ed Schaller <schallee@darkmist.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.darkmist.alib.io;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nullable;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static methods to convinently close {@link Closeable}s.
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
	@CanIgnoreReturnValue
	@Nullable
	public static <T extends Closeable> T close(@Nullable T toClose, @Nullable Logger logExceptionTo, @Nullable Object name)
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
	@CanIgnoreReturnValue
	@Nullable
	public static <T extends Closeable> T close(@Nullable T toClose, @Nullable Object name)
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
	@CanIgnoreReturnValue
	@Nullable
	public static <T extends Closeable> T close(@Nullable T toClose)
	{
		return close(toClose, null, null);
	}
}

