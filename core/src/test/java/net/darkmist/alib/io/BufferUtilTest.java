package net.darkmist.alib.io;

import java.nio.ByteBuffer;
import java.util.Arrays;

import junit.framework.TestCase;

import net.darkmist.alib.str.Hex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferUtilTest extends TestCase
{
	private static final Class<BufferUtilTest> CLASS = BufferUtilTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	public void testSliceMiddle()
	{
		ByteBuffer input = ByteBuffer.wrap(new byte[]{0,1,2,3,4,5,6,7,8,9});
		byte[] expected = new byte[]{4,5,6};
		ByteBuffer actualBuf = BufferUtil.slice(input, 4, 3);
		byte[] actual = BufferUtil.asBytes(actualBuf);
		logger.debug("actualBuf.position()={} actualBuf.limit()={}", actualBuf.position(), actualBuf.limit());
		logger.debug("expected=\n{}", Hex.dump(expected));
		logger.debug("actualBuf=\n{}", Hex.dump(actualBuf));
		logger.debug("actual=\n{}", Hex.dump(actual));
		assertTrue(Arrays.equals(expected, actual));
	}

	public void testIsAll0True()
	{
		byte[] bytes = new byte[1024];
		assertTrue(BufferUtil.isAll(ByteBuffer.wrap(bytes), (byte)0));
	}

	public void testIsAll0False()
	{
		byte[] bytes = new byte[1024];
		bytes[bytes.length-1] = (byte)0xff;
		assertFalse(BufferUtil.isAll(ByteBuffer.wrap(bytes), (byte)0));
	}
}
