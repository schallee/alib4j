package net.darkmist.alib.servlet;

import java.io.Serializable;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

public class NameRequestDispatcher<T extends Servlet & Serializable> extends SerializableRequestDispatcher<T>
{
	private static final long serialVersionUID = 1l;

	/**
	 * Constructor.
	 * @param servlet The servlet to get dispatchers from.
	 * @param name Name of dispatcher to acquire.
	 * @throws ServletException if the dispatcher cannot be acquired.
	 */
	public NameRequestDispatcher(T servlet, String name) throws ServletException
	{
		super(servlet, true, name);
	}
	
}
