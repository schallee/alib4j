package net.darkmist.alib.db;

// not changed since qcomm

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetSingleLong
{
	private GetSingleLong()
	{
	}

	public static long getSingleLong(ResultSet rs) throws SQLException
	{
		long ret;

		if(!rs.next())
			throw new SQLException("Expected single row from query but got no rows at all.");
		ret = rs.getLong(1);
		if(rs.next())
			throw new SQLException("Expected single row from query but got more than one row.");

		return ret;
	}

	public static long getSingleLongExclusive(ResultSet rs) throws SQLException
	{
		long ret;

		ret = getSingleLong(rs);
		rs.close();
		rs = null;
		return ret;
	}

	public static long getSingleLong(PreparedStatement stmt) throws SQLException
	{
		return getSingleLongExclusive(stmt.executeQuery());
	}

	public static long getSingleLongExclusive(PreparedStatement stmt) throws SQLException
	{
		long ret;

		ret = getSingleLong(stmt);
		stmt.clearParameters();
		stmt.close();
		stmt = null;
		return ret;
	}

	public static long getSingleLong(Connection db, String sql) throws SQLException
	{
		return getSingleLongExclusive(db.prepareStatement(sql));
	}
}
