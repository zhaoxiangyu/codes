package com.wutka.jox.test;

import com.wutka.jox.*;
import java.io.*;
import org.xml.sax.*;

public class TestDeser3
{
    public static void main(String[] args)
    {
        try
        {
            FileInputStream in = new FileInputStream("bean3.xml");

            JOXBeanInputStream joxIn = new JOXBeanInputStream(in);

            TestBean3 testBean = (TestBean3) joxIn.readObject(
                TestBean3.class);

            System.out.println(testBean);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
