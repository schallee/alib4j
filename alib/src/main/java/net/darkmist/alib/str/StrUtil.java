package net.darkmist.alib.str;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StrUtil
{
	private static final Class<StrUtil> CLASS = StrUtil.class;
        private static final Log logger = LogFactory.getLog(CLASS);

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
}
