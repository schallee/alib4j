package net.darkmist.alib.escape;

import java.io.Writer;
import java.io.IOException;
import java.io.FilterWriter;

class EscapingWriter extends FilterWriter
{
	private final Escaper escaper;

	EscapingWriter(Writer out, Escaper esc)
	{
		super(out);
		escaper = esc;
	}

	@Override
	public void write(int ch) throws IOException
	{
		escaper.escape(out, ch);
	}

	@Override
	public void write(char[] chars) throws IOException
	{
		escaper.escape(out, chars);
	}

	@Override
	public void write(char[] chars, int off, int len) throws IOException
	{
		escaper.escape(out, chars, off, len);
	}

	@Override
	public void write(String str) throws IOException
	{
		escaper.escape(out, str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException
	{
		escaper.escape(out, str, off, len);
	}

	@Override
	public Writer append(char ch) throws IOException
	{
		escaper.escape(out, ch);
		return this;
	}

	@Override
	public Writer append(CharSequence str) throws IOException
	{
		escaper.escape(out, str);
		return this;
	}

	@Override
	public Writer append(CharSequence str, int off, int len) throws IOException
	{
		escaper.escape(out, str, off, len);
		return this;
	}

	@Override
	public void flush() throws IOException
	{
		out.flush();
	}

	@Override
	public void close() throws IOException
	{
		out.close();
	}
}
