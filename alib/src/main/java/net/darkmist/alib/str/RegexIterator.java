package net.darkmist.alib.str;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class RegexIterator<T> implements Iterator<T>
{
	private static final Class<RegexIterator> CLASS = RegexIterator.class;
        private static final Log logger = LogFactory.getLog(CLASS);

	protected Matcher matcher;
	protected T next;
	protected boolean hasNext;

	protected abstract T getObj(Matcher matcher);
	protected abstract String getRegex();

	protected Pattern getPattern()
	{
		return Pattern.compile(getRegex());
	}

	protected Matcher getMatcher(CharSequence data)
	{
		return getPattern().matcher(data);
	}

	private boolean advance()
	{
		if(matcher.find())
		{
			hasNext = true;
			next = getObj(matcher);
		}
		else
			hasNext = false;
		return hasNext;
	}

	private void init(CharSequence data)
	{
		matcher = getMatcher(data);
		hasNext = false;
	}

	public RegexIterator(byte[] data)
	{
		init(new String(data));
	}

	public RegexIterator(CharSequence data)
	{
		init(data);
	}

	public boolean hasNext()
	{
		if(hasNext)
			return true;
		return advance();
	}

	public T next() throws NoSuchElementException
	{
		T ret;

		if(!hasNext)
		{
			if(!advance())
				throw new NoSuchElementException();
		}
		ret = next;
		next = null;	// don't keep a reference
		hasNext = false;
		return ret;
	}

	public void remove() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
}
