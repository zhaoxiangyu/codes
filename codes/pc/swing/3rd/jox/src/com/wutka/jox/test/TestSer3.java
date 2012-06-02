package com.wutka.jox.test;

import com.wutka.jox.*;
import java.io.*;

public class TestSer3
{
    public static void main(String[] args)
    {
        try
        {
            TestBean3 b = new TestBean3();
            b.setFoo(5);
            b.setBar("This is the bar value");
            b.setThingies(new String[] {
                "Moe", "Larry", "Curly", "Shemp", "Curly Joe" });
            b.setQuux(new int[] { 1,2,3,4,5 });
            TestSubbean sub = new TestSubbean();
            sub.setName("Mark");
            sub.setAge(35);

            TestSubbean sub2 = new TestSubbean();
            sub2.setName("Sammy");
            sub2.setAge(7);

            b.setSubbeans(new TestSubbean[] { sub, sub2 });

            FileOutputStream fileOut = new FileOutputStream("bean3.xml");
            JOXBeanOutputStream joxOut = new JOXBeanOutputStream(fileOut);

            joxOut.writeObject("MarkTest", b);

            joxOut.close();
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
