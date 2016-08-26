package net.darkmist.net.alib.proxy;

/*

Options:

bases:

volatile,final,automic? refs?
	automic could be done using volatile and AtomicReferenceFieldUpdater
	automic could be done with AtomicReference

synchronized on proxy/synchronized on object/unsynchronized
	synchronization must be handled by each sub class...

strong/soft/weak/phantom					yes

flags for the moment:

allow null/forbid null						yes
final target/retargetable => setTarget()			no without exception throwing
accessible/inaccessable => getTarget/unwrap			no without exception throwing

~ 96 varieties
flags would have to be stored adding to overhead

public native int hashCode();
	target's hashcode, our hashcode including info from target or something elese?

public boolean equals(Object);
	equal to target or equal to proxy with target?

protected native Object clone() throws CloneNotSupportedException;
	not supported

public String toString();
	proxy for: target.toString()?

public final native Class<?> getClass();
public final native void notify();
public final native void notifyAll();
public final native void wait(long) throws InterruptedException;
public final void wait(long, int) throws InterruptedException;
public final void wait() throws InterruptedException;

protected void finalize() throws Throwable;
	do nothing

Sould the base Object methods return exactly what the target returns?
	toString()

*/
