package net.darkmist.alib.escape;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

class StrCache
{
	private class StrRef extends WeakReference<String>
	{
		private final int ch;

		StrRef(int ch, String str, ReferenceQueue queue)
		{
			super(str, queue);
			this.ch = ch;
		}

		int getChar()
		{
			return ch;
		}
	}

	private ReferenceQueue<String> queue = new ReferenceQueue();
	private Map<Integer,Object> map = new HashMap<Integer,Object>();
	private StrMaker maker;

	StrCache(StrMaker maker)
	{
		this.maker = maker;
	}

	private void clean()
	{
		Reference<? extends String> ref;

		while((ref = queue.poll()) != null)
		{
			if(!(ref instanceof StrRef))
				throw new IllegalStateException("Reference queue returned reference not of type StrRef but " + ref.getClass() + '.');
			map.remove(((StrRef)ref).getChar());
		}
	}

	public synchronized void put(Integer ch, String str)
	{
		clean();
		map.put(ch, str);
	}

	public void putInterned(Integer ch, String str)
	{
		put(ch, str.intern());
	}

	public void putInterned(Integer ch)
	{
		put(ch, maker.makeStr(ch).intern());
	}

	private void putWeakNoExistingCheck(Integer ch, String str)
	{
		map.put(ch, new StrRef(ch, str, queue));
	}

	public synchronized void putWeak(Integer ch, String str)
	{
		if(get(ch) != str)
			putWeakNoExistingCheck(ch, str);
	}

	public synchronized void putWeak(Integer ch)
	{
		if(get(ch) == null)
			putWeakNoExistingCheck(ch, maker.makeStr(ch));
	}

	public synchronized String get(Integer ch)
	{
		Object o;
		String str;

		clean();
		o = map.get(ch);
		if(o == null)
			return null;
		if(o instanceof String)
			return (String)o;
		if(!(o instanceof StrRef))
			throw new IllegalStateException("Cache's map value for character " + ch + " was neither String nor StrRef but " + o.getClass() + '.');
		if((str=((StrRef)o).get())==null)
		{
			map.remove(ch);
			return null;
		}
		return str;
	}

	public synchronized String getOrMake(Integer ch)
	{
		String str;

		// already a clean in get
		if((str = get(ch))!=null)
			return str;
		str = maker.makeStr(ch);
		putWeakNoExistingCheck(ch, str);
		return str;
	}
}
