package org.sharp.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlUtils {

	public static String[] xpath(String fp,String exp){
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(fp);
			if(file==null || !file.exists() || !file.isFile())
				return list.toArray(new String[0]);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fp);	
			
			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile(exp);
		    Object result = expr.evaluate(doc, XPathConstants.NODESET);
		    NodeList nodes = (NodeList) result;
		    for (int i = 0; i < nodes.getLength(); i++) {
		    	list.add(nodes.item(i).getNodeValue()); 
		    }		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.toArray(new String[0]);
	};
}
