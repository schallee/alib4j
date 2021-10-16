package net.darkmist.alib.osgi.gogo.metatype;

import java.util.Comparator;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;
import static net.darkmist.alib.lang.NullSafe.requireNonNull;

import org.osgi.service.metatype.ObjectClassDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Meta data entry for a PID. This class is immutable and should not
 * contain references to other services once constructed.
 */
final class MetaTypeEntry
{
	private static final Class<MetaTypeEntry> CLASS = MetaTypeEntry.class;
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private final String pid;
	private final boolean factory;
	private final String name;
	private final String id;
	private final String description;

	MetaTypeEntry(String pid, boolean factory, ObjectClassDefinition objClsDef)
	{
		if(objClsDef == null || pid == null)
			throw new NullPointerException();
		this.pid = requireNonNull(pid, "pid");
		this.factory = factory;
		requireNonNull(objClsDef, "objClsDef");
		this.name = objClsDef.getName();
		this.id = objClsDef.getID();
		this.description = objClsDef.getDescription();
	}

	public String getPid()
	{
		return pid;
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API method")
	public boolean isFactory()
	{
		return factory;
	}

	public String getName()
	{
		return name;
	}

	public String getId()
	{
		return id;
	}

	public String getDescription()
	{
		return description;
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(pid, factory, name, id, description);
	}

	private static boolean nullSafeEquals(Object a, Object b)
	{
		// Objects.equals(a,b);
		if(a==b)
			return true;
		if(a==null || b==null)
			return false;
		return a.equals(b);
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(o==null)
			return false;
		if(!(o instanceof MetaTypeEntry))
			return false;
		MetaTypeEntry other = (MetaTypeEntry)o;
		if(factory != other.isFactory())
			return false;
		if(!nullSafeEquals(pid, other.getPid()))
			return false;
		if(!nullSafeEquals(name, other.getName()))
			return false;
		if(!nullSafeEquals(id, other.getId()))
			return false;
		if(!nullSafeEquals(description, other.getDescription()))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return pid + ( factory ? " factory for \"" : " \"") + name + "\" with id " + id + '.';
	}

	//private static Comparator<MetaTypeEntry> PID_NAME_COMPARATOR;

	private enum PidNameComparator implements Comparator<MetaTypeEntry>
	{
		INSTANCE;

		private static final Class<PidNameComparator> CLASS = PidNameComparator.class;
		private static final Logger logger = LoggerFactory.getLogger(CLASS);

		private static PidNameComparator instance()
		{
			return INSTANCE;
		}

		@Override
		public int compare(MetaTypeEntry a, MetaTypeEntry b)
		{
			int ret;
			String aStr, bStr;

			logger.debug("a={} b={}", a, b);
			if(a==null || b==null)
				throw new NullPointerException();
			if((aStr = a.getPid())==null)
				throw new NullPointerException();
			if((bStr = b.getPid())==null)
				throw new NullPointerException();
			logger.debug("aStr={} bStr={}", aStr, bStr);
			if((ret=aStr.compareTo(bStr))!=0)
				return ret;
			if((aStr = a.getName())==null)
				throw new NullPointerException();
			if((bStr = b.getName())==null)
				throw new NullPointerException();
			return aStr.compareTo(bStr);
		}

		@Override
		public String toString()
		{
			return "Compare MetaTypeEntry by Pid and then Name.";
		}
	}

	static Comparator<MetaTypeEntry> getByPidNameComparator()
	{
		return PidNameComparator.instance();
	}
}
