package net.darkmist.alib.osgi.metatype;

import java.util.regex.Pattern;

import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Attributes
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
		return new SimpleAttribute(name, id, desc, 0, AttributeDefinition.STRING, Validators.getURLValidator(), null, null, defaultVal == null ? null : new String[]{defaultVal});
	}

	public static AttributeDefinition tcpPortAttribute(String name, String id, String desc, String defaultVal)
	{
		return new SimpleAttribute(name, id, desc, 0, AttributeDefinition.INTEGER, Validators.getUDPPortValidator(), null, null, defaultVal == null ? null : new String[]{defaultVal});
	}

	public static AttributeDefinition udpPortAttribute(String name, String id, String desc, String defaultVal)
	{
		return tcpPortAttribute(name, id, desc, defaultVal);
	}

	public static AttributeDefinition inetAddressAttribute(String name, String id, String desc, String defaultVal)
	{
		return new SimpleAttribute(name, id, desc, 0, AttributeDefinition.STRING, Validators.getInetAddressValidator(), null, null, defaultVal == null ? null : new String[]{defaultVal});
	}

	public static AttributeDefinition stringAttribute(String name, String id, String desc, String defaultVal)
	{
		return new SimpleAttribute(name, id, desc, 0, AttributeDefinition.STRING, null, null, defaultVal == null ? null : new String[]{defaultVal});
	}

	public static AttributeDefinition stringRegexedAttribute(String name, String id, String desc, String defaultVal, Pattern validationPattern)
	{
		return new SimpleAttribute(name, id, desc, 0, AttributeDefinition.STRING, Validators.getRegexValidator(validationPattern), null, null, defaultVal == null ? null : new String[]{defaultVal});
	}

	public static AttributeDefinition stringRegexedAttribute(String name, String id, String desc, String defaultVal, String validationPattern)
	{
		return new SimpleAttribute(name, id, desc, 0, AttributeDefinition.STRING, Validators.getRegexValidator(validationPattern), null, null, defaultVal == null ? null : new String[]{defaultVal});
	}
}
