package com.wutka.jox;

import java.io.*;
import java.text.*;
import java.lang.reflect.*;
import java.beans.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import com.wutka.dtd.*;

/** Converts a Java Bean into an XML Document. If you don't supply a
 * Document object, this class uses the DocumentBuilder to create a
 * new document. For some tools like the Xalan XSL processor, you must
 * use a specific Document implementation (the Xerces implementation).
 * If you need to use Xalan, create your own Document using the Xerces
 * API and pass it to one of the JOXBeanDOM constructors.
 *
 * @author Mark Wutka
 * @version 1.5 06/22/2000
 */

public class JOXBeanDOM extends JOXBeanOutput
{
	protected Document document;
    protected static Hashtable beanCache = new Hashtable();

/** Creates a JOXBeanDOM that stores simple properties as attributes */
	public JOXBeanDOM()
		throws IOException
	{
		this(true);
	}

/** Creates a JOXBeanDOM that stores simple properties as attributes
 * @param aDocument The document to store the bean into
 */
	public JOXBeanDOM(Document aDocument)
		throws IOException
	{
		this(aDocument, true);
	}

/** Creates a JOXBeanDOM that uses a DTD to determine which attributes
 *  to store and what the format of the names should be.
 * @param dtdURL The URL of the DTD
 */
	public JOXBeanDOM(String dtdURL)
		throws IOException
	{
        dtd = readDTD(dtdURL);
        setupNameTranslation();

		createDocument();

// writeAttributes is false here because when the DTD reader functions
// like it eventually should, we'll know which items need to be attributes
// and which need to be elements.
        writeAttributes = false;

	}

/** Creates a JOXBeanDOM that uses a DTD to determine which attributes
 *  to store and what the format of the names should be.
 * @param dtdURL The URL of the DTD
 */
	public JOXBeanDOM(DTD aDTD)
		throws IOException
	{
        dtd = aDTD;
        setupNameTranslation();

		createDocument();

// writeAttributes is false here because when the DTD reader functions
// like it eventually should, we'll know which items need to be attributes
// and which need to be elements.
        writeAttributes = false;

	}

/** Creates a JOXBeanDOM that uses a DTD to determine which attributes
 *  to store and what the format of the names should be.
 * @param dtdURL The URL of the DTD
 * @param aDocument The XML document the bean will be stored in
 */
	public JOXBeanDOM(Document aDocument, String dtdURL)
		throws IOException
	{
		document = aDocument;

        dtd = readDTD(dtdURL);
        setupNameTranslation();

// writeAttributes is false here because when the DTD reader functions
// like it eventually should, we'll know which items need to be attributes
// and which need to be elements.
        writeAttributes = false;
	}

/** Creates a JOXBeanDOM that uses a DTD to determine which attributes
 *  to store and what the format of the names should be.
 * @param dtdURL The URL of the DTD
 * @param aDocument The XML document the bean will be stored in
 */
	public JOXBeanDOM(Document aDocument, DTD aDTD)
    {
        dtd = aDTD;
        setupNameTranslation();

// writeAttributes is false here because when the DTD reader functions
// like it eventually should, we'll know which items need to be attributes
// and which need to be elements.
        writeAttributes = false;
	}

/** Creates a JOXBeanDOM that doesn't use a DTD
 * @param writeAttributesFlag True if simple properties should be stored as attributes
 */
	public JOXBeanDOM(boolean writeAttributesFlag)
		throws IOException
	{
        writeAttributes = writeAttributesFlag;

		nameTranslation = null;

		createDocument();
	}

/** Creates a JOXBeanDOM that doesn't use a DTD
 * @param aDocument The XML document the bean will be stored in
 * @param writeAttributesFlag True if simple properties should be stored as attributes
 */
	public JOXBeanDOM(Document aDocument, boolean writeAttributesFlag)
		throws IOException
	{
		document = aDocument;

        writeAttributes = writeAttributesFlag;

		nameTranslation = null;
	}

/** Creates a new Document using a DocumentBuilder */
	protected void createDocument()
		throws IOException
	{
		try
		{
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = fact.newDocumentBuilder();

			document = builder.newDocument();
		}
		catch (ParserConfigurationException exc)
		{
			throw new IOException(exc.toString());
		}
	}

/** Stores a Java bean in an XML document (this is the main method for
 *  JOXBeanDOM).
 * @param rootName The name of the document's root tag
 * @param ob The bean to store in the document
 */
    public Document beanToDocument(String rootName, Object ob)
        throws IOException
    {
		document.appendChild(storeObject(rootName, ob));

		return document;
	}

/** Stores a bean in an XML element and returns the element
 *  @param rootName The name of the element's tag
 *  @param ob The bean to store in the element
 */
	public Element storeObject(String rootName, Object ob)
        throws IOException
	{
		String tagName = getTranslatedEntityName(rootName);
		if (tagName == null) return null;

		Element element = document.createElement(tagName);

        if (writeAttributes)
        {
            storeObjectFields(tagName, ob, WRITE_AS_ATTRIBUTE, element);
        }

// Write out the properties of the object
        storeObjectFields(tagName, ob, WRITE_AS_ELEMENT, element);

		return element;
    }

/** Stores the properties of a bean as DOM Elements
 * @param xmlTagName The XML tag name of the bean being written
 * @param ob The bean whose properties will be written
 * @param writeType The kind of fields being written (attribute or element)
 * @exception IOException If there is an error writing the object
 */
    public void storeObjectFields(String xmlTagName, Object ob, int writeType,
		Element parentElement)
        throws IOException
    {
// If the object is null, don't bother writing anything
        if (ob == null) return;

        try
        {
            BeanInfo info = (BeanInfo) beanCache.get(ob.getClass());

// Get the bean info for the bean, but don't get the attributes from object
            if (info == null)
            {
                info = Introspector.getBeanInfo(ob.getClass(),
                    Object.class);

                beanCache.put(ob.getClass(), info);
            }

// Get the list of properties for this bean
            PropertyDescriptor[] props = info.getPropertyDescriptors();

            for (int i=0; i < props.length; i++)
            {
// If the property is indexed (if it represents an array of props),
// use a different routine to write it out
                if (props[i] instanceof IndexedPropertyDescriptor)
                {
                    if (writeType == WRITE_AS_ELEMENT)
                    {
                        storeIndexedProperty(xmlTagName, ob,
                            (IndexedPropertyDescriptor) props[i],
							parentElement);
                    }
                }
                else
                {
                    storeProperty(xmlTagName, ob, props[i], writeType,
						parentElement);
                }
            }
        }
        catch (IntrospectionException exc)
        {
            throw new IOException("Got introspection exception "+
                exc.toString());
        }
    }

/** Stores a non-indexed property
 * @param xmlTagName The XML tag name of the bean being written
 * @param ob The object containing the property
 * @param prop The property descriptor of the property
 * @param writeType Should we try to write this property
 *  as an attribute? If not, don't write the object if
 *  writeAttributes is true and this object can be written
 *  as an attribute
 * @throws IOException If there is an error writing out the property
 */
    public void storeProperty(String xmlTagName, Object ob,
        PropertyDescriptor prop, int writeType, Element parentElement)
        throws IOException
    {
        try
        {
// Get the method used to read the property
            Method reader = prop.getReadMethod();

// Get the property value
            Object propValue = reader.invoke(ob, getParams);

// Write the property value
            storePropertyValue(xmlTagName, propValue, prop, writeType,
				parentElement);
        }
        catch (InvocationTargetException exc)
        {
            throw new IOException("Unable to read property "+
                prop.getName()+": "+exc.toString());
        }
        catch (IllegalAccessException exc)
        {
            throw new IOException("Unable to read property "+
                prop.getName()+": "+exc.toString());
        }
    }

/** Stores an indexed property (an array of properties)
 * @param xmlTagName The XML tag name of the bean being written
 * @param ob The object containing the property
 * @param prop The descriptor for the property
 *  as an attribute? If not, don't write the object if
 *  writeAttributes is true and this object can be written
 *  as an attribute
 * @throws IOException If there is an error writing the properties
 */
    public void storeIndexedProperty(String xmlTagName, Object ob,
        IndexedPropertyDescriptor prop, Element parentElement)
        throws IOException
    {
        try
        {
// Get the method used to read the property values
            Method reader = prop.getReadMethod();

// Get the entire array of property values
            Object[] propValues = (Object[]) reader.invoke(ob, getParams);

// Write out each property value as its own element
            for (int i=0; i < propValues.length; i++)
            {
                storePropertyValue(xmlTagName, propValues[i], prop,
                    WRITE_AS_INDEXED_ELEMENT, parentElement);
            }
        }
        catch (InvocationTargetException exc)
        {
            throw new IOException("Unable to read property "+
                prop.getName()+": "+exc.toString());
        }
        catch (IllegalAccessException exc)
        {
            throw new IOException("Unable to read property "+
                prop.getName()+": "+exc.toString());
        }
    }

/** Stores a property value based on the property type
 * @param xmlTagName The XML tag name of the bean being written
 * @param propValue The value to write
 * @param prop The property descriptor of the property
 * @param writeType Should we try to write this property
 *  as an attribute? If not, don't write the object if
 *  writeAttributes is true and this object can be written
 *  as an attribute
 * @throws IOException If there is an error while writing the property
 */
    public void storePropertyValue(String xmlTagName, Object propValue,
        PropertyDescriptor prop, int writeType, Element parentElement)
        throws IOException
    {
// See what kind of object it is
        Class type = prop.getPropertyType();

// If the object is an array, find out what the base type is
        if (type.isArray() && (propValue != null))
        {
            type = type.getComponentType();

            int len = Array.getLength(propValue);

            for (int i=0; i < len; i++)
            {
                Object realValue = Array.get(propValue, i);

                storePropertyValueByType(xmlTagName, realValue, prop,
                    writeType, parentElement, type);
            }
        }
        else
        {
            storePropertyValueByType(xmlTagName, propValue, prop,
                writeType, parentElement, type);
        }
    }

    public void storePropertyValueByType(String xmlTagName, Object propValue,
        PropertyDescriptor prop, int writeType, Element parentElement, 
        Class type)
        throws IOException
    {

// If the object is a native type or the object wrapper for a native type,
// or if it is a string, use the basic output routine
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
            if (propValue != null)
            {
                storeBasicType(xmlTagName, prop.getName(), propValue, writeType,
					parentElement);
            }
        }
		else if (String.class.isAssignableFrom(type))
		{
            if (propValue != null)
            {
			    storeString(xmlTagName, prop.getName(), propValue, writeType,
					parentElement);
            }
		}
        else if (java.util.Date.class.isAssignableFrom(type))
        {
            if (propValue != null)
            {
// otherwise if it's a date, use the date formatter
                storeDate(xmlTagName, prop.getName(), propValue, writeType,
					parentElement);
            }
        }
        else
        {
// if none of the above, assume it's a bean and write out a nested
// object using the property name as the tag for the nested object
            if ((writeType == WRITE_AS_ELEMENT) ||
				(writeType == WRITE_AS_INDEXED_ELEMENT))
            {
                if (propValue != null)
                {
					Element element = storeObject(prop.getName(), propValue);
					if (element != null)
					{
						parentElement.appendChild(element);
					}
                }
            }
        }
    }

/** Writes out a basic type including the opening and closing tags.
 * @param xmlTagName The XML tag name of the bean being written
 * @param propName The name of the property to write
 * @param propValue The value of the property
 * @throws IOException If there is an error while writing the property
 */
    public void storeBasicType(String xmlTagName, String propName,
        Object propValue, int writeType, Element parentElement)
        throws IOException
    {
// Get the translated name of the property
		String tagName = getTranslatedChildName(xmlTagName, propName);

// If the property doesn't exist, don't write anything (this only
// happens when there is a DTD and there is no match in the DTD for
// this property
		if (tagName == null) return;

        if ((writeAttributes || writeAsAttribute(xmlTagName, tagName)) &&
            (writeType != WRITE_AS_INDEXED_ELEMENT))
        {
            if (writeType == WRITE_AS_ATTRIBUTE)
            {
				parentElement.setAttribute(tagName, propValue.toString());
            }
            else
            {
                // Don't write basic types as elements if they
                // should have been written as attributes
                return;
            }
        }
        else
        {
			Element child = document.createElement(tagName);
			child.appendChild(document.createTextNode(propValue.toString()));

			parentElement.appendChild(child);
        }
    }

/** Writes out a string, using CDATA if the string contains < or &
 * @param xmlTagName The XML tag name of the bean being written
 * @param propName The name of the property to write
 * @param propValue The value of the property
 * @param outputStream The output stream or writer where the value
 *  is to be written
 * @throws IOException If there is an error while writing the property
 */
    public void storeString(String xmlTagName, String propName,
        Object propValue, int writeType, Element parentElement)
        throws IOException
    {
// Get the translated name of the property
		String tagName = getTranslatedChildName(xmlTagName, propName);

// If the property doesn't exist, don't write anything (this only
// happens when there is a DTD and there is no match in the DTD for
// this property
		if (tagName == null) return;

        if ((writeAttributes || writeAsAttribute(xmlTagName, tagName)) &&
            (writeType != WRITE_AS_INDEXED_ELEMENT))
        {
            if (writeType == WRITE_AS_ATTRIBUTE)
            {
				parentElement.setAttribute(tagName, (String) propValue);
            }
            else
            {
                // Don't write strings as elements if they
                // should have been written as attributes
                return;
            }
        }
        else
        {
			Element child = document.createElement(tagName);
			child.appendChild(document.createTextNode((String) propValue));

			parentElement.appendChild(child);
        }
    }

/** Writes out a date including the opening and closing tags
 * @param xmlTagName The XML tag name of the bean being written
 * @param propName The name of the property to write
 * @param propValue The value of the property
 * @param outputStream The output stream or writer where the value
 *  is to be written
 * @throws IOException If there is an error while writing the property
 */
    public void storeDate(String xmlTagName, String propName, Object propValue,
        int writeType, Element parentElement)
        throws IOException
    {
// Get the translated name of the property
		String tagName = getTranslatedChildName(xmlTagName, propName);

// If the property doesn't exist, don't write anything (this only
// happens when there is a DTD and there is no match in the DTD for
// this property
		if (tagName == null) return;

        if ((writeAttributes || writeAsAttribute(xmlTagName, tagName)) &&
            (writeType != WRITE_AS_INDEXED_ELEMENT))
        {
            if (writeType == WRITE_AS_ATTRIBUTE)
            {
				parentElement.setAttribute(tagName, dateFormat.format(
                    (java.util.Date) propValue));
            }
            else
            {
                // Don't write date values as elements
                // if they should have been written as an attribute
            }
        }
        else
        {
			Element child = document.createElement(tagName);
			child.appendChild(document.createTextNode(
				dateFormat.format((java.util.Date) propValue)));

			parentElement.appendChild(child);
        }
    }
}
