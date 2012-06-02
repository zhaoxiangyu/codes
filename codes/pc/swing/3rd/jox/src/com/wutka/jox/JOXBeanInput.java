package com.wutka.jox;

import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import java.text.*;
import java.util.*;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/** Performs the actual reading of an XML document and copies the
 *  values into a bean. This class uses the DocumentBuilder portion
 *  of the Java XML API. It is not as efficient as SAX, but much easier
 *  to deal with when copying values into beans.
 *
 * @author Mark Wutka
 * @version 1.0 05/08/2000
 * @version 1.1 05/09/2000
 * @version 1.6 07/30/2000
 */

class JOXBeanInput
{
/** The document builder factory used to instantiate new document
 *  builders.
 */
    protected static DocumentBuilderFactory factory =
        DocumentBuilderFactory.newInstance();

    protected static Hashtable beanCache = new Hashtable();

/** Reads an XML document from an input source and copies its
 *  values into the specified object
 * @param ob The object to receive the values
 * @param source The location of the XML document
 * @throws IOException If there is an error reading the document
 */
    public void readObject(Object ob, InputSource source)
        throws IOException
    {
        try
        {
// Create a document builder to read the document
            DocumentBuilder builder = factory.newDocumentBuilder();

// Read the document
            Document doc = builder.parse(source);

// Get the root element
            Element element = doc.getDocumentElement();

// Copy the root element into the bean
            readObject(ob, element);
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

/** Reads the children of an XML element and matches them to properties
 *  of a bean.
 * @param ob The bean to receive the values
 * @param element The element the corresponds to the bean
 * @throws IOException If there is an error reading the document
 */
    public void readObject(Object ob, Element element)
        throws IOException
    {
// If the object is null, skip the element
        if (ob == null)
        {
            return;
        }

        try
        {
            BeanInfo info = (BeanInfo) beanCache.get(ob.getClass());

            if (info == null)
            {
// Get the bean info for the object
                info = Introspector.getBeanInfo(ob.getClass(),
                    Object.class);

                beanCache.put(ob.getClass(), info);
            }

// Get the object's properties
            PropertyDescriptor[] props = info.getPropertyDescriptors();

// Get the attributes of the node
            NamedNodeMap attrs = element.getAttributes();

// Get the children of the XML element
            NodeList nodes = element.getChildNodes();

            int numNodes = nodes.getLength();

            for (int i=0; i < props.length; i++)
            {
// Treat indexed properties a little differently
                if (props[i] instanceof IndexedPropertyDescriptor)
                {
                    readIndexedProperty(ob,
                        (IndexedPropertyDescriptor) props[i], nodes,
                        attrs);
                }
                else
                {
                    readProperty(ob, props[i], nodes, attrs);
                }
            }
        }
        catch (IntrospectionException exc)
        {
            throw new IOException(
                "Error getting bean info for "+ob.getClass().getName()+
                ": "+exc.toString());
        }
    }

/** Reads an XML element into a bean property by first locating the
 *  XML element corresponding to this property.
 * @param ob The bean whose property is being set
 * @param desc The property that will be set
 * @param nodes The list of XML items that may contain the property
 * @throws IOException If there is an error reading the document
 */
    public void readProperty(Object ob,
        PropertyDescriptor desc, NodeList nodes, NamedNodeMap attrs)
        throws IOException
    {
        int numAttrs = attrs.getLength();

        for (int i=0; i < numAttrs; i++)
        {
// See if the attribute name matches the property name
            if (namesMatch(desc.getName(), attrs.item(i).getNodeName()))
            {
// Get the method used to set this property
                Method setter = desc.getWriteMethod();

// If this object has no setter, don't bother writing it
                if (setter == null) continue;

// Get the value of the property
                Object obValue = getObjectValue(desc, attrs.item(i).getNodeValue());
                if (obValue != null)
                {
                    try
                    {
// Set the property value
                        setter.invoke(ob, new Object[] { obValue } );
                    }
                    catch (InvocationTargetException exc)
                    {
                        throw new IOException(
                            "Error setting property "+
                            desc.getName()+": "+exc.toString());
                    }
                    catch (IllegalAccessException exc)
                    {
                        throw new IOException(
                            "Error setting property "+
                            desc.getName()+": "+exc.toString());
                    }
                }

                return;
            }
        }

        int numNodes = nodes.getLength();

        Vector arrayBuild = null;

        for (int i=0; i < numNodes; i++)
        {
            Node node = nodes.item(i);

// If this node isn't an element, skip it
            if (!(node instanceof Element)) continue;

            Element element = (Element) node;

// See if the tag name matches the property name
            if (namesMatch(desc.getName(), element.getTagName()))
            {
// Get the method used to set this property
                Method setter = desc.getWriteMethod();

// If this object has no setter, don't bother writing it
                if (setter == null) continue;

// Get the value of the property
                Object obValue = getObjectValue(desc, element);

// 070201 MAW: Modified from change submitted by Steve Poulson
                if (setter.getParameterTypes()[0].isArray())
                {
                    if (arrayBuild == null)
                    {
                        arrayBuild = new Vector();
                    }
                    arrayBuild.addElement(obValue);

// 070201 MAW: Go ahead and read through the rest of the nodes in case
//             another one matches the array. This has the effect of skipping
//             over the "return" statement down below
                    continue;
                }

                if (obValue != null)
                {
                    try
                    {
// Set the property value
                        setter.invoke(ob, new Object[] { obValue } );
                    }
                    catch (InvocationTargetException exc)
                    {
                        throw new IOException(
                            "Error setting property "+
                            desc.getName()+": "+exc.toString());
                    }
                    catch (IllegalAccessException exc)
                    {
                        throw new IOException(
                            "Error setting property "+
                            desc.getName()+": "+exc.toString());
                    }
                }
                return;
            }
        }

// If we build a vector of array members, convert the vector into
// an array and save it in the property
        if (arrayBuild != null)
        {
// Get the method used to set this property
            Method setter = desc.getWriteMethod();

            if (setter == null) return;

            Object[] obValues = (Object[]) Array.newInstance(
                desc.getPropertyType(), arrayBuild.size());

            arrayBuild.copyInto(obValues);

            try
            {
                setter.invoke(ob, new Object[] { obValues } );
            }
            catch (InvocationTargetException exc)
            {
                throw new IOException(
                    "Error setting property "+
                    desc.getName()+": "+exc.toString());
            }
            catch (IllegalAccessException exc)
            {
                throw new IOException(
                    "Error setting property "+
                    desc.getName()+": "+exc.toString());
            }

            return;
        }
    }

/** Reads XML element(s) into an indexed bean property by first locating
 * the  XML element(s) corresponding to this property.
 * @param ob The bean whose property is being set
 * @param desc The property that will be set
 * @param nodes The list of XML items that may contain the property
 * @throws IOException If there is an error reading the document
 */
    public void readIndexedProperty(Object ob,
        IndexedPropertyDescriptor desc, NodeList nodes, NamedNodeMap attrs)
        throws IOException
    {
// Create a vector to hold the property values
        Vector v = new Vector();

        int numAttrs = attrs.getLength();

        for (int i=0; i < numAttrs; i++)
        {
// See if this attribute matches the property name
            if (namesMatch(desc.getName(), attrs.item(i).getNodeName()))
            {
// Get the property value
                Object obValue = getObjectValue(desc, attrs.item(i).
                    getNodeValue());

                if (obValue != null)
                {
// Add the value to the list of values to be set
                    v.addElement(obValue);
                }
            }
        }

        int numNodes = nodes.getLength();

        for (int i=0; i < numNodes; i++)
        {
            Node node = nodes.item(i);

// Skip non-element nodes
            if (!(node instanceof Element)) continue;

            Element element = (Element) node;

// See if this element tag matches the property name
            if (namesMatch(desc.getName(), element.getTagName()))
            {
// Get the property value
                Object obValue = getObjectValue(desc, element);

                if (obValue != null)
                {
// Add the value to the list of values to be set
                    v.addElement(obValue);
                }
            }
        }

// Get the method used to set the property value
        Method setter = desc.getWriteMethod();

// If this property has no setter, don't write it
        if (setter == null) return;

// Create a new array of property values
        Object propArray = Array.newInstance(
            desc.getPropertyType().getComponentType(), v.size());

// Copy the vector into the array
        v.copyInto((Object[]) propArray);

        try
        {
// Store the array of property values
            setter.invoke(ob, new Object[] { propArray } );
        }
        catch (InvocationTargetException exc)
        {
            throw new IOException(
                "Error setting property "+
                desc.getName()+": "+exc.toString());
        }
        catch (IllegalAccessException exc)
        {
            throw new IOException(
                "Error setting property "+
                desc.getName()+": "+exc.toString());
        }
    }

/** Examines a property's type to see which method should be used
 *  to parse the property's value.
 * @param desc The description of the property
 * @param element The XML element containing the property value
 * @return The value stored in the element
 * @throws IOException If there is an error reading the document
 */
    public Object getObjectValue(PropertyDescriptor desc,
        Element element)
        throws IOException
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
            Double.class.isAssignableFrom(type) ||
            String.class.isAssignableFrom(type))
        {
            return readBasicType(type, element);
        }
        else if (java.util.Date.class.isAssignableFrom(type))
        {
// If it's a date, use the date parser
            return readDate(element);
        }
        else
        {
            try
            {
// If it's an object, create a new instance of the object (it should
// be a bean, or there will be trouble)
                Object newOb = type.newInstance();

// Copy the XML element into the bean
                readObject(newOb, element);

                return newOb;
            }
            catch (InstantiationException exc)
            {
                throw new IOException(
                    "Error creating object for "+
                    desc.getName()+": "+exc.toString());
            }
            catch (IllegalAccessException exc)
            {
                throw new IOException(
                    "Error creating object for "+
                    desc.getName()+": "+exc.toString());
            }
        }
    }

/** Examines a property's type to see which method should be used
 *  to parse the property's value.
 * @param desc The description of the property
 * @param value The value of the XML attribute containing the prop value
 * @return The value stored in the element
 * @throws IOException If there is an error reading the document
 */
    public Object getObjectValue(PropertyDescriptor desc,
        String value)
        throws IOException
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
// If it's a date, use the date parser
            return parseDate( value, JOXDateHandler.determineDateFormat() );
        }
        else
        {
            return null;
        }
    }

/** Reads an XML text element into a basic type
 * @param type The type of the element to read
 * @param element The element containing the value
 * @return The parsed value of the element
 */
    public static Object readBasicType(Class type, Element element)
    {
// Get the text corresponding to this element
        String str = getElementString(element);

// If there isn't any, don't parse
        if (str == null) return null;

// If it's a string, don't need to do anything else
        if (String.class.isAssignableFrom(type)) return str;

        return parseBasicType(type, str);
    }

/** Reads an string into a basic type
 * @param type The type of the string to read
 * @param str The string containing the value
 * @return The parsed value of the string
 */
    public static Object parseBasicType(Class type, String str)
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

/** Parses an XML element as a date
 * @param element The element containing the date
 * @return The date value parsed from the element
 * @throws IOException If there's an error parsing the date
 */
    public Object readDate(Element element)
        throws IOException
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

/** Parses a string as a date
 * @param str The string containing the date
 * @return The date value parsed from the string
 * @throws IOException If there's an error parsing the date
 */
    public Object parseDate(String str, DateFormat dateFormat)
        throws IOException
    {
// If there isn't any, don't parse
        if (str == null) return null;

        try
        {
            return dateFormat.parse(str);
        }
        catch (ParseException exc)
        {
            throw new IOException(
                "Error parsing date "+str);
        }
    }

/** Searches the children of an element looking for a Text node. If
 * it finds one, it returns it.
 * @param element The element whose children will be searched
 * @return The text for the element, or null if there is none
 */
    public static String getElementString(Element element)
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
    public static boolean namesMatch(String beanName, String elementName)
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
}
