/*
 *  Copyright (C) 2015 Ed Schaller <schallee@darkmist.net>
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

package net.darkmist.alib.beans;

import java.beans.PropertyEditorSupport;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;
import net.darkmist.alib.lang.NullSafe;
import net.darkmist.alib.reflect.Reflections;

// FIXME: there are Char tests below that are comented out because there is not char property editors...
// FIXME: setting/getting of non-existant properties?
// FIXME: attempting to set wrong type of value?

public class BeansTest extends TestCase
{
	private static final Class<BeansTest> CLASS = BeansTest.class;
	private static final Logger logger = LoggerFactory.getLogger(CLASS);

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Bad
	{
	}

	private static class TestProperty
	{
		private String str;

		public String getStr()
		{
			return str;
		}

		public void setStr(String val)
		{
			str = val;
		}

		@Override
		public boolean equals(Object o)
		{
			if(this==o)
				return true;
			if(o==null)
				return false;
			if(!(o instanceof TestProperty))
				return false;
			TestProperty other = (TestProperty)o;
			return NullSafe.equals(getStr(), other.getStr());
		}

		@Override
		public String toString()
		{
			return str;
		}
	}

	public static class TestPropertyEditor extends PropertyEditorSupport
	{
		@Override
		public void setAsText(String str)
		{
			TestProperty tp = new TestProperty();

			tp.setStr(str);
			setValue(tp);
		}

		@Override
		public String getAsText()
		{
			TestProperty tp = (TestProperty)getValue();
			return tp.getStr();
		}
	}

	private static class TestBean
	{
		private boolean someBoolean;
		private byte someByte;
		private short someShort;
		private char someChar;
		private int someInt;
		private long someLong;
		private float someFloat;
		private double someDouble;
		private String someString;
		private TestProperty someProperty;

		public boolean getSomeBoolean()
		{
			return someBoolean;
		}

		public void setSomeBoolean(boolean someBoolean_)
		{
			this.someBoolean = someBoolean_;
		}

		public byte getSomeByte()
		{
			return someByte;
		}

		public void setSomeByte(byte someByte_)
		{
			this.someByte = someByte_;
		}

		public short getSomeShort()
		{
			return someShort;
		}

		public void setSomeShort(short someShort_)
		{
			this.someShort = someShort_;
		}

		public char getSomeChar()
		{
			return someChar;
		}

		public void setSomeChar(char someChar_)
		{
			this.someChar = someChar_;
		}

		public int getSomeInt()
		{
			return someInt;
		}

		public void setSomeInt(int someInt_)
		{
			this.someInt = someInt_;
		}

		public long getSomeLong()
		{
			return someLong;
		}

		public void setSomeLong(long someLong_)
		{
			this.someLong = someLong_;
		}

		public float getSomeFloat()
		{
			return someFloat;
		}

		public void setSomeFloat(float someFloat_)
		{
			this.someFloat = someFloat_;
		}

		public double getSomeDouble()
		{
			return someDouble;
		}

		public void setSomeDouble(double someDouble_)
		{
			this.someDouble = someDouble_;
		}

		@Property
		public String getSomeString()
		{
			return someString;
		}

		@Property
		public void setSomeString(String someString_)
		{
			this.someString = someString_;
		}

		@Property
		public TestProperty getSomeProperty()
		{
			return someProperty;
		}

		@Property
		public void setSomeProperty(TestProperty val)
		{
			someProperty=val;
		}
	}

	public void testGetInt() throws Exception
	{
		TestBean bean = new TestBean();
		Integer expected = 42;
		Integer actual;

		bean.setSomeInt(expected);
		actual = Beans.getProperty(bean, "someInt", Integer.class);
		assertEquals(actual, expected);
	}

	public void testGetIntTagged() throws Exception
	{
		try
		{
			Beans.getProperty(new TestBean(), "someInt", Integer.class, Property.class);
			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	public void testGetIntAsString() throws Exception
	{
		TestBean bean = new TestBean();
		Integer expected = 42;
		String actual;

		bean.setSomeInt(expected);
		actual = Beans.getProperty(bean, "someInt", String.class);
		assertEquals(actual, expected.toString());
	}

	public void testGetIntAsStringTagged() throws Exception
	{
		try
		{
			Beans.getProperty(new TestBean(), "someInt", String.class, Property.class);
			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	public void testGetIntAsText() throws Exception
	{
		TestBean bean = new TestBean();
		Integer expected = 42;
		String actual;

		bean.setSomeInt(expected);
		actual = Beans.getPropertyAsText(bean, "someInt");
		assertEquals(actual, expected.toString());
	}

	public void testGetIntAsTextTagged() throws Exception
	{
		try
		{
			Beans.getPropertyAsText(new TestBean(), "someInt", Property.class);
			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	public void testSetInt() throws Exception
	{
		TestBean bean = new TestBean();
		Integer expected = 42;
		Integer actual;

		Beans.setProperty(bean, "someInt", expected);
		actual = bean.getSomeInt();
		assertEquals(actual, expected);
	}

	public void testSetIntTagged() throws Exception
	{
		try
		{
			Beans.setProperty(new TestBean(), "someInt", 42, Property.class);
			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	public void testSetIntAsText() throws Exception
	{
		TestBean bean = new TestBean();
		Integer expected = 42;
		Integer actual;

		Beans.setPropertyAsText(bean, "someInt", expected.toString());
		actual = bean.getSomeInt();
		assertEquals(actual, expected);
	}

	public void testSetIntAsTextTagged() throws Exception
	{
		try
		{
			Beans.setPropertyAsText(new TestBean(), "someInt", "42", Property.class);
			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	public void testGetChar() throws Exception
	{
		TestBean bean = new TestBean();
		Character expected = 'A';
		Character actual;

		bean.setSomeChar(expected);
		actual = Beans.getProperty(bean, "someChar", Character.class);
		assertEquals(actual, expected);
	}

	public void testGetCharAsString() throws Exception
	{
		TestBean bean = new TestBean();
		Character expected = 'A';
		String actual;

		bean.setSomeChar(expected);
		actual = Beans.getProperty(bean, "someChar", String.class);
		assertEquals(actual, expected.toString());
	}

	public void testGetCharAsText() throws Exception
	{
		TestBean bean = new TestBean();
		Character expected = 'A';
		String actual;

		bean.setSomeChar(expected);
		actual = Beans.getPropertyAsText(bean, "someChar");
		assertEquals(actual, expected.toString());
	}

	public void testSetChar() throws Exception
	{
		TestBean bean = new TestBean();
		Character expected = 'A';
		Character actual;

		Beans.setProperty(bean, "someChar", expected);
		actual = bean.getSomeChar();
		assertEquals(actual, expected);
	}

	/* No built in PropertyEditor for char...
	public void testSetCharAsText() throws Exception
	{
		TestBean bean = new TestBean();
		Character expected = 'A';
		Character actual;

		Beans.setPropertyAsText(bean, "someChar", expected.toString());
		actual = bean.getSomeChar();
		assertEquals(actual, expected);
	}
	*/

	public void testGetPropAsText() throws Exception
	{
		TestBean bean = new TestBean();
		TestProperty prop = new TestProperty();
		String expected = Reflections.getMethodName() + ".str";
		String actual;

		prop.setStr(expected);
		bean.setSomeProperty(prop);
		actual = Beans.getPropertyAsText(bean, "someProperty");
		assertEquals(actual, expected);
	}

	public void testGetPropAsTextTagged() throws Exception
	{
		TestBean bean = new TestBean();
		TestProperty prop = new TestProperty();
		String expected = Reflections.getMethodName() + ".str";
		String actual;

		prop.setStr(expected);
		bean.setSomeProperty(prop);
		actual = Beans.getPropertyAsText(bean, "someProperty", Property.class);
		assertEquals(actual, expected);
	}

	public void testGetPropAsTextBadTagged() throws Exception
	{
		try
		{
			Beans.getPropertyAsText(new TestBean(), "someProperty", Bad.class);
			fail("Expected IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	public void testSetPropAsText() throws Exception
	{
		TestBean bean = new TestBean();
		String expected = Reflections.getMethodName() + ".str";
		String actual;

		Beans.setPropertyAsText(bean, "someProperty", expected);
		actual = bean.getSomeProperty().getStr();
		assertEquals(actual, expected);
	}

	public void testSetPropAsTextTagged() throws Exception
	{
		TestBean bean = new TestBean();
		String expected = Reflections.getMethodName() + ".str";
		String actual;

		Beans.setPropertyAsText(bean, "someProperty", expected, Property.class);
		actual = bean.getSomeProperty().getStr();
		assertEquals(actual, expected);
	}

	public void testSetPropAsTextBadTagged() throws Exception
	{
		String expected = Reflections.getMethodName() + ".str";

		try
		{
			Beans.setPropertyAsText(new TestBean(), "someProperty", expected, Bad.class);
			fail("Expected IllegalArgumentException");
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	public void testGetProps() throws Exception
	{
		String METH = Reflections.getMethodName();
		TestBean bean = new TestBean();
		TestProperty prop = new TestProperty();
		Map<String,?> map;

		bean.setSomeBoolean(((METH + ".bool").hashCode() & 0x1) != 0);
		bean.setSomeByte((byte)((METH + ".byte").hashCode()));
		bean.setSomeShort((short)((METH + ".short").hashCode()));
		bean.setSomeChar((char)((METH + ".char").hashCode()));
		bean.setSomeInt((METH + ".int").hashCode());
		bean.setSomeLong((long)((METH + ".long").hashCode())*Integer.MAX_VALUE);
		bean.setSomeFloat((float)((METH + ".long").hashCode())*(float)(0.001));
		bean.setSomeDouble((double)((METH + ".long").hashCode())*0.001);
		bean.setSomeString(METH + ".str");
		prop.setStr(METH + ".prop");
		bean.setSomeProperty(prop);

		map = Beans.getProperties(bean);
		logger.debug("map.keySet()={}", map.keySet());
		assertEquals(map.get("someBoolean"), bean.getSomeBoolean());
		assertEquals(map.get("someByte"), bean.getSomeByte());
		assertEquals(map.get("someShort"), bean.getSomeShort());
		assertEquals(map.get("someChar"), bean.getSomeChar());
		assertEquals(map.get("someInt"), bean.getSomeInt());
		assertEquals(map.get("someLong"), bean.getSomeLong());
		assertEquals(map.get("someFloat"), bean.getSomeFloat());
		assertEquals(map.get("someDouble"), bean.getSomeDouble());
		assertEquals(map.get("someString"), bean.getSomeString());
		assertEquals(map.get("someProperty"), bean.getSomeProperty());
		assertEquals(map.get("class"), bean.getClass());
		assertEquals(map.size(), 11);
	}

	public void testGetPropsTagged() throws Exception
	{
		String METH = Reflections.getMethodName();
		TestBean bean = new TestBean();
		TestProperty prop = new TestProperty();
		Map<String,?> map;

		bean.setSomeString(METH + ".str");
		prop.setStr(METH + ".prop");
		bean.setSomeProperty(prop);

		map = Beans.getProperties(bean,Property.class);
		logger.debug("map.keySet()={}", map.keySet());
		assertEquals(map.get("someString"), bean.getSomeString());
		assertEquals(map.get("someProperty"), bean.getSomeProperty());
		assertEquals(map.size(), 2);
	}

	public void testGetPropsBadTagged() throws Exception
	{
		@SuppressWarnings("unused")
		String METH = Reflections.getMethodName();
		Map<String,?> map;

		map = Beans.getProperties(new TestBean(),Bad.class);
		logger.debug("map.keySet()={}", map.keySet());
		assertEquals(map.size(), 0);
	}

	public void testSetProps() throws Exception
	{
		String METH = Reflections.getMethodName();
		TestBean bean = new TestBean();
		TestProperty prop = new TestProperty();
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("someBoolean", ((METH + ".bool").hashCode() & 0x1) != 0);
		map.put("someByte", (byte)((METH + ".byte").hashCode()));
		map.put("someShort", (short)((METH + ".short").hashCode()));
		map.put("someChar", (char)((METH + ".char").hashCode()));
		map.put("someInt", (METH + ".int").hashCode());
		map.put("someLong", (long)((METH + ".long").hashCode())*Integer.MAX_VALUE);
		map.put("someFloat", (float)((METH + ".long").hashCode())*(float)(0.001));
		map.put("someDouble", (double)((METH + ".long").hashCode())*0.001);
		map.put("someString", METH + ".str");
		prop.setStr(METH + ".prop");
		map.put("someProperty", prop);

		Beans.setProperties(bean, map);
		assertEquals(map.get("someBoolean"), bean.getSomeBoolean());
		assertEquals(map.get("someByte"), bean.getSomeByte());
		assertEquals(map.get("someShort"), bean.getSomeShort());
		assertEquals(map.get("someChar"), bean.getSomeChar());
		assertEquals(map.get("someInt"), bean.getSomeInt());
		assertEquals(map.get("someLong"), bean.getSomeLong());
		assertEquals(map.get("someFloat"), bean.getSomeFloat());
		assertEquals(map.get("someDouble"), bean.getSomeDouble());
		assertEquals(map.get("someString"), bean.getSomeString());
		assertEquals(map.get("someProperty"), bean.getSomeProperty());
	}

	public void testSetPropsTaggedSuccess() throws Exception
	{
		String METH = Reflections.getMethodName();
		TestBean bean = new TestBean();
		TestProperty prop = new TestProperty();
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("someString", METH + ".str");
		prop.setStr(METH + ".prop");
		map.put("someProperty", prop);

		Beans.setProperties(bean, map, Property.class);
		assertEquals(map.get("someString"), bean.getSomeString());
		assertEquals(map.get("someProperty"), bean.getSomeProperty());
	}

	public void testSetPropsTaggedFailure() throws Exception
	{
		String METH = Reflections.getMethodName();
		TestBean bean = new TestBean();
		TestProperty prop = new TestProperty();
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("someString", METH + ".str");
		prop.setStr(METH + ".prop");
		map.put("someProperty", prop);
		map.put("someBoolean", ((METH + ".bool").hashCode() & 0x1) != 0);

		try
		{
			Beans.setProperties(bean, map, Property.class);
			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	public void testSetPropsBadTaggedFailure() throws Exception
	{
		String METH = Reflections.getMethodName();
		TestBean bean = new TestBean();
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("someString", METH + ".str");

		try
		{
			Beans.setProperties(bean, map, Bad.class);
			fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	public void testSetPropsAsText() throws Exception
	{
		String METH = Reflections.getMethodName();
		TestBean bean = new TestBean();
		TestProperty prop = new TestProperty();
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> strMap = new HashMap<String,String>();

		map.put("someBoolean", ((METH + ".bool").hashCode() & 0x1) != 0);
		map.put("someByte", (byte)((METH + ".byte").hashCode()));
		map.put("someShort", (short)((METH + ".short").hashCode()));
		//map.put("someChar", (char)((METH + ".char").hashCode()));
		map.put("someInt", (METH + ".int").hashCode());
		map.put("someLong", (long)((METH + ".long").hashCode())*Integer.MAX_VALUE);
		map.put("someFloat", (float)((METH + ".long").hashCode())*(float)(0.001));
		map.put("someDouble", (double)((METH + ".long").hashCode())*0.001);
		map.put("someString", METH + ".str");
		prop.setStr(METH + ".prop");
		map.put("someProperty", prop);

		for(Map.Entry<String,Object> entry : map.entrySet())
			strMap.put(entry.getKey(), entry.getValue().toString());

		Beans.setProperties(bean, strMap);
		assertEquals(map.get("someBoolean"), bean.getSomeBoolean());
		assertEquals(map.get("someByte"), bean.getSomeByte());
		assertEquals(map.get("someShort"), bean.getSomeShort());
		//assertEquals(map.get("someChar"), bean.getSomeChar());
		assertEquals(map.get("someInt"), bean.getSomeInt());
		assertEquals(map.get("someLong"), bean.getSomeLong());
		assertEquals(map.get("someFloat"), bean.getSomeFloat());
		assertEquals(map.get("someDouble"), bean.getSomeDouble());
		assertEquals(map.get("someString"), bean.getSomeString());
		assertEquals(map.get("someProperty"), bean.getSomeProperty());
	}
}
