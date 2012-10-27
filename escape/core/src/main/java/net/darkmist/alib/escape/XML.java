package net.darkmist.alib.escape;

import java.io.IOException;

public class XML extends Escaper.Abstract
{
	private static final XML SINGLETON = new XML();
	private static final StrMaker MAKER = XMLEntityMaker.instance();
 
	protected XML()
	{
	}

	public static XML instance()
	{
		return SINGLETON;
	}

	@Override
	public Appendable escape(Appendable appendable, int ch) throws IOException
	{
		switch(ch)
		{
			case 'a':
			case 'b':
			case 'c':
			case 'd':
			case 'e':
			case 'f':
			case 'g':
			case 'h':
			case 'i':
			case 'j':
			case 'k':
			case 'l':
			case 'm':
			case 'n':
			case 'o':
			case 'p':
			case 'q':
			case 'r':
			case 's':
			case 't':
			case 'u':
			case 'v':
			case 'w':
			case 'x':
			case 'y':
			case 'z':
			case 'A':
			case 'B':
			case 'C':
			case 'D':
			case 'E':
			case 'F':
			case 'G':
			case 'H':
			case 'I':
			case 'J':
			case 'K':
			case 'L':
			case 'M':
			case 'N':
			case 'O':
			case 'P':
			case 'Q':
			case 'R':
			case 'S':
			case 'T':
			case 'U':
			case 'V':
			case 'W':
			case 'X':
			case 'Y':
			case 'Z':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				return appendable.append((char)ch);
		}
		if(ch < 0)
			throw new IllegalArgumentException("Negative code point " + ch);
		return MAKER.appendStr(appendable, ch);
	}
}
