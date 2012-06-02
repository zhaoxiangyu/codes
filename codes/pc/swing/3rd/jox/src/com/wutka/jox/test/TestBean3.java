package com.wutka.jox.test;

import com.wutka.jox.*;
import java.util.*;

public class TestBean3 implements java.io.Serializable
{
    protected int foo;
    protected String bar;
    protected java.util.Date baz;
    protected int[] quux;
    protected Vector thingies;
    protected Vector subbeans;

    public TestBean3()
    {
        bar = "";
        baz = new Date();
        quux = new int[0];
        thingies = new Vector();
        subbeans = new Vector();
    }

    public int getFoo() { return foo; }
    public void setFoo(int aFoo) { foo = aFoo; }

    public String getBar() { return bar; }
    public void setBar(String aBar) { bar = aBar; }

    public java.util.Date getBaz() { return baz; }
    public void setBaz(java.util.Date aBaz) { baz = aBaz; }

    public int[] getQuux() { return quux; }
    public void setQuux(int[] aQuux) { quux = aQuux; }

    public String[] getThingies()
    {
        String[] retThingies = new String[thingies.size()];
        if (thingies.size() > 0) thingies.copyInto(retThingies);

        return retThingies;
    }

    public void setThingies(String[] newThingies)
    {
        thingies = new Vector(newThingies.length);
        for (int i=0; i < newThingies.length; i++)
        {
            thingies.addElement(newThingies[i]);
        }
    }

    public TestSubbean[] getSubbeans()
    {
        TestSubbean[] retSubbeans = new TestSubbean[subbeans.size()];
        if (subbeans.size() > 0) subbeans.copyInto(retSubbeans);

        return retSubbeans;
    }

    public void setSubbeans(TestSubbean[] newSubbeans)
    {
        subbeans = new Vector(newSubbeans.length);
        for (int i=0; i < newSubbeans.length; i++)
        {
            subbeans.addElement(newSubbeans[i]);
        }
    }

    public String toString()
    {
        StringBuffer ret = new StringBuffer(
            "foo="+foo+";bar="+bar+";baz="+baz.toString()+
            ";thingies=");
        for (int i=0; i < thingies.size(); i++)
        {
            if (i > 0) ret.append(",");
            ret.append((String) thingies.elementAt(i));
        }
        ret.append(";quux=");
        for (int i=0; i < quux.length; i++)
        {
            if (i > 0) ret.append(",");
            ret.append(quux[i]);
        }

        ret.append(";subbeans=");
		for (int i=0; i < subbeans.size(); i++)
		{
            if (i > 0) ret.append("|");
	        ret.append(subbeans.elementAt(i).toString());
		}

        return ret.toString();
    }
}
