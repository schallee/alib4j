package net.darkmist.alib.handler;

public interface ExceptionProducing<T extends Exception>
{
	public void setExceptionHandler(Handler<T> exceptionHandler);
}
