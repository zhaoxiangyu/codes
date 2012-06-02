package com.wutka.jox;

import java.io.*;
import java.text.*;
import java.lang.reflect.*;
import java.beans.*;
import java.util.*;
import java.net.*;
import org.w3c.dom.*;

import com.wutka.dtd.*;

/** Performs the actual task of serializing a bean as XML.
 *
 * @author Mark Wutka
 * @version 1.0 05/08/2000
 * @version 1.1 05/09/2000
 * @version 1.2 06/01/2000
 * @version 1.4 06/15/2000
 * @version 1.9 11/06/2000
 * @version 1.10 11/29/2000
 * @version 1.12 02/15/2001
 * @version 1.13 03/22/2001 bug fix by Jeff Wescott
 */

class JOXBeanOutput
{
    public static final int WRITE_AS_ATTRIBUTE = 1;
    public static final int WRITE_AS_ELEMENT = 2;
    public static final int WRITE_AS_INDEXED_ELEMENT = 3;

/** The date formatter used to write out dates */
    //protected DateFormat dateFormat = SimpleDateFormat.getDateInstance();
    protected DateFormat dateFormat = JOXDateHandler.determineDateFormat();
    protected boolean dateFormatIsDefault = true;

/** The dummy parameter block for invoking the getters for the bean */
    protected static Object[] getParams = new Object[0];

    protected Hashtable nameTranslation;
    protected Hashtable attrTranslation;
    protected Hashtable childTranslation;
    protected Hashtable propertyDescCache;
    protected static Hashtable beanCache = new Hashtable();

    protected DTD dtd;

    protected JOXOutput outputStream;

    protected boolean writeAttributes;

    protected JOXBeanOutput()
    {
    }

/** Creates a JOXBeanOutput that uses a DTD to determine which attributes
 *  to write and what the format of the names should be.
 * @param dtdURL The URL of the DTD
 * @param theStream The stream where the XML will be written
 */
    public JOXBeanOutput(String dtdURL, JOXOutput theStream)
        throws IOException
    {
        outputStream = theStream;

        dtd = readDTD(dtdURL);
        setupNameTranslation();
        propertyDescCache = new Hashtable();

// writeAttributes is false here because when the DTD reader functions
// like it eventually should, we'll know which items need to be attributes
// and which need to be elements.
        writeAttributes = false;
    }

/** Creates a JOXBeanOutput that uses a DTD to determine which attributes
 *  to write and what the format of the names should be.
 * @param dtd The DTD for the XML to write
 * @param theStream The stream where the XML will be written
 */
    public JOXBeanOutput(DTD aDtd, JOXOutput theStream)
        throws IOException
    {
        outputStream = theStream;

        dtd = aDtd;
        setupNameTranslation();
        propertyDescCache = new Hashtable();

// writeAttributes is false here because when the DTD reader functions
// like it eventually should, we'll know which items need to be attributes
// and which need to be elements.
        writeAttributes = false;
    }

/** Creates a JOXBeanOutput that doesn't use a DTD
 * @param theStream The stream where the XML will be written
 */
    public JOXBeanOutput(JOXOutput theStream, boolean writeAttributesFlag)
    {
        outputStream = theStream;

        writeAttributes = writeAttributesFlag;

        nameTranslation = null;
    }

    synchronized void setDateFormat(DateFormat fmt)
    {
    dateFormat = fmt;
    dateFormatIsDefault = false;
    }

    protected DTD readDTD(String dtdURL)
        throws IOException
    {
        Reader reader = null;

        try
        {
            URL url = new URL(dtdURL);

            DTDParser parser = new DTDParser(url, false);

            return parser.parse(false);
        }
        catch (IOException ioExc)
        {
            throw ioExc;
        }
        catch (Exception exc)
        {
            throw new IOException(exc.toString());
        }
        finally
        {
            if (reader != null)
            { 
                try { reader.close(); } catch (Exception ignore) {}
            }
        }
    }

/** Writes out an object as XML
 * @param rootName The tag name for this object (if this object is a
 *  property of a bean, the tag name will be the property name)
 * @param ob The object to write out
 * @throws IOException If there is an error while writing the object
 */
    public void writeObject(String rootName, Object ob)
        throws IOException
    {

        String tagName = getTranslatedEntityName(rootName);
        if (tagName == null) return;

// Write out the opening tag
        outputStream.writeString("<");
        outputStream.writeString(tagName);

        writeObjectFields(tagName, ob, WRITE_AS_ATTRIBUTE);

        outputStream.writeString(">\n");

// Write out the properties of the object
        writeObjectFields(tagName, ob, WRITE_AS_ELEMENT);

// Write out the closing tag
        outputStream.writeString("</");
        outputStream.writeString(tagName);
        outputStream.writeString(">\n");
    }

/** Writes out the properties of a bean as XML
 * @param xmlTagName The XML tag name of the bean being written
 * @param ob The bean whose properties will be written
 * @param writeType The kind of fields being written (attribute or element)
 * @exception IOException If there is an error writing the object
 */
    public void writeObjectFields(String xmlTagName, Object ob, int writeType)
        throws IOException
    {
// If the object is null, don't bother writing anything
        if (ob == null) return;

        try
        {
            BeanInfo info = (BeanInfo) beanCache.get(ob.getClass());

            if (info == null)
            {
// Get the bean info for the bean, but don't get the attributes from object
                info = Introspector.getBeanInfo(ob.getClass(),
                    Object.class);

                beanCache.put(ob.getClass(), info);
            }

// Get the list of properties for this bean
            PropertyDescriptor[] props = info.getPropertyDescriptors();

            if ((dtd != null) && (writeType == WRITE_AS_ELEMENT))
            {
                props = filterPropertyDescriptors(props, xmlTagName,
                    ob.getClass());
            }

            for (int i=0; i < props.length; i++)
            {
// If the property is indexed (if it represents an array of props),
// use a different routine to write it out
                if (props[i] instanceof IndexedPropertyDescriptor)
                {
                    if (writeType == WRITE_AS_ELEMENT)
                    {
                        writeIndexedProperty(xmlTagName, ob,
                            (IndexedPropertyDescriptor) props[i]);
                    }
                }
                else
                {
                    writeProperty(xmlTagName, ob, props[i], writeType);
                }
            }
        }
        catch (IntrospectionException exc)
        {
            throw new IOException("Got introspection exception "+
                exc.toString());
        }
    }

/** Writes out a non-indexed property
 * @param xmlTagName The XML tag name of the bean being written
 * @param ob The object containing the property
 * @param prop The property descriptor of the property
 * @param writeType Should we try to write this property
 *  as an attribute? If not, don't write the object if
 *  writeAttributes is true and this object can be written
 *  as an attribute
 * @throws IOException If there is an error writing out the property
 */
    public void writeProperty(String xmlTagName, Object ob,
        PropertyDescriptor prop, int writeType)
        throws IOException
    {
        try
        {
// Get the method used to read the property
            Method reader = prop.getReadMethod();

// If this property has no reader, don't bother reading it
            if (reader == null) return;

// Get the property value
            Object propValue = reader.invoke(ob, getParams);

// Write the property value
            writePropertyValue(xmlTagName, propValue, prop, writeType);
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

/** Writes out an indexed property (an array of properties)
 * @param xmlTagName The XML tag name of the bean being written
 * @param ob The object containing the property
 * @param prop The descriptor for the property
 *  as an attribute? If not, don't write the object if
 *  writeAttributes is true and this object can be written
 *  as an attribute
 * @throws IOException If there is an error writing the properties
 */
    public void writeIndexedProperty(String xmlTagName, Object ob,
        IndexedPropertyDescriptor prop)
        throws IOException
    {
        try
        {
// Get the method used to read the property values
            Method reader = prop.getReadMethod();

// If this property has no reader, don't bother reading it
            if (reader == null) return;

// Get the entire array of property values
            Object[] propValues = (Object[]) reader.invoke(ob, getParams);

// Write out each property value as its own element
            writePropertyValue(xmlTagName, propValues, prop,
                WRITE_AS_INDEXED_ELEMENT);
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

/** Writes out a property value based on the property type
 * @param xmlTagName The XML tag name of the bean being written
 * @param propValue The value to write
 * @param prop The property descriptor of the property
 * @param writeType Should we try to write this property
 *  as an attribute? If not, don't write the object if
 *  writeAttributes is true and this object can be written
 *  as an attribute
 * @throws IOException If there is an error while writing the property
 */
    public void writePropertyValue(String xmlTagName, Object propValue,
        PropertyDescriptor prop, int writeType)
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

                writePropertyValueByType(xmlTagName, realValue,
                    prop, writeType, type);
            }
        }
        else
        {
            writePropertyValueByType(xmlTagName, propValue,
                prop, writeType, type);
        }
    }

    public void writePropertyValueByType(String xmlTagName,
        Object propValue, PropertyDescriptor prop, int writeType,
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
                writeBasicType(xmlTagName, prop.getName(), propValue, writeType);
            }
        }
        else if (String.class.isAssignableFrom(type))
        {
            if (propValue != null)
            {
                writeString(xmlTagName, prop.getName(), propValue, writeType);
            }
        }
        else if (java.util.Date.class.isAssignableFrom(type))
        {
            if (propValue != null)
            {
// otherwise if it's a date, use the date formatter
                writeDate(xmlTagName, prop.getName(), propValue, writeType);
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
                    writeObject(prop.getName(), propValue);
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
    public void writeBasicType(String xmlTagName, String propName,
        Object propValue, int writeType)
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
                outputStream.writeString(" ");
                outputStream.writeString(tagName);
                outputStream.writeString("=\"");
                outputStream.writeString(propValue.toString());
                outputStream.writeString("\"");
            }
            else
            {
                // Don't write basic types as elements if they
                // should have been written as attributes
                return;
            }
        }
        else if (writeType != WRITE_AS_ATTRIBUTE)
        {
            outputStream.writeString("<");
            outputStream.writeString(tagName);
            outputStream.writeString(">");

            outputStream.writeString(propValue.toString());

            outputStream.writeString("</");
            outputStream.writeString(tagName);
            outputStream.writeString(">\n");
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
    public void writeString(String xmlTagName, String propName,
        Object propValue, int writeType)
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
                outputStream.writeString(" ");
                outputStream.writeString(tagName);
                outputStream.writeString("=\"");
                outputStream.writeString((String) propValue);
                outputStream.writeString("\"");
            }
            else
            {
                // Don't write strings as elements if they
                // should have been written as attributes
                return;
            }
        }
        else if (writeType != WRITE_AS_ATTRIBUTE)
        {
            outputStream.writeString("<");
            outputStream.writeString(tagName);
            outputStream.writeString(">");

            String propString = (String) propValue;

            if ((propString.indexOf('<') >= 0) ||
                (propString.indexOf('>') >= 0) ||
                (propString.indexOf('&') >= 0))
            {
                outputStream.writeString("<![CDATA[\n");
                outputStream.writeString(propString);
                outputStream.writeString("]]>\n");
            }
            else
            {
                outputStream.writeString(propString);
            }
    
            outputStream.writeString("</");
            outputStream.writeString(tagName);
            outputStream.writeString(">\n");
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
    public void writeDate(String xmlTagName, String propName, Object propValue,
        int writeType)
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
                outputStream.writeString(" ");
                outputStream.writeString(tagName);
                outputStream.writeString("=\"");

                outputStream.writeString(dateFormat.format(
                    (java.util.Date) propValue));
                outputStream.writeString("\"");
            }
            else
            {
                // Don't write date values as elements
                // if they should have been written as an attribute
            }
        }
        else if (writeType != WRITE_AS_ATTRIBUTE)
        {
            outputStream.writeString("<");
            outputStream.writeString(tagName);
        if (!dateFormatIsDefault) {
        try {
            SimpleDateFormat fmt = (SimpleDateFormat)dateFormat;
            outputStream.writeString(" format=\"");
            outputStream.writeString( fmt.toPattern() );
            outputStream.writeString("\"");
        } catch (ClassCastException e) {
            // if it's not a SimpleDateFormat, just don't
            // output the format attribute
        }
        }
            outputStream.writeString(">");

            outputStream.writeString(dateFormat.format(
                (java.util.Date) propValue));

            outputStream.writeString("</");
            outputStream.writeString(tagName);
            outputStream.writeString(">\n");
        }
    }

/** Converts a name to lower case and removes any '-' or '_' characters
 * @param name The name to strip
 * @return The stripped version of the name
 */
    public String stripName(String name)
    {
// Create a buffer to hold the stripped name
        StringBuffer stripped = new StringBuffer();

        int len = name.length();

// Loop through all the characters in the name
        for (int i=0; i < len; i++)
        {

// Convert each character to lower case
            char ch = Character.toLowerCase(name.charAt(i));

// Skip '-' and '_'
            if ((ch == '-') || (ch == '_') || (ch == '.') ||
                (ch == ':')) continue;

            stripped.append(ch);
        }

        return stripped.toString();
    }

    protected void setupNameTranslation()
    {
        nameTranslation = new Hashtable();
        attrTranslation = new Hashtable();
        childTranslation = new Hashtable();

        Enumeration e = dtd.elements.elements();

        while (e.hasMoreElements())
        {
            DTDElement element = (DTDElement) e.nextElement();

            String trans = stripName(element.name);

            nameTranslation.put(trans, element.name);

            Hashtable childTransTable = new Hashtable();

            DTDItem children = element.getContent();

            if (children instanceof DTDContainer)
            {
                expandChildElements((DTDContainer) children,
                    childTransTable);
            }
            childTranslation.put(trans, childTransTable);

            Hashtable attTransTable = new Hashtable();

            Enumeration atts = element.attributes.elements();

            while (atts.hasMoreElements())
            {
                DTDAttribute att = (DTDAttribute) atts.nextElement();

                String attTrans = stripName(att.getName());
                attTransTable.put(attTrans, att.getName());
            }
            attrTranslation.put(trans, attTransTable);
        }
    }

    public void expandChildElements(DTDContainer children,
        Hashtable childTransTable)
    {
        Enumeration childElems = children.getItemsVec().elements();

        while (childElems.hasMoreElements())
        {
            Object nextChild = childElems.nextElement();

            if (nextChild instanceof DTDElement)
            {
                String elementName = ((DTDElement)nextChild).getName();

                String childTrans = stripName(elementName);
                childTransTable.put(childTrans, elementName);
            }
            else if (nextChild instanceof DTDContainer)
            {
                expandChildElements((DTDContainer) nextChild,
                    childTransTable);
            }
            else if (nextChild instanceof DTDName)
            {
                String elementName = ((DTDName)nextChild).getValue();

                String childTrans = stripName(elementName);
                childTransTable.put(childTrans, elementName);
            }
        }
    }


/** Finds the correct format of a name as given by the DTD.
 *  If there is no DTD, this method returns the original name.
 *  If there is a DTD but no match, this method returns null.
 * @param The name to translate
 * @return The translated name or null if no translation exists
 */
    public String getTranslatedEntityName(String name)
    {
        if (nameTranslation == null) return name;

        String trans = (String) nameTranslation.get(stripName(name));

        return trans;
    }

    public String getTranslatedChildName(String elementName, String childName)
    {
        if (nameTranslation == null) return childName;

        String stripped = stripName(elementName);

        Hashtable childTrans = (Hashtable) childTranslation.get(stripped);

        Hashtable attTrans = (Hashtable) attrTranslation.get(stripped);

        if ((childTrans == null) && (attTrans == null))
        {
            return getTranslatedEntityName(childName);
        }

        String childStripped = stripName(childName);

        if (attTrans != null)
        {
            String trans = (String) attTrans.get(childStripped);
            if (trans != null) return trans;
        }

        if (childTrans != null)
        {
            String trans = (String) childTrans.get(childStripped);
            if (trans != null) return trans;
        }

        return null;
    }

/** Returns true if a tag name for a particular object should
 *  be written as an attribute
 *  @param elementName The element whose tag is being written
 *  @param attributeName The potential attribute name
 *  @return True if the tag should be written as an attribute
 */
    public boolean writeAsAttribute(String elementName,
        String attributeName)
    {
        if (nameTranslation == null) return false;

        Hashtable attrTrans = (Hashtable) attrTranslation.get(
            stripName(elementName));

        if (attrTrans == null) return false;

        if (attrTrans.get(stripName(attributeName)) != null)
        {
            return true;
        }

        return false;
    }

/** Filters a list of property descriptors based on a DTD, removing
 *  descriptors that aren't in the DTD and ordering the elements the
 *  way they are in the DTD.
 */
    protected PropertyDescriptor[] filterPropertyDescriptors(
        PropertyDescriptor[] descriptors, String xmlTagName, Class beanClass)
    {
// First look in the cache for the ordered descriptors
        PropertyDescriptor[] returnProps = (PropertyDescriptor[])
            propertyDescCache.get(new DescCacheKey(xmlTagName, beanClass));
        if (returnProps != null)
        {
            return returnProps;
        }

        Vector v = new Vector();

// Locate the element in the dtd
        DTDElement element = (DTDElement) dtd.elements.get(
            getTranslatedEntityName(xmlTagName));
            
// If the element isn't there, just spit back the original descriptors,
// the program should filter the element out.. in fact, this should
// probable never happen, but it's better to make sure.
        if (element == null)
        {
// Cache the original descriptor set
            propertyDescCache.put(new DescCacheKey(xmlTagName, beanClass),
                descriptors);
            return descriptors;
        }

        DTDItem item = element.content;
        if (!(item instanceof DTDContainer))
        {
// Cache the original descriptor set
            propertyDescCache.put(new DescCacheKey(xmlTagName, beanClass),
                descriptors);
            return descriptors;
        }

// Put the properties in a table for quick lookup
        Hashtable propLookup = new Hashtable();
        for (int i=0; i < descriptors.length; i++)
        {
            String tagName = getTranslatedChildName(xmlTagName,
                descriptors[i].getName());
            if (tagName != null)
            {
                propLookup.put(tagName, descriptors[i]);
            }
        }
        
// Look for the properties as the occur as children of this element
        extractPropertyOrder((DTDContainer) item,
            propLookup, v);

        PropertyDescriptor newProps[] = new PropertyDescriptor[v.size()];
        v.copyInto(newProps);

// Cache the new ordered property list
        propertyDescCache.put(new DescCacheKey(xmlTagName, beanClass),
            newProps);
        return newProps;
    }

/** Recursively look through a DTDContainer adding properties to the
    property list.
*/
    protected void extractPropertyOrder(DTDContainer container,
        Hashtable propLookup, Vector propList)
    {
        Enumeration e = container.getItemsVec().elements();

        while (e.hasMoreElements())
        {
            DTDItem item = (DTDItem) e.nextElement();

// If the item is a name, it's an element
            if (item instanceof DTDName)
            {
                DTDName aName = (DTDName) item;

                PropertyDescriptor desc = (PropertyDescriptor) propLookup.get(
                    aName.value);
                if (desc != null)
                {
// Only add it to the list if it isn't already there
                    if (!propList.contains(desc))
                    {
                        propList.addElement(desc);
                    }
                }
            }
            else if (item instanceof DTDContainer)
            {
// Expand the child container
                extractPropertyOrder((DTDContainer) item,
                    propLookup, propList);
            }
        }
    }
}

class DescCacheKey
{
    public String tagName;
    public Class obClass;

    public DescCacheKey(String aTagName, Class anObClass)
    {
        tagName = aTagName;
        obClass = anObClass;
    }

    public int hashCode()
    {
        return tagName.hashCode() + obClass.hashCode();
    }

    public boolean equals(Object other)
    {
        if (other == null) return false;
        if (!(other instanceof DescCacheKey)) return false;
        DescCacheKey otherKey = (DescCacheKey) other;
        return tagName.equals(otherKey.tagName) &&
            obClass.equals(otherKey.obClass);
    }
}
