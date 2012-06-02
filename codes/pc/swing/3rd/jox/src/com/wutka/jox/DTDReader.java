package com.wutka.jox;

import java.io.*;
import java.net.*;
import java.util.Vector;

/** Stupid parser that extracts element names from a DTD.
 *
 * @author Mark Wutka
 * @version 1.1 05/09/2000
 */
class DTDReader
{
/** The URL of the DTD */
	protected URL dtdURL;

/** Creates a DTDReader to read a DTD from a URL */
	public DTDReader(URL aDtdURL)
	{
		dtdURL = aDtdURL;
	}

/** Creates a DTDReader to read a DTD from a URL */
	public DTDReader(String aDtdURL)
		throws IOException
	{
		try
		{
			dtdURL = new URL(aDtdURL);
		}
		catch (MalformedURLException exc)
		{
			throw new IOException("Invalid DTD URL "+aDtdURL+": "+
				exc.toString());
		}
	}

/** Returns an array of the names of elements defined in the DTD
 * @return The elements in the DTD
 * @throws IOException If there is an error reading the DTD
 */
	public String[] getElements()
		throws IOException
	{
		Vector v = new Vector();

		PushbackInputStream in = null;

		try
		{
// Open the connection
			URLConnection urlConn = dtdURL.openConnection();

// Create a stream for parsing the DTD
			in = new PushbackInputStream(new BufferedInputStream(
					urlConn.getInputStream()));

// Keep searching for <!
			while (scanForLTBang(in))
			{

// Get the keywords that comes after <!
				String elementType = getString(in);

// If this is an ELEMENT tag, get the name of the element
				if (elementType.equals("ELEMENT"))
				{
					skipWhiteSpace(in);
// Get the element name
					String elementName = getString(in);
// Add the name to the list
					v.addElement(elementName);
				}
			}
			in.close();

// Create an array of elements
			String[] elements = new String[v.size()];
			v.copyInto(elements);

			return elements;
		}
		catch (Exception exc)
		{
			if (in != null)
			{
				try { in.close(); } catch (Exception ignore) {}
			}
			throw new IOException("Error reading DTD: "+exc.toString());
		}
	}

/** Searches for &lt;! in an input stream.
 * @param in The input stream to read from
 * @throws IOException If there is an error reading the stream
 */
	protected boolean scanForLTBang(PushbackInputStream in)
		throws IOException
	{
		int ch;

// Keep reading until there are no more chars to read
		while ((ch = in.read()) >= 0)
		{
// Got a < ? If so, look for a !
			if (ch == '<')
			{
// Get the character after the <
				ch = in.read();

// If end-of-file, forget it
				if (ch < 0) return false;

// If it's a !, the <! has been found
				if (ch == '!')
				{
					return true;
				}

// If the character after < was also <, unread the <. That way,
// the second < is still in the input stream so the routine can check
// to see if there's a ! after it.
				if (ch == '<')
				{
					in.unread((byte) ch);
				}
			}
		}

		return false;
	}

/** Skips over any whitespace characters in the stream
 * @param in The input stream to read
 * @throws IOException If there is an error reading the stream
 */
	protected void skipWhiteSpace(PushbackInputStream in)
		throws IOException
	{
		int ch;

// Keep reading until there are no more chars to read
		while ((ch = in.read()) >= 0)
		{
			if (!Character.isWhitespace((char) ch))
			{
// If the most recent character isn't whitespace, unread the character
// so another routine can pick it up
				in.unread((byte) ch);
				return;
			}
		}
	}

/** Reads a whitespace-delimited string from a stream
 * @param in The input stream to read
 * @throws IOException If there is an error reading the stream
 */
	protected String getString(PushbackInputStream in)
		throws IOException
	{
		StringBuffer str = new StringBuffer();

		int ch;

// Keep reading until there are no more chars to read
		while ((ch = in.read()) >= 0)
		{
			if (Character.isWhitespace((char) ch))
			{
// If the most recent character is whitespace, unread the character
// so another routine can pick it up
				in.unread((byte) ch);
				return str.toString();
			}
			else
			{
// If the character isn't a space, append it to the buffer
				str.append((char) ch);
			}
		}

		return null;
	}
}
