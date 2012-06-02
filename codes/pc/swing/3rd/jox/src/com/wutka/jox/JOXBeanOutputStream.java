package com.wutka.jox;

import java.io.*;
import java.text.*;
import org.w3c.dom.*;
import com.wutka.dtd.*;

/** An output stream filter that writes out a bean as an XML document.
 * The stream can write out basic Java types, their object equivalents
 * and also strings, dates and other beans. The XML tag names are the
 * same as the bean properties.
 *
 * @author Mark Wutka
 * @version 1.0 05/08/2000
 * @version 1.1 05/09/2000
 * @version 1.2 06/01/2000
 * @version 1.11 11/30/2000
 */

public class JOXBeanOutputStream extends FilterOutputStream
    implements JOXOutput
{
    public static final String DEFAULT_ENCODING = "ISO-8859-1";
	protected JOXBeanOutput output;
    protected String encoding;

/** Create a new output stream around an existing stream
 * @param baseOutputStream The underlying output stream
 */
    public JOXBeanOutputStream(OutputStream baseOutputStream)
    {
        this(baseOutputStream, false, DEFAULT_ENCODING);
    }

/** Create a new output stream around an existing stream
 * @param baseOutputStream The underlying output stream
 * @param encoding The XML encoding for the output
 */
    public JOXBeanOutputStream(OutputStream baseOutputStream,
        String anEncoding)
    {
        this(baseOutputStream, false, anEncoding);
    }

/** Create a new output stream around an existing stream
 * @param baseOutputStream The underlying output stream
 * @param writeAttributes Indicates whether we should write simple properties
 *  as attributes
 */
    public JOXBeanOutputStream(OutputStream baseOutputStream,
        boolean writeAttributes)
    {
        this(baseOutputStream, writeAttributes, DEFAULT_ENCODING);
    }

/** Create a new output stream around an existing stream
 * @param baseOutputStream The underlying output stream
 * @param writeAttributes Indicates whether we should write simple properties
 *  as attributes
 * @param encoding The XML encoding for the output
 */
    public JOXBeanOutputStream(OutputStream baseOutputStream,
        boolean writeAttributes, String anEncoding)
    {
        super(baseOutputStream);
        encoding = anEncoding;
		output = new JOXBeanOutput(this, writeAttributes);
    }

/** Create a new output stream around an existing stream and specifies
 * a DTD for selecting which attributes should be written and what the
 * names should look like.
 * @param dtdURI The URI of the DTD
 * @param baseOutputStream The underlying output stream
 */
	public JOXBeanOutputStream(String dtdURI, OutputStream baseOutputStream)
		throws IOException
	{
        this(dtdURI, baseOutputStream, DEFAULT_ENCODING);
    }

/** Create a new output stream around an existing stream and specifies
 * a DTD for selecting which attributes should be written and what the
 * names should look like.
 * @param dtdURI The URI of the DTD
 * @param baseOutputStream The underlying output stream
 * @param encoding The XML encoding for the output
 */
	public JOXBeanOutputStream(String dtdURI, OutputStream baseOutputStream,
        String anEncoding)
		throws IOException
	{
		super(baseOutputStream);

        encoding = anEncoding;
		output = new JOXBeanOutput(dtdURI, this);
	}

/** Create a new output stream around an existing stream and specifies
 * a DTD for selecting which attributes should be written and what the
 * names should look like.
 * @param dtd The DTD to use
 * @param baseOutputStream The underlying output stream
 */
	public JOXBeanOutputStream(DTD dtd, OutputStream baseOutputStream)
		throws IOException
	{
        this(dtd, baseOutputStream, DEFAULT_ENCODING);
    }

/** Create a new output stream around an existing stream and specifies
 * a DTD for selecting which attributes should be written and what the
 * names should look like.
 * @param dtd The DTD to use
 * @param baseOutputStream The underlying output stream
 */
	public JOXBeanOutputStream(DTD dtd, OutputStream baseOutputStream,
        String anEncoding)
		throws IOException
	{
		super(baseOutputStream);

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

/** Write a string to the output stream.
 *  This method is used by the output utility to write a string
 *  to either an output stream or a writer.
 * @param str The string to write
 */
    public void writeString(String str)
        throws IOException
    {
        write(str.getBytes());
    }
}
