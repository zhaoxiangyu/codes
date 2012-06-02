package com.wutka.jox.test;

import com.wutka.jox.*;
import java.io.*;

public class TestReaderDeser
{
    public static void main(String[] args)
    {
        try
        {
            FileReader in = new FileReader("bean2.xml");

            JOXBeanReader joxIn = new JOXBeanReader(in);

            TestBean testBean = (TestBean) joxIn.readObject(
                TestBean.class);

            System.out.println(testBean);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
