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
