package net.darkmist.alib;

// not changed since qcomm

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import org.apache.log4j.Logger;

public class ResourceGetterTest extends TestCase
{
	private static final Logger logger = Logger.getLogger(ResourceGetterTest.class);

	protected void setUp()
	{
	}

	public void testSQLFixupSimple() throws Exception
	{
		String input = "select column from table -- comment\n";
		String expected = "select column from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.info("input=" + input);
		logger.info("expected=" + expected);
		logger.info("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupQuotes() throws Exception
	{
		String input = "select column as 'name' from table -- comment\n";
		String expected = "select column as 'name' from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.info("input=" + input);
		logger.info("expected=" + expected);
		logger.info("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupDoubleQuotes() throws Exception
	{
		String input = "select column as \"name\" from table -- comment\n";
		String expected = "select column as \"name\" from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.info("input=" + input);
		logger.info("expected=" + expected);
		logger.info("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupQuotedComment() throws Exception
	{
		String input = "select column as '--name' from table -- comment\n";
		String expected = "select column as '--name' from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.info("input=" + input);
		logger.info("expected=" + expected);
		logger.info("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupDoubleQuotedComment() throws Exception
	{
		String input = "select column as \"--name\" from table -- comment\n";
		String expected = "select column as \"--name\" from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.info("input=" + input);
		logger.info("expected=" + expected);
		logger.info("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupDoubleQuoteInQuote() throws Exception
	{
		String input = "select column as '--\"nam\"e' from table -- comment\n";
		String expected = "select column as '--\"nam\"e' from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.info("input=" + input);
		logger.info("expected=" + expected);
		logger.info("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupQuoteInDoubleQuote() throws Exception
	{
		String input = "select column as \"name's\" from table -- comment\n";
		String expected = "select column as \"name's\" from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.info("input=" + input);
		logger.info("expected=" + expected);
		logger.info("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupCommentOnlyLine() throws Exception
	{
		String input = "-- a comment \nselect column from table\n";
		String expected = "\nselect column from table\n";
		String result = ResourceGetter.sqlFixup(input);
		logger.info("input=" + input);
		logger.info("expected=" + expected);
		logger.info("result=" + result);
		assertEquals(expected.trim(), result.trim());
	}

	public void testSQLFixupCommentInComment() throws Exception
	{
		String input = "select column from table -- first -- comment\n";
		String expected = "select column from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.info("input=" + input);
		logger.info("expected=" + expected);
		logger.info("result=" + result);
		assertEquals(expected, result);
	}

	protected void tearDown()
	{
	}

	public static Test suite()
	{
		return new TestSuite(ResourceGetterTest.class);
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(suite());
	}
}
