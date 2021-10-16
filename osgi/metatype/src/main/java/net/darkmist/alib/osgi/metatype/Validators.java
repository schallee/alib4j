package net.darkmist.alib.osgi.metatype;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.net.URL;
import java.util.regex.Pattern;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
		@SuppressFBWarnings(value="SEC_SIDE_EFFECT_CONSTRUCTOR", justification="Not a good way to verify otherwise")
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
				intVal = Integer.parseInt(value);
			}
			catch(NumberFormatException e)
			{
				logger.warn("Unable to convert {} to integer value.", value, e);
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

	private static enum NonEmptyStringValidator implements Validator
	{
		SINGLETON;

		@Override
		public String validate(String value)
		{
			if(value == null)
				return "Value cannot be null.";
			if(value.isEmpty())
				return "Value cannot be empty.";
			return value;
		}
	}

	public static Validator getNonEmptyStringValidator()
	{
		return NonEmptyStringValidator.SINGLETON;
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

	private static final class IntRangeValidator implements Validator
	{
		private final int min;
		private final int max;

		IntRangeValidator(int min, int max)
		{
			this.min = min;
			this.max = max;
			if(max < min)
				throw new IllegalArgumentException("Max value " + max + " was less then min value " + min);
		}

		@Override
		public String validate(String value)
		{
			int i;

			if(value == null)
				return "Value cannot be null.";
			try
			{
				i = Integer.parseInt(value);
			}
			catch(NumberFormatException e)
			{
				return "Unable to convert value " + value + " to a valid integer.";
			}
			if(i < min)
				return "Value " + i + " is less then the minimum value " + min + '.';
			if(i > max)
				return "Value " + i + " is more than the maximum value " + max + '.';
			return "";
		}
	}

	public static Validator getIntMinValidator(int min)
	{
		return new IntRangeValidator(min, Integer.MAX_VALUE);
	}

	public static Validator getIntMaxValidator(int max)
	{
		return new IntRangeValidator(Integer.MIN_VALUE, max);
	}

	public static Validator getIntRangeValidator(int min, int max)
	{
		return new IntRangeValidator(min, max);
	}

	private static enum IntValidator implements Validator
	{
		SINGLETON;

		@Override
		public String validate(String value)
		{
			if(value == null)
				return "Value cannot be null.";
			try
			{
				Integer.parseInt(value);
			}
			catch(NumberFormatException e)
			{
				return "Unable to convert value " + value + " to a valid integer.";
			}
			return "";
		}
		
	}

	public static Validator getIntValidator()
	{
		return IntValidator.SINGLETON;
	}
}
