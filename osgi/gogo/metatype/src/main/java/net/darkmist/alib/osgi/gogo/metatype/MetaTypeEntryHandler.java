package net.darkmist.alib.osgi.gogo.metatype;

import java.util.Collection;

import org.osgi.service.metatype.ObjectClassDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface MetaTypeEntryHandler<T>
{
	/** Handle info.
	 * @return true if handling is finished.
	 */
	public boolean handle(String pid, boolean factory, ObjectClassDefinition objClsDef);
	public T result();

	final static class CollectionMetaTypeEntryHandler<T extends Collection<MetaTypeEntry>> implements MetaTypeEntryHandler<T>
	{
		@SuppressWarnings("rawtypes")
		private static final Class<CollectionMetaTypeEntryHandler> CLASS = CollectionMetaTypeEntryHandler.class;
		private static final Logger logger = LoggerFactory.getLogger(CLASS);
		private final T collection;

		public CollectionMetaTypeEntryHandler(T collection)
		{
			if((this.collection=collection)== null)
				throw new NullPointerException();
			if(logger.isDebugEnabled())
				logger.debug("collection={} collection.getClass()={}", collection, collection.getClass());
		}

		@Override
		public boolean handle(String pid, boolean factory, ObjectClassDefinition objClsDef)
		{
			if(logger.isDebugEnabled())
				logger.debug("pid={} factory={} objClsDef={} collection.size()={}", pid, factory, objClsDef, collection.size());
			collection.add(new MetaTypeEntry(pid,factory,objClsDef));
			if(logger.isDebugEnabled())
				logger.debug("after add: collection.size()={}", collection.size());
			return false;
		}

		@Override
		public T result()
		{	
			return collection;
		}
	}
}
