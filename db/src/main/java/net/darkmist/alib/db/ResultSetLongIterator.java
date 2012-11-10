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

	@Override
	public Long resultSetIteratorRow2Obj(ResultSet rs) throws SQLException
	{
		return rs.getLong(1);
	}
}
