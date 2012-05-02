package net.darkmist.alib.str;

import org.apache.commons.lang.StringEscapeUtils;

/** @deprecated Use {@link org.apache.commons.lang.StringEscapeUtils} instead. */
@Deprecated
public abstract class HTMLEscape
{
	private HTMLEscape()
	{
	}

	/** @deprecated Use {@link org.apache.commons.lang.StringEscapeUtils#escapeHtml(java.lang.String)} instead. */
	// FIXME: we don't trust either of these...
	@Deprecated
	public static String escape(String in)
	{
		return StringEscapeUtils.escapeHtml(in);
		/*
		int i;
		StringBuilder ret = new StringBuilder(in.length());

		for(i=0;i<in.length();i++)
		{
			char ch = in.charAt(i);
			if('A' <= ch && ch <= 'Z')
				ret.append(ch);
			else if('a' <= ch && ch <= 'z')
				ret.append(ch);
			else if('0' <= ch && ch <= '9')
				ret.append(ch);
			else
				switch(ch)
				{
					case '_':
					case '-':
					case ' ':
					case '\t':
						ret.append(ch);
						break;
					default:
						ret.append(String.format("&#%04X;", Character.codePointAt(in,i)));
				}
		}
		return ret.toString();
		*/
	}
}
