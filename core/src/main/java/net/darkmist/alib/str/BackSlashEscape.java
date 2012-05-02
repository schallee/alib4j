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

package net.darkmist.alib.str;

// we may be able to use org.apache.commons.lang.StringEscapeUtils.escapeJava(String)

public class BackSlashEscape
{
	private static BackSlashEscape singleton = new BackSlashEscape();

	protected byte escapeChar()
	{
		return (byte)'\\';
	}

	public String escape(byte[] in)
	{
		byte escape = escapeChar();
		byte[] out = new byte[in.length * 4];
		int in_counter;
		int out_counter = 0;
		for(in_counter = 0; in_counter<in.length; in_counter++)
		{
			byte b = in[in_counter];

			if(
				('a' <= b && b <= 'z')
				|| ('A' <= b && b <= 'Z')
				|| ('0' <= b && b <= '9')
			)
			{
				out[out_counter++] = b;
				continue;
			}
			switch(b)
			{
				case ' ':
					out[out_counter++] = b;
					continue;
			}
			// all the rest will need an escape...
			out[out_counter++] = escape;
			if(b == escape)
			{
				out[out_counter++] = escape;
				continue;
			}
			switch(b)
			{
				case '\007':
					out[out_counter++] = 'a';
					continue;
				case '\b':
					out[out_counter++] = 'b';
					continue;
				case '\f':
					out[out_counter++] = 'f';
					continue;
				case '\n':
					out[out_counter++] = 'n';
					continue;
				case '\r':
					out[out_counter++] = 'r';
					continue;
				case '\t':
					out[out_counter++] = 't';
					continue;
				case '\013':
					out[out_counter++] = 'v';
					continue;
			}
			byte[] hex  = Integer.toHexString(b).getBytes();
			out[out_counter++] = 'x';
			if(hex.length == 1)
			{
				out[out_counter++] = '0';
				out[out_counter++] = hex[0];
			}
			else
			{
				out[out_counter++] = hex[0];
				out[out_counter++] = hex[1];
			}
		}
		return new String(out, 0, out_counter);
	}

	public String escape(String in)
	{
		return escape(in.getBytes());
	}

	public String escape(CharSequence in)
	{
		return escape(in.toString().getBytes());
	}

	public static BackSlashEscape getInstance()
	{
		return singleton;
	}

	protected BackSlashEscape()
	{
	}
}
