package net.darkmist.alib.escape;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.zip.GZIPInputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HTMLTest extends TestCase
{
	private static final Class<HTMLTest> CLASS = HTMLTest.class;
	private static transient Reference<String> bigText;

	private HTML html = HTML.instance();

	private static String getBigText()
	{
		String ret;

		if(bigText != null && (ret = bigText.get())!=null)
			return ret;
		try
		{
			System.out.println("Reading big text.");
			BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(CLASS.getResourceAsStream("/phrack.concat.txt.gz"))));
			StringBuilder sb = new StringBuilder();
			String line;

			while((line = reader.readLine())!=null)
				sb.append(line).append('\n');
			reader.close();
			ret = sb.toString();
			sb = null;
			bigText = new WeakReference<String>(ret);
			return ret;
		}
		catch(Exception e)
		{
			if(e instanceof RuntimeException)
				throw (RuntimeException)e;
			throw new IllegalStateException(e);
		}
	}

	private static int testAndDecodeChar(String encoded)
	{
		String hex;

		assertTrue(encoded.length() >= 4);
		assertEquals('&', encoded.charAt(0));
		assertEquals('#', encoded.charAt(1));
		assertEquals(';', encoded.charAt(encoded.length()-1));
		if(encoded.charAt(2) == 'x')
			return Integer.parseInt(encoded.substring(3, encoded.length()-1), 16);
		else
			return Integer.parseInt(encoded.substring(2, encoded.length()-1), 10);
	}

	public void testTest()
	{
		assertEquals("test", html.escape("test"));
	}
	
	public void testTwoByteChars()
	{
		String encoded;
		String charStr;

		for(int i=Character.MIN_VALUE;i<=Character.MAX_VALUE;i++)
		{
			charStr = Character.toString((char)i);
			encoded = html.escape(charStr);

			if(charStr.equals(encoded))
			{
				assertTrue(Util.isAlphaNumericOrWhiteSpace(i));
				continue;
			}
			assertEquals(i, testAndDecodeChar(encoded));
		}
	}

	public void testSurrogates()
	{
		char array[] = new char[2];
		String str;
		String encoded;
		int codepoint;

		for(char high=0xD800;high<=0xDBFF;high++)
		{
			array[0] = high;
			for(char low=0xDC00;low<=0xDFFF;low++)
			{
				array[1] = low;
				str = String.valueOf(array);
				assertEquals(1, str.codePointCount(0,2));
				encoded = html.escape(str);
				codepoint = str.codePointAt(0);
				assertEquals(codepoint, testAndDecodeChar(encoded));
			}
		}
	}

	/*
	public void testBigTextSpeed()
	{
		String encoded;
		long start;
		long end;
		String bigText = getBigText();

		start = System.currentTimeMillis();
		encoded = html.escape(bigText);
		end = System.currentTimeMillis();
		System.out.println("BigText time: " + (end-start));
	}
	*/
	
	/*
	public void testBigTextThreadedSpeed() throws InterruptedException
	{
		Thread threads[] = new Thread[Runtime.getRuntime().availableProcessors()];
		long start;
		long end;
		final String text = getBigText();

		System.out.println("BigText threaded start.");
		for(int i=0;i<threads.length;i++)
		{
			threads[i] = new Thread(new Runnable()
			{
				public void run()
				{
					html.escape(text);
				}
			});
		}
		start = System.currentTimeMillis();
		for(Thread t : threads)
			t.start();
		for(Thread t : threads)
			t.join();
		end = System.currentTimeMillis();
		System.out.println("BigText threaded time: " + (end-start));
	}
	*/
}
