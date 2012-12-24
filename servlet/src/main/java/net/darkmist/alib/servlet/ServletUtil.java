package net.darkmist.alib.servlet;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletUtil
{
	private static final Class<ServletUtil> CLASS = ServletUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	public enum GetOpts
	{
		THROW_ON_MISSING,
		LOG_ON_MISSING,
		THROW_ON_WRONG_TYPE,
		LOG_ON_WRONG_TYPE
	};
	public static final Set<GetOpts> EMPTY_OPTS = Collections.unmodifiableSet(EnumSet.noneOf(GetOpts.class));
	public static final Set<GetOpts> LOGGING = Collections.unmodifiableSet(EnumSet.of(GetOpts.LOG_ON_MISSING,GetOpts.LOG_ON_WRONG_TYPE));
	public static final Set<GetOpts> THROWING = Collections.unmodifiableSet(EnumSet.of(GetOpts.THROW_ON_MISSING,GetOpts.THROW_ON_WRONG_TYPE));
	public static final Set<GetOpts> THROW_ON_WRONG_TYPE = Collections.unmodifiableSet(EnumSet.of(GetOpts.THROW_ON_WRONG_TYPE));
	public static final Set<GetOpts> LOG_ON_WRONG_TYPE = Collections.unmodifiableSet(EnumSet.of(GetOpts.LOG_ON_WRONG_TYPE));

	private ServletUtil()
	{
	}

	/**
	 * Get a servlet context attribute. If there is no such attribute, an {@link IllegalStateException} is thrown with notFoundError as the message. Similarly if the attribute is not of type cls then a {@link IllegalStateException} is thrown with an error message of wrongTypeError and the actual class name appended.
	 * @param ctx Context to retrieve the attribute from
	 * @param name Attribute name to retrieve
	 * @param cls Expected type
	 * @param opts set of options defining how to handle conditions
	 * @param defaultValue the default value if opts allow
	 * @return The attribute value
	 */
	public static final <T> T getContextAttribute(ServletContext ctx, String name, Class<T> cls, Set<GetOpts> opts, T defaultValue)
	{
		Object o;

		if((o = ctx.getAttribute(name))==null)
		{
			if(opts.contains(GetOpts.THROW_ON_MISSING))
				throw new IllegalStateException("ServletContext attribute " + name + " was expected to exist but it didn't");
			if(opts.contains(GetOpts.LOG_ON_MISSING))
				logger.warn("ServletContext attribute {} was expected to exist but it didn't", name);
			return defaultValue;
		}
		if(!cls.isInstance(o))
		{
			if(opts.contains(GetOpts.THROW_ON_WRONG_TYPE))
				throw new IllegalStateException("ServletContext attribute " + name + " was expected to be of type " + cls.getName() + " but it was of type " + o.getClass().getName());
			if(opts.contains(GetOpts.LOG_ON_WRONG_TYPE) && logger.isWarnEnabled())
				logger.warn("ServletContext attribute " + name + " was expected to be of type " + cls.getName() + " but it was of type " + o.getClass().getName());
			return defaultValue;
		}
		return cls.cast(o);
	}

	public static final <T> T getContextAttribute(ServletContext ctx, String name, Class<T> cls, Set<GetOpts> opts)
	{
		return getContextAttribute(ctx, name, cls, opts, null);
	}

	public static final <T> T getContextAttribute(ServletContext ctx, String name, Class<T> cls, T defaultValue)
	{
		return getContextAttribute(ctx, name, cls, THROW_ON_WRONG_TYPE, null);
	}

	/* getContextAttribute(ServletConfig...) */

	public static final <T> T getContextAttribute(ServletConfig conf, String name, Class<T> cls, Set<GetOpts> opts, T defaultValue)
	{
		return getContextAttribute(conf.getServletContext(), name, cls, opts, defaultValue);
	}

	public static final <T> T getContextAttribute(ServletConfig conf, String name, Class<T> cls, Set<GetOpts> opts)
	{
		return getContextAttribute(conf, name, cls, opts, null);
	}

	public static final <T> T getContextAttribute(ServletConfig conf, String name, Class<T> cls, T defaultValue)
	{
		return getContextAttribute(conf, name, cls, THROW_ON_WRONG_TYPE, defaultValue);
	}

	/* getContextAttribute(Servlet...) */

	public static final <T> T getContextAttribute(Servlet servlet, String name, Class<T> cls, Set<GetOpts> opts, T defaultValue)
	{
		return getContextAttribute(servlet.getServletConfig(), name, cls, opts, defaultValue);
	}

	public static final <T> T getContextAttribute(Servlet servlet, String name, Class<T> cls, Set<GetOpts> opts)
	{
		return getContextAttribute(servlet, name, cls, opts, null);
	}

	public static final <T> T getContextAttribute(Servlet servlet, String name, Class<T> cls, T defaultValue)
	{
		return getContextAttribute(servlet, name, cls, THROW_ON_WRONG_TYPE, defaultValue);
	}

	/* getContextAttribute(HttpServlet...) which implements ServletConfig and causes ambigous calls unless explicitly specified... */

	public static final <T> T getContextAttribute(HttpServlet servlet, String name, Class<T> cls, Set<GetOpts> opts, T defaultValue)
	{
		return getContextAttribute((ServletConfig)servlet, name, cls, opts, defaultValue);
	}

	public static final <T> T getContextAttribute(HttpServlet servlet, String name, Class<T> cls, Set<GetOpts> opts)
	{
		return getContextAttribute(servlet, name, cls, opts, null);
	}

	public static final <T> T getContextAttribute(HttpServlet servlet, String name, Class<T> cls, T defaultValue)
	{
		return getContextAttribute(servlet, name, cls, THROW_ON_WRONG_TYPE, defaultValue);
	}

	/* getServletParameter(ServletConfig...) */

	public static String getServletParameter(ServletConfig conf, String name, Set<GetOpts> opts, String defaultValue)
	{
		String ret;

		if(conf == null)
			throw new NullPointerException("ServletConfig cannot be null");
		if(name == null)
			throw new NullPointerException("name cannot be null");
		if(opts == null)
			opts = EMPTY_OPTS;
		if((ret = conf.getInitParameter(name))!=null)
			return ret;
		if(opts.contains(GetOpts.THROW_ON_MISSING))
			throw new IllegalStateException("The servlet init paramter " + name + " is required but wasn't set.");
		if(opts.contains(GetOpts.LOG_ON_MISSING))
			logger.warn("The servlet init paramter {} is expected but wasn't set.", name);
		return defaultValue;
	}

	public static String getServletParameter(ServletConfig conf, String name, Set<GetOpts> opts)
	{
		return getServletParameter(conf, name, opts, null);
	}

	public static String getServletParameter(ServletConfig conf, String name, String defaultValue)
	{
		return getServletParameter(conf, name, EMPTY_OPTS, defaultValue);
	}

	/* getServletParameter(Servlet...) */

	public static String getServletParameter(Servlet servlet, String name, Set<GetOpts> opts, String defaultValue)
	{
		return getServletParameter(servlet.getServletConfig(), name, opts, defaultValue);
	}

	public static String getServletParameter(Servlet servlet, String name, Set<GetOpts> opts)
	{
		return getServletParameter(servlet, name, opts, null);
	}

	public static String getServletParameter(Servlet servlet, String name, String defaultValue)
	{
		return getServletParameter(servlet, name, EMPTY_OPTS, defaultValue);
	}

	/* getServletParameter(HttpServlet...) HttpServlet implements ServletConfig as well as Servlet... */

	public static String getServletParameter(HttpServlet servlet, String name, Set<GetOpts> opts, String defaultValue)
	{
		return getServletParameter((ServletConfig)servlet, name, opts, defaultValue);
	}

	public static String getServletParameter(HttpServlet servlet, String name, Set<GetOpts> opts)
	{
		return getServletParameter(servlet, name, opts, null);
	}

	public static String getServletParameter(HttpServlet servlet, String name, String defaultValue)
	{
		return getServletParameter(servlet, name, EMPTY_OPTS, defaultValue);
	}

	/* getRequestAttribute(...) */

	public static <T> T getRequestAttribute(ServletRequest req, String name, Class<T> cls, Set<GetOpts> opts, T defaultValue)
	{
		Object o;

		if(req == null)
			throw new NullPointerException("req cannot be null");
		if(name == null)
			throw new NullPointerException("name cannot be null");
		if(cls == null)
			throw new NullPointerException("cls cannot be null");
		if(opts == null)
			opts = EMPTY_OPTS;
		if((o = req.getAttribute(name))==null)
		{
			if(opts.contains(GetOpts.THROW_ON_MISSING))
				throw new IllegalStateException("Request attribute " + name + " is not present.");
			if(opts.contains(GetOpts.LOG_ON_MISSING))
				logger.warn("Request attribute " + name + " is not present.");
			return defaultValue;
		}
		if(!cls.isInstance(o))
		{
			if(opts.contains(GetOpts.THROW_ON_WRONG_TYPE))
				throw new IllegalStateException("Request attribute " + name + " was of type " + o.getClass().getName() + " instead of " + cls.getName());
			if(opts.contains(GetOpts.LOG_ON_MISSING))
				logger.warn("Request attribute " + name + " was of type " + o.getClass().getName() + " instead of " + cls.getName());
			return defaultValue;
		}
		return cls.cast(o);
	}

	public static <T> T getRequestAttribute(ServletRequest req, String name, Class<T> cls, Set<GetOpts> opts)
	{
		return getRequestAttribute(req, name, cls, opts, null);
	}

	public static <T> T getRequestAttribute(ServletRequest req, String name, Class<T> cls)
	{
		return getRequestAttribute(req, name, cls, THROW_ON_WRONG_TYPE, null);
	}
}
