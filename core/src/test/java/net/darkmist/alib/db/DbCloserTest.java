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

package net.darkmist.alib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbCloserTest extends TestCase
{
	private static final Class<DbCloserTest> CLASS = DbCloserTest.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private IMocksControl ctrl;
	private Connection conn;
	private Logger log;
	private ResultSet rs;
	private PreparedStatement prepStmt;
	private Statement stmt;
	private SQLException sqlE;

	@Override
	protected void setUp()
	{
		ctrl = EasyMock.createStrictControl();
		conn = ctrl.createMock(Connection.class);
		log = ctrl.createMock(Logger.class);
		rs = ctrl.createMock(ResultSet.class);
		prepStmt = ctrl.createMock(PreparedStatement.class);
		stmt = ctrl.createMock(Statement.class);
		sqlE = new SQLException("sqlexception toast is yummy");
	}

	@Override
	protected void tearDown()
	{
		ctrl = null;
		conn = null;
		log = null;
		rs = null;
		prepStmt = null;
		stmt = null;
		sqlE = null;
	}

	public void testCloseStatementLogName() throws Exception
	{
		stmt.close();
		ctrl.replay();
		assertNull(DbCloser.close(stmt,log,"name toat is yummy"));
		ctrl.verify();
	}

	public void testCloseStatementNullLogName() throws Exception
	{
		ctrl.replay();
		assertNull(DbCloser.close((Statement)null,log,"name toat is yummy"));
		ctrl.verify();
	}

	public void testCloseExceptionStatementLogName() throws Exception
	{
		stmt.close();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find("name toast is yummy"),EasyMock.same(sqlE));
		ctrl.replay();
		assertNull(DbCloser.close(stmt,log,"name toast is yummy"));
		ctrl.verify();
	}

	public void testCloseStatementLog() throws Exception
	{
		stmt.close();
		ctrl.replay();
		assertNull(DbCloser.close(stmt,log));
		ctrl.verify();
	}

	public void testCloseExceptionStatementLog() throws Exception
	{
		stmt.close();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find(stmt.toString()),EasyMock.same(sqlE));
		ctrl.replay();
		assertNull(DbCloser.close(stmt,log));
		ctrl.verify();
	}

	public void testCloseStatement() throws Exception
	{
		stmt.close();
		ctrl.replay();
		assertNull(DbCloser.close(stmt));
		ctrl.verify();
	}

	public void testClosePreparedStatementLogName() throws Exception
	{
		prepStmt.clearParameters();
		prepStmt.close();
		ctrl.replay();
		assertNull(DbCloser.close(prepStmt,log,"name toat is yummy"));
		ctrl.verify();
	}

	public void testClosePreparedStatementNullLogName() throws Exception
	{
		ctrl.replay();
		assertNull(DbCloser.close((PreparedStatement)null,log,"name toat is yummy"));
		ctrl.verify();
	}

	public void testClosePreparedStatementLogNameCloseException() throws Exception
	{
		prepStmt.clearParameters();
		prepStmt.close();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find("name toast is yummy"),EasyMock.same(sqlE));
		ctrl.replay();
		assertNull(DbCloser.close(prepStmt,log,"name toast is yummy"));
		ctrl.verify();
	}

	public void testClosePreparedStatementLogNameClearParametersException() throws Exception
	{
		prepStmt.clearParameters();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find("name toast is yummy"),EasyMock.same(sqlE));
		prepStmt.close();
		ctrl.replay();
		assertNull(DbCloser.close(prepStmt,log,"name toast is yummy"));
		ctrl.verify();
	}

	public void testClosePreparedStatementLogNameClearParametersExceptionCloseException() throws Exception
	{
		prepStmt.clearParameters();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find("name toast is yummy"),EasyMock.same(sqlE));
		prepStmt.close();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find("name toast is yummy"),EasyMock.same(sqlE));
		ctrl.replay();
		assertNull(DbCloser.close(prepStmt,log,"name toast is yummy"));
		ctrl.verify();
	}

	public void testClosePreparedStatementLog() throws Exception
	{
		prepStmt.clearParameters();
		prepStmt.close();
		ctrl.replay();
		assertNull(DbCloser.close(prepStmt,log));
		ctrl.verify();
	}
	public void testClosePreparedStatement() throws Exception
	{
		prepStmt.clearParameters();
		prepStmt.close();
		ctrl.replay();
		assertNull(DbCloser.close(prepStmt));
		ctrl.verify();
	}

	public void testCloseResultSetLogName() throws Exception
	{
		rs.close();
		ctrl.replay();
		DbCloser.close(rs,log,"toast is yummy");
		ctrl.verify();
	}

	public void testCloseResultSetNullLogName() throws Exception
	{
		ctrl.replay();
		DbCloser.close((ResultSet)null,log,"toast is yummy");
		ctrl.verify();
	}

	public void testCloseExceptionResultSetLogName() throws Exception
	{
		rs.close();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find("name toast is yummy"),EasyMock.same(sqlE));
		ctrl.replay();
		DbCloser.close(rs,log,"name toast is yummy");
		ctrl.verify();
	}

	public void testCloseResultSetLog() throws Exception
	{
		rs.close();
		ctrl.replay();
		DbCloser.close(rs,log);
		ctrl.verify();
	}

	public void testCloseExceptionResultSetLog() throws Exception
	{
		rs.close();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find(rs.toString()),EasyMock.same(sqlE));
		ctrl.replay();
		DbCloser.close(rs,log);
		ctrl.verify();
	}

	public void testCloseResultSet() throws Exception
	{
		rs.close();
		ctrl.replay();
		assertNull(DbCloser.close(rs));
		ctrl.verify();
	}

	public void testCloseConnectionLogName() throws Exception
	{
		conn.close();
		ctrl.replay();
		DbCloser.close(conn,log,"toast is yummy");
		ctrl.verify();
	}

	public void testCloseConnectionNullLogName() throws Exception
	{
		ctrl.replay();
		DbCloser.close((Connection)null,log,"toast is yummy");
		ctrl.verify();
	}

	public void testCloseExceptionConnectionLogName() throws Exception
	{
		conn.close();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find("name toast is yummy"),EasyMock.same(sqlE));
		ctrl.replay();
		DbCloser.close(conn,log,"name toast is yummy");
		ctrl.verify();
	}

	public void testCloseConnectionLog() throws Exception
	{
		conn.close();
		ctrl.replay();
		DbCloser.close(conn,log);
		ctrl.verify();
	}

	public void testCloseExceptionConnectionLog() throws Exception
	{
		conn.close();
		EasyMock.expectLastCall().andThrow(sqlE);
		log.warn(EasyMock.find(conn.toString()),EasyMock.same(sqlE));
		ctrl.replay();
		DbCloser.close(conn,log);
		ctrl.verify();
	}

	public void testCloseConnection() throws Exception
	{
		conn.close();
		ctrl.replay();
		assertNull(DbCloser.close(conn));
		ctrl.verify();
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
