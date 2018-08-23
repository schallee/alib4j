package net.darkmist.alib.osgi.gogo.metatype;

import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.service.metatype.MetaTypeService;

public class Command
{
	private BundleContext ctx;
	private MetaTypeService mts;

	public void activate(BundleContext ctx)
	{
		this.ctx = ctx;
	}

	public void setMetaTypeService(MetaTypeService service)
	{
		this.mts = service;
	}

	private void listPids()
	{
		if(ctx==null)
			throw new NullPointerException("Null BundleContext for activated component?");
		if(mts==null)
			throw new NullPointerException("MetaTypeService should have been set by DS before activation but it is null.");
		for(MetaTypeEntry entry : MetaTypeEntries.getEntries(ctx, mts, new TreeSet<MetaTypeEntry>(MetaTypeEntry.getByPidNameComparator())))
			System.out.println(entry);
	}

	public void ls()
	{
		listPids();
	}

	public void list()
	{
		listPids();
	}
}
