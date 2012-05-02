package net.darkmist.alib.str;

import  java.util.regex.Matcher;

public abstract class RegexStringIterator extends RegexIterator<String>
{
	protected int getGroup()
	{
		return 0;
	}

	@Override
	protected String getObj(Matcher matcher_param)
	{
		return matcher_param.group(getGroup());
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
