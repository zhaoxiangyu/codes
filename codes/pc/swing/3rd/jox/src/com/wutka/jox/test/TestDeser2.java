package com.wutka.jox.test;

import com.wutka.jox.*;
import java.io.*;

public class TestDeser2
{
    public static void main(String[] args)
    {
        try
        {
            FileInputStream in = new FileInputStream("bean2.xml");

            JOXBeanInputStream joxIn = new JOXBeanInputStream(in);

            TestBean2 testBean = (TestBean2) joxIn.readObject(
                TestBean2.class);

            System.out.println(testBean);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
