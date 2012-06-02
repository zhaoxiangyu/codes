package com.wutka.jox;

import java.io.*;
import java.text.*;
import com.wutka.dtd.*;

/** A writer filter that writes out a bean as an XML document.
 * The writer can write out basic Java types, their object equivalents
 * and also strings, dates and other beans. The XML tag names are the
 * same as the bean properties.
 *
 * @author Mark Wutka
 * @version 1.0 05/08/2000
 * @version 1.1 05/09/2000
 * @version 1.2 06/01/2000
 * @version 1.11 11/30/2000
 */

public class JOXBeanWriter extends FilterWriter implements JOXOutput
{
    protected static final String DEFAULT_ENCODING = "ISO-8859-1";

	protected JOXBeanOutput output;
    protected String encoding;

/** Creates a new writer around an existing writer
 * @param baseWriter The underlying writer
 */
    public JOXBeanWriter(Writer baseWriter)
    {
        this(baseWriter, false, DEFAULT_ENCODING);
    }

/** Creates a new writer around an existing writer
 * @param baseWriter The underlying writer
 * @param anEncoding The XML encoding to use
 */
    public JOXBeanWriter(Writer baseWriter, String anEncoding)
    {
        this(baseWriter, false, anEncoding);
    }

/** Creates a new writer around an existing writer
 * @param baseWriter The underlying writer
 * @param writeAttributes Indicates whether we should write simple
 *  properties as attributes
 */
    public JOXBeanWriter(Writer baseWriter, boolean writeAttributes)
    {
        this(baseWriter, writeAttributes, DEFAULT_ENCODING);
    }

/** Creates a new writer around an existing writer
 * @param baseWriter The underlying writer
 * @param writeAttributes Indicates whether we should write simple
 *  properties as attributes
 * @param anEncoding The XML encoding to use
 */
    public JOXBeanWriter(Writer baseWriter, boolean writeAttributes,
        String anEncoding)
    {
        super(baseWriter);

        encoding = anEncoding;

		output = new JOXBeanOutput(this, writeAttributes);
    }

/** Create a new writer around an existing writer and specifies
 * a DTD for selecting which attributes should be written and what the
 * names should look like.
 * @param dtdURI The URI of the DTD
 * @param baseWriter The underlying writer
 */
    public JOXBeanWriter(String dtdURI, Writer baseWriter)
		throws IOException
    {
        this(dtdURI, baseWriter, DEFAULT_ENCODING);
    }

/** Create a new writer around an existing writer and specifies
 * a DTD for selecting which attributes should be written and what the
 * names should look like.
 * @param dtdURI The URI of the DTD
 * @param baseWriter The underlying writer
 * @param anEncoding The XML encoding to use
 */
    public JOXBeanWriter(String dtdURI, Writer baseWriter,
        String anEncoding)
		throws IOException
    {
        super(baseWriter);

        encoding = anEncoding;

		output = new JOXBeanOutput(dtdURI, this);
    }

/** Create a new writer around an existing writer and specifies
 * a DTD for selecting which attributes should be written and what the
 * names should look like.
 * @param dtd The DTD to use
 * @param baseWriter The underlying writer
 */
    public JOXBeanWriter(DTD dtd, Writer baseWriter)
		throws IOException
    {
        this(dtd, baseWriter, DEFAULT_ENCODING);
    }

/** Create a new writer around an existing writer and specifies
 * a DTD for selecting which attributes should be written and what the
 * names should look like.
 * @param dtd The DTD to use
 * @param baseWriter The underlying writer
 * @param anEncoding The XML encoding to use
 */
    public JOXBeanWriter(DTD dtd, Writer baseWriter,
        String anEncoding)
		throws IOException
    {
        super(baseWriter);

        encoding = anEncoding;

		output = new JOXBeanOutput(dtd, this);
    }

/** Allow the default date format to be overridden by the caller.
 * @param fmt the date format to use when outputting dates
 */
    public void setDateFormat(DateFormat fmt)
    {
	output.setDateFormat(fmt);
    }

/** Writes a bean as XML, using rootName as the tag name for the
 *  document root. Other tag names will come from the names of the
 *  bean attributes.
 * @param rootName The name of the document root
 * @param ob The object to write out
 * @throws IOException If there is an error writing the object
 */
    public void writeObject(String rootName, Object ob)
        throws IOException
    {
// Write out the XML header
        writeString("<?xml version=\"1.0\"");

        if (encoding != null)
        {
            writeString(" encoding=\""+encoding+"\"");
        }
        
        writeString("?>\n");

// Write out the bean as XML
        output.writeObject(rootName, ob);
    }

/** Write a string to the writer
 *  This method is used by the output utility to write a string
 *  to either an output stream or a writer.
 * @param str The string to write
 */
    public void writeString(String str)
        throws IOException
    {
        write(str, 0, str.length());
    }
}
