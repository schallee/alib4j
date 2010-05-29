package net.darkmist.alib.math;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

public class BitsTest extends TestCase
{
	private static final Class CLASS = BitsTest.class;

	protected void setUp()
	{
	}

	public void testReverseByteLowNybbleToHighNybble()
	{
		// zero bits
		assertEquals(0x00,Bits.reverseByte(0x00));

		// one bit
		assertEquals(0x80,Bits.reverseByte(0x01));
		assertEquals(0x40,Bits.reverseByte(0x02));
		assertEquals(0x20,Bits.reverseByte(0x04));
		assertEquals(0x10,Bits.reverseByte(0x08));

		// two bits
		assertEquals(0xC0,Bits.reverseByte(0x03));
		assertEquals(0xA0,Bits.reverseByte(0x05));
		assertEquals(0x60,Bits.reverseByte(0x06));
		assertEquals(0x90,Bits.reverseByte(0x09));
		assertEquals(0x50,Bits.reverseByte(0x0A));
		assertEquals(0x30,Bits.reverseByte(0x0C));

		// three bits
		assertEquals(0xE0,Bits.reverseByte(0x07));
		assertEquals(0x70,Bits.reverseByte(0x0E));

		// four bits
		assertEquals(0xF0,Bits.reverseByte(0x0F));
	}

	public void testReverseByteHighNybbleToLowNybble()
	{
		// zero bits
		assertEquals(0x00,Bits.reverseByte(0x00));

		// one bit
		assertEquals(0x01,Bits.reverseByte(0x80));
		assertEquals(0x02,Bits.reverseByte(0x40));
		assertEquals(0x04,Bits.reverseByte(0x20));
		assertEquals(0x08,Bits.reverseByte(0x10));

		// two bits
		assertEquals(0x0C,Bits.reverseByte(0x30));
		assertEquals(0x0A,Bits.reverseByte(0x50));
		assertEquals(0x06,Bits.reverseByte(0x60));
		assertEquals(0x09,Bits.reverseByte(0x90));
		assertEquals(0x05,Bits.reverseByte(0xA0));
		assertEquals(0x03,Bits.reverseByte(0xC0));

		// three bits
		assertEquals(0x0E,Bits.reverseByte(0x70));
		assertEquals(0x07,Bits.reverseByte(0xE0));

		// four bits
		assertEquals(0x0F,Bits.reverseByte(0xF0));
	}

	public void testReverseShortLowNybbleToHighNybble()
	{
		// zero bits
		assertEquals(0x00,Bits.reverseShort(0x0000));

		// one bit
		assertEquals(0x8000,Bits.reverseShort(0x0001));
		assertEquals(0x4000,Bits.reverseShort(0x0002));
		assertEquals(0x2000,Bits.reverseShort(0x0004));
		assertEquals(0x1000,Bits.reverseShort(0x0008));

		// two bits
		assertEquals(0xC000,Bits.reverseShort(0x0003));
		assertEquals(0xA000,Bits.reverseShort(0x0005));
		assertEquals(0x6000,Bits.reverseShort(0x0006));
		assertEquals(0x9000,Bits.reverseShort(0x0009));
		assertEquals(0x5000,Bits.reverseShort(0x000A));
		assertEquals(0x3000,Bits.reverseShort(0x000C));

		// three bits
		assertEquals(0xE000,Bits.reverseShort(0x0007));
		assertEquals(0x7000,Bits.reverseShort(0x000E));

		// four bits
		assertEquals(0xF000,Bits.reverseShort(0x000F));
	}

	public void testReverseShortHighNybbleToLowNybble()
	{
		// zero bits
		assertEquals(0x0000,Bits.reverseShort(0x0000));

		// one bit
		assertEquals(0x0001,Bits.reverseShort(0x8000));
		assertEquals(0x0002,Bits.reverseShort(0x4000));
		assertEquals(0x0004,Bits.reverseShort(0x2000));
		assertEquals(0x0008,Bits.reverseShort(0x1000));

		// two bits
		assertEquals(0x000C,Bits.reverseShort(0x3000));
		assertEquals(0x000A,Bits.reverseShort(0x5000));
		assertEquals(0x0006,Bits.reverseShort(0x6000));
		assertEquals(0x0009,Bits.reverseShort(0x9000));
		assertEquals(0x0005,Bits.reverseShort(0xA000));
		assertEquals(0x0003,Bits.reverseShort(0xC000));

		// three bits
		assertEquals(0x000E,Bits.reverseShort(0x7000));
		assertEquals(0x0007,Bits.reverseShort(0xE000));

		// four bits
		assertEquals(0x000F,Bits.reverseShort(0xF000));
	}

	public static Test suite()
	{
		return new TestSuite(CLASS);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
