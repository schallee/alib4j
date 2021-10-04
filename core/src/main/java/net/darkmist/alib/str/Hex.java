/*
 *  Copyright (C) 2012 Ed Schaller <schallee@darkmist.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.darkmist.alib.str;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD",justification="API method")
public class Hex
{
	private static final String HEX_DUMP_HEADER = "          0011 2233 4455 6677  8899 aabb ccdd eeff  0123456789abcdef\n";
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

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hexNybble(T dst, int n) throws IOException
	{
		dst.append(hexNybble(n));
		return dst;
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hexByte(T dst, int b) throws IOException
	{
		hexNybble(dst,(b>>HEX_CHAR_SIZE)&0xf);
		return hexNybble(dst,b&0xf);
	}

	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS", justification="Appending to StringBuilder")
	public static StringBuilder hexByte(StringBuilder dst, int b)
	{
		try
		{
			hexByte((Appendable)dst, b);
			return dst;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hexByte(int b)
	{
		return hexByte(new StringBuilder(), b).toString();
	}

	public static String hex(byte b)
	{
		return hexByte(b);
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hex(T dst, byte b) throws IOException
	{
		return hexByte(dst,b);
	}

	@CanIgnoreReturnValue
	public static StringBuilder hex(StringBuilder dst, byte b)
	{
		return hexByte(dst, b);
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hexShort(T dst, int s) throws IOException
	{
		hexByte(dst,(s>>8)&0xff);
		return hexByte(dst,s&0xff);
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS", justification="Appending to StringBuilder")
	public static StringBuilder hexShort(StringBuilder dst, int s)
	{
		try
		{
			hexShort((Appendable)dst, s);
			return dst;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hexShort(int s)
	{
		return hexShort(new StringBuilder(), s).toString();
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hex(T dst, short s) throws IOException
	{
		return hexShort(dst,s);
	}

	@CanIgnoreReturnValue
	public static StringBuilder hex(StringBuilder dst, short s)
	{
		return hexShort(dst,s);
	}

	public static String hex(short s)
	{
		return hexShort(s);
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hexInt(T dst, int i) throws IOException
	{
		hexShort(dst,(i>>16)&0xffff);
		return hexShort(dst,i&0xffff);
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS", justification="Appending to StringBuilder")
	public static StringBuilder hexInt(StringBuilder dst, int i)
	{
		try
		{
			hexInt((Appendable)dst, i);
			return dst;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hexInt(int i)
	{
		return hexInt(new StringBuilder(), i).toString();
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hex(T dst, int i) throws IOException
	{
		return hexInt(dst,i);
	}

	@CanIgnoreReturnValue
	public static StringBuilder hex(StringBuilder dst, int i)
	{
		return hexInt(dst,i);
	}

	public static String hex(int i)
	{
		return hexInt(i);
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hexLong(T dst, long l) throws IOException
	{
		hexInt(dst,(int)(l>>32));
		return hexInt(dst,(int)l);
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS", justification="Appending to StringBuilder")
	public static StringBuilder hexLong(StringBuilder dst, long l)
	{
		try
		{
			hexLong((Appendable)dst, l);
			return dst;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hexLong(long l)
	{
		return hexLong(new StringBuilder(), l).toString();
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hex(T dst, long l) throws IOException
	{
		return hexLong(dst,l);
	}

	@CanIgnoreReturnValue
	public static StringBuilder hex(StringBuilder dst, long l)
	{
		return hexLong(dst, l);
	}

	public static String hex(long l)
	{
		return hexLong(l);
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hex(T dst, byte[] bytes, int off, int len) throws IOException
	{
		int end = off + len;

		if(end > bytes.length)
			end = bytes.length;
		for(int i=off;i<end;i++)
			hexByte(dst, bytes[i]);
		return dst;
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hex(T dst, byte[] bytes, int off) throws IOException
	{
		return hex(dst, bytes, off, bytes.length - off);
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hex(T dst, byte[] bytes) throws IOException
	{
		return hex(dst, bytes, 0, bytes.length);
	}

	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS", justification="Appending to StringBuilder")
	public static StringBuilder hex(StringBuilder dst, byte[] bytes, int off, int len)
	{
		try
		{
			hex((Appendable)dst, bytes, off, len);
			return dst;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hex(byte[] bytes, int off, int len)
	{
		return hex(new StringBuilder(), bytes, off, len).toString();
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS", justification="Appending to StringBuilder")
	public static StringBuilder hex(StringBuilder dst, byte[] bytes, int off)
	{
		try
		{
			hex((Appendable)dst, bytes, off);
			return dst;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hex(byte[] bytes, int off)
	{
		return hex(new StringBuilder(), bytes, off).toString();
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS", justification="Appending to StringBuilder")
	public static StringBuilder hex(StringBuilder dst, byte[] bytes)
	{
		try
		{
			hex((Appendable)dst, bytes);
			return dst;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hex(byte[] bytes)
	{
		return hex(new StringBuilder(), bytes).toString();
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T hex(T dst, ByteBuffer buf) throws IOException
	{
		buf = buf.duplicate();
		while(buf.hasRemaining())
			hexByte(dst, buf.get());
		return dst;
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value="EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS", justification="Writing to ByteBuffer")
	public static StringBuilder hex(StringBuilder sb, ByteBuffer buf)
	{
		try
		{
			hex((Appendable)sb, buf);
			return sb;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("IOException caught when appending to a " + StringBuilder.class.getName(), e);
		}
	}

	public static String hex(ByteBuffer buf)
	{
		return hex(new StringBuilder(), buf).toString();
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

	@CanIgnoreReturnValue
	public static <T extends Appendable> T dump(T sb, ByteBuffer buf) throws IOException
	{
		StringBuilder printable = new StringBuilder(0x10);
		int off=0;

		buf = buf.duplicate();
		sb.append(HEX_DUMP_HEADER);
		while(buf.hasRemaining())
		{	// line
			hexInt(sb, off);
			off+=0x10;
			sb.append(": ");
			printable.delete(0,printable.length());
			for(int i=0;i<2;i++)
			{
				for(int j=0;j<4;j++)
				{
					for(int k=0;k<2;k++)
					{
						if(buf.hasRemaining())
						{
							byte b = buf.get();
							hexByte(sb, b);
							printable.append(ASCII.printable(b));
						}
						else
							sb.append("  ");
					}
					sb.append(' ');
				}
				sb.append(' ');
			}
			sb.append(printable.toString());
			sb.append('\n');
		}
		return sb;
	}

	@CanIgnoreReturnValue
	@SuppressFBWarnings(value={"WEM_WEAK_EXCEPTION_MESSAGING","EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS"}, justification="Boolean case, writing to ByteBuffer")
	public static StringBuilder dump(StringBuilder sb, ByteBuffer buf)
	{
		try
		{
			dump((Appendable)sb, buf);
			return sb;
		}
		catch(IOException e)
		{
			throw new IllegalStateException("Caught IOException appending to a StringBuilder", e);
		}
	}

	public static String dump(ByteBuffer buf)
	{
		return dump(new StringBuilder(), buf).toString();
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T dump(T sb, byte[] bytes) throws IOException
	{
		return dump(sb, ByteBuffer.wrap(bytes));
	}

	public static StringBuilder dump(StringBuilder sb, byte[] bytes)
	{
		return dump(sb, ByteBuffer.wrap(bytes));
	}

	public static String dump(byte[] bytes)
	{
		return dump(ByteBuffer.wrap(bytes));
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T dump(T sb, byte[] bytes, int off, int len) throws IOException
	{
		return dump(sb, ByteBuffer.wrap(bytes,off,len));
	}

	@CanIgnoreReturnValue
	public static StringBuilder dump(StringBuilder sb, byte[] bytes, int off, int len)
	{
		return dump(sb, ByteBuffer.wrap(bytes,off,len));
	}

	public static String dump(byte[] bytes, int off, int len)
	{
		return dump(ByteBuffer.wrap(bytes,off,len));
	}

	@CanIgnoreReturnValue
	public static <T extends Appendable> T dump(T sb, byte[] bytes, int off) throws IOException
	{
		return dump(sb, ByteBuffer.wrap(bytes,off,bytes.length-off));
	}

	@CanIgnoreReturnValue
	public static StringBuilder dump(StringBuilder sb, byte[] bytes, int off)
	{
		return dump(sb, ByteBuffer.wrap(bytes,off,bytes.length-off));
	}

	public static String dump(byte[] bytes, int off)
	{
		return dump(ByteBuffer.wrap(bytes,off,bytes.length-off));
	}
}
