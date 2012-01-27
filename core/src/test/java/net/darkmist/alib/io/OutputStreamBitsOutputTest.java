package net.darkmist.alib.io;


import java.io.IOException;
import java.io.EOFException;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import net.darkmist.alib.str.Binary;
import net.darkmist.alib.str.Hex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputStreamBitsOutputTest extends TestCase
{
	private static final Class<OutputStreamBitsOutputTest> CLASS = OutputStreamBitsOutputTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	public void testWriteNothing() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.close();
		result = baos.toByteArray();
		if(logger.isDebugEnabled())
			logger.debug("result={}", (result==null ? "(null)" : Arrays.toString(result)));
		assertTrue("no writing still caused writing to underlying stream.", result==null||result.length==0);
	}

	public void testSingle1Bit() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBit(true);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0x80, result[0]);
	}

	public void testSingle0Bit() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBit(false);
		bits.close();
		result = baos.toByteArray();
		if(logger.isDebugEnabled())
			logger.debug("result={}", (result==null ? "(null)" : Arrays.toString(result)));
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0x00, result[0]);
	}

	public void testWriteA() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBits(4,0xA);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0xA0, result[0]);
	}

	public void testWrite5() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBits(4,0x5);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0x50, result[0]);
	}

	public void testWrite4Bit0() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBits(4,0x0);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0x00, result[0]);
	}

	public void testWriteF() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBits(4,0xF);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0xF0, result[0]);
	}

	public void testWrite8Bit0() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBits(8,0x0);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0x00, result[0]);
	}

	public void testWriteFF() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBits(8,0xFF);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0xFF, result[0]);
	}

	public void testWriteA5() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBits(8,0xA5);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0xA5, result[0]);
	}

	public void testWrite5A() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] result;

		bits.writeBits(8,0x5A);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertEquals("resultant array was not one byte", 1, result.length);
		assertEquals((byte)0x5A, result[0]);
	}

	public void testWrite12Bit0() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] expected = new byte[]{(byte)0x0,(byte)0x0};
		byte[] result;

		bits.writeBits(12,0x0);
		bits.close();
		result = baos.toByteArray();
		assertNotNull(result);
		assertTrue(Arrays.equals(expected,result));
		assertEquals((byte)0x00, result[0]);
	}

	public void testWriteFFF() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] expected = new byte[]{(byte)0xFF,(byte)0xF0};
		byte[] result;

		bits.writeBits(12,0xFFF);
		bits.close();
		result = baos.toByteArray();
		if(logger.isDebugEnabled())
		{
			logger.debug("result  ={}", (result==null ? "(null)" : Hex.hex(result)));
			logger.debug("expected={}", (result==null ? "(null)" : Hex.hex(expected)));
		}
		assertNotNull(result);
		assertTrue(Arrays.equals(expected,result));
	}

	public void testWriteA5A() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] expected = new byte[]{(byte)0xA5,(byte)0xA0};
		byte[] result;

		bits.writeBits(12,0xA5A);
		bits.close();
		result = baos.toByteArray();
		if(logger.isDebugEnabled())
			logger.debug("result={}", (result==null ? "(null)" : Hex.hex(result)));
		assertNotNull(result);
		assertTrue(Arrays.equals(expected,result));
	}

	public void testWrite5A5() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamBitsOutput bits = new OutputStreamBitsOutput(baos);
		byte[] expected = new byte[]{(byte)0x5A,(byte)0x50};
		byte[] result;

		bits.writeBits(12,0x5A5);
		bits.close();
		result = baos.toByteArray();
		if(logger.isDebugEnabled())
			logger.debug("result={}", (result==null ? "(null)" : Hex.hex(result)));
		assertNotNull(result);
		assertTrue(Arrays.equals(expected,result));
	}
}
