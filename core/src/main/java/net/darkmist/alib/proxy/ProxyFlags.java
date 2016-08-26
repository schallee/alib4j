package net.darkmist.alib.proxy;

import java.util.Collections;
import java.util.Set;

public enum ProxyFlags
{
	//RETARGETABLE,
	//ACCESSIBLE,
	NULL;

	private static final Set<ProxyFlags> DEFAULT_FLAGS = Collections.emptySet();

	public static Set<ProxyFlags> getDefaultFlags()
	{
		return DEFAULT_FLAGS;
	}

	public static <T> T validate(Set<ProxyFlags> flags, T target)
	{
		if(target==null && !flags.contains(ProxyFlags.NULL))
			throw new NullPointerException("Proxy target cannot be null.");
		return target;
	}
}

