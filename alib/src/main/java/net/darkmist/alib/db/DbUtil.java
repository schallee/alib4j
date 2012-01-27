package net.darkmist.alib.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DbUtil
{
	public static final void cleanup(PreparedStatement stmt)
	{
		if(stmt == null)
			return;
		try
		{
			stmt.clearParameters();
		}
		catch(SQLException ignored)
		{}
		try
		{
			stmt.close();
		}
		catch(SQLException ignored)
		{}
	}
	
	public static final void cleanup(ResultSet rs)
	{
		if(rs == null)
			return;
		try
		{
			rs.close();
		}
		catch(SQLException ignored)
		{}
	}

	public static final void cleanup(ResultSet rs, PreparedStatement stmt)
	{
		cleanup(rs);
		cleanup(stmt);
	}

	public static final void cleanup(PreparedStatement stmt, ResultSet rs)
	{
		cleanup(rs,stmt);
	}
}
