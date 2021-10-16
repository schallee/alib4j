package net.darkmist.alib.osgi;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;
import static net.darkmist.alib.lang.NullSafe.requireNonNull;
import static net.darkmist.alib.lang.NullSafe.requireNonNullElse;
import static net.darkmist.alib.osgi.MiscUtil.contains;
import static net.darkmist.alib.osgi.MiscUtil.castOrNull;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceUtil
{
	private static final Class<ServiceUtil> CLASS = ServiceUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/**
	 * Static util methods only.
	 */
	private ServiceUtil()
	{
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API method")
	public static String eventTypeToString(int type)
	{
		switch(type)
		{
			case ServiceEvent.MODIFIED:
				return "MODIFIED";
			case ServiceEvent.MODIFIED_ENDMATCH:
				return "MODIFIED_ENDMATCH";
			case ServiceEvent.REGISTERED:
				return "REGISTERED";
			case ServiceEvent.UNREGISTERING:
				return "UNREGISTERING";
			default:
				return "unknown ServiceEvent type " + type;
		}
	}

	public static String eventTypeToString(ServiceEvent event)
	{
		if(event == null)
			return "null_event";
		return eventTypeToString(event.getType());
	}

	public static String refObjectClassesToString(ServiceReference<?> servRef)
	{
		Object o;
		String[] objectClasses;
		StringBuilder sb;

		o = servRef.getProperty(Constants.OBJECTCLASS);
		if(o == null)
			return "no_objectClass_property";
		if(!(o instanceof String[]))
			return "objectClass_property_not_String[]_but_" + o.getClass().getName();
		objectClasses = (String[])o;
		sb = new StringBuilder();
		for(String cls : objectClasses)
			sb.append(cls).append(", ");
		return sb.substring(0,sb.length() -2);
	}

	public enum PropRetrival
	{
		THROW_ON_NON_EXISTANT,
		THROW_ON_WRONG_TYPE,
		THROW_ON_NULL_SERVICE_REF,
		THROW_ON_NULL_PROP_NAME,
		THROW_ON_NULL_TYPE,
		LOG_ON_NON_EXISTANT,
		LOG_ON_WRONG_TYPE,
		LOG_ON_NULL_SERVICE_REF,
		LOG_ON_NULL_PROP_NAME,
		LOG_ON_NULL_TYPE,
		DEFAULT_ON_NON_EXISTANT,
		DEFAULT_ON_WRONG_TYPE,
		DEFAULT_ON_NULL_SERVICE_REF,
		DEFAULT_ON_NULL_PROP_NAME,
		DEFAULT_ON_NULL_TYPE;
	};

	private static final Set<PropRetrival> EMPTY_PROP_RETRIVAL_FLAGS = Collections.emptySet();
	private static final Set<PropRetrival> LOGGING_PROP_RETRIVAL = Collections.unmodifiableSet(EnumSet.of(PropRetrival.LOG_ON_NON_EXISTANT, PropRetrival.LOG_ON_WRONG_TYPE, PropRetrival.LOG_ON_NULL_SERVICE_REF, PropRetrival.LOG_ON_NULL_PROP_NAME, PropRetrival.LOG_ON_NULL_TYPE));
	private static final Set<PropRetrival> THROWING_PROP_RETRIVAL = Collections.unmodifiableSet(EnumSet.of(PropRetrival.THROW_ON_NON_EXISTANT, PropRetrival.THROW_ON_WRONG_TYPE, PropRetrival.THROW_ON_NULL_SERVICE_REF, PropRetrival.THROW_ON_NULL_PROP_NAME, PropRetrival.THROW_ON_NULL_TYPE));
	
	@Nullable
	@SuppressFBWarnings(value={"BL_BURYING_LOGIC","WEM_WEAK_EXCEPTION_MESSAGING"}, justification="symatry of handling, message provided by caller")
	public static <T> T getPropertyAs(ServiceReference<?> servRef, String propName, Class<T> type, T defaultValue, @Nullable Set<PropRetrival> flags)
	{
		Object o;
		T val;
		String msg;

		flags = requireNonNullElse(flags, EMPTY_PROP_RETRIVAL_FLAGS);
		if(servRef == null)
		{
			msg = "ServiceReference servRef was null.";
			if(flags.contains(PropRetrival.THROW_ON_NULL_SERVICE_REF))
				throw new NullPointerException(msg);
			if(flags.contains(PropRetrival.LOG_ON_NULL_SERVICE_REF))
				logger.warn(msg);
			return defaultValue;
		}
		if(propName == null)
		{
			msg = "Property name was null.";
			if(flags.contains(PropRetrival.THROW_ON_NULL_PROP_NAME))
				throw new NullPointerException(msg);
			if(flags.contains(PropRetrival.LOG_ON_NULL_PROP_NAME))
				logger.warn(msg);
			return defaultValue;
		}
		if(type == null)
		{
			msg = "Type was null.";
			if(flags.contains(PropRetrival.THROW_ON_NULL_TYPE))
				throw new NullPointerException(msg);
			if(flags.contains(PropRetrival.LOG_ON_NULL_TYPE))
				logger.warn(msg);
			return defaultValue;
		}
		if((o = servRef.getProperty(propName))==null)
		{
			if(flags.contains(PropRetrival.THROW_ON_NON_EXISTANT))
				throw new IllegalStateException("Service property " + propName + " does not exist");
			if(flags.contains(PropRetrival.LOG_ON_NON_EXISTANT))
				logger.warn("Service property {} does not exist", propName);
			return defaultValue;
		}
		if((val = castOrNull(o, type))==null)
		{
			if(flags.contains(PropRetrival.THROW_ON_WRONG_TYPE))
				throw new IllegalStateException("Service property " + propName + " exists but is of type " + o.getClass().getName() + " instead of " + type.getName());
			if(flags.contains(PropRetrival.LOG_ON_NON_EXISTANT))
				logger.warn("Service property {} exists but is of type {} instead of {}", propName, o.getClass().getName(), type.getName());
			return defaultValue;
		}
		return val;
	}

	@Nullable
	public static <T> T getPropertyAs(ServiceReference<?> servRef, String propName, Class<T> type, Set<PropRetrival> flags)
	{
		return getPropertyAs(servRef, propName, type, null, flags);
	}

	@Nullable
	public static <T> T getPropertyAs(ServiceReference<?> servRef, String propName, Class<T> type, T defaultValue)
	{
		return getPropertyAs(servRef, propName, type, defaultValue, EMPTY_PROP_RETRIVAL_FLAGS);
	}

	@Nullable
	public static <T> T getPropertyAs(ServiceReference<?> servRef, String propName, Class<T> type)
	{
		return getPropertyAs(servRef, propName, type, null, EMPTY_PROP_RETRIVAL_FLAGS);
	}

	@Nullable
	public static <T> T getPropertyAsLogging(ServiceReference<?> servRef, String propName, Class<T> type, T defaultValue)
	{
		return getPropertyAs(servRef, propName, type, defaultValue, LOGGING_PROP_RETRIVAL);
	}

	@Nullable
	public static <T> T getPropertyAsLogging(ServiceReference<?> servRef, String propName, Class<T> type)
	{
		return getPropertyAsLogging(servRef, propName, type, null);
	}

	@Nullable
	public static <T> T getPropertyAsThrowing(ServiceReference<?> servRef, String propName, Class<T> type)
	{
		return getPropertyAs(servRef, propName, type, null, THROWING_PROP_RETRIVAL);
	}

	/**
	 * Cast a ServiceReference to the right genericified type.
	 * @param servRef The reference to cast
	 * @param cls The class this reference is expected to be
	 * @return servRef as ServiceReference for cls if neither is null and servRef claims cls as a class. null otherwise.
	 */
	@Nullable
	@SuppressWarnings("unchecked")
	public static <T> ServiceReference<T> getServiceRefAs(ServiceReference<?> servRef, Class<T> cls)
	{
		if(servRef == null || cls == null)
			return null;
		if(NullSafe.equals(cls.getName(), getPropertyAsLogging(servRef, Constants.OBJECTCLASS, String[].class)))
			return (ServiceReference<T>)servRef;
		return null;
	}

}
