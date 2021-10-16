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

package net.darkmist.alib.escape.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.darkmist.alib.escape.Escaper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class BaseTag extends BodyTagSupport
{
	private static final long serialVersionUID = 1l;
	private static final Class<BaseTag> CLASS = BaseTag.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private Escaper escaper;

	protected BaseTag(Escaper escaper)
	{
		this.escaper = escaper;
	}

	@Override
	public int doAfterBody() throws JspTagException
	{
		try
		{
			escaper.escape(getPreviousOut(), bodyContent.getString());
		}
		catch (IOException e)
		{
			throw new JspTagException("IOException writing " + bodyContent.getString() + " encoded output.", e);
		}

		bodyContent.clearBody();
		return SKIP_BODY;
	}

}
