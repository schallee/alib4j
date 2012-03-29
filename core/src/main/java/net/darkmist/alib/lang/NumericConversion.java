package net.darkmist.alib.lang;

public class NumericConversion
{
	private NumericConversion()
	{
	}

	public static byte checkByte(byte b)
	{
		return b;
	}

	public static byte checkByte(short s)
	{
		if(s > Byte.MAX_VALUE)
			throw new IllegalArgumentException("Value " + s + " is larger than " + Byte.MAX_VALUE);
		if(s < Byte.MIN_VALUE)
			throw new IllegalArgumentException("Value " + s + " is smaller than " + Byte.MAX_VALUE);
		return (byte)s;
	}

	public static byte checkByte(int i)
	{
		if(i > Byte.MAX_VALUE)
			throw new IllegalArgumentException("Value " + i + " is larger than " + Byte.MAX_VALUE);
		if(i < Byte.MIN_VALUE)
			throw new IllegalArgumentException("Value " + i + " is smaller than " + Byte.MAX_VALUE);
		return (byte)i;
	}

	public static byte checkByte(long l)
	{
		if(l > Byte.MAX_VALUE)
			throw new IllegalArgumentException("Value " + l + " is larger than " + Byte.MAX_VALUE);
		if(l < Byte.MIN_VALUE)
			throw new IllegalArgumentException("Value " + l + " is smaller than " + Byte.MAX_VALUE);
		return (byte)l;
	}

	public static short checkShort(byte b)
	{
		return b;
	}

	public static short checkShort(short s)
	{
		return s;
	}

	public static short checkShort(int i)
	{
		if(i > Short.MAX_VALUE)
			throw new IllegalArgumentException("Value " + i + " is larger than " + Short.MAX_VALUE);
		if(i < Short.MIN_VALUE)
			throw new IllegalArgumentException("Value " + i + " is smaller than " + Short.MAX_VALUE);
		return (short)i;
	}

	public static short checkShort(long l)
	{
		if(l > Short.MAX_VALUE)
			throw new IllegalArgumentException("Value " + l + " is larger than " + Short.MAX_VALUE);
		if(l < Short.MIN_VALUE)
			throw new IllegalArgumentException("Value " + l + " is smaller than " + Short.MAX_VALUE);
		return (short)l;
	}

	public static int checkInt(byte b)
	{
		return b;
	}

	public static int checkInt(short s)
	{
		return s;
	}

	public static int checkInt(int i)
	{
		return i;
	}

	public static int checkInt(long l)
	{
		if(l > Integer.MAX_VALUE)
			throw new IllegalArgumentException("Value " + l + " is larger than " + Integer.MAX_VALUE);
		if(l < Integer.MIN_VALUE)
			throw new IllegalArgumentException("Value " + l + " is smaller than " + Integer.MAX_VALUE);
		return (int)l;
	}
}
