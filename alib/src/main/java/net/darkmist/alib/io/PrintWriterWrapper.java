package net.darkmist.alib.io;

import java.io.PrintWriter;
import java.io.Writer;
import java.io.IOException;
import java.util.Locale;

/** Wrapper for a PrintWriter. */
public class PrintWriterWrapper extends PrintWriter
{
	private boolean firstOutputHappened = false;

	/** Construct wrapper based on paramater.
	 * @param pw_ the {@link PrintWriter} to wrap.
	 */
	public PrintWriterWrapper(PrintWriter pw_)
	{
		super(pw_);
	}

	/** Called before the first output is written.
	 * The default version does nothing.
	 */
	protected void onFirstOutput()
	{
	}

	/** Has the first output occured?
	 * @return whether the first output has occured or not.
	 */
	public boolean hasFirstOutputHappened()
	{
		return firstOutputHappened;
	}

	/** Check whether first output calls should be made.
	 * If the first output has not occured, {@link #onFirstOutput} is called.
	 * @see #onFirstOutput
	 */
	protected void checkFirstOutput()
	{
		if(firstOutputHappened)
			return;
		// set first so we don't have infinite recursion...
		firstOutputHappened = true;
		onFirstOutput();
	}

	@Override
	public void flush()
	{
		checkFirstOutput();
		super.flush();
	}

	@Override
	public void close()
	{
		checkFirstOutput();
		super.close();
	}

	@Override
	public void write(int b)
	{
		checkFirstOutput();
		super.write(b);
	}

	@Override
	public void write(char[] bytes, int off, int len)
	{
		checkFirstOutput();
		super.write(bytes, off, len);
	}

	@Override
	public void write(char[] chars)
	{
		checkFirstOutput();
		super.write(chars);
	}

	@Override
	public void write(String s, int off, int len)
	{
		checkFirstOutput();
		super.write(s,off,len);
	}

	@Override
	public void write(String s)
	{
		checkFirstOutput();
		super.write(s);
	}

	@Override
	public void print(boolean bool)
	{
		checkFirstOutput();
		super.print(bool);
	}

	@Override
	public void print(char ch)
	{
		checkFirstOutput();
		super.print(ch);
	}

	@Override
	public void print(int i)
	{
		checkFirstOutput();
		super.print(i);
	}

	@Override
	public void print(long l)
	{
		checkFirstOutput();
		super.print(l);
	}

	@Override
	public void print(float f)
	{
		checkFirstOutput();
		super.print(f);
	}

	@Override
	public void print(double d)
	{
		checkFirstOutput();
		super.print(d);
	}

	@Override
	public void print(char[] chars)
	{
		checkFirstOutput();
		super.print(chars);
	}

	@Override
	public void print(String s)
	{
		checkFirstOutput();
		super.print(s);
	}

	@Override
	public void print(Object o)
	{
		checkFirstOutput();
		super.print(o);
	}

	@Override
	public void println()
	{
		checkFirstOutput();
		super.println();
	}

	@Override
	public void println(boolean bool)
	{
		checkFirstOutput();
		super.println(bool);
	}

	@Override
	public void println(char ch)
	{
		checkFirstOutput();
		super.println(ch);
	}

	@Override
	public void println(int i)
	{
		checkFirstOutput();
		super.println(i);
	}

	@Override
	public void println(long l)
	{
		checkFirstOutput();
		super.println(l);
	}

	@Override
	public void println(float f)
	{
		checkFirstOutput();
		super.println(f);
	}

	@Override
	public void println(double d)
	{
		checkFirstOutput();
		super.println(d);
	}

	@Override
	public void println(char[] chars)
	{
		checkFirstOutput();
		super.println(chars);
	}

	@Override
	public void println(String s)
	{
		checkFirstOutput();
		super.println(s);
	}

	@Override
	public void println(Object o)
	{
		checkFirstOutput();
		super.println(o);
	}

	@Override
	public PrintWriter printf(String fmt, Object... args)
	{
		checkFirstOutput();
		return super.printf(fmt,args);
	}

	@Override
	public PrintWriter printf(Locale locale, String fmt, Object... args)
	{
		checkFirstOutput();
		return super.printf(locale,fmt,args);
	}

	@Override
	public PrintWriter format(String fmt, Object... args)
	{
		checkFirstOutput();
		return super.format(fmt,args);
	}

	@Override
	public PrintWriter format(Locale locale, String fmt, Object... args)
	{
		checkFirstOutput();
		return super.format(locale,fmt,args);
	}

	@Override
	public PrintWriter append(CharSequence s)
	{
		checkFirstOutput();
		return super.append(s);
	}

	@Override
	public PrintWriter append(CharSequence s, int off, int len)
	{
		checkFirstOutput();
		return super.append(s,off,len);
	}

	@Override
	public PrintWriter append(char ch)
	{
		checkFirstOutput();
		return super.append(ch);
	}
}
