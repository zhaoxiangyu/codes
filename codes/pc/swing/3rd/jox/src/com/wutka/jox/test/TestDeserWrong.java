package com.wutka.jox.test;

import com.wutka.jox.*;
import java.io.*;

public class TestDeserWrong
{
    public static void main(String[] args)
    {
        try
        {
            FileInputStream in = new FileInputStream("bean.xml");

            JOXBeanInputStream joxIn = new JOXBeanInputStream(in);

            TestSubbean testBean = (TestSubbean) joxIn.readObject(
                TestSubbean.class);

            System.out.println(testBean);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
