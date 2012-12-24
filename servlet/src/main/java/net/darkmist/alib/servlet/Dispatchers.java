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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatchers<T extends Servlet & Serializable> implements Serializable
{
	private static final long serialVersionUID = 1l;
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
		if(servlet == null)
			throw new NullPointerException("Servlet cannot be null.");
		this.servlet = servlet;
		init();
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		// this should set servlet...
		in.defaultReadObject();
		init();
	}

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

	public void forwardByName(String name, ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		getByName(name).forward(req,resp);
	}

	public void includeByName(String name, ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		getByName(name).include(req,resp);
	}

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

	public void forwardByPath(String path, ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		getByPath(path).forward(req,resp);
	}

	public void includeByPath(String path, ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		getByPath(path).include(req,resp);
	}
}
