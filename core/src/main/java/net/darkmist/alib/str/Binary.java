package net.darkmist.alib.str;

public class Binary
{
	/**
	 * Currently only static methods so a private constructor.
	 */
	private Binary()
	{
	}

	/**
	 * Convert a long to a binary string.
	 * This is similar to {@Long.toString(bits,2)} except that bits
	 * is handled as unsigned and the result is zero padded to the
	 * full number of bits.
	 * @param bits The long to convert
	 * @param zero The character to use for a zero bit.
	 * @param one The character to use for a one bit.
	 * @return a String representing bits in binary.
	 */
	public static String toString(long bits, char zero, char one)
	{
		StringBuilder sb = new StringBuilder(Long.SIZE);
			
		for(long mask = 0x8000000000000000l;mask!=0;mask>>>=1)
			if((mask & bits)==0)
				sb.append(zero);
			else
				sb.append(one);
		return sb.toString();
	}

	/**
	 * Convert a long to a binary string.
	 * This is similar to {@Long.toString(bits,2)} except that bits
	 * is handled as unsigned and the result is zero padded to the
	 * full number of bits.
	 * @param bits The long to convert
	 * @return a String representing bits in binary.
	 */
	public static String toString(long bits)
	{
		return toString(bits, '0', '1');
	}

	/**
	 * Convert a int to a binary string.
	 * This is similar to {@Integer.toString(bits,2)} except that bits
	 * is handled as unsigned and the result is zero padded to the
	 * full number of bits.
	 * @param bits The int to convert
	 * @param zero The character to use for a zero bit.
	 * @param one The character to use for a one bit.
	 * @return a String representing bits in binary.
	 */
	public static String toString(int bits, char zero, char one)
	{
		StringBuilder sb = new StringBuilder(Integer.SIZE);
			
		for(int mask = 0x80000000;mask!=0;mask>>>=1)
			if((mask & bits)==0)
				sb.append(zero);
			else
				sb.append(one);
		return sb.toString();
	}

	/**
	 * Convert a int to a binary string.
	 * This is similar to {@Integer.toString(bits,2)} except that
	 * bits is handled as unsigned and the result is zero padded to
	 * the full number of bits.
	 * @param bits The int to convert
	 * @return a String representing bits in binary.
	 */
	public static String toString(int bits)
	{
		return toString(bits, '0', '1');
	}
}
