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

package net.darkmist.alib.db;

import javax.annotation.Nullable;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Local NullSafe to keep from depending on alib.core
 */
@CanIgnoreReturnValue
final class LocalNullSafe
{
	private LocalNullSafe()
	{
	}

	/**
	 * Equals that properly handles null values.
	 */
	static boolean equals(@Nullable Object a, @Nullable Object b)
	{
		if(a==b)
			return true;
		if(a==null)
			return false;
		if(b==null)
			return false;
		return a.equals(b);
	}

	/**
	 * Get a object's hash code and handle null.
	 * @return 0 if o is null. o.hashCode() otherwise.
	 */
	static int hashCode(@Nullable Object o)
	{
		return (o==null ? 0 : o.hashCode());
	}

	/**
	 * Compute a hash code from a combination of each object's
	 * hash code. Null objects will have a zero hash code.
	 * @param objs Objects to get hash codes from.
	 * @return Hashcode appropriate for an object containing each
	 * 	object in objs.
	 */
	static int hashCode(@Nullable Object first, Object...objs)
	{
		int ret;

		ret = hashCode(first);
		for(Object o : objs)
			ret = ret*31 + hashCode(o);
		return ret;
	}

	@SuppressFBWarnings(value={"OPM_OVERLY_PERMISSIVE_METHOD","RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE"}, justification="API methods, Spotbugs assumes toString always returns non-null")
	public static String toString(@Nullable Object o, String ifNull)
	{
		String ret;

		if(o==null)
			return ifNull;
		ret=o.toString();
		if(ret==null)
			return ifNull;
		return ret;
	}

	static String toString(@Nullable Object o)
	{
		return toString(o,"");
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value={"WEM_WEAK_EXCEPTION_MESSAGING","NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE"},justification="Boolean state, Spotbugs bug")
	static <T> T requireNonNull(@Nullable T o)
	{
		if(o==null)
			throw new NullPointerException();
		return o;
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="NP_PARAMETER_MUST_BE_NONNULL_BUT_MARKED_AS_NULLABLE",justification="Spotbugs bugs")
	static <T> T requireNonNull(@Nullable T o, String message)
	{
		if(o==null)
			throw new NullPointerException(message);
		return o;
	}
}
