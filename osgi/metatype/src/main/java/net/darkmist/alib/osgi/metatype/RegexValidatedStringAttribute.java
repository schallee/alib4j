package net.darkmist.alib.osgi.metatype;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.osgi.service.metatype.AttributeDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class RegexValidatedStringAttribute implements AttributeDefinition
{
	private static final Class<RegexValidatedStringAttribute> CLASS = RegexValidatedStringAttribute.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private final String name;
	private final String id;
	private final String desc;
	private final int card;
	private final String[] defaults;
	private final Validator validator;

	public RegexValidatedStringAttribute(String name, String id, String desc, int card, String[] defaults, Pattern validationPattern)
	{
		this.name = name;
		this.id = id;
		this.desc = desc;
		this.card = card;
		this.defaults = defaults;
		this.validator = Validators.getRegexValidator(validationPattern);
	}

	public RegexValidatedStringAttribute(String name, String id, String desc, int card, String[] defaults, String validationPattern)
	{
		this(name, id, desc, card, defaults, Pattern.compile(validationPattern));
	}

	@Override
	public String getName()
	{
		logger.debug("getName()=>{}", name);
		return name;
	}

	@Override
	public String getID()
	{
		logger.debug("getID()=>{}", id);
		return id;
	}

	@Override
	public String getDescription()
	{
		logger.debug("getDescription()=>{}", desc);
		return desc;
	}

	@Override
	public int getCardinality()
	{
		if(logger.isDebugEnabled())
			logger.debug("getCardinality()=>{}", card);
		return card;
	}

	@Override
	public int getType()
	{
		return AttributeDefinition.STRING;
	}

	@Override
	public String[] getOptionValues()
	{
		return null;
	}

	@Override
	public String[] getOptionLabels()
	{
		return null;
	}

	@Override
	public String validate(String value)
	{
		return validator.validate(value);
	}

	@Override
	public String[] getDefaultValue()
	{
		if(logger.isDebugEnabled())
			logger.debug("getDefaultvalue()=>{}", Arrays.toString(defaults));
		if(defaults==null || defaults.length==0)
			return defaults;
		return Arrays.copyOf(defaults, defaults.length);
	}
}
