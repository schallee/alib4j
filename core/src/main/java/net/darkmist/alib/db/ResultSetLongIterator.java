package net.darkmist.alib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetLongIterator extends ResultSetIterator<Long> implements ResultSetIterator.Row2Obj<Long>
{
	public ResultSetLongIterator(ResultSet rs) throws SQLException
	{
		super(rs);
		setConverter(this);
	}

	public ResultSetLongIterator(PreparedStatement stmt) throws SQLException
	{
		super(stmt);
		setConverter(this);
	}

	public ResultSetLongIterator(Connection db, String sql) throws SQLException
	{
		super(db,sql);
		setConverter(this);
	}

	public ResultSetLongIterator(ResultSet rs, boolean exclusive) throws SQLException
	{
		super(rs, exclusive);
		setConverter(this);
	}

	public ResultSetLongIterator(PreparedStatement stmt, boolean exclusive) throws SQLException
	{
		super(stmt,exclusive);
		setConverter(this);
	}

	public Long resultSetIteratorRow2Obj(ResultSet rs) throws SQLException
	{
		return rs.getLong(1);
	}
}
