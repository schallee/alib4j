package net.darkmist.alib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static methods to convinently close JDBC objects.
 */
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
	public static Statement close(Statement stmt, Logger logExceptionTo, Object name)
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
	 * {@link #close(Statement,Logger,Object} with null for the name
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed object.
	 */
	public static Statement close(Statement stmt, Logger logExceptionTo)
	{
		return close(stmt, logExceptionTo, null);
	}

	/**
	 * Handle closing a statment. Calls
	 * {@link #close(Statement,Logger,Object} with null for
	 * logExceptionTo and name
	 * @param stmt The statement to close.
	 * @return null as a convinence to null out the closed object.
	 */
	public static Statement close(Statement stmt)
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
	public static PreparedStatement close(PreparedStatement stmt, Logger logExceptionTo, Object name)
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
	 * {@link #close(PreparedStatement,Logger,Object} with null for
	 * the name.
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 */
	public static PreparedStatement close(PreparedStatement stmt, Logger logExceptionTo)
	{
		return close(stmt, logExceptionTo, null);
	}

	/**
	 * Handle closing a prepared statment. Calls
	 * {@link #close(PreparedStatement,Logger,Object} with null for
	 * logExceptionTo and name.
	 * @param stmt The statement to close.
	 */
	public static PreparedStatement close(PreparedStatement stmt)
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
	public static ResultSet close(ResultSet rs, Logger logExceptionTo, Object name)
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
	public static ResultSet close(ResultSet rs, Logger logExceptionTo)
	{
		return close(rs,logExceptionTo,null);
	}

	/**
	 * Handle closing a result set. Calls
	 * {@link #close(ResultSet,Logger,Object)} with null for
	 * logExceptionTo and name
	 * @param rs The result set to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed object.
	 */
	public static ResultSet close(ResultSet rs)
	{
		return close(rs,(Logger)null,null);
	}

	/**
	 * Handle closing a result set and a prepared statment. This
	 * calls {@ #close(ResultSet,Logger,Object)} followed by
	 * {@ #close(PreparedStatement,Logger,Object)}.
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
	public static ResultSet close(ResultSet rs, PreparedStatement stmt, Logger logExceptionTo, Object name)
	{
		close(rs, logExceptionTo, name);
		close(stmt, logExceptionTo, name);
		return null;
	}

	/**
	 * Handle closing a result set and a prepared statment. This
	 * calls {@ #close(ResultSet,PreparedStatement,Logger,Object)}
	 * with null for name.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed objects.
	 */
	public static ResultSet close(ResultSet rs, PreparedStatement stmt, Logger logExceptionTo)
	{
		return close(rs,stmt,logExceptionTo,null);
	}

	/**
	 * Handle closing a result set and a prepared statment. This
	 * calls {@ #close(ResultSet,PreparedStatement,Logger,Object)}
	 * with null for logException to and name.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed objects.
	 */
	public static ResultSet close(ResultSet rs, PreparedStatement stmt)
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
	public static Connection close(Connection conn, Logger logExceptionTo, Object name)
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
	public static Connection close(Connection conn, Logger logExceptionTo)
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
	public static Connection close(Connection conn)
	{
		return close(conn,null,null);
	}

	/**
	 * Handle closing a result set and a prepared statment. This
	 * calls {@ #close(ResultSet,Logger,Object)},
	 * {@ #close(PreparedStatement,Logger,Object)} and finally
	 * {@ #close(Connection,Logger,Object)}.
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
	public static ResultSet close(ResultSet rs, PreparedStatement stmt, Connection conn, Logger logExceptionTo, Object name)
	{
		close(rs, stmt, logExceptionTo,name);
		close(conn,logExceptionTo,name);
		return null;
	}

	/**
	 * Handle closing a result set and a prepared statment. This calls
	 * {@ #close(ResultSet,PreparedStatement,Connection,Logger,Object)}
	 * with null for name.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @param conn The connection to close.
	 * @param logExceptionTo The log to log any {@link SQLException}
	 *	to. If this is null, the logger for the DbCloser class
	 *	will be used.
	 * @return null as a convinence to null out the closed objects.
	 */
	public static ResultSet close(ResultSet rs, PreparedStatement stmt, Connection conn, Logger logExceptionTo)
	{
		return close(rs,stmt,conn,logExceptionTo,null);
	}
	
	/**
	 * Handle closing a result set and a prepared statment. This calls
	 * {@ #close(ResultSet,PreparedStatement,Connection,Logger,Object)}
	 * with null for logExceptionTo and name.
	 * @param rs The result set to close.
	 * @param stmt The statement to close.
	 * @param conn The connection to close.
	 * @return null as a convinence to null out the closed objects.
	 */
	public static ResultSet close(ResultSet rs, PreparedStatement stmt, Connection conn)
	{
		return close(rs,stmt,conn,null,null);
	}
}
