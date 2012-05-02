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

package net.darkmist.alib;

// not changed since qcomm

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceGetterTest extends TestCase
{
	private static final Class<ResourceGetterTest> CLASS = ResourceGetterTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	public void testSQLFixupSimple() throws Exception
	{
		String input = "select column from table -- comment\n";
		String expected = "select column from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.debug("input=" + input);
		logger.debug("expected=" + expected);
		logger.debug("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupQuotes() throws Exception
	{
		String input = "select column as 'name' from table -- comment\n";
		String expected = "select column as 'name' from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.debug("input=" + input);
		logger.debug("expected=" + expected);
		logger.debug("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupDoubleQuotes() throws Exception
	{
		String input = "select column as \"name\" from table -- comment\n";
		String expected = "select column as \"name\" from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.debug("input=" + input);
		logger.debug("expected=" + expected);
		logger.debug("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupQuotedComment() throws Exception
	{
		String input = "select column as '--name' from table -- comment\n";
		String expected = "select column as '--name' from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.debug("input=" + input);
		logger.debug("expected=" + expected);
		logger.debug("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupDoubleQuotedComment() throws Exception
	{
		String input = "select column as \"--name\" from table -- comment\n";
		String expected = "select column as \"--name\" from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.debug("input=" + input);
		logger.debug("expected=" + expected);
		logger.debug("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupDoubleQuoteInQuote() throws Exception
	{
		String input = "select column as '--\"nam\"e' from table -- comment\n";
		String expected = "select column as '--\"nam\"e' from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.debug("input=" + input);
		logger.debug("expected=" + expected);
		logger.debug("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupQuoteInDoubleQuote() throws Exception
	{
		String input = "select column as \"name's\" from table -- comment\n";
		String expected = "select column as \"name's\" from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.debug("input=" + input);
		logger.debug("expected=" + expected);
		logger.debug("result=" + result);
		assertEquals(expected, result);
	}

	public void testSQLFixupCommentOnlyLine() throws Exception
	{
		String input = "-- a comment \nselect column from table\n";
		String expected = "\nselect column from table\n";
		String result = ResourceGetter.sqlFixup(input);
		logger.debug("input=" + input);
		logger.debug("expected=" + expected);
		logger.debug("result=" + result);
		assertEquals(expected.trim(), result.trim());
	}

	public void testSQLFixupCommentInComment() throws Exception
	{
		String input = "select column from table -- first -- comment\n";
		String expected = "select column from table \n";
		String result = ResourceGetter.sqlFixup(input);
		logger.debug("input=" + input);
		logger.debug("expected=" + expected);
		logger.debug("result=" + result);
		assertEquals(expected, result);
	}
}
