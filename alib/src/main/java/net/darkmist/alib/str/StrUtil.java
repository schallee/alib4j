package net.darkmist.alib.str;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StrUtil
{
	private static final Class<StrUtil> CLASS = StrUtil.class;
        private static final Log logger = LogFactory.getLog(CLASS);
	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private StrUtil()
	{
	}

	/**
	 * Join strings with a delimiter.
	 * @param delimiter The delimiter to use between strings.
	 * @param strs The Strings to join.
	 */
	// not deprecated because this allows vargs
	public static final String join(CharSequence delimiter, CharSequence... strs)
	{
		return StringUtils.join(strs, delimiter.toString());
	}

	/**
	 * Split a string on a delimiter.
	 * @param str The string to split.
	 * @param delim The delimiter to split on.
	 * @return The substrings of str that were seperated by delim.
	 */
	public static String[] split(String str, char delim)
	{
		List strs;
		int len;
		int start,end;
		
		if(str == null)
			return EMPTY_STRING_ARRAY;
		if((len = str.length())==0)
			return new String[]{""};
		strs = new ArrayList(len);
		for(start=0;start<len && (end = str.indexOf(delim, start))>=0;start=end+1)
			strs.add(str.substring(start,end));
		strs.add(str.substring(start));
		return (String[])strs.toArray(EMPTY_STRING_ARRAY);
	}
}
