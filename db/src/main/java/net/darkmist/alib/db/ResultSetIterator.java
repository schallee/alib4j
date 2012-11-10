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
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** {@link java.util.Iterator} interface to a {@link java.sql.ResultSet}. */
public class ResultSetIterator<E> implements Iterator<E>
{
	/** Class object for this class. */
	@SuppressWarnings("rawtypes")
	private static final Class<ResultSetIterator> CLASS = ResultSetIterator.class;
	/** commons logging logger */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/** The {@link java.sql.ResultSet} to iterate through. */
	private ResultSet rs = null;
	/** The {@link java.sql.PreparedStatement} to iterate through. */
	private PreparedStatement stmt = null;
	/** Flag specifying if more rows are availible. */
	private boolean more = false;
	/** Object to use to convert rows to objects */
	private Row2Obj<E> converter;
	/** Do we have exclusive access to {@link #rs} and {@link #stmt}? */
	private boolean exclusive = false;

	////////////////////////
	// Internal Interface //
	////////////////////////

	/** ResultSet to Object conversion interface. Each row in the
	  * result set is converted to an object using the methods in this
	  * interface.
	  * @see ResultSetIterator#ResultSetIterator(ResultSet,Row2Obj) */
	public interface Row2Obj<E>
	{
		/** Converts a ResultSet to an Object
		  * @param rs The ResultSet to convert.
		  * @return The object produced by this row.
		  * @throws SQLException As the retreiving data from the row
		  * could throw this, we'll expect the method to throw it
		  * too.
		  * @throws Exception Allow method to throw arbitray
		  * exceptions. */
		public E resultSetIteratorRow2Obj(ResultSet rs) throws SQLException, Exception;
	}

	//////////////////
	// Constructors //
	//////////////////

	/** Create a {@link ResultSetIterator} from a
	  * {@link java.sql.ResultSet}.
	  * @param rs The result set to iterate through.
	  * @param converter The {@link Row2Obj} to convert rows to
	  * objects with.
	  * @throws SQLException if the {@link java.sql.ResultSet} does
	  * when calling {@link java.sql.ResultSet#next()}. */
	public ResultSetIterator(ResultSet rs, Row2Obj<E> converter) throws SQLException
	{
		this.rs = rs;
		this.converter = converter;
		advance();
	}

	/** Create a {@link ResultSetIterator} from a
	  * {@link java.sql.PreparedStatement}.
	  * @param stmt The query PreparedStatement to execute and use results from.
	  * @param converter The {@link Row2Obj} to convert rows to
	  * objects with.
	  * @throws SQLException if calling
	  * {@link java.sql.PreparedStatement#executeQuery()} on stmt does or if
	  * calling {@link java.sql.ResultSet#next()} on the resulting
	  * {@link java.sql.ResultSet} does. */
	public ResultSetIterator(PreparedStatement stmt, Row2Obj<E> converter) throws SQLException
	{
		this.converter = converter;
		this.stmt = stmt;
		rs = stmt.executeQuery();
		advance();
	}

	public ResultSetIterator(PreparedStatement stmt, Row2Obj<E> converter, boolean exclusive) throws SQLException
	{
		this.converter = converter;
		this.stmt = stmt;
		rs = stmt.executeQuery();
		this.exclusive = exclusive;
		if(exclusive)
			stmt.clearParameters();
		advance();
	}


	protected ResultSetIterator(ResultSet rs, boolean exclusive) throws SQLException
	{
		this.rs = rs;
		this.exclusive = exclusive;
		advance();
	}

	protected ResultSetIterator(ResultSet rs) throws SQLException
	{
		this.rs = rs;
		advance();
	}

	protected ResultSetIterator(PreparedStatement stmt) throws SQLException
	{
		rs = stmt.executeQuery();
		this.stmt = stmt;
		advance();
	}

	protected ResultSetIterator(PreparedStatement stmt, boolean exclusive) throws SQLException
	{
		rs = stmt.executeQuery();
		this.stmt = stmt;
		this.exclusive = exclusive;
		if(exclusive)
			stmt.clearParameters();
		advance();
	}

	protected ResultSetIterator(Connection db, String sql) throws SQLException
	{
		stmt = db.prepareStatement(sql);
		exclusive = true;
		rs = stmt.executeQuery();
		stmt.clearParameters();
		advance();
	}

	@SuppressWarnings("unused")
	protected void setConverter(Row2Obj<E> converter) throws SQLException
	{
		this.converter = converter;
	}

	/////////////////////
	// Private Methods //
	/////////////////////

	/** Advance the ResultSet cursor and set flags. {@link #more} is
	  * set if there are more rows to retrieve.
	  * @throws SQLException if calling
	  * {@link java.sql.ResultSet#next()} on {@link #rs} does. */
	private void advance() throws SQLException
	{
		if(rs.next())
			more = true;
		else
		{
			more = false;
			close();
		}
	}

	////////////////////
	// public Methods //
	////////////////////

	public void close() throws SQLException
	{
		more = false;
		if(!exclusive)
			return;
		if(rs != null)
		{
			rs.close();
			rs = null;
		}
		if(stmt != null)
		{
			stmt.close();
			stmt = null;
		}
	}

	public ResultSetIterator<E> setExclusive(boolean exclusive) throws SQLException
	{
		this.exclusive = exclusive;
		if(stmt != null)
			stmt.clearParameters();
		if(exclusive && !more)
			close();
		return this;
	}

	public ResultSetIterator<E> setExclusive() throws SQLException
	{
		return setExclusive(true);
	}

	/////////////////////////
	// Iterator<E> Methods //
	/////////////////////////

	/** More data to retrieve?
	  * @return true if there is more data to retrieve. */
	@Override
	public boolean hasNext()
	{
		return more;
	}

	/** Return the next result.
	  * @return Result of converting the next row to an object using
	  * {@link #converter converter's}
	  * {@link Row2Obj#resultSetIteratorRow2Obj(ResultSet)} method.
	  * @throws NoSuchElementException if there are no more results.
	  * @throws IllegalStateException For other exceptions caught from {@link Row2Obj#resultSetIteratorRow2Obj(ResultSet)} or an {@link java.sql.SQLException} from {@link java.sql.ResultSet#next()}. */
	@Override
	public E next() throws NoSuchElementException
	{
		if(!more)
			throw new NoSuchElementException("No more rows to return");
		try
		{
			E ret = converter.resultSetIteratorRow2Obj(rs);
			advance();
			return ret;
		}
		catch(NoSuchElementException e)
		{
			throw e;
		}
		catch(SQLException e)
		{
			throw new IllegalStateException("SQLException which cannot be rethrown caught", e);
		}
		catch(Exception e)
		{
			throw new IllegalStateException("Exception which cannot be rethrown caught", e);
		}
	}

	/** Unsupported remove operation.
	  * @throws UnsupportedOperationException always */
	@Override
	public void remove() throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException("remove is not implemented for ResultSetIterator");
	}
}
