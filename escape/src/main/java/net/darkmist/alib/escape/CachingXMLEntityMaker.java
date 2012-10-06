package net.darkmist.alib.escape;

class CachingXMLEntityMaker extends StrMaker.CachingAbstract
{
	private static final CachingXMLEntityMaker SINGLETON = new CachingXMLEntityMaker();

	private CachingXMLEntityMaker()
	{
		super(XMLEntityMaker.instance());

		StrCache cache;

		cache = getCache();
		for(int i=0;i<128;i++)
			cache.putInterned(i);
	}

	static CachingXMLEntityMaker instance()
	{
		return SINGLETON;
	}

}
