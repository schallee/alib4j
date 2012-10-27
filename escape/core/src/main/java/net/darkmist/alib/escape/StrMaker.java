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

package net.darkmist.alib.escape;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

interface StrMaker
{
	public String makeStr(int code);
	public Appendable appendStr(Appendable appendable, int code) throws IOException;
	public StringBuilder appendStr(StringBuilder sb, int code);
	public StringBuffer appendStr(StringBuffer sb, int code);

	abstract class Abstract implements StrMaker
	{
		private static final Class<Abstract> CLASS = Abstract.class;
		@SuppressWarnings("unused")
		private static final Logger logger = LoggerFactory.getLogger(CLASS);

		protected Abstract()
		{
		}

		@Override
		public String makeStr(int code)
		{
			return appendStr(new StringBuilder(), code).toString();
		}

		@Override
		public StringBuilder appendStr(StringBuilder sb, int code)
		{
			try
			{
				appendStr((Appendable)sb, code);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException(Util.STRING_BUILDER_IO_EXCEPTION, e);
			}
		}

		@Override
		public StringBuffer appendStr(StringBuffer sb, int code)
		{
			try
			{
				appendStr((Appendable)sb, code);
				return sb;
			}
			catch(IOException e)
			{
				throw new IllegalStateException(Util.STRING_BUFFER_IO_EXCEPTION, e);
			}
		}
	}

	// note cache is on the instance level not static!
	abstract class PreCachedSingletonAbstract extends Abstract
	{
		private static final Class<PreCachedSingletonAbstract> CLASS = PreCachedSingletonAbstract.class;
		private static final Logger logger = LoggerFactory.getLogger(CLASS);

		// cannot be final as we're not setting it in the
		// constructor
		private List<String> cache;

		protected PreCachedSingletonAbstract()
		{
			// we CANNOT call makeCache here as the extending
			// class has not had time to do it's own
			// construction yet
		}

		protected final void makeCache()
		{
			int size;
			String[] cacheArray;

			// we want the class name of the descentant so use this
			if((size = Util.getPositiveIntProp(this.getClass(), Util.getCacheSizePropName(), Util.getCacheSizeDefault()))==0)
			{
				cache = Collections.emptyList();
				return;
			}
			cacheArray = new String[size];
			for(int i=0;i<size;i++)
				cacheArray[i] = makeStrNoCache(i).intern();
			cache = Collections.unmodifiableList(Arrays.asList(cacheArray));
		}

		protected abstract Appendable appendStrNoCache(Appendable appendable, int ch) throws IOException;

		protected String makeStrNoCache(int ch)
		{
			try
			{
				return appendStrNoCache(new StringBuilder(), ch).toString();
			}
			catch(IOException e)
			{
				throw new IllegalStateException(Util.STRING_BUILDER_IO_EXCEPTION, e);
			}
		}

		@Override
		public String makeStr(int ch)
		{
			if(ch < 0)
				throw new IllegalArgumentException("Character code was less than zero");
			if(cache == null)
				logger.warn("Cache was never initialized for {}", this);
			else if(ch<cache.size())
				return cache.get(ch);
			return makeStrNoCache(ch);
		}

		@Override
		public Appendable appendStr(Appendable appendable, int ch) throws IOException
		{
			if(ch < 0)
				throw new IllegalArgumentException("Character code was less than zero");
			if(cache == null)
				logger.warn("Cache was never initialized for {}", this);
			else if(ch<cache.size())
				return appendable.append(cache.get(ch));
			return appendStrNoCache(appendable, ch);
		}
	}
}
