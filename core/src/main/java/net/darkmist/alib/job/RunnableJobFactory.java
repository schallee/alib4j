package net.darkmist.alib.job;

public interface RunnableJobFactory<T> extends JobFactory
{
	public Runnable mkJob(T arg);
}
