package net.darkmist.alib.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;
import static net.darkmist.alib.lang.NullSafe.requireNonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base for serializable @{link RequestDispatcher} wrappers.
 *
 * Note that this does <em>not</em> follow normal abstract principles
 * as it has no abstract methods. The reason for this is that the
 * overridable part would be how the RequestDispatcher is acquired. The
 * acquisition is done in the constructor and deserialization where it
 * is not a good idea to call overridable methods as the object may
 * not have been fully constructed yet. Hence this switches behavior
 * based on @{link #isName}. I'm not sure how else to do this without
 * duplicting most of this code in @{link NameRequestDispatcher} and
 * @{link PathRequestDispatcher}.
 */
abstract class SerializableRequestDispatcher<T extends Servlet & Serializable> implements Serializable, RequestDispatcher
{
	private static final long serialVersionUID = 1l;
	@SuppressWarnings("rawtypes")
	private static final Class<SerializableRequestDispatcher> CLASS = SerializableRequestDispatcher.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/** Serializable Servlet to get dispatchers from. */
	private T servlet;

	/** Switch for how to get the dispatcher. */
	private boolean isName;

	/** The string use to identify the dispatcher. */
	private String nameOrPath;

	/** Dispatcher we're wrapping. */
	private transient RequestDispatcher dispatcher;

	/**
	 * Initialize transient dispatcher. This is called from
	 * constructor so it must be private and/or final.
	 */
	private final void init() throws ServletException
	{
		ServletConfig conf;
		ServletContext ctx;

		logger.debug("servlet={}", servlet);

		// If the api user doesn't init a HttpServlet right
		// the conf may be null. Let's double check it all with
		// useful exception messages.
		requireNonNull(servlet, "servlet");
		conf = requireNonNull(servlet.getServletConfig(), "servlet config");
		ctx = requireNonNull(conf.getServletContext(), "servlet context");

		// get the actual dispatcher
		if(isName)
			dispatcher = ctx.getNamedDispatcher(nameOrPath);
		else
			dispatcher = ctx.getRequestDispatcher(nameOrPath);
		if(dispatcher == null)
			throw new ServletException("Unable to get dispatcher for \"" + nameOrPath + "\".");
	}

	/**
	 * Constructor.
	 * @param servlet The servlet to get dispatchers from.
	 * @param isName Whether nameOrPath is a name.
	 * @param nameOrPath What the dispatcher is for.
	 * @throws ServletException if the dispatcher cannot be acquired.
	 */
	@SuppressFBWarnings(value="WEM_WEAK_EXCEPTION_MESSAGING", justification="Boolean state")
	protected SerializableRequestDispatcher(T servlet, boolean isName, String nameOrPath) throws ServletException
	{
		this.servlet = requireNonNull(servlet, "servlet");
		this.isName = isName;
		this.nameOrPath = requireNonNull(nameOrPath, "nameOrPath");
		if(nameOrPath.length() <= 0)
			throw new IllegalArgumentException("NameOrPath cannot be empty.");
		init();
	}
	
	/**
	 * Custom deserialization. This handles setting transient fields
	 * through @{link #init()}.
	 */
	@SuppressFBWarnings(value={"EXS_EXCEPTION_SOFTENING_NO_CONSTRAINTS","WEM_WEAK_EXCEPTION_MESSAGING"}, justification="Boolean state")
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		// this should set servlet...
		in.defaultReadObject();
		try
		{
			init();
		}
		catch(ServletException e)
		{	// This could happen if it is deserialized in
			// a container where the name or path does not
			// exist. I can't see this happening unless the
			// container itself is screwed up though.
			throw new IllegalStateException("Unable to reinitialize dispatcher after deserialization.", e);
		}
	}

	@Override
	@SuppressFBWarnings(value="REQUESTDISPATCHER_FILE_DISCLOSURE", justification="Library API that assumes sane caller")
	public void forward(ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		dispatcher.forward(req, resp);
	}

	@Override
	@SuppressFBWarnings(value="REQUESTDISPATCHER_FILE_DISCLOSURE", justification="Library API that assumes sane caller")
	public void include(ServletRequest req, ServletResponse resp) throws ServletException, IOException
	{
		dispatcher.include(req, resp);
	}

}
