package net.darkmist.alib.io;

import java.io.File;
import java.util.concurrent.Executor;

import net.darkmist.alib.exception.ExceptionHandler;
import net.darkmist.alib.exception.LoggingExceptionHandler;
import net.darkmist.alib.job.RunnableJobFactory;
import net.darkmist.alib.job.CurrentThreadExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirTraverser
{
	private static final Class CLASS = DirTraverser.class;
	private static final String CLASS_NAME = CLASS.getName();
	private static final Log logger = LogFactory.getLog(CLASS);

	private Executor dirExecutor;
	private Executor fileExecutor;
	private RunnableJobFactory<File> jobFactory;

	private class DirJob implements Runnable
	{
		private File dir;

		protected void setDir(File dir_)
		{
			dir = dir_;
		}

		protected File getDir()
		{
			return dir;
		}

		protected DirJob()
		{
		}

		protected DirJob(File dir_)
		{
			setDir(dir_);
		}

		public void run()
		{
			File[] files;

			files = dir.listFiles();
			for(File file : files)
				traverse(file);
		}
	}

	public DirTraverser(Executor dirExecutor_, Executor fileExecutor_, RunnableJobFactory<File> jobFactory_)
	{
		setDirExecutor(dirExecutor_);
		setFileExecutor(fileExecutor_);
		setJobFactory(jobFactory_);
	}

	public DirTraverser(Executor executor_, RunnableJobFactory<File> jobFactory_)
	{
		this(executor_, executor_, jobFactory_);
	}

	public DirTraverser(RunnableJobFactory<File> jobFactory_)
	{
		this(CurrentThreadExecutor.instance(), jobFactory_);
	}

	protected void setDirExecutor(Executor dirExecutor_)
	{
		dirExecutor = dirExecutor_;
	}

	protected Executor getDirExecutor()
	{
		return dirExecutor;
	}

	protected void setFileExecutor(Executor fileExecutor_)
	{
		fileExecutor = fileExecutor_;
	}

	protected Executor getFileExecutor()
	{
		return fileExecutor;
	}

	protected void setJobFactory(RunnableJobFactory<File> jobFactory_)
	{
		jobFactory = jobFactory_;
	}

	protected RunnableJobFactory<File> getJobFactory()
	{
		return jobFactory;
	}

	public void traverse(File file)
	{
		if(file.isDirectory())
			getDirExecutor().execute(new DirJob(file));
		else
		{
			Runnable job;

			if((job = getJobFactory().mkJob(file))!=null)
				getFileExecutor().execute(job);
		}
	}

	private static class PrintingRunnable implements Runnable
	{
		private File file;

		PrintingRunnable(File file_)
		{
			file = file_;
		}

		public void run()
		{
			System.out.println(file.getPath());
		}
	}

	private static class PrintingRunnableFactory implements RunnableJobFactory<File>
	{
		private static final PrintingRunnableFactory singleton = new PrintingRunnableFactory();

		private PrintingRunnableFactory()
		{
		}

		static PrintingRunnableFactory instance()
		{
			return singleton;
		}

		public Runnable mkJob(File file)
		{
			return new PrintingRunnable(file);
		}
	}

	public static void main(String[] args)
	{
		DirTraverser traverser = new DirTraverser(PrintingRunnableFactory.instance());

		if(args.length == 0)
			traverser.traverse(new File("."));
		else
			for(String arg : args)
				traverser.traverse(new File(arg));
	}
}
