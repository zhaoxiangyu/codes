package com.wutka.jox.test;

import com.wutka.jox.*;
import java.io.*;

public class TestWriterSer
{
    public static void main(String[] args)
    {
        try
        {
            TestBean b = new TestBean();
            b.setFoo(5);
            b.setBar("This is the bar value");
            b.setThingies(new String[] {
                "Moe", "Larry", "Curly", "Shemp", "Curly Joe" });
            TestSubbean sub = new TestSubbean();
            sub.setName("Mark");
            sub.setAge(35);
            b.setSub(sub);

            FileWriter fileOut = new FileWriter("bean2.xml");
            JOXBeanWriter joxOut = new JOXBeanWriter(fileOut);

            joxOut.writeObject("MarkTest", b);

            joxOut.close();
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
