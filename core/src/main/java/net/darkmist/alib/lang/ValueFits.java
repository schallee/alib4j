package net.darkmist.alib.lang;

public class ValueFits
{
	private ValueFits()
	{
	}

	public static boolean fitsByte(@SuppressWarnings("unused") byte b)
	{
		return true;
	}

	public static boolean fitsByte(short s)
	{
		return Byte.MIN_VALUE <= s && s <= Byte.MAX_VALUE;
	}

	public static boolean fitsByte(int i)
	{
		return Byte.MIN_VALUE <= i && i <= Byte.MAX_VALUE;
	}

	public static boolean fitsByte(long l)
	{
		return Byte.MIN_VALUE <= l && l <= Byte.MAX_VALUE;
	}

	public static boolean fitsByte(@SuppressWarnings("unused") Byte b)
	{
		return true;
	}

	public static boolean fitsByte(Short s)
	{
		return fitsShort(s.shortValue());
	}

	public static boolean fitsByte(Integer i)
	{
		return fitsShort(i.intValue());
	}

	public static boolean fitsByte(Long l)
	{
		return fitsShort(l.longValue());
	}

	public static boolean fitsByte(Object o)
	{
		if(o instanceof Byte)
			return true;
		if(o instanceof Short)
			return fitsByte(((Short)o).shortValue());
		if(o instanceof Integer)
			return fitsByte(((Integer)o).intValue());
		if(o instanceof Long)
			return fitsByte(((Long)o).longValue());
		return false;
	}

	public static boolean fitsShort(@SuppressWarnings("unused") byte b)
	{
		return true;
	}

	public static boolean fitsShort(@SuppressWarnings("unused") short s)
	{
		return true;
	}

	public static boolean fitsShort(int i)
	{
		return Short.MIN_VALUE <= i && i <= Short.MAX_VALUE;
	}

	public static boolean fitsShort(long l)
	{
		return Short.MIN_VALUE <= l && l <= Short.MAX_VALUE;
	}

	public static boolean fitsShort(@SuppressWarnings("unused") Byte b)
	{
		return true;
	}

	public static boolean fitsShort(@SuppressWarnings("unused") Short s)
	{
		return true;
	}

	public static boolean fitsShort(Integer i)
	{
		return fitsShort(i.intValue());
	}

	public static boolean fitsShort(Long l)
	{
		return fitsShort(l.longValue());
	}

	public static boolean fitsShort(Object o)
	{
		if(o instanceof Byte)
			return true;
		if(o instanceof Short)
			return true;
		if(o instanceof Integer)
			return fitsShort(((Integer)o).intValue());
		if(o instanceof Long)
			return fitsShort(((Long)o).longValue());
		return false;
	}

	public static boolean fitsInt(@SuppressWarnings("unused") byte b)
	{
		return true;
	}

	public static boolean fitsInt(@SuppressWarnings("unused") short s)
	{
		return true;
	}

	public static boolean fitsInt(@SuppressWarnings("unused") int i)
	{
		return true;
	}

	public static boolean fitsInt(long l)
	{
		return Integer.MIN_VALUE <= l && l <= Integer.MAX_VALUE;
	}

	public static boolean fitsInt(@SuppressWarnings("unused") Byte b)
	{
		return true;
	}

	public static boolean fitsInt(@SuppressWarnings("unused") Short s)
	{
		return true;
	}

	public static boolean fitsInt(@SuppressWarnings("unused") Integer i)
	{
		return true;
	}

	public static boolean fitsInt(Long l)
	{
		return fitsInt(l.longValue());
	}

	public static boolean fitsInt(Object o)
	{
		if(o instanceof Byte)
			return true;
		if(o instanceof Short)
			return true;
		if(o instanceof Integer)
			return true;
		if(o instanceof Long)
			return fitsInt(((Long)o).longValue());
		return false;
	}
}
