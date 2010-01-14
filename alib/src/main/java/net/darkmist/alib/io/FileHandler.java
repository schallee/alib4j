package net.darkmist.alib.io;

import java.io.File;

/**
 * Interface for handling {@link File}s.
 */
public interface FileHandler
{
	/**
	 * handle the given File in some manner.
	 * @param file The file to handle.
	 */
	public void handleFile(File file);
}
