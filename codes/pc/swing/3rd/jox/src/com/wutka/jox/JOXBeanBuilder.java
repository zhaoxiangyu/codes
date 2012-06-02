package com.wutka.jox;

import java.io.*;
import java.text.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

/** 
 * JOXBeanBuilder builds a bean from an XML Element subtree.
 * 
 * @author Jeffrey Wescott
 * @version 1.0 2001/02/01
 */
public class JOXBeanBuilder
{
    private Element el;

/** Creates a new JOXBeanBuilder around a DOM Element.
 * @param el The DOM Element to use for the bean
 */
    public JOXBeanBuilder(Element el)
    {
        this.el = el;
    }

/** Reads an XML document into the object, matching tag names to
 *  bean property names.
 * @param ob The object that will receive data from the XML document
 * @throws IOException If there is an error reading the document
 */
    public void readObject(Object ob)
        throws IOException
    {
/* Wrap an InputSource around the input stream and ask the input
 * utility class to process the document */
        JOXBeanInput builder = new JOXBeanInput();
        builder.readObject(ob, el);
    }

/** Reads an XML document into a new instance of the given class,
 *  matching tag names to bean property names.
 * @param obClass The class for the object that will receive data from
 *      the XML document
 * @return The new instance of the class containing the parsed data
 * @throws IOException If there is an error reading the document
 */
    public Object readObject(Class obClass)
        throws IOException
    {
        try
        {
// Create the new bean instance to receive the data
            Object ob = obClass.newInstance();

/* Wrap an InputSource around the input stream and ask the input
 * utility class to process the document */
            JOXBeanInput reader = new JOXBeanInput();
            reader.readObject(ob, el);

// Return the new bean instance
            return ob;
        }
        catch (InstantiationException exc)
        {
            throw new IOException("Error instantiating "+
                obClass.getName()+": "+exc.toString());
        }
        catch (IllegalAccessException exc)
        {
            throw new IOException("Error instantiating "+
                obClass.getName()+": "+exc.toString());
        }
    }
}
