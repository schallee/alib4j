package net.darkmist.alib.spring;

import org.springframework.context.support.FileSystemXmlApplicationContext;


public class Main
{
	private static final Class CLASS = Main.class;
	private static final String CLASS_NAME = CLASS.getName();
	private static final int ERROR_EXIT = 1;
	private static final String MAIN_BEAN = "main";

	private static void usage()
	{
		System.err.println("Usage: " + CLASS_NAME + " spring-config [args]");
		System.exit(ERROR_EXIT);
	}

	public static void main(String[] args)
	{
		FileSystemXmlApplicationContext ctx;
		Runnable mb;
		boolean doExit = false;
		int exitCode = 0;
		
		if(args.length < 1)
			usage();
		ctx = new FileSystemXmlApplicationContext(args[0]);
		mb = (Runnable)ctx.getBean(MAIN_BEAN);
		if(mb instanceof MainBean)
			((MainBean)mb).setArgs(args, 1, args.length-1);
		else if(args.length > 1)
			throw new IllegalArgumentException("main bean does not take arguments");
		mb.run();
		if(mb instanceof MainBean)
		{
			exitCode = ((MainBean)mb).getExitCode();
			doExit = true;
		}
		mb = null;
		ctx.close();
		if(doExit)
			System.exit(exitCode);
	}
}
