package net.darkmist.alib.osgi.metatype;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import net.darkmist.alib.lang.NullSafe;

import org.osgi.service.metatype.AttributeDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleAttribute implements AttributeDefinition
{
	private static final Class<SimpleAttribute> CLASS = SimpleAttribute.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);
	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	private final String name;
	private final String id;
	private final String desc;
	private final int card;
	private final int type;
	private final Validator validator;
	private final String[] optVals;
	private final String[] optLabels;
	private final String[] defaults;

	public SimpleAttribute(String name, String id, String desc, int card, int type, Validator validator, String[] optVals, String[] optLabels, String[] defaults)
	{
		this.name = name;
		this.id = id;
		this.desc = desc;
		this.card = card;
		this.type = Attributes.validTypeOrThrow(type);
		this.validator = validator;
		if(optVals == null)
			this.optVals = EMPTY_STRING_ARRAY;
		else
			this.optVals = Arrays.copyOf(optVals, optVals.length);
		if(optLabels == null)
			this.optLabels = EMPTY_STRING_ARRAY;
		else
			this.optLabels = Arrays.copyOf(optLabels, optLabels.length);
		if(defaults == null)
			this.defaults = EMPTY_STRING_ARRAY;
		else
			this.defaults = Arrays.copyOf(defaults, defaults.length);
	}

	public SimpleAttribute(String name, String id, String desc, int card, int type, String[] optVals, String[] optLabels, String[] defaults)
	{
		this(name, id, desc, card, type, null, optVals, optLabels, defaults);
	}

	public static class Builder
	{
		private String name;
		private String id;
		private String desc;
		private int card = 0;
		private int type = AttributeDefinition.STRING;
		private List<String> optVals = null;
		private List<String> optLabels = null;
		private List<String> defaults = null;
		private Validator validator = null;

		@Nullable
		// FIXME:
		@SuppressFBWarnings(value="PZLA_PREFER_ZERO_LENGTH_ARRAYS", justification="Requires API change")
		@SuppressWarnings("unchecked")
		private static <T> List<T> arrayToMutableList(T...array)
		{
			if(array == null)
				return null;
			return new ArrayList<T>(Arrays.asList(array));
		}

		@Nullable
		// FIXME:
		@SuppressFBWarnings(value="PZLA_PREFER_ZERO_LENGTH_ARRAYS", justification="Requires API change")
		private static String[] listToArray(List<String> strs)
		{
			if(strs == null)
				return null;
			return strs.toArray(new String[strs.size()]);
		}
		
		Builder()
		{
		}

		public static Builder instance()
		{
			return new Builder();
		}

		public SimpleAttribute build()
		{
			return new SimpleAttribute(name, id, desc, card, type, validator, listToArray(optVals), listToArray(optLabels), listToArray(defaults));
		}

		public Builder name(String name_)
		{
			this.name = name_;
			return this;
		}
	
		public Builder id(String id_)
		{
			this.id = id_;
			return this;
		}
	
		public Builder description(String desc_)
		{
			this.desc = desc_;
			return this;
		}

		public Builder cardinality(int card)
		{
			this.card = card;
			return this;
		}

		public Builder type(int type_)
		{
			this.type = Attributes.validTypeOrThrow(type_);
			return this;
		}

		public Builder optionValues(String...optVals_)
		{
			optVals = arrayToMutableList(optVals_);
			return this;
		}

		public Builder addOptionValue(String val)
		{
			if(optVals == null)
				optVals = new ArrayList<String>();
			optVals.add(val);
			return this;
		}

		public Builder optionLabels(String...optLabels_)
		{
			optLabels = arrayToMutableList(optLabels_);
			return this;
		}

		public Builder addOptionLabel(String label)
		{
			if(optLabels == null)
				optLabels = new ArrayList<String>();
			optLabels.add(label);
			return this;
		}

		public Builder addLabeledOption(String label, String val)
		{
			addOptionLabel(label);
			addOptionValue(val);
			return this;
		}

		public Builder defaults(String...defaults_)
		{
			defaults = arrayToMutableList(defaults_);
			return this;
		}

		public Builder addDefault(String def)
		{
			if(defaults == null)
				defaults = new ArrayList<String>();
			defaults.add(def);
			return this;
		}

		public Builder validator(Validator validator)
		{
			this.validator = validator;
			return this;
		}
	}

	public static Builder builder()
	{
		return new Builder();
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
		if(logger.isDebugEnabled())
			logger.debug("getType()={}", Attributes.typeToString(type));
		return type;
	}

	@Override
	@SuppressFBWarnings(value="EI_EXPOSE_REP", justification="Only exposes when array is zero length.")
	public String[] getOptionValues()
	{
		if(logger.isDebugEnabled())
			logger.debug("getOptionValues()=>{}", Arrays.toString(optVals));
		if(optVals==null || optVals.length==0)
			return optVals;
		return Arrays.copyOf(optVals, optVals.length);
	}

	@Override
	@SuppressFBWarnings(value="EI_EXPOSE_REP", justification="Only exposes when array is zero length.")
	public String[] getOptionLabels()
	{
		if(logger.isDebugEnabled())
			logger.debug("getOptionLabels()=>{}", Arrays.toString(optLabels));
		if(optLabels==null || optLabels.length==0)
			return optLabels;
		return Arrays.copyOf(optLabels, optLabels.length);
	}

	@Override
	public String validate(String value)
	{
		if(validator != null)
			return validator.validate(value);
		logger.debug("default validate(\"{}\") returning success", value);
		return "";
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
		if(!(o instanceof SimpleAttribute))
			return false;
		SimpleAttribute that = (SimpleAttribute)o;
		if(this.card!=that.card)
			return false;
		if(this.type!=that.type)
			return false;
		if(!NullSafe.equals(this.name, that.name))
			return false;
		if(!NullSafe.equals(this.id, that.id))
			return false;
		if(!NullSafe.equals(this.desc, that.desc))
			return false;
		if(!Arrays.equals(this.optVals,that.optVals))
			return false;
		if(!Arrays.equals(this.optLabels,that.optLabels))
			return false;
		if(!Arrays.equals(this.defaults,that.defaults))
			return false;
		return NullSafe.equals(this.validator, that.validator);
	}

	@Override
	public int hashCode()
	{
		return NullSafe.hashCode(
			name,
			id,
			desc,
			card,
			type,
			validator,
			Arrays.hashCode(optVals),
			Arrays.hashCode(optLabels),
			Arrays.hashCode(defaults)
		);
	}

	@Override
	public String toString()
	{
		return new StringBuilder(getClass().getSimpleName()).append(':')
			.append(" name=").append(name)
			.append(" id=").append(id)
			.append(" desc=").append(desc)
			.append(" card=").append(card)
			.append(" type=").append(type)
			.append(" validator=").append(validator)
			.append(" optVals=").append(Arrays.toString(optVals))
			.append(" optLabels=").append(Arrays.toString(optLabels))
			.append(" defaults=").append(Arrays.toString(defaults))
			.toString();
	}

}
