package net.darkmist.alib.spring;

public interface MainBean extends Runnable
{
	public void setArgs(String[] args, int off, int len);
	public int getExitCode();
}
