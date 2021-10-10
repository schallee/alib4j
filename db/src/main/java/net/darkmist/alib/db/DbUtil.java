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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DbUtil
{
	private static final Class<DbUtil> CLASS = DbUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/**
	 * Only static methods so no public construtor
	 */
	private DbUtil()
	{
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="API")
	public static void cleanup(PreparedStatement stmt)
	{
		if(stmt == null)
			return;
		try
		{
			stmt.clearParameters();
		}
		catch(SQLException e)
		{
			logger.warn("Exception clearing paramaters from prepared statement", e);
		}
		try
		{
			stmt.close();
		}
		catch(SQLException e)
		{
			logger.warn("Exception closing prepared statement", e);
		}
	}
	
	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="API")
	public static void cleanup(ResultSet rs)
	{
		if(rs == null)
			return;
		try
		{
			rs.close();
		}
		catch(SQLException e)
		{
			logger.warn("Exception closing result set", e);
		}
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="API")
	public static void cleanup(ResultSet rs, PreparedStatement stmt)
	{
		cleanup(rs);
		cleanup(stmt);
	}

	public static void cleanup(PreparedStatement stmt, ResultSet rs)
	{
		cleanup(rs,stmt);
	}
}
