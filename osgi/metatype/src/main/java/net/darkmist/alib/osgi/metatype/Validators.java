package net.darkmist.alib.osgi.metatype;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.net.URL;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Validators
{
	private static final Class<Validators> CLASS = Validators.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private Validators()
	{
	}

	private static enum URLValidator implements Validator
	{
		SINGLETON;

		@Override
		public String validate(String value)
		{
			logger.debug("validate(\"{}\")", value);
			if(value==null)
				return "Null value is invalid.";
			try
			{
				new URL(value);
				logger.debug("validated url {}", value);
				return "";
			}
			catch(MalformedURLException e)
			{
				return e.toString();
			}
		}
	}

	public static Validator getURLValidator()
	{
		return URLValidator.SINGLETON;
	}

	private static enum TCPUDPPortValidator implements Validator
	{
		SINGLETON;

		@Override
		public String validate(String value)
		{
			int intVal=0;

			logger.debug("validate(\"{}\")", value);

			if(value==null)
				return "Null value is invalid.";
			try
			{
				intVal = Integer.valueOf(value);
			}
			catch(NumberFormatException e)
			{
				logger.warn("Unable to convert {} to integer value.", e);
				return e.toString();
			}

			if(intVal <= 0 || 65535 < intVal)
			{
				if(logger.isDebugEnabled())
					logger.debug("Port {} not in range 1 and 65535.", intVal);
				return "Port value must be between 1 and 65535.";
			}

			logger.debug("validated port {}", value);
			return "";
		}
	}

	public static Validator getTCPUDPPortValidator()
	{
		return TCPUDPPortValidator.SINGLETON;
	}

	public static Validator getTCPPortValidator()
	{
		return TCPUDPPortValidator.SINGLETON;
	}

	public static Validator getUDPPortValidator()
	{
		return TCPUDPPortValidator.SINGLETON;
	}

	private static enum InetAddressValidator implements Validator
	{
		SINGLETON;

		@Override
		public String validate(String value)
		{
			byte[] addrBytes=null;

			if(value==null)
				return "Null value is invalid.";
			try
			{
				addrBytes = InetAddress.getByName(value).getAddress();
			}
			catch(UnknownHostException e)
			{
				return e.toString();
			}

			if(addrBytes == null)
			{
				logger.debug("unable to validate address {}.", value);
				return "Unable to convert " + value + " to InetAddress.";
			}

			logger.debug("validated address {}", value);
			return "";
		}
	}

	public static Validator getInetAddressValidator()
	{
		return InetAddressValidator.SINGLETON;
	}

	public static Validator getRegexValidator(final Pattern pat)
	{
		return new Validator()
		{
			@Override
			public String validate(String value)
			{
				if(value==null)
					return "Null value is invalid.";
				if(!pat.matcher(value).matches())
					return "Value must match Java regular expression \"" + pat.pattern() + "\".";
				return "";
			}
		};
	}

	public static Validator getRegexValidator(String pattern)
	{
		return getRegexValidator(Pattern.compile(pattern));
	}
}
