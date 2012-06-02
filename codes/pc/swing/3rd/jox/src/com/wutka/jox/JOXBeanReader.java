package com.wutka.jox;

import java.io.*;
import org.xml.sax.InputSource;

/** An Reader filter that reads XML into a bean.
 *
 * When you read an XML document, you must supply either a class
 * or an object instance. The reader will attempt to match
 * XML tags to bean attributes in the class/object you supply.
 * <p>
 * If you supply a class, the reader will automatically create
 * a new object instance to hold the data.
 * <p>
 * The reader understands the basic Java data types and their object
 * equivalents, plus strings and dates. Anything else must be a bean.
 * It can also read arrays of any of the supported types or of beans
 * if it tries to read a bean with an indexed property.
 * <p>
 * If there are XML fields that don't match the bean, it will ignore them.
 * If the data types are not compatible, you will get an exception. At some
 * point the reader will be smart enough to skip over incompatible fields.
 *
 *
 * <i>Note: JOXBeanInputStream and JOXBeanReader use the same underlying class
 *    to read the XML document, so their behaviour should be identical.</i>
 *
 * @author Mark Wutka
 * @version 1.0 05/08/2000
 * @version 1.1 05/09/2000
 */
public class JOXBeanReader extends FilterReader
{
/** Creates a new JOXReader around an existing reader.
 * @param in The reader to be read from
 */
    public JOXBeanReader(Reader in)
    {
        super(in);
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
		JOXSAXBeanInput reader = new JOXSAXBeanInput();

        reader.readObject(ob,
            new InputSource(in));
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
			JOXSAXBeanInput reader = new JOXSAXBeanInput();
			
            reader.readObject(ob, new InputSource(in));

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
