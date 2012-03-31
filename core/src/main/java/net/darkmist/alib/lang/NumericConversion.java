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

	public static byte checkByte(Byte b)
	{
		return b.byteValue();
	}

	public static byte checkByte(Short s)
	{
		return checkByte(s.shortValue());
	}

	public static byte checkByte(Integer i)
	{
		return checkByte(i.intValue());
	}

	public static byte checkByte(Long l)
	{
		return checkByte(l.longValue());
	}

	public static byte checkByte(Object o)
	{
		if(o instanceof Byte)
			return ((Byte)o).byteValue();
		if(o instanceof Short)
			return checkByte(((Short)o).shortValue());
		if(o instanceof Integer)
			return checkByte(((Integer)o).intValue());
		if(o instanceof Long)
			return checkByte(((Long)o).longValue());
		throw new IllegalArgumentException("Object " + o + " not of a integer type");
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

	public static short checkShort(Byte b)
	{
		return b.shortValue();
	}

	public static short checkShort(Short s)
	{
		return s.shortValue();
	}

	public static short checkShort(Integer i)
	{
		return checkShort(i.intValue());
	}

	public static short checkShort(Long l)
	{
		return checkShort(l.longValue());
	}

	public static short checkShort(Object o)
	{
		if(o instanceof Byte)
			return ((Byte)o).shortValue();
		if(o instanceof Short)
			return ((Short)o).shortValue();
		if(o instanceof Integer)
			return checkShort(((Integer)o).intValue());
		if(o instanceof Long)
			return checkShort(((Long)o).longValue());
		throw new IllegalArgumentException("Object " + o + " not of a integer type");
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

	public static int checkInt(Byte b)
	{
		return b.intValue();
	}

	public static int checkInt(Short s)
	{
		return s.intValue();
	}

	public static int checkInt(Integer i)
	{
		return i.intValue();
	}

	public static int checkInt(Long l)
	{
		return checkInt(l.longValue());
	}

	public static int checkInt(Object o)
	{
		if(o instanceof Byte)
			return ((Byte)o).intValue();
		if(o instanceof Short)
			return ((Short)o).intValue();
		if(o instanceof Integer)
			return ((Integer)o).intValue();
		if(o instanceof Long)
			return checkInt(((Long)o).longValue());
		throw new IllegalArgumentException("Object " + o + " not of a integer type");
	}

}
