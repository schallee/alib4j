package net.darkmist.alib.io;

import java.io.Closeable;
import java.io.IOException;

public interface BitsInput extends Closeable
{
	public int readBits(int num) throws IOException;
	public boolean readBit() throws IOException;
}
