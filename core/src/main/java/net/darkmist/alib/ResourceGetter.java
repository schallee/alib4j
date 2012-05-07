/*
 *  Copyright (C) 2012 Ed Schaller <schallee@darkmist.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package net.darkmist.alib;

import java.io.InputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Get resources from classpath. Resources are based on the full name of the
 * class requesting the resource. 
 */
public class ResourceGetter
{
	/** The class to get resources for */
	protected Class<?> src;

	// sql fixup stuff:
	// seperate vars for readablility
	// private so hopefully javac will hopefully optimize them all out...
	/** regex to match a double quoted string */
	private static final String regexp_double_quote = "\"(?:\\\\.|[^\"\\\\])*\"";
	/** regex to match a single quoted string */
	private static final String regexp_single_quote = "'(?:\\\\.|[^'\\\\])*'";
	/** regex to match a double quoted string with a length of at least one */
	private static final String regexp_double_quote_one = "\"(?:\\\\.|[^\"\\\\])+\"";	// at least one char inside
	/** regex to match a single quoted string with a length of at least one */
	private static final String regexp_single_quote_one = "'(?:\\\\.|[^'\\\\])+'";	// at least one char inside
	/** regex to match a sql comment */
	private static final String regexp_comment = "--[^\\n]*\\n";
	/** regex to match a MySQL variable */
	private static final String regexp_sqlvar = "@(?:" + regexp_double_quote_one + "|" + regexp_single_quote_one + "|[a-zA-Z0-9_\\.\\$]+)";
	/** regex to match quoted strings, comments and vars in a sql query */
	private static final String regexp_sqlfix = "(" + regexp_double_quote + "|" + regexp_single_quote + "|" + regexp_comment + "|" + regexp_sqlvar + ")";
	/** Pattern to match quoted strings, comments and vars in a sql query */
	private static final Pattern sqlfix_pat = Pattern.compile(regexp_sqlfix, Pattern.MULTILINE);

	/** Construct a resource getter for an object.
	 * @param obj Object to get resources for.
	 */
	public ResourceGetter(Object obj)
	{
		src = obj.getClass();
	}

	/** Construct a resource getter for a class.
	 * @param src_n Class to get resources for.
	 */
	public ResourceGetter(Class<?> src_n)
	{
		src = src_n;
	}

	/** Get resource by name.
	 * @param name The resource name to get
	 * @return The resource requested or null if not found.
	 */
	public String getResourceName(String name)
	{
		return getResourceNameFor(name, src);
	}

	static public String getResourceNameFor(String name, Class<?> src)
	{
		return src.getSimpleName() + "." + name;
	}

	static public String getResourceNameFor(String name, Object src)
	{
		return getResourceNameFor(name, src.getClass());
	}

	public InputStream get(String name)
	{
		return getFor(name, src);
	}

	static public InputStream getFor(String name, Class<?> src)
	{
		return src.getResourceAsStream(getResourceNameFor(name, src));
	}

	static public InputStream getFor(String name, Object src)
	{
		return getFor(name, src.getClass());
	}

	static public String getStringFor(String name, Class<?> src)
	{
		try
		{
			InputStream in;
			StringBuilder ret = new StringBuilder();
			int ch;
	
			if((in = getFor(name, src))==null)
				return null;
			while((ch = in.read()) != -1)
				ret.append((char)ch);
			return ret.toString();
		}
		catch(IOException e)
		{
			throw new IllegalStateException("Unable to get resource \"" + name + "\" for class \"" + src.getSimpleName() + "\"", e);
		}
	}

	static public String getStringFor(String name, Object src)
	{
		return getStringFor(name, src.getClass());
	}

	public String getString(String name)
	{
		return getStringFor(name, src);
	}

	public static String sqlFixup(CharSequence in)
	{
		StringBuffer out = new StringBuffer();
		Matcher matcher = sqlfix_pat.matcher(in);

		while(matcher.find())
			switch(matcher.group().charAt(0))
			{
				// replace all sql vars with ? for prepared statements...
				case '@':
					matcher.appendReplacement(out, "?");
					break;
				// nuke comments as jboss strips new lines out of queries...
				case '-':
					matcher.appendReplacement(out, "\n");
					break;
				// match quoted/double quoted strings so we don't get confused about comments/vars inside one
				case '\'':
				case '"':
				default:	// default shouldn't happen...
					matcher.appendReplacement(out, "$1");
					break;
			}
		return matcher.appendTail(out).toString();
	}

	static public String getSQLFor(String name, Class<?> src)
	{
		String sql;
	       
		if((sql	= getStringFor(name + ".sql", src))==null)
			return null;
		return sqlFixup(sql);
	}

	static public String getSQLFor(String name, Object src)
	{
		return getSQLFor(name, src.getClass());
	}
}
