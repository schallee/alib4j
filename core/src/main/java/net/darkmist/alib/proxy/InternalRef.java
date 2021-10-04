package net.darkmist.alib.proxy;

// import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
// import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import static net.darkmist.alib.lang.NullSafe.requireNonNull;

@Deprecated // Never functional
public interface InternalRef<T>
{

	//public interface ProxyOps<T>
	//{
		public T get();
		public void set(T target);
		public void clear();
	//}

	public interface InternalRefFactory
	{
		public <T> InternalRef<T> instance(T target);
	}

	public interface InternalRefNextFactory
	{
		public <T> InternalRef<T> instance(InternalRefFactory nextFactory, T target);
	}

	public enum ModifierType implements InternalRefNextFactory
	{
		NONE
		{
			@Override
			public <T> InternalRef<T>  instance(final InternalRefFactory nextFactory, final T target)
			{
				requireNonNull(nextFactory, "nextFactory");
				return new InternalRef<T>()
				{
					private InternalRef<T> next = getNext(nextFactory, target);
			
					@Override
					public T get()
					{
						T ret;
			
						if(next==null)
							return null;
						if((ret=next.get())==null)
							next = null;
						return ret;
					}
	
					@Override
					public void set(T target)
					{
						if(target==null)
							clear();
						else
							next = nextFactory.instance(target);
					}
	
					@Override
					public void clear()
					{
						next = null;
					}
				};
			}
		},
		VOLATILE
		{
			@Override
			public <T> InternalRef<T>  instance(final InternalRefFactory nextFactory, final T target)
			{
				requireNonNull(nextFactory, "nextFactory");
				return new InternalRef<T>()
				{
					private volatile InternalRef<T> next = getNext(nextFactory, target);
	
					@Override
					public T get()
					{
						T ret;
	
						if(next==null)
							return null;
						if((ret=next.get())==null)
							next = null;
						return ret;
					}
	
					@Override
					public void set(T target)
					{
						if(target == null)
							clear();
						else
							next = nextFactory.instance(target);
					}
	
					@Override
					public void clear()
					{
						next = null;
					}
				};
			}
		},
		FINAL
		{
			@Override
			public <T> InternalRef<T>  instance(final InternalRefFactory nextFactory, final T target)
			{
				if(nextFactory==null)
					throw new NullPointerException();
				return new InternalRef<T>()
				{
					private final InternalRef<T> next = getNext(nextFactory, target);
	
					@Override
					public T get()
					{
						if(next==null)
							return null;
						return next.get();
					}
	
					@Override
					public void set(T target)
					{
						if(target != null)
							throw new UnsupportedOperationException();
						clear();
					}
	
					@Override
					public void clear()
					{
						if(next != null)
							next.clear();
					}
				};
			}
		};

		static private <T> InternalRef<T> getNext(final InternalRefFactory nextFactory, final T target)
		{
			return target==null ? null : nextFactory.instance(target);
		}
	}
	
	public enum ReferenceType implements InternalRefFactory
	{
		STRONG
		{
			@Override
			public <T> InternalRef<T> instance(final T target)
			{
				if(target == null)
					return null;
				return new InternalRef<T>()
				{
					private T internalTarget = target;

					@Override
					public T get()
					{
						return internalTarget;
					}

					@Override
					public void set(T target)
					{
						throw new UnsupportedOperationException();
					}

					@Override
					public void clear()
					{
						internalTarget = null;
					}
				};
			}
		},
		SOFT
		{
			@Override
			public <T> InternalRef<T> instance(T target)
			{
				if(target == null)
					return null;
				return new Ref<T>(new SoftReference<T>(target));
			}
		},
		WEAK
		{
			@Override
			public <T> InternalRef<T> instance(T target)
			{
				if(target == null)
					return null;
				return new Ref<T>(new WeakReference<T>(target));
			}
		},
		PHANTOM
		{
			@Override
			public <T> InternalRef<T> instance(T target)
			{
				if(target == null)
					return null;
				// FIXME
				throw new UnsupportedOperationException();
				//return new Ref<T>(new PhantomReference<T>(target));
			}
		};

		private  static class Ref<T> implements InternalRef<T>
		{
			private final Reference<T> targetRef;

			Ref(Reference<T> targetRef)
			{
				if(targetRef==null)
					throw new NullPointerException();
				this.targetRef = targetRef;
			}
	
			@Override
			public T get()
			{
				return targetRef.get();
			}
	
			@Override
			public void set(T target)
			{
				throw new UnsupportedOperationException();
			}

			@Override
			public void clear()
			{
				targetRef.clear();
			}
		}
	}

	public static class Support
	{
		private Support()
		{
		}

		public static <T> InternalRef<T> instance(ModifierType modType, ReferenceType refType, T target)
		{
			requireNonNull(modType, "modType");
			requireNonNull(refType, "refType");
			return modType.instance(refType, target);
		}
	}


	/*
	public abstract T get();
	//public boolean isAccessible();
	public abstract void set(T target);
	//public boolean isMutable();
	public abstract void clear();
	//public boolean isClearable();
	*/

	/*
	private static final <T> T throwOnNull(T target)
	{
		if(target == null)
			throw new NullPointerException();
		return target;
	}

	public interface Validator<T>
	{
		T throwUnlessValid(T target);
		boolean isValid(T target);

		public abstract class Abstract<T> implements Validator<T>
		{
			@Override
			public T throwUnlessValid(T target)
			{
				if(isValid(target))
					return target;
				throw new IllegalArgumentException();
			}
		}

		public class AlwaysValid<T> implements Validator<T>
		{
			private static final AlwaysValid<?> SINGLETON = new AlwaysValid<?>();

			private AlwaysValid()
			{
			}

			public static final AlwaysValid<T> instance()
			{
				return SINGLETON;
			}

			@Override
			public T throwUnlessValid(T target)
			{
				return target;
			}

			@Override
			public boolean isValid(T target)
			{
				return true;
			}
		}

		public class NotNull<T> implements Validator<T>
		{
			private static final NotNull<?> SINGLETON = new NotNull<?>();

			private NotNull()
			{
			}

			public static final NotNull<T> instance()
			{
				return SINGLETON;
			}

			@Override
			public T throwUnlessValid(T target)
			{
				if(target == null)
					throw new NullPointerException();
				return target;
			}

			@Override
			public boolean isValid(T target)
			{
				return target!=null;
			}
		}
	}

	public static enum Op
	{
		GETTABLE,
		CLEARABLE,
		SETTABLE;
	}

	private final Set<Op> ops;
	private final Validator<T> validator;

	protected InternalRef(Set<Op> ops, Validator<T> validator, T target)
	{
		if((this.validator=validator)==null)
			throw new NullPointerException("Validator cannot be null.");
		if(ops==null || ops.size()==0)
			ops=Collections.emptySet();
		else
			ops=Collections.unmodifiableSet(EnumSet.copyOf(ops));
		throwUnlessValid(target);
	}

	public T get()
	{
		if(!ops.contains(Op.GETTABLE))
			throw new UnsupportedOperationException();
		return null;
	}

	//public boolean isAccessible();

	public void set(T target)
	{
		if(!(ops.contains(SETTABLE)||(target==null&&ops.contains(CLEARABLE))))
			throw new UnsupportedOperationException();
		validator.throwUnlessValid(target);
	}

	//public boolean isMutable();

	public void clear()
	{
		if(!(ops.contains(SETTABLE)||ops.contains(CLEARABLE)))
			throw new UnsupportedOperationException();
		validator.throwUnlessValid(null);
	}

	public abstract class Strong<T> extends InternalRef<T>
	{
		protected Strong(Set<Op> ops, Validator<T> validator, T target)
		{
			super(ops,validator,target);
		}

		public class Normal<T> extends Strong<T>
		{
			private T target;

			Normal(Set<Op> ops, Validator<T> validator, T target)
			{
				super(ops, validator,target);
				this.target = target;
			}

			@Override
			public T get()
			{
				super.get();
				return target;
			}

			@Override
			public void set(T target)
			{
				super.set(target);
				this.target = target;
			}

			@Override
			public void clear()
			{
				super.clear();
				this.target = null;
			}
		}

		public class Final<T> extends Strong<T>
		{
			private final T target;

			Final(Set<Op> ops, Validator<T> validator, T target)
			{
				super(ops,validator,target);
				this.target = target;
				if(ops.contains(Op.SETTABLE))
					throw new IllegalArgumentException("Final cannot be settable.");
 				if(ops.contains(Op.CLEARABLE))
					throw new IllegalArgumentException("Final cannot be clearable.");
			}

			// FIXME: short circuit clear/set?
		}

		public abstract class Volatile<T> extends Strong<T>
		{
			private volatile T target;

			Volatile(Set<Op> ops, Validator<T> validator, T target)
			{
				super(ops,validator,targeT);
				this.target = target;
			}

			@Override
			public void set(T target)
			{
				super.set(target);
				this.target = target;
			}

			@Override
			public void clear()
			{
				super.clear();
				this.target = null;
			}
		}
	}

	public abstract class Soft<T> extends InternalRef<T>
	{
		public class Normal<T> extends Soft<T>
		{
			private SoftReference<T> targetRef;

			Normal(Set<Op> ops, Validator<T> validator, T target)
			{
				super(ops, validator,target);
				if(target==null)
					this.targetRef = null;
				else
					this.targetRef = new SoftReference<T>(target);
			}

			@Override
			public T get()
			{
				T ret;

				super.get();
				if(targetRef==null)
					return null;
				if((ret=targetRef.get())==null)
					targetRef=null;
				return ret;
			}

			@Override
			public void set(T target)
			{
				super.set(target);
				this.targetRef = new SoftReference<T>(target);
			}

			@Override
			public void clear()
			{
				super.clear();
				this.targetRef = null;
			}
		}

		public abstract class Final<T> extends Soft<T>
		{
			private final SoftReference<T> targetRef;

			Final(Set<Op> ops, Validator<T> validator, T target)
			{
				super(ops,validator,target);
				if(target==null)
					targetRef=null;
				else
					targetRef = new SoftReference<T>(target);
				if(ops.contains(Op.SETTABLE))
					throw new IllegalArgumentException("Final cannot be settable.");
			}

			@Override
			public T get()
			{
				T ret;

				super.get();
				if(targetRef==null)
					return null;
				if((ret=targetRef.get())==null)
					targetRef=null;
				return ret;
			}

			@Override
			public void set(T target)
			{
				super.set(target);
				if(target!=null)
					throw new IllegalArgumentException();
				if(targetRef==null)
					return;
				targetRef.clear();
			}

			@Override
			public void clear()
			{
				super.clear();
				if(targetRef==null)
					return;
				targetRef.clear();
			}
		}

		public abstract class Volatile<T> extends Soft<T>
		{
			private volatile SoftReference<T> targetRef;

			Normal(Set<Op> ops, Validator<T> validator, T target)
			{
				super(ops, validator,target);
				if(target==null)
					this.targetRef = null;
				else
					this.targetRef = new SoftReference<T>(target);
			}

			@Override
			public T get()
			{
				T ret;

				super.get();
				if(targetRef==null)
					return null;
				if((ret=targetRef.get())==null)
					targetRef=null;
				return ret;
			}

			@Override
			public void set(T target)
			{
				super.set(target);
				this.targetRef = new SoftReference<T>(target);
			}

			@Override
			public void clear()
			{
				super.clear();
				this.targetRef = null;
			}
		}
	}

	public abstract class Weak<T> extends InternalRef<T>
	{
		public abstract class Normal<T> extends Weak<T>
		{
		}

		public abstract class Final<T> extends Weak<T>
		{
		}

		public abstract class Volatile<T> extends Weak<T>
		{
		}
	}

	public abstract class Phantom<T> extends InternalRef<T>
	{
		public abstract class Normal<T> extends Phantom<T>
		{
		}

		public abstract class Final<T> extends Phantom<T>
		{
		}

		public abstract class Volatile<T> extends Phantom<T>
		{
		}
	}

	public abstract class Normal<T> implements InternalRef<T>
	{
	}

	public abstract class Final<T> implements InternalRef<T>
	{
		private Final
	}

	public abstract class Volatile<T> implements InternalRef<T>
	{
	}
	*/

	/* ------------------------------------*/

	/*
	public enum ProxyBaseTypes
	{
		DEFAULT,
		FINAL,
		SOFT,
		VOLATILE,
		WEAK;
	}

	static class Default<T> implements InternalRef<T>
	{
		private T target;
		
		Default(T target)
		{
			this.target = target;
		}

		@Override
		public T get()
		{
			return target;
		}

		*//*
		@Override
		public boolean isAccessible()
		{
			return true;
		}
		*//*

		@Override
		public void set(T target)
		{
			this.target = target;
		}

		@Override
		public void clear()
		{
			this.target = null;
		}

		*//*
		@Override
		public boolean isMutable()
		{
			return true;
		}
		*//*
	}

	static class Final<T> implements InternalRef<T>
	{
		private final T target;
		
		Final(T target)
		{
			this.target = target;
		}

		@Override
		public T get()
		{
			return target;
		}

		@Override
		public void set(T target)
		{
			throw new UnsupportedOperationException("Cannot alter target declared final.");
		}

		@Override
		public void clear()
		{
			throw new UnsupportedOperationException("Cannot alter target declared final.");
		}
	}

	static class Volatile<T> implements InternalRef<T>
	{
		private volatile T target;
		
		Volatile(T target)
		{
			this.target = target;
		}

		@Override
		public T get()
		{
			return target;
		}

		@Override
		public void set(T target)
		{
			this.target = target;
		}

		@Override
		public void clear()
		{
			this.target = null;
		}
	}

	static abstract class Proxy<T> implements InternalRef<T>
	{
		final InternalRef<T> target;

		Proxy(InternalRef<T> target)
		{
			if(target==null)
				throw new NullPointerException("Proxied InternalRef cannot be null.");
			this.target = target;
		}

		@Override
		public T get()
		{
			return target.get();
		}

		@Override
		public void set(T target)
		{
			this.target.set(target);
		}

		@Override
		public void clear()
		{
			target.clear();
		}
	}

	static class NonNull<T> extends Proxy<T>
	{
		NonNull(InternalRef<T> proxyRef)
		{
			super(proxyRef);
			if(proxyRef.get()==null)
				throw new NullPointerException("Proxy declared to not allow null.");
		}

		@Override
		public void set(T target)
		{
			if(target==null)
				throw new NullPointerException("Proxy declared to not allow null.");
			super.set(target);
		}

		@Override
		public void clear()
		{
			if(target==null)
				throw new NullPointerException("Proxy declared to not allow null.");
		}
	}

	static enum LangRefType
	{
		SOFT
		{
			@Override
			public <T> Reference<T> instance(T target)
			{
				return new SoftReference<T>(target);
			}

			@Override
			public <T> Reference<T> instance(T target, ReferenceQueue<? super T> q)
			{
				return new SoftReference<T>(target, q);
			}
		},
		WEAK
		{
			@Override
			public <T> Reference<T> instance(T target)
			{
				return new WeakReference<T>(target);
			}

			@Override
			public <T> Reference<T> instance(T target, ReferenceQueue<? super T> q)
			{
				return new WeakReference<T>(target, q);
			}
		},
		PHANTOM
		{
			@Override
			public <T> Reference<T> instance(T target)
			{
				throw new UnsupportedOperationException("Cannot create phantom references without a reference queue to associate.");
			}

			@Override
			public <T> Reference<T> instance(T target, ReferenceQueue<? super T> q)
			{
				return new PhantomReference<T>(target, q);
			}
		};

		public abstract <T> Reference<T> instance(T target);

		public abstract <T> Reference<T> instance(T target, ReferenceQueue<? super T> q);
	}

	static class LangRef<T> extends Proxy<T>
	{
		final InternalRef<Reference<T>> targetRef;

		LangRef(LangRefType type, InternalRef<Reference<T>> targetRef)
		{
			if(target==null)
				throw new NullPointerException("Proxied InternalRef cannot be null.");
			this.target = target;
		}

		@Override
		public T get()
		{
			return target.get();
		}

		@Override
		public void set(T target)
		{
			this.target.set(target);
		}

		@Override
		public void clear()
		{
			target.clear();
		}
	}
	*/

	/*
	static abstract class BaseLangRef<T> implements InternalRef<T>
	{
		private final LangRefType type;

		public BaseLangRef(LangRefType type)
		{
			if(type==null)
				throw new NullPointerException("Type cannot be null.");
			this.type=type;
		}

		protected abstract Reference<T> getRef();
		protected abstract void setRef(Reference<T> ref);

		@Override
		public T get()
		{
			Reference<T> ref = getRef();

			if(ref==null)
				return null;
			return ref.get();
		}

		@Override
		public void set(T target)
		{
			if(target==null)
				setRef(null);
			else
				setRef(type.instance(target));
		}

		@Override
		public void clear()
		{
			setRef(null);
		}
	}

	static class LangRef<T> extends BaseLangRef<T>
	{
		private Reference<T> ref;

		public LangRef(LangRefType type, T target)
		{
			super(type);
			if(target==null)
				ref=null;
			else
				ref=type.instance(target);
		}

		@Override
		protected Reference<T> getRef()
		{
			return ref;
		}

		@Override
		protected void setRef(Reference<T> ref)
		{
			this.ref = ref;
		}
	}

	static class FinalLangRef<T> extends BaseLangRef<T>
	{
		private final Reference<T> ref;

		public FinalLangRef(LangRefType type, T target)
		{
			super(type);
			if(target==null)
				ref=null;
			else
				ref=type.instance(target);
		}

		@Override
		protected Reference<T> getRef()
		{
			return ref;
		}

		@Override
		protected void setRef(Reference<T> ref)
		{
			throw new UnsupportedOperationException("Reference configured to be unsettable.");
		}

		@Override
		public void set(T target)
		{
			throw new UnsupportedOperationException("Reference configured to be unsettable.");
		}

		@Override
		public void clear()
		{
			if(ref==null)
				return;
			ref.clear();
		}
	}

	static class VolatileLangRef<T> extends BaseLangRef<T>
	{
		private volatile Reference<T> ref;

		public VolatileLangRef(LangRefType type, T target)
		{
			super(type);
			if(target==null)
				ref=null;
			else
				ref=type.instance(target);
		}

		@Override
		protected Reference<T> getRef()
		{
			return ref;
		}

		@Override
		protected void setRef(Reference<T> ref)
		{
			this.ref = ref;
		}
	}
	*/
}
