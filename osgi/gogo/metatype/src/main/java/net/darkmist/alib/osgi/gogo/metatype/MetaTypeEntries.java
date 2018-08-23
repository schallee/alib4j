package net.darkmist.alib.osgi.gogo.metatype;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.metatype.MetaTypeInformation;
import org.osgi.service.metatype.MetaTypeService;
import org.osgi.service.metatype.ObjectClassDefinition;

enum MetaTypeEntries
{
	SINGLETON;

	private static final Class<MetaTypeEntries> CLASS = MetaTypeEntries.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	/**
	 * Retrieve a pid's meta info and handle it.
	 * @param pid Pid of the ObjectClassDefinition
	 * @param factory Is the associated pid a factory?
	 * @param metaInfo MetaTypeInfo to retrieve ObjectClassDefinition from
	 * @param handler Handler to handle the retrieved information.
	 * @return false if pid or the retreived ObjectClassDefinition is null. The result of the handler otherwise.
	 */
	private static <T> boolean retrieveObjectClassDefAndHandle(String pid, boolean factory, MetaTypeInformation metaInfo, MetaTypeEntryHandler<T> handler)
	{
		ObjectClassDefinition objClsDef;

		if(pid==null)
		{
			logger.debug("pid was null");
			return false;
		}
		// NOTE: implement locale support?
		objClsDef = metaInfo.getObjectClassDefinition(pid, null);
		logger.debug("objClsDef={}", objClsDef);
		if(objClsDef == null)
			return false;
		return handler.handle(pid, factory, objClsDef);
	}

	private static <T> void enumerateMetaTypeEntriesHelper(BundleContext ctx, MetaTypeService mts, MetaTypeEntryHandler<T> handler)
	{
		MetaTypeInformation metaInfo;
		String pids[];

		for(Bundle bundle : ctx.getBundles())
		{
			if(bundle==null)
				continue;
			if(bundle.getState()!=Bundle.ACTIVE)
			{
				if(logger.isDebugEnabled())
					logger.debug("bundle {} not active", bundle.getSymbolicName());
				continue;
			}
			if((metaInfo = mts.getMetaTypeInformation(bundle))==null)
			{
				if(logger.isDebugEnabled())
					logger.debug("bundle {} has null MetaTypeInfo", bundle.getSymbolicName());
				continue;
			}
			pids = metaInfo.getFactoryPids();
			if(logger.isDebugEnabled())
				logger.debug("bundle {} factory pids={}", bundle.getSymbolicName(), Arrays.toString(pids));
			for(String pid : pids)
			{
				if(retrieveObjectClassDefAndHandle(pid, true, metaInfo, handler))
					return;
			}
			pids = metaInfo.getPids();
			if(logger.isDebugEnabled())
				logger.debug("bundle {} pids={}", bundle.getSymbolicName(), Arrays.toString(pids));
			for(String pid : pids)
				if(retrieveObjectClassDefAndHandle(pid, false, metaInfo, handler))
					return;
		}
	}

	/**
	 * Enumerate every pid and associated ObjectClassDefintion known to the MetaTypeService.
	 * @param handler Handle each result
	 * @return The result of the handler.
	 */
	static <T> T enumerateMetaTypeEntries(BundleContext ctx, MetaTypeService mts, MetaTypeEntryHandler<T> handler)
	{
		if(ctx==null||mts==null||handler==null)
			throw new NullPointerException();
		enumerateMetaTypeEntriesHelper(ctx, mts, handler);
		return handler.result();
	}

	static <T extends Collection<MetaTypeEntry>> T getEntries(BundleContext ctx, MetaTypeService mts, T collection)
	{
		return enumerateMetaTypeEntries(ctx, mts, new MetaTypeEntryHandler.CollectionMetaTypeEntryHandler<T>(collection));
	}

	static Set<MetaTypeEntry> getEntries(BundleContext ctx, MetaTypeService mts)
	{
		return getEntries(ctx, mts, new HashSet<MetaTypeEntry>());
	}
}
