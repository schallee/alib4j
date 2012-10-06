package net.darkmist.alib.escape;

class CachingXMLEntityMaker extends StrMaker.CachingAbstract
{
	private static final Class<CachingXMLEntityMaker> CLASS = CachingXMLEntityMaker.class;
	private static final CachingXMLEntityMaker SINGLETON = new CachingXMLEntityMaker();

	private CachingXMLEntityMaker()
	{
		super(XMLEntityMaker.instance());

		StrCache cache;
		int size;

		if((size = Util.getPositiveIntProp(CLASS, Util.getCacheSizePropName(), Util.getCacheSizeDefault()))==0)
			return;
		cache = getCache();
		for(int i=0;i<size;i++)
			cache.putInterned(i);
	}

	static CachingXMLEntityMaker instance()
	{
		return SINGLETON;
	}

}
