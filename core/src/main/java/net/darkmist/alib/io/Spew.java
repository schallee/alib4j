package net.darkmist.alib.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

public abstract class Spew
{
	private Spew()
	{
	}

	/** @deprecated Use {@link java.io.OutputStream#write(byte[])} instead */
	@Deprecated
	public static void spew(OutputStream stream, byte[] data) throws IOException
	{
		stream.write(data);
	}

	/** @deprecated Use {@link org.apache.commons.io.FileUtils#writeByteArrayToFile(java.io.File, byte[])} instead. */
	@Deprecated
	public static void spew(File file, byte[] data) throws IOException
	{
		FileUtils.writeByteArrayToFile(file,data);
		/*
		OutputStream stream = new FileOutputStream(file);

		spew(stream, data);
		stream.close();
		*/
	}

	public static void spew(String filename, byte[] data) throws IOException
	{
		FileUtils.writeByteArrayToFile(new File(filename),data);
		//spew(new File(filename), data);
	}
}
