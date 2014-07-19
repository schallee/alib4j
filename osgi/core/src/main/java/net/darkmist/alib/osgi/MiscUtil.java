package net.darkmist.alib.osgi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiscUtil
{
	private static final Class<MiscUtil> CLASS = MiscUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/**
	 * Static utils only
	 */
	private MiscUtil()
	{
	}

	/**
	 * Does an array contain a null value?
	 * @param hayStack the array to look in
	 * @return true if hayStack was non-null and contained a null value. false otherwise
	 */
	public static <T> boolean containsNull(T...hayStack)
	{
		if(hayStack == null)
			return false;
		for(T hay : hayStack)
			if(hay == null)
				return true;
		return false;
	}

	/**
	 * Does an array contain a value.
	 * @param needle The value to look for. This can be null.
	 * @param hayStack The array to look for the value in.
	 * @return true if the value was found in the array. false if it was not or hayStack was null.
	 */
	public static <T> boolean contains(T needle, T...hayStack)
	{
		if(needle == null)
			return containsNull(hayStack);
		if(hayStack == null)
			return false;
		for(T hay : hayStack)
			if(needle.equals(hay))
				return true;
		return false;
	}

	/**
	 * Cast object to class or return null.
	 * @param o The object to cast
	 * @param cls The class to cast o to
	 * @return o cast as cls. null if o is null or not a instance of cls.
	 */
	public static <T> T castOrNull(Object o, Class<T> cls)
	{
		if(o == null)
			return null;
		if(cls.isInstance(o))
			return cls.cast(o);
		return null;
	}

}
