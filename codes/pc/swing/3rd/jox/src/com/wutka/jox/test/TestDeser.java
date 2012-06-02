package com.wutka.jox.test;

import com.wutka.jox.*;
import java.io.*;

public class TestDeser
{
    public static void main(String[] args)
    {
        try
        {
            FileInputStream in = new FileInputStream("bean.xml");

            JOXBeanInputStream joxIn = new JOXBeanInputStream(in);

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
