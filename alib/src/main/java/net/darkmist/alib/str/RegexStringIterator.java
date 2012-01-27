package net.darkmist.alib.str;

import  java.util.regex.Matcher;

public abstract class RegexStringIterator extends RegexIterator<String>
{
	protected int getGroup()
	{
		return 0;
	}

	protected String getObj(Matcher matcher)
	{
		return matcher.group(getGroup());
	}

	public RegexStringIterator(byte[] data)
	{
		super(data);
	}

	public RegexStringIterator(CharSequence data)
	{
		super(data);
	}
}
