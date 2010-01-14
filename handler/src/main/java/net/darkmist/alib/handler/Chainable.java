package net.darkmist.alib.handler;

public interface Chainable<T>
{
	public void setNextHandler(Handler<T> next);
}
