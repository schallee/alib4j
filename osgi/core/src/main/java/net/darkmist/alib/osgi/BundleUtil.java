package net.darkmist.alib.osgi;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleUtil
{
	private static final Class<BundleUtil> CLASS = BundleUtil.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/**
	 * Static utils only
	 */
	private BundleUtil()
	{
	}

	public static String stateToString(Bundle bundle)
	{
		if(bundle == null)
			return "null_bundle";
		return stateToString(bundle.getState());
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public static String stateToString(int state)
	{
		switch(state)
		{
			case Bundle.ACTIVE:
				return "ACTIVE";
			case Bundle.INSTALLED:
				return "INSTALLED";
			case Bundle.RESOLVED:
				return "RESOLVED";
			case Bundle.SIGNERS_ALL:
				return "SIGNERS_ALL";
			//case Bundle.SIGNERS_TRUSTED:
				//return "SIGNERS_TRUSTED";
			//case Bundle.START_ACTIVATION_POLICY:
				//return "START_ACTIVATION_POLICY";
			//case Bundle.START_TRANSIENT:
				//return "START_TRANSIENT";
			case Bundle.STARTING:
				return "STARTING";
			//case Bundle.STOP_TRANSIENT:
				//return "STOP_TRANSIENT";
			case Bundle.STOPPING:
				return "STOPPING";
			//case Bundle.UNINSTALLED:
				//return "UNINSTALLED";
			default:
				return "unknown_bundle_state_" + state;
		}
	}

	public static String eventTypeToString(BundleEvent event)
	{
		if(event == null)
			return "null event";
		return eventTypeToString(event.getType());
	}

	@SuppressFBWarnings(value="OPM_OVERLY_PERMISSIVE_METHOD", justification="Library API")
	public static String eventTypeToString(int type)
	{
		switch(type)
		{
			case BundleEvent.INSTALLED:
				return "INSTALLED";
			case BundleEvent.LAZY_ACTIVATION:
				return "LAZY_ACTIVATION";
			case BundleEvent.RESOLVED:
				return "RESOLVED";
			case BundleEvent.STARTED:
				return "STARTED";
			case BundleEvent.STARTING:
				return "STARTING";
			case BundleEvent.STOPPED:
				return "STOPPED";
			case BundleEvent.STOPPING:
				return "STOPPING";
			case BundleEvent.UNINSTALLED:
				return "UNINSTALLED";
			case BundleEvent.UNRESOLVED:
				return "UNRESOLVED";
			case BundleEvent.UPDATED:
				return "UPDATED";
			default:
				return "unknown BundleEvent type " + type;
		}
	}
	
	
}
