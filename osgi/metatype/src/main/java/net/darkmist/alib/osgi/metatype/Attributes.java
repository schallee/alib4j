package net.darkmist.alib.osgi.metatype;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.net.URL;

import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Attributes
{
	private static final Class<Attributes> CLASS = Attributes.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	private Attributes()
	{
	}

	public static boolean isValidType(int type)
	{
		switch(type)
		{
			case AttributeDefinition.BOOLEAN:
			case AttributeDefinition.BYTE:
			case AttributeDefinition.CHARACTER:
			case AttributeDefinition.DOUBLE:
			case AttributeDefinition.FLOAT:
			case AttributeDefinition.INTEGER:
			case AttributeDefinition.LONG:
			case AttributeDefinition.PASSWORD:
			case AttributeDefinition.SHORT:
			case AttributeDefinition.STRING:
				return true;
			default:
				return false;
		}
	}

	public static int validTypeOrThrow(int type)
	{
		if(!isValidType(type))
			throw new IllegalArgumentException("Type " + type + " is invalid.");
		return type;
	}

	public static String typeToString(int type)
	{
		switch(type)
		{
			case AttributeDefinition.BOOLEAN:
				return "BOOLEAN";
			case AttributeDefinition.BYTE:
				return "BYTE";
			case AttributeDefinition.CHARACTER:
				return "CHARACTER";
			case AttributeDefinition.DOUBLE:
				return "DOUBLE";
			case AttributeDefinition.FLOAT:
				return "FLOAT";
			case AttributeDefinition.INTEGER:
				return "INTEGER";
			case AttributeDefinition.LONG:
				return "LONG";
			case AttributeDefinition.PASSWORD:
				return "PASSWORD";
			case AttributeDefinition.SHORT:
				return "SHORT";
			case AttributeDefinition.STRING:
				return "STRING";
			default:
				return "INVALID";
		}
	}

	public static String filterToString(int type)
	{
		switch(type)
		{
			case ObjectClassDefinition.ALL:
				return "ALL";
			case ObjectClassDefinition.REQUIRED:
				return "REQUIRED";
			case ObjectClassDefinition.OPTIONAL:
				return "OPTIONAL";
			default:
				return "INVALID";
		}
	}

	public static AttributeDefinition urlAttribute(String name, String id, String desc, String defaultVal)
	{
		return new SimpleAttribute(name, id, desc, 0, AttributeDefinition.STRING, null, null, new String[]{defaultVal})
		{
			@Override
			public String validate(String value)
			{
				logger.debug("validate(\"{}\")", value);
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
		};
	}

	public static AttributeDefinition tcpPortAttribute(String name, String id, String desc, String defaultVal)
	{
		return new SimpleAttribute(name, id, desc, 0, AttributeDefinition.INTEGER, null, null, new String[]{defaultVal})
		{
			@Override
			public String validate(String value)
			{
				int intVal=0;

				logger.debug("validate(\"{}\")", value);

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
		};
	}

	public static AttributeDefinition udpPortAttribute(String name, String id, String desc, String defaultVal)
	{
		return tcpPortAttribute(name, id, desc, defaultVal);
	}

	public static AttributeDefinition inetAddressAttribute(String name, String id, String desc, String defaultVal)
	{
		return new SimpleAttribute(name, id, desc, 0, AttributeDefinition.STRING, null, null, new String[]{defaultVal})
		{
			@Override
			public String validate(String value)
			{
				byte[] addrBytes=null;

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
		};
	}
}
