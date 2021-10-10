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

import javax.annotation.Nullable;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static methods to convinently close JDBC objects.
 */
@CanIgnoreReturnValue
public class DbCloser
{
	private static final Class<DbCloser> CLASS = DbCloser.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/** Only static methods so private. */
	private DbCloser()
	{
	}

	/**
	 * Handle closing a statment.
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @param name Object to call {@link Object#toString()} on to
	 * 	name the object closed for any logged exception. If this is null
	 *	stmt.toString() will be used.
	 * @return null as a convinence to null out the closed object.
	 */
	@Nullable
	public static Statement close(@Nullable Statement stmt, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		if(stmt == null)
			return null;
		try
		{
			stmt.close();
		}
		catch(SQLException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("SQLException closing " + (name == null ? stmt.toString() : name) + " ignored.", e);
		}
		return null;
	}

	/**
	 * Handle closing a statment. Calls
	 * {@link #close(Statement,Logger,Object)} with null for the name
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed object.
	 */
	@Nullable
	public static Statement close(@Nullable Statement stmt, @Nullable Logger logExceptionTo)
	{
		return close(stmt, logExceptionTo, null);
	}

	/**
	 * Handle closing a statment. Calls
	 * {@link #close(Statement,Logger,Object)} with null for
	 * logExceptionTo and name
	 * @param stmt The statement to close.
	 * @return null as a convinence to null out the closed object.
	 */
	@Nullable
	public static Statement close(@Nullable Statement stmt)
	{
		return close(stmt, null, null);
	}

	/**
	 * Handle closing a prepared statment.
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @param name Object to call {@link Object#toString()} on to
	 * 	name the object closed for any logged exception. If this is null
	 *	stmt.toString() will be used.
	 * @return null as a convinence to null out the closed object.
	 */
	@Nullable
	public static PreparedStatement close(@Nullable PreparedStatement stmt, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		if(stmt == null)
			return null;
		try
		{
			stmt.clearParameters();
		}
		catch(SQLException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("SQLException clearing parameters for " + (name == null ? stmt.toString() : name) + " ignored.", e);
		}
		close((Statement)stmt, logExceptionTo, name);
		return null;
	}

	/**
	 * Handle closing a prepared statment. Calls
	 * {@link #close(PreparedStatement,Logger,Object)} with null for
	 * the name.
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 */
	@Nullable
	public static PreparedStatement close(@Nullable PreparedStatement stmt, @Nullable Logger logExceptionTo)
	{
		return close(stmt, logExceptionTo, null);
	}

	/**
	 * Handle closing a prepared statment. Calls
	 * {@link #close(PreparedStatement,Logger,Object)} with null for
	 * logExceptionTo and name.
	 * @param stmt The statement to close.
	 */
	@Nullable
	public static PreparedStatement close(@Nullable PreparedStatement stmt)
	{
		return close(stmt, null, null);
	}

	/**
	 * Handle closing a result set.
	 * @param rs The result set to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @param name Object to call {@link Object#toString()} on to
	 * 	name the object closed for any logged exception. If this is null
	 *	rs.toString() will be used.
	 * @return null as a convinence to null out the closed object.
	 */
	@Nullable
	public static ResultSet close(@Nullable ResultSet rs, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		if(rs == null)
			return null;
		try
		{
			rs.close();
		}
		catch(SQLException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("SQLException closing " + (name == null ? rs.toString() : name) + " ignored.", e);
		}
		return null;
	}

	/**
	 * Handle closing a result set. Calls
	 * {@link #close(ResultSet,Logger,Object)} with null for name.
	 * @param rs The result set to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed object.
	 */
	@Nullable
	public static ResultSet close(@Nullable ResultSet rs, @Nullable Logger logExceptionTo)
	{
		return close(rs,logExceptionTo,null);
	}

	/**
	 * Handle closing a result set. Calls
	 * {@link #close(ResultSet,Logger,Object)} with null for
	 * logExceptionTo and name
	 * @param rs The result set to close.
	 */
	@Nullable
	public static ResultSet close(@Nullable ResultSet rs)
	{
		return close(rs,(Logger)null,null);
	}

	/**
	 * Handle closing a result set and a prepared statment. This
	 * calls {@link #close(ResultSet,Logger,Object)} followed by
	 * {@link #close(PreparedStatement,Logger,Object)}.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @param name Object to call {@link Object#toString()} on to
	 * 	name the object closed for any logged exception. If this is null
	 *	rs.toString() will be used.
	 * @return null as a convinence to null out the closed objects.
	 */
	@Nullable
	public static ResultSet close(@Nullable ResultSet rs, @Nullable PreparedStatement stmt, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		close(rs, logExceptionTo, name);
		close(stmt, logExceptionTo, name);
		return null;
	}

	/**
	 * Handle closing a result set and a prepared statment. This
	 * calls {@link #close(ResultSet,PreparedStatement,Logger,Object)}
	 * with null for name.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed objects.
	 */
	@Nullable
	public static ResultSet close(@Nullable ResultSet rs, @Nullable PreparedStatement stmt, @Nullable Logger logExceptionTo)
	{
		return close(rs,stmt,logExceptionTo,null);
	}

	/**
	 * Handle closing a result set and a prepared statment. This
	 * calls {@link #close(ResultSet,PreparedStatement,Logger,Object)}
	 * with null for logException to and name.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @return null as a convinence to null out the closed objects.
	 */
	@Nullable
	public static ResultSet close(@Nullable ResultSet rs, @Nullable PreparedStatement stmt)
	{
		return close(rs,stmt,(Logger)null,null);
	}

	/**
	 * Handle closing a connection.
	 * @param conn The conneciton to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @param name Object to call {@link Object#toString()} on to
	 * 	name the object closed for any logged exception. If this is null
	 *	conn.toString() will be used.
	 * @return null as a convinence to null out the closed object.
	 */
	@Nullable
	public static Connection close(@Nullable Connection conn, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		if(conn == null)
			return null;
		try
		{
			conn.close();
		}
		catch(SQLException e)
		{
			(logExceptionTo==null ? logger : logExceptionTo).warn("SQLException closing " + (name == null ? conn.toString() : name) + " ignored.", e);
		}
		return null;
	}

	/**
	 * Handle closing a connection. Calls
	 * {@link #close(Connection,Logger,Object)} with null for name.
	 * @param conn The conneciton to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed object.
	 */
	@Nullable
	public static Connection close(@Nullable Connection conn, @Nullable Logger logExceptionTo)
	{
		return close(conn,logExceptionTo,null);
	}

	/**
	 * Handle closing a connection. Calls
	 * {@link #close(Connection,Logger,Object)} with null for
	 * logException to and name.
	 * @param conn The conneciton to close.
	 * @return null as a convinence to null out the closed object.
	 */
	@Nullable
	public static Connection close(@Nullable Connection conn)
	{
		return close(conn,null,null);
	}

	/**
	 * Handle closing a result set and a prepared statment. This
	 * calls {@link #close(ResultSet,Logger,Object)},
	 * {@link #close(PreparedStatement,Logger,Object)} and finally
	 * {@link #close(Connection,Logger,Object)}.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @param conn The connection to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @param name Object to call {@link Object#toString()} on to
	 * 	name the object closed for any logged exception.
	 * @return null as a convinence to null out the closed objects.
	 */
	@Nullable
	public static ResultSet close(@Nullable ResultSet rs, @Nullable PreparedStatement stmt, @Nullable Connection conn, @Nullable Logger logExceptionTo, @Nullable Object name)
	{
		close(rs, stmt, logExceptionTo,name);
		close(conn,logExceptionTo,name);
		return null;
	}

	/**
	 * Handle closing a result set and a prepared statment. This calls
	 * {@link #close(ResultSet,PreparedStatement,Connection,Logger,Object)}
	 * with null for name.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @param conn The connection to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed objects.
	 */
	@Nullable
	public static ResultSet close(@Nullable ResultSet rs, @Nullable PreparedStatement stmt, @Nullable Connection conn, @Nullable Logger logExceptionTo)
	{
		return close(rs,stmt,conn,logExceptionTo,null);
	}
	
	/**
	 * Handle closing a result set and a prepared statment. This calls
	 * {@link #close(ResultSet,PreparedStatement,Connection,Logger,Object)}
	 * with null for logExceptionTo and name.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @param conn The connection to close.
	 * @return null as a convinence to null out the closed objects.
	 */
	@Nullable
	public static ResultSet close(@Nullable ResultSet rs, @Nullable PreparedStatement stmt, @Nullable Connection conn)
	{
		return close(rs,stmt,conn,null,null);
	}
}
