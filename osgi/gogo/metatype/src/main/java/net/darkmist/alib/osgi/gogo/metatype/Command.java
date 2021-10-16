package net.darkmist.alib.osgi.gogo.metatype;

import java.util.TreeSet;

import net.darkmist.alib.lang.NullSafe;
import static net.darkmist.alib.lang.NullSafe.requireNonNull;

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
		requireNonNull(ctx, "ctx");
		requireNonNull(mts, "mts");
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

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof Command))
			return false;
		Command that = (Command)o;
		if(!NullSafe.equals(this.ctx, that.ctx))
			return true;
		return NullSafe.equals(this.mts, that.mts);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(ctx, mts);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + ": ctx=" + ctx + " mts=" + mts;
	}
}
