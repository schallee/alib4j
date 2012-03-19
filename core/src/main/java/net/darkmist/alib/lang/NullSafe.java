package net.darkmist.alib.lang;

public class NullSafe
{
	protected NullSafe()
	{
	}

	public static boolean equals(Object a, Object b)
	{
		if(a==b)
			return true;
		if(a==null)
			return false;
		if(b==null)
			return false;
		return a.equals(b);
	}

	public static int hashCode(Object o)
	{
		return (o==null ? 0 : o.hashCode());
	}

	public static <T extends Comparable<T>> int compare(T a, T b)
	{
		if(a == b)
			return 0;
		if(a == null)
			return -1;
		if(b == null)
			return 1;
		return a.compareTo(b);
	}
}
