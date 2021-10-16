package net.darkmist.alib.osgi.metatype;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

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
		// FIXME: Can we keep from copying this? this.defaults = defaults;
		this.defaults = Arrays.copyOf(defaults, defaults.length);
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

	@Nullable
	@Override
	// FIXME:
	@SuppressFBWarnings(value="PZLA_PREFER_ZERO_LENGTH_ARRAYS", justification="Requires API change")
	public String[] getOptionValues()
	{
		return null;
	}

	@Nullable
	@Override
	// FIXME:
	@SuppressFBWarnings(value="PZLA_PREFER_ZERO_LENGTH_ARRAYS", justification="Requires API change")
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
	@SuppressFBWarnings(value="EI_EXPOSE_REP", justification="Only exposes when array is zero length.")
	public String[] getDefaultValue()
	{
		if(logger.isDebugEnabled())
			logger.debug("getDefaultvalue()=>{}", Arrays.toString(defaults));
		if(defaults==null || defaults.length==0)
			return defaults;
		return Arrays.copyOf(defaults, defaults.length);
	}

	@Override
	public boolean equals(Object o)
	{
		if(this==o)
			return true;
		if(!(o instanceof RegexValidatedStringAttribute))
			return false;
		RegexValidatedStringAttribute that = (RegexValidatedStringAttribute)o;
		if(this.card!=that.card)
			return false;
		if(!NullSafe.equals(this.name, that.name))
			return false;
		if(!NullSafe.equals(this.id, that.id))
			return false;
		if(!NullSafe.equals(this.desc, that.desc))
			return false;
		if(!Arrays.equals(this.defaults,that.defaults))
			return false;
		return NullSafe.equals(this.validator, that.validator);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(name, id, desc, card, Arrays.hashCode(defaults), validator);
	}

	@Override
	public String toString()
	{
		return new StringBuilder(getClass().getSimpleName()).append(':')
			.append(" name=").append(name)
			.append(" id=").append(id)
			.append(" desc=").append(desc)
			.append(" card=").append(card)
			.append(" defaults=").append(Arrays.toString(defaults))
			.append(" validator=").append(validator)
			.toString();
	}
}
