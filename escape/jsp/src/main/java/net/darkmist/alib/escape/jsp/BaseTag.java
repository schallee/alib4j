package net.darkmist.alib.escape.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.darkmist.alib.escape.Escaper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class BaseTag extends BodyTagSupport
{
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
			throw new JspTagException("IOException writing encoded output.", e);
		}

		bodyContent.clearBody();
		return SKIP_BODY;
	}

}
