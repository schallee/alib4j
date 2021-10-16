package net.darkmist.alib.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;
import static net.darkmist.alib.lang.NullSafe.requireNonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatchers<T extends Servlet & Serializable> implements Serializable
{
	private static final long serialVersionUID = 1l;
	@SuppressWarnings("rawtypes")
	private static final Class<Dispatchers> CLASS = Dispatchers.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private T servlet;
	private transient ServletContext ctx;
	private transient Map<String,RequestDispatcher> namedDispatchers;
	private transient Map<String,RequestDispatcher> pathDispatchers;

	private final void init()
	{
		namedDispatchers = new HashMap<String,RequestDispatcher>();
		pathDispatchers = new HashMap<String,RequestDispatcher>();
		logger.debug("servlet={}", servlet);
		ctx = servlet.getServletConfig().getServletContext();
	}

	public Dispatchers(T servlet)
	{
		this.servlet = requireNonNull(servlet, "servlet");
		init();
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		// this should set servlet...
		in.defaultReadObject();
		init();
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public synchronized RequestDispatcher getByName(String name) throws ServletException
	{
		RequestDispatcher dispatcher;

		if((dispatcher = namedDispatchers.get(name))==null)
		{
			if((dispatcher = ctx.getNamedDispatcher(name))==null)
				throw new ServletException("No dispatcher for name " + name);
			namedDispatchers.put(name, dispatcher);
		}
		return dispatcher;
	}

	@SuppressFBWarnings(value="REQUESTDISPATCHER_FILE_DISCLOSURE", justification="Library API that assumes sane caller")
	public void forwardByName(String name, ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		getByName(name).forward(req,resp);
	}

	@SuppressFBWarnings(value="REQUESTDISPATCHER_FILE_DISCLOSURE", justification="Library API that assumes sane caller")
	public void includeByName(String name, ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		getByName(name).include(req,resp);
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public synchronized RequestDispatcher getByPath(String path) throws ServletException
	{
		RequestDispatcher dispatcher;

		if((dispatcher = pathDispatchers.get(path))==null)
		{
			if((dispatcher = ctx.getRequestDispatcher(path))==null)
				throw new ServletException("No dispatcher for path " + path);
			pathDispatchers.put(path, dispatcher);
		}
		return dispatcher;
	}

	@SuppressFBWarnings(value="REQUESTDISPATCHER_FILE_DISCLOSURE", justification="Library API that assumes sane caller")
	public void forwardByPath(String path, ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		getByPath(path).forward(req,resp);
	}

	@SuppressFBWarnings(value="REQUESTDISPATCHER_FILE_DISCLOSURE", justification="Library API that assumes sane caller")
	public void includeByPath(String path, ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		getByPath(path).include(req,resp);
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof Dispatchers))
			return false;
		Dispatchers<?> that = (Dispatchers<?>)o;
		if(!NullSafe.equals(this.servlet, that.servlet))
			return false;
		if(!NullSafe.equals(this.ctx, that.ctx))
			return false;
		if(!NullSafe.equals(this.namedDispatchers, that.namedDispatchers))
			return false;
		return NullSafe.equals(this.pathDispatchers, that.pathDispatchers);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(servlet, ctx, namedDispatchers, pathDispatchers);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": servlet=" + servlet + " ctx=" + ctx + " namedDispatchers.size()=" + namedDispatchers.size() + " pathDispatchers.size()=" + pathDispatchers.size();
	}
}
