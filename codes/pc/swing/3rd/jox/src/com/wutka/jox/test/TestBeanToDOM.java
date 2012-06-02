package com.wutka.jox.test;

import com.wutka.jox.*;
import java.io.*;
import org.w3c.dom.*;

public class TestBeanToDOM
{
    public static void main(String[] args)
    {
        try
        {
            FileInputStream in = new FileInputStream("bean.xml");

            JOXBeanInputStream joxIn = new JOXBeanInputStream(in);

            TestBean testBean = (TestBean) joxIn.readObject(
                TestBean.class);

			JOXBeanDOM beanDOM = new JOXBeanDOM();
			Document doc = beanDOM.beanToDocument("testbean", testBean);

			Element element = doc.getDocumentElement();
			dumpElement(element, "");
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
    }

	public static void dumpElement(Element element, String indent)
	{
		System.out.println(indent+"Element: "+element.getNodeName());
		NamedNodeMap attributes = element.getAttributes();

		if ((attributes != null) && (attributes.getLength() > 0))
		{
			System.out.println(indent+"Attributes:");
			for (int i=0; i < attributes.getLength(); i++)
			{
				Node attr = attributes.item(i);
				System.out.println(indent+"  "+attr.getNodeName()+"="+attr.getNodeValue());
			}
		}

		NodeList children = element.getChildNodes();

		for (int i=0; i < children.getLength(); i++)
		{
			Node n = children.item(i);

			if (n instanceof Element)
			{
				dumpElement((Element) n, indent+"    ");
			}
			else if (n instanceof CharacterData)
			{
				System.out.println(indent+"    "+((CharacterData)n).getData());
			}
		}
	}
}
