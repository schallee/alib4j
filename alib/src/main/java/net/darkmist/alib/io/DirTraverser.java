package net.darkmist.alib.io;

import java.io.File;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirTraverser
{
	private static final Class<DirTraverser> CLASS = DirTraverser.class;
	@SuppressWarnings("unused")
	private static final String CLASS_NAME = CLASS.getName();
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(CLASS);

	private FileHandler fileHandler;
	private Queue<File> frontier;

	/**
	 * Create a queue for use. This is here to ease changing of the
	 * default quque implementation.
	 * @return empty queue.
	 */
	private static final Queue<File> newQueue()
	{
		return new LinkedList<File>();
	}

	private static final Queue<File> newQueue(File initial)
	{
		Queue<File> ret = newQueue();

		ret.add(initial);
		return ret;
	}

	public DirTraverser(Queue<File> frontier, FileHandler fileHandler)
	{
		if(frontier == null)
			frontier = newQueue();
		this.frontier = frontier;
		this.fileHandler = fileHandler;
	}

	public DirTraverser(File start, FileHandler fileHandler)
	{
		this(newQueue(start), fileHandler);
	}

	protected void onFile(File file)
	{
		if(fileHandler != null)
			fileHandler.handleFile(file);
	}

	private void onRawFile(File file)
	{
		if(file.isDirectory())
			frontier.add(file);
		else
			onFile(file);
	}

	private static <T> T[] sort(T...a)
	{
		Arrays.sort(a);
		return a;
	}

	public void run()
	{
		File dir;

		try
		{
			while((dir = frontier.remove())!=null)
			{
				if(dir.isDirectory())
				{
					File[] files = dir.listFiles();
					if(files != null)
						for(File file : sort(dir.listFiles()))
							onRawFile(file);
				}
				else
					onFile(dir);
			}
		}
		catch(NoSuchElementException ignored)
		{
			// we're done
		}
	}
}
