package com.wutka.jox;

import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class JOXSAXBeanInput extends HandlerBase
{
/** The date formatter used to parse dates */
    protected static SAXParserFactory factory = SAXParserFactory.newInstance();

    protected static final Object SKIP = new Object();

    protected Object rootObject;

    protected Stack obStack;

    public void readObject(Object ob, InputSource source)
        throws IOException
    {
        try
        {
            SAXParser parser = factory.newSAXParser();

            rootObject = ob;

            obStack = new Stack();

            parser.parse(source, this);
        }
        catch (SAXException exc)
        {
            throw new IOException(
                "Error parsing XML document: "+exc.toString());
        }
        catch (ParserConfigurationException exc)
        {
            throw new IOException(
                "Error parsing XML document: "+exc.toString());
        }
    }

    public void startDocument()
        throws SAXException
    {
    }

    public void startElement(String elementName, AttributeList attributes)
        throws SAXException
    {
		if (obStack.isEmpty())
		{
			try
			{
				BeanInfo info = Introspector.getBeanInfo(rootObject.getClass(),
					Object.class);

				ObjectInfo newObjectInfo = new ObjectInfo(rootObject, info);
				obStack.push(newObjectInfo);

				processAttributes(newObjectInfo, attributes);
				return;
			}
			catch (IntrospectionException exc)
			{
				throw new SAXException(
					"Error getting bean info for "+
					rootObject.getClass().getName()+
					": "+exc.toString(), exc);
			}
		}

        Object stackOb = obStack.peek();

        if (stackOb == SKIP)
		{
			obStack.push(SKIP);
			return;
		}
		else if (stackOb instanceof ObjectProperty)
		{
			obStack.push(SKIP);
			return;
		}
		else if (stackOb instanceof StringBuffer)
		{
			obStack.push(SKIP);
			return;
        }

        ObjectInfo obInfo = (ObjectInfo) stackOb;

        Object ob = obInfo.ob;
        BeanInfo info = obInfo.beanInfo;

        PropertyDescriptor[] props = info.getPropertyDescriptors();

        for (int i=0; i < props.length; i++)
        {
            if (namesMatch(props[i].getName(), elementName))
			{

				Class newObClass = props[i].getPropertyType();
				if (newObClass.isArray())
				{
					newObClass = newObClass.getComponentType();
				}

				Object newOb = null;

				if ((props[i] instanceof IndexedPropertyDescriptor) ||
                    isArray(props[i]))
				{
					Vector v = obInfo.getIndexedProperty(props[i].getName());
					
					if (!isSimpleType(props[i]))
					{
						try
						{
							newOb = newObClass.newInstance();
						}
						catch (Exception exc)
						{
							continue;
						}

						v.addElement(newOb);
					}
				}
				else if (!isSimpleType(props[i]))
				{
					Method setter = props[i].getWriteMethod();
					if (setter == null)
					{
						obStack.push(SKIP);
						return;
					}
					try
					{
						newOb = newObClass.newInstance();

						setter.invoke(ob, new Object[] { newOb });
					}
					catch (InstantiationException exc)
					{
						throw new SAXException(
							"Error setting property "+
							props[i].getName()+": "+exc.toString(), exc);
					}
					catch (InvocationTargetException exc)
					{
						throw new SAXException(
							"Error setting property "+
							props[i].getName()+": "+exc.toString(), exc);
					}
					catch (IllegalAccessException exc)
					{
						throw new SAXException(
							"Error setting property "+
							props[i].getName()+": "+exc.toString(), exc);
					}
				}

				if (isSimpleType(props[i]))
				{
                                        if ( props[i].getPropertyType().equals(Date.class) ) {

                                            String fmt = attributes.getValue("format");
                                            if (null != fmt)
                                                obStack.push( new SimpleDateFormat(fmt) );
                                        }

					Vector v = null;
					if ((props[i] instanceof IndexedPropertyDescriptor) ||
                        isArray(props[i]))
					{
						v = obInfo.getIndexedProperty(props[i].getName());
					}

					ObjectProperty obProp = new ObjectProperty(
						ob, props[i], v);


					obStack.push(obProp);
					return;
				}
				else
				{
					try
					{
						ObjectInfo newObjectInfo = new ObjectInfo(newOb, 
							Introspector.getBeanInfo(newOb.getClass(),
							Object.class));
						processAttributes(newObjectInfo, attributes);
						obStack.push(newObjectInfo);
					}
					catch (IntrospectionException exc)
					{
						throw new SAXException(
							"Error getting bean info for "+
								newOb.getClass().getName()+
								": "+exc.toString(), exc);
					}
					return;
				}
			}
		}
		obStack.push(SKIP);
    }

	public void processAttributes(ObjectInfo objectInfo,
		AttributeList attributes)
		throws SAXException
	{
		int len = attributes.getLength();

		PropertyDescriptor[] props = objectInfo.beanInfo.
			getPropertyDescriptors();

		for (int i=0; i < len; i++)
		{
			for (int j=0; j < props.length; j++)
			{



				if (namesMatch(props[j].getName(), attributes.getName(i)))
				{
					if (props[j] instanceof IndexedPropertyDescriptor)
					{
						Vector v = objectInfo.getIndexedProperty(
							props[j].getName());
						
						Object obValue = getObjectValue(props[j],
							attributes.getValue(i));

						if (obValue != null)
						{
							v.addElement(obValue);
						}
					}
					else
					{
// Get the method used to set this property
						Method setter = props[j].getWriteMethod();

// If this object has no setter, don't bother writing it
						if (setter == null) break;

// Get the value of the property
		                Object obValue = getObjectValue(props[j],
							attributes.getValue(i));

		                if (obValue != null)
						{
							try
							{
// Set the property value
								setter.invoke(objectInfo.ob,
									new Object[] { obValue } );
							}
							catch (InvocationTargetException exc)
							{
								throw new SAXException(
									"Error setting property "+
									props[j].getName()+": "+exc.toString(), exc);
							}
							catch (IllegalAccessException exc)
							{
								throw new SAXException(
									"Error setting property "+
									props[j].getName()+": "+exc.toString(), exc);
							}
		                }
					}
				}
            }
        }
	}

	public void characters(char[] ch, int start, int length)
	{
		if (obStack.isEmpty()) return;

		Object ob = obStack.peek();

		if (ob instanceof StringBuffer)
		{
			((StringBuffer) ob).append(ch, start, length);
		}
		else if (ob instanceof ObjectProperty)
		{
			StringBuffer buff = new StringBuffer();
			buff.append(ch, start, length);
			obStack.push(buff);
		}
	}

	public void endElement(String elementName)
		throws SAXException
	{
		Object ob = obStack.pop();

		if (ob instanceof StringBuffer)
		{
			String valueStr = ob.toString();

			ObjectProperty obProp = (ObjectProperty) obStack.pop();

            Object obValue = getObjectValue(obProp.prop, valueStr);

			if ((obValue != null) && (obProp.v != null))
			{
				obProp.v.addElement(obValue);

				return;
			}

// Get the method used to set this property
			Method setter = obProp.prop.getWriteMethod();

// If this object has no setter, don't bother writing it
			if (setter == null) return;

// Get the value of the property

            if (obValue != null)
			{
				try
				{
// Set the property value
					setter.invoke(obProp.ob,
						new Object[] { obValue } );
				}
				catch (InvocationTargetException exc)
				{
					throw new SAXException(
						"Error setting property "+
						obProp.prop.getName()+": "+exc.toString(), exc);
				}
				catch (IllegalAccessException exc)
				{
					throw new SAXException(
						"Error setting property "+
						obProp.prop.getName()+": "+exc.toString(), exc);
				}
				catch (IllegalArgumentException exc)
				{
					throw new SAXException(
						"Error setting property "+
						obProp.prop.getName()+": "+exc.toString(), exc);
				}
            }
		}
		else if (ob instanceof ObjectProperty)
		{
			return;
		}
		else if (ob instanceof ObjectInfo)
		{
			ObjectInfo obInfo = (ObjectInfo) ob;

			if (obInfo.indexedProperties != null)
			{
				PropertyDescriptor[] props =
					obInfo.beanInfo.getPropertyDescriptors();

				for (int i=0; i < props.length; i++)
				{
					if (!(props[i] instanceof IndexedPropertyDescriptor) &&
                        !isArray(props[i]))
					{
						continue;
					}

					Vector v = (Vector) obInfo.indexedProperties.get(
						props[i].getName());

					if (v == null) continue;

					Method setter = props[i].getWriteMethod();

					if (setter == null) continue;

					Class type = props[i].getPropertyType();
					if (type.isArray())
					{
						type = type.getComponentType();
					}

                    Object obs;

                    if (!isSimpleType(props[i]))
                    {
					    obs = (Object[]) Array.newInstance(
						    type, v.size());
					    v.copyInto((Object[]) obs);
                    }
                    else
                    {
                        int len = v.size();
                        obs = Array.newInstance(type, len);

                        for (int j=0; j < len; j++)
                        {
                            Array.set(obs, j, v.elementAt(j));
                        }
                    }

					try
					{
						setter.invoke(obInfo.ob, new Object[] { obs } );
					}
					catch (InvocationTargetException exc)
					{
						throw new SAXException(
							"Error setting property "+
						props[i].getName()+": "+exc.toString(), exc);
					}
					catch (IllegalAccessException exc)
					{
						throw new SAXException(
							"Error setting property "+
							props[i].getName()+": "+exc.toString(), exc);
					}
				}
			}
		}
	}

    public Object getObjectValue(PropertyDescriptor desc,
        String value)
        throws SAXException
    {
// Find out what kind of property it is
        Class type = desc.getPropertyType();

// If it's an array, get the base type 
        if (type.isArray())
        {
            type = type.getComponentType();
        }

// For native types, object wrappers for natives, and strings, use the
// basic parse routine
        if (type.equals(Integer.TYPE) ||
            type.equals(Long.TYPE) ||
            type.equals(Short.TYPE) ||
            type.equals(Byte.TYPE) ||
            type.equals(Boolean.TYPE) ||
            type.equals(Float.TYPE) ||
            type.equals(Double.TYPE) ||
            Integer.class.isAssignableFrom(type) ||
            Long.class.isAssignableFrom(type) ||
            Short.class.isAssignableFrom(type) ||
            Byte.class.isAssignableFrom(type) ||
            Boolean.class.isAssignableFrom(type) ||
            Float.class.isAssignableFrom(type) ||
            Double.class.isAssignableFrom(type))
        {
            return parseBasicType(type, value);
        }
        else if (String.class.isAssignableFrom(type))
        {
            return value;
        }
        else if (java.util.Date.class.isAssignableFrom(type))
        {
            Object ob = obStack.peek();
            if (ob instanceof DateFormat) {
                DateFormat fmt = (DateFormat)obStack.pop();
                return parseDate( value, fmt );
            } else {
                return parseDate( value, JOXDateHandler.determineDateFormat() );
            }
        }
        else
        {
            return null;
        }
    }

    public Object readBasicType(Class type, Element element)
    {
// Get the text corresponding to this element
        String str = getElementString(element);

// If there isn't any, don't parse
        if (str == null) return null;

// If it's a string, don't need to do anything else
        if (String.class.isAssignableFrom(type)) return str;

        return parseBasicType(type, str);
    }

    public Object parseBasicType(Class type, String str)
    {
// Parse the text based on the property type

        if (type.equals(Integer.TYPE) ||
            Integer.class.isAssignableFrom(type))
        {
            return new Integer(str);
        }
        else if (type.equals(Long.TYPE) ||
            Long.class.isAssignableFrom(type))
        {
            return new Long(str);
        }
        else if (type.equals(Short.TYPE) ||
            Short.class.isAssignableFrom(type))
        {
            return new Short(str);
        }
        else if (type.equals(Byte.TYPE) ||
            Byte.class.isAssignableFrom(type))
        {
            return new Byte(str);
        }
        else if (type.equals(Boolean.TYPE) ||
            Boolean.class.isAssignableFrom(type))
        {
            return new Boolean(str);
        }
        else if (type.equals(Float.TYPE) ||
            Float.class.isAssignableFrom(type))
        {
            return new Float(str);
        }
        else if (type.equals(Double.TYPE) ||
            Double.class.isAssignableFrom(type))
        {
            return new Double(str);
        }

        return null;
    }

    public Object readDate(Element element)
        throws SAXException
    { 
// Get the text corresponding to this element
        String str = getElementString(element);
        String fmt = element.getAttribute("format");
		 		
        if ( "".equals(fmt) ) {	
            return parseDate( str, JOXDateHandler.determineDateFormat() );
        } else {
            return parseDate( str, new SimpleDateFormat(fmt) );
        }
    }

    public Object parseDate(String str, DateFormat dateFormat)
        throws SAXException
    {
	
// If there isn't any, don't parse
        if (str == null) return null;

        try
        {
            return dateFormat.parse(str);
        }
        catch (ParseException exc)
        {
            throw new SAXException(
                "Error parsing date "+str, exc);
        }
    }

    public String getElementString(Element element)
    {
        NodeList nodes = element.getChildNodes();

        int numNodes = nodes.getLength();

        for (int i=0; i < numNodes; i++)
        {
            Node node = nodes.item(i);

            if (node instanceof Text)
            {
                return ((Text) node).getData();
            }
        }

        return null;
    }

/** Returns true if two names match without regard to case or the
 *  presence of '-' or '_' characters.
 * @param beanName The name of the bean property to compare
 * @param elementName The name of the element to compare
 * @return True if the names match
 */
	public boolean namesMatch(String beanName, String elementName)
	{
		int beanNameLen = beanName.length();
		int elementNameLen = elementName.length();

		int elementPos = 0;
		int beanPos = 0;

// Keep looping until you hit the end of either of the strings
		while ((elementPos < elementNameLen) && (beanPos < beanNameLen))
		{
// If the next character in the bean name is a '-' or a '/', skip it
			char beanCh = Character.toLowerCase(beanName.charAt(beanPos));
			if ((beanCh == '-') || (beanCh == '_'))
			{
				beanPos++;
				continue;
			}

// If the next character in the element name is a '-' or a '/', skip it
			char elementCh = Character.toLowerCase(
				elementName.charAt(elementPos));
			if ((elementCh == '-') || (elementCh == '_'))
			{
				elementPos++;
				continue;
			}

// If the characters don't match, the names don't match
			if (elementCh != beanCh) return false;
			elementPos++;
			beanPos++;
		}

// You hit the end of both names at the same time, the names match
		if ((elementPos == elementNameLen) && 
			(beanPos == beanNameLen))
		{
			return true;
		}

		return false;
	}

	public boolean isSimpleType(PropertyDescriptor desc)
	{
		Class type = desc.getPropertyType();

		if (type.isArray())
		{
			type = type.getComponentType();
		}

        if (type.equals(Integer.TYPE) ||
            type.equals(Long.TYPE) ||
            type.equals(Short.TYPE) ||
            type.equals(Byte.TYPE) ||
            type.equals(Boolean.TYPE) ||
            type.equals(Float.TYPE) ||
            type.equals(Double.TYPE) ||
            Integer.class.isAssignableFrom(type) ||
            Long.class.isAssignableFrom(type) ||
            Short.class.isAssignableFrom(type) ||
            Byte.class.isAssignableFrom(type) ||
            Boolean.class.isAssignableFrom(type) ||
            Float.class.isAssignableFrom(type) ||
            Double.class.isAssignableFrom(type) ||
			String.class.isAssignableFrom(type) ||
			Date.class.isAssignableFrom(type))
		{
			return true;
		}
		return false;
	}

	public boolean isArray(PropertyDescriptor desc)
	{
		Class type = desc.getPropertyType();

		return type.isArray();
    }

protected class ObjectInfo
{
    public Object ob;
    public BeanInfo beanInfo;
	public Hashtable indexedProperties;

    public ObjectInfo(Object anObject, BeanInfo theBeanInfo)
    {
        ob = anObject;
        beanInfo = theBeanInfo;
		indexedProperties = null;
    }

	public Vector getIndexedProperty(String propertyName)
	{
		if (indexedProperties == null)
		{
			indexedProperties = new Hashtable();
		}

		Vector v = (Vector) indexedProperties.get(propertyName);

		if (v == null)
		{
			v = new Vector();
			indexedProperties.put(propertyName, v);
		}

		return v;
	}
}
protected class ObjectProperty
{
	public Object ob;
	public PropertyDescriptor prop;
	public Vector v;

	public ObjectProperty(Object anObject, PropertyDescriptor aProp,
		Vector aVector)
	{
		ob = anObject;
		prop = aProp;
		v = aVector;
	}
}

}
