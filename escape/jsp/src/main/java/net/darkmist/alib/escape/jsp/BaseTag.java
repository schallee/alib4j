package net.darkmist.alib.escape.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import net.darkmist.alib.escape.Escaper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class BaseTag implements Tag
{
	private static final Class<BaseTag> CLASS = BaseTag.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private Escaper escaper;
	private PageContext pc;
	private Tag parent;

	protected BaseTag(Escaper escaper)
	{
		this.escaper = escaper;
	}

	@Override
	public void setPageContext(PageContext pc_)
	{
		this.pc = pc_;
	}

	@Override
	public void setParent(Tag t)
	{
		parent = t;
	}

	@Override
	public Tag getParent()
	{
		return parent;
	}

	@Override
	public int doStartTag() throws JspTagException
	{
		pc.pushBody(escaper.escape(pc.getOut()));
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspTagException
	{
		pc.popBody();
		return EVAL_PAGE;
	}

	@Override
	public void release()
	{
		pc = null;
		parent = null;
	}
}
