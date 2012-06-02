package com.wutka.jox;

/** An interface used by the output utility class so it can write
 *  XML to either an output stream or a writer without caring.
 *
 * This interface is implemented by both JOXOutputStream and
 * JOXWriter.
 *
 * @author Mark Wutka
 * @version 1.0 05/08/2000
 * @version 1.1 05/09/2000
 */
 
interface JOXOutput
{
    public void writeString(String str) throws java.io.IOException;
}
