package net.darkmist.alib.str;

// not changed since qcomm
// changed now, use StringUtils instead....

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class StrUtil
{
	private static final Class<StrUtil> CLASS = StrUtil.class;
        private static final Log logger = LogFactory.getLog(CLASS);

	// not deprecated because this allows vargs
	public static final String join(CharSequence delimiter, CharSequence... strs)
	{
		return StringUtils.join(strs, delimiter.toString());
		/*
		StringBuilder out;
		int len=0;
		int c;

		if(strs.length <= 0)
			return "";
		for(c=0;c<strs.length;c++)
			len+=strs[c].length() + delimiter.length();
		out = new StringBuilder(len);
		out.append(strs[0]);
		for(c=1;c<strs.length;c++)
		{
			out.append(delimiter);
			out.append(strs[c]);
		}
		return out.toString();
		*/
	}
}
