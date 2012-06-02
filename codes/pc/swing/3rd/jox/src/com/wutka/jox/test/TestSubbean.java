package com.wutka.jox.test;

import com.wutka.jox.*;

public class TestSubbean implements java.io.Serializable
{
    protected String name;
    protected int age;

    public TestSubbean()
    {
    }

    public String getName() { return name; }
    public void setName(String aName) { name = aName; }

    public int getAge() { return age; }
    public void setAge(int anAge) { age = anAge; }

    public String toString()
    {
        return "name:"+name+";age="+age;
    }
}
