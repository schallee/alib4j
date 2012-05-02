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
