package net.darkmist.alib.servlet;

import java.io.Serializable;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

public class PathRequestDispatcher<T extends Servlet & Serializable> extends SerializableRequestDispatcher<T>
{
	private static final long serialVersionUID = 1l;

	/**
	 * Constructor.
	 * @param servlet The servlet to get dispatchers from.
	 * @param path Path of dispatcher to acquire.
	 * @throws ServletException if the dispatcher cannot be acquired.
	 */
	public PathRequestDispatcher(T servlet, String path) throws ServletException
	{
		super(servlet, false, path);
	}
	
}
