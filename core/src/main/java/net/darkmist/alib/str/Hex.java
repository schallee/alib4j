package net.darkmist.alib.str;

import java.io.IOException;

public class Hex
{
	public static final int HEX_CHAR_SIZE = 4;
	public static final int BYTE_HEX_CHARS = Byte.SIZE / HEX_CHAR_SIZE;
	public static final int SHORT_HEX_CHARS = Short.SIZE / HEX_CHAR_SIZE;
	public static final int INT_HEX_CHARS = Integer.SIZE / HEX_CHAR_SIZE;
	public static final int LONG_HEX_CHARS = Long.SIZE / HEX_CHAR_SIZE;

	public static boolean isHex(int c)
	{
		if(Character.isDigit(c))
			return true;
		if('a' <= c && c <= 'f')
			return true;
		if('A' <= c && c <= 'F')
			return true;
		return false;
	}

	public static char hexNybble(int n)
	{
		n &= 0xf;	// force to nibble
		return (char)((n<0xa ? '0' : 'a'-0xa) + n);
	}

	public static Appendable hexNybble(Appendable dst, int n) throws IOException
	{
		return dst.append(hexNybble(n));
	}

	public static Appendable hexByte(Appendable dst, int b) throws IOException
	{
		dst.append(hexNybble((b>>HEX_CHAR_SIZE)&0xf));
		return dst.append(hexNybble(b&0xf));
	}

	public static String hexByte(int b)
	{
		try
		{
			return hexByte(new StringBuilder(), b).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hex(byte b)
	{
		return hexByte(b);
	}

	public static Appendable hex(Appendable dst, byte b) throws IOException
	{
		return hexByte(dst,b);
	}

	public static Appendable hexShort(Appendable dst, int s) throws IOException
	{
		hexByte(dst,(s>>8)&0xff);
		return hexByte(dst,s&0xff);
	}

	public static String hexShort(int s)
	{
		try
		{
			return hexShort(new StringBuilder(), s).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static Appendable hex(Appendable dst, short s) throws IOException
	{
		return hexShort(dst,s);
	}

	public static String hex(short s)
	{
		return hexShort(s);
	}

	public static Appendable hexInt(Appendable dst, int i) throws IOException
	{
		hexShort(dst,(i>>16)&0xffff);
		return hexShort(dst,i&0xffff);
	}

	public static String hexInt(int i)
	{
		try
		{
			return hexInt(new StringBuilder(), i).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static Appendable hex(Appendable dst, int i) throws IOException
	{
		return hexInt(dst,i);
	}

	public static String hex(int i)
	{
		return hexInt(i);
	}

	public static Appendable hexLong(Appendable dst, long l) throws IOException
	{
		hexInt(dst,(int)(l>>32));
		return hexInt(dst,(int)l);
	}

	public static String hexLong(long l)
	{
		try
		{
			return hexLong(new StringBuilder(), l).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static Appendable hex(Appendable dst, long l) throws IOException
	{
		return hexLong(dst,l);
	}

	public static String hex(long l)
	{
		return hexLong(l);
	}

	public static Appendable hex(Appendable dst, byte[] bytes, int off, int len) throws IOException
	{
		int end = off + len;

		if(end > bytes.length)
			end = bytes.length;
		for(int i=off;i<end;i++)
			hexByte(dst, bytes[i]);
		return dst;
	}

	public static Appendable hex(Appendable dst, byte[] bytes, int off) throws IOException
	{
		return hex(dst, bytes, off, bytes.length - off);
	}

	public static Appendable hex(Appendable dst, byte[] bytes) throws IOException
	{
		return hex(dst, bytes, 0, bytes.length);
	}

	public static String hex(byte[] bytes, int off, int len)
	{
		try
		{
			return hex(new StringBuilder(), bytes, off, len).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hex(byte[] bytes, int off)
	{
		try
		{
			return hex(new StringBuilder(), bytes, off).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hex(byte[] bytes)
	{
		try
		{
			return hex(new StringBuilder(), bytes).toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static byte unhexNybble(int c)
	{
		if('0'<=c&&c<='9')
			return (byte)(c-'0');
		else if('a'<=c&&c<='f')
			return (byte)((c-'a')+0xa);
		else if('A'<=c&&c<='F')
			return (byte)((c-'A')+0xa);
		else
			throw new NumberFormatException("Hex Nybble contained non-hex character " + (char)c);
	}

	public static byte unhexByte(int c1, int c2)
	{
		return((byte)((unhexNybble(c1)<<HEX_CHAR_SIZE)|unhexNybble(c2)));
	}

	public static byte unhexByte(CharSequence src, int off, int len)
	{
		if(len <= 0)
			return (byte)0;
		if(len == 1)
			return unhexNybble(src.charAt(off));
		return unhexByte(src.charAt(off),src.charAt(off+1));
	}

	public static byte unhexByte(CharSequence src, int off)
	{
		return unhexByte(src, off, BYTE_HEX_CHARS);
	}

	public static byte unhexByte(CharSequence src)
	{
		return unhexByte(src, 0, BYTE_HEX_CHARS);
	}

	public static short unhexShort(CharSequence src, int off, int len)
	{
		return (short)unhexInt(src,off,len);
	}

	public static short unhexShort(CharSequence src, int off)
	{
		return unhexShort(src,off,SHORT_HEX_CHARS);
	}

	public static short unhexShort(CharSequence src)
	{
		return unhexShort(src,0,SHORT_HEX_CHARS);
	}

	public static int unhexInt(CharSequence src, int off, int len)
	{
		int ret = 0;

		len = Math.min(len,INT_HEX_CHARS);
		for(int end=off+len;off<end;off++)
			ret = (ret << HEX_CHAR_SIZE) | unhexNybble(src.charAt(off));
		return ret;
	}

	public static int unhexInt(CharSequence src, int off)
	{
		return unhexInt(src, off, INT_HEX_CHARS);
	}

	public static int unhexInt(CharSequence src)
	{
		return unhexInt(src, 0, INT_HEX_CHARS);
	}

	public static long unhexLong(CharSequence src, int off, int len)
	{
		long ret = 0;

		len = Math.min(len,LONG_HEX_CHARS);
		for(int end = off+len; off<end;off++)
			ret = (ret << HEX_CHAR_SIZE) | unhexNybble(src.charAt(off));
		return ret;
	}

	public static long unhexLong(CharSequence src, int off)
	{
		return unhexLong(src, off, LONG_HEX_CHARS);
	}

	public static long unhexLong(CharSequence src)
	{
		return unhexLong(src, 0, LONG_HEX_CHARS);
	}

	public static byte[] unhex(CharSequence src, int srcOff, int srcLen, byte[] dst, int dstOff)
	{
		for(int srcEnd = srcOff + srcLen;srcOff < srcEnd;srcOff+=2,dstOff++)
			dst[dstOff] = unhexByte(src, srcOff);
		return dst;
	}

	public static byte[] unhex(CharSequence src, int srcOff, int srcLen)
	{
		return unhex(src, srcOff, srcLen, new  byte[srcLen/2], 0);
	}

	public static byte[] unhex(CharSequence src, int srcOff)
	{
		int srcLen = src.length() - srcOff;

		return unhex(src, srcOff, srcLen, new  byte[srcLen/2], 0);
	}

	public static byte[] unhex(CharSequence src)
	{
		int srcLen = src.length();

		return unhex(src, 0, srcLen, new  byte[srcLen/2], 0);
	}
}
