package net.darkmist.alib.proxy;

public abstract class ProxyBase<T>
{
	/**
	 * Called by base class to use target for various purposes.
	 */
	protected abstract T getProxyTargetInternal();

}
/*
public interface StaticProxy<T>
{
	public interface Accessible<T> extends StaticProxy<T>
	{
		public T getTarget();
	}

	public interface Retargetable<T> extends StaticProxy<T>
	{
		public void setTarget(T target);
	}

/*
	/* ********* */
	/* Abstracts */
	/* ********* */

/*
	public static abstract class Immutable<T> implements StaticProxy<T>
	{
		protected final T target;

		protected Immutable(T target)
		{
			this.target = Objects.requireNonNull(target);
		}
	}

	public static abstract class Mutable<T> implements StaticProxy<T>
	{
		protected T target;

		protected Mutable(T target)
		{
			this.target = Objects.requireNonNull(target);
		}
	}

	public static abstract class SynchronziedMutable<T> implements StaticProxy<T>
	{
		protected volatile T target;

		protected SynchronizedMutable(T target)
		{
			this.target = Objects.requireNonNull(target);
		}
	}

	public static abstract class AccessibleImmutable<T> extends Immutable<T> implements Accessible<T>
	{
		protected AccessibleImmutable(T target)
		{
			super(target);
		}

		@Override
		public T getTarget()
		{
			return target;
		}
	}

	public static abstract class AccessibleMutable<T> extends Mutable<T> implements Accessible<T>
	{
		protected AccessibleMutable(T target)
		{
			super(target);
		}

		@Override
		public T getTarget()
		{
			return target;
		}
	}

	public static abstract class AccessibleSynchronizedMutable<T> extends SyncrhonizedMutable<T> implements Accessible<T>
	{
		protected AccessibleMutable(T target)
		{
			super(target);
		}

		@Override
		public T getTarget()
		{
			return target;
		}
	}

	public static abstract class AbstractRetargetable<T> extends Mutable<T> implements Retargetable<T>
	{
		protected AbstractRetargetable(T target)
		{
			super(target);
		}

		@Override
		public void setTarget(T target)
		{
			this.target = target;
		}
	}
*/
	/*
	public static class SQLWrapper<T> extends NonReflectiveProxy<T> implements Wrapper
	{
	}
}
	*/
