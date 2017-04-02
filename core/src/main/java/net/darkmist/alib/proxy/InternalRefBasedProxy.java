package net.darkmist.alib.proxy;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class InternalRefBasedProxy<T> implements ProxyIface<T>
{
	private static Set<Flags> unmodifiableFlags(Flags...flags)
	{
		EnumSet<Flags> ret;

		if(flags==null || flags.length==0)
			return Collections.emptySet();
		ret=EnumSet.noneOf(Flags.class);
		for(Flags flag : flags)
			ret.add(flag);
		return Collections.unmodifiableSet(ret);
	}

	// These can't all be done in enum constructor parameters as the compiler has a fit over illegal forward references. Of course, this is the same thing. Just don't tell the compiler.

	private static final Set<Flags> ACCESSIBLE_INCOMPATIBLE = unmodifiableFlags(
		Flags.PHANTOM
	);
	private static final Set<Flags> CLEARABLE_INCOMPATIBLE = unmodifiableFlags(
		Flags.NONNULL,
		Flags.FINAL
	);
	private static final Set<Flags> FINAL_INCOMPATIBLE = unmodifiableFlags(
		Flags.CLEARABLE,
		Flags.RETARGETABLE,
		Flags.PHANTOM,	// only if null...
		Flags.SOFT,		// only if null...
		Flags.VOLATILE,
		Flags.WEAK		// only if null...
	);
	private static final Set<Flags> NONNULL_INCOMPATIBLE = unmodifiableFlags(
		Flags.CLEARABLE,
		Flags.PHANTOM,
		Flags.SOFT,
		Flags.WEAK
	);
	private static final Set<Flags> PHANTOM_IMPLIES = unmodifiableFlags(
		Flags.CLEARABLE
	);
	private static final Set<Flags> PHANTOM_INCOMPATIBLE = unmodifiableFlags(
		Flags.ACCESSIBLE,
		Flags.FINAL,		// unless null
		Flags.NONNULL,
		Flags.RETARGETABLE,
		Flags.SOFT,
		Flags.WEAK
	);
	private static final Set<Flags> RETARGETABLE_INCOMPATIBLE = unmodifiableFlags(
		Flags.FINAL,
		Flags.PHANTOM	// unless null...
	);
	private static final Set<Flags> SOFT_IMPLIES = unmodifiableFlags(
		Flags.CLEARABLE
	);
	private static final Set<Flags> SOFT_INCOMPATIBLE = unmodifiableFlags(
		Flags.FINAL,
		Flags.NONNULL,
		Flags.PHANTOM,
		Flags.WEAK
	);
	private static final Set<Flags> VOLATILE_IMPLIES = unmodifiableFlags(
		Flags.RETARGETABLE
	);
	private static final Set<Flags> VOLATILE_INCOMPATIBLE = unmodifiableFlags(
		Flags.FINAL
	);
	private static final Set<Flags> WEAK_IMPLIES = unmodifiableFlags(
		Flags.CLEARABLE
	);
	private static final Set<Flags> WEAK_INCOMPATIBLE = unmodifiableFlags(
		Flags.FINAL,
		Flags.NONNULL,
		Flags.PHANTOM,
		Flags.SOFT
	);

	public static enum BaseType
	{
		NORMAL,
		FINAL,
		VOLATILE;
	}

	public static enum MemRefType
	{
		STRONG,
		SOFT,
		WEAK,
		PHANTOM;
	}

	public static enum OpType
	{
		ACCESSIBLE,
		CLEARABLE,
		RETARGETABLE;
	}

	public static enum ValidationType
	{
		NULL,
		NONNULL;
	}

	public static enum Flags
	{
		ACCESSIBLE(ACCESSIBLE_INCOMPATIBLE),
		CLEARABLE(CLEARABLE_INCOMPATIBLE),
			FINAL(true, FINAL_INCOMPATIBLE)
		{
		/*
			@Override
			public <T> InternalRef<T> instance(Set<Flags> flags, T target)
			{
				return new InternalRef.Final<T>(target);
			}
			*/
		},
		NONNULL(NONNULL_INCOMPATIBLE),
			PHANTOM(true, PHANTOM_INCOMPATIBLE, PHANTOM_IMPLIES)
		{
			/*
			@Override
			public <T> InternalRef<T> instance(Set<Flags> flags, T target)
			{
				if(flags.contains(
				return new InternalRef.Phantom(
			}
			*/
		},
		RETARGETABLE(RETARGETABLE_INCOMPATIBLE),
			SOFT(SOFT_INCOMPATIBLE, SOFT_IMPLIES),
			VOLATILE(VOLATILE_INCOMPATIBLE, VOLATILE_IMPLIES),
			WEAK(WEAK_INCOMPATIBLE, WEAK_IMPLIES);

		private final boolean isFactory;
		private final Set<Flags> incompatible;
		private final Set<Flags> implies;

		private Flags(boolean isFactory, Set<Flags> incompatible, Set<Flags> implies)
		{
			if((this.incompatible=incompatible)==null)
				throw new IllegalStateException("incompatible was null");
			if(implies==null)
				implies = Collections.emptySet();
			this.implies = implies;
			this.isFactory = isFactory;
		}

		private Flags(Set<Flags> incompatible, Set<Flags> implies)
		{
			this(false, incompatible, implies);
		}

		private Flags(boolean isFactory, Set<Flags> incompatible)
		{
			this(isFactory, incompatible, null);
		}

		private Flags(Set<Flags> incompatible)
		{
			this(false, incompatible, null);
		}


		public final boolean isCompatible(Set<Flags> flags)
		{
			for(Flags flag : getIncompatible())
				if(flags.contains(flag))
					return false;
			return true;
		}

		public final Set<Flags> getIncompatible()
		{
			return incompatible;
		}

		public final Set<Flags> getImplies()
		{
			return implies;
		}

		public <T> InternalRef<T> instance(Set<Flags> flags, T target)
		{
			throw new IllegalStateException("Not facotry.");
		}

		public static Set<Flags> expandImplies(Set<Flags> flags)
		{
			EnumSet<Flags> expanded = EnumSet.copyOf(flags);
			for(Flags flag : flags)
				expanded.addAll(flag.getImplies());
			return expanded;
		}

		public static boolean isSetCompatible(Set<Flags> flags)
		{
			for(Flags flag : flags)
				if(!flag.isCompatible(flags))
					return false;
			return true;
		}
	}

	//private final InternalRef<T> ref;
	private final Set<Flags> flags;

	protected InternalRefBasedProxy(InternalRef<T> ref, Set<Flags> flags)
	{
		flags = Flags.expandImplies(flags);
		if(!Flags.isSetCompatible(flags))
			throw new IllegalArgumentException("Incompatible flags.");

		if((/*this.ref=*/ref)==null)
			throw new NullPointerException("Ref cannot be null.");
		if((this.flags=flags)==null)
			throw new NullPointerException("Flags cannot be null.");
	}

	protected T getProxyTargetInternal()
	{
		// FIXME
		throw new RuntimeException();
		//return ref.get();
	}

	@Override
	public T getProxyTarget()
	{
		if(flags.contains(Flags.ACCESSIBLE))
			return getProxyTargetInternal();
		throw new UnsupportedOperationException("Non-accessible proxy.");
	}

	@Override
	public void setProxyTarget(T target)
	{
		if(target==null)
		{
			if(flags.contains(Flags.NONNULL))
				throw new NullPointerException("Proxy reference is configured to never be null.");
			if(flags.contains(Flags.CLEARABLE))
			{
				clearProxyTarget();
				return;
			}
		}
		if(!flags.contains(Flags.RETARGETABLE))
			throw new IllegalArgumentException("Proxy reference is configured to not be set.");
		// FIXME: ref.set(target);
	}

	@Override
	public void clearProxyTarget()
	{
		if(flags.contains(Flags.CLEARABLE) || (flags.contains(Flags.RETARGETABLE) && !flags.contains(Flags.NONNULL)))
		{
			// FIXME: ref.clear();
			return;
		}
		throw new UnsupportedOperationException("Proxy reference configured to not be cleared.");
	}
}
