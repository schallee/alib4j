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

package net.darkmist.alib.jpa;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPAUtil
{
	private static final Class<JPAUtil> CLASS = JPAUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private JPAUtil()
	{
	}

	public static <T> T getSingleResultOrNull(Class<T> cls, Query query)
	{
		List<?> l;

		logger.debug("Calling getRequestList()");
		l = query.getResultList();
		logger.debug("l={}", l);
		switch(l.size())
		{
			case 0:
				logger.debug("No results, returning null");
				return null;
			case 1:
				logger.debug("One result, returning it.");
				return cls.cast(l.get(0));
			default:
				throw new NonUniqueResultException("More than one result was returned by query.");
		}
	}
}
