package org.sharp.utils;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;

public class SdbUtils {

	static AmazonSimpleDB sdb;

	public static void init() {
		sdb = new AmazonSimpleDBClient(new BasicAWSCredentials("AKIAJNJN2HFUQBX7EGBQ",
				"gr+g1+pwyGVHGTbezChqo1c9WcFbBSuEznNn6hly"));
	}
	
	public static void createDomain(String domain){
		sdb.createDomain(new CreateDomainRequest(domain));
	}

	public static void putAttribute(String domain,String itemname,String attriname,String attrivalue) {
		List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();
		data.add(new ReplaceableItem().withName(itemname).withAttributes(
				new ReplaceableAttribute().withName(attriname)
						.withValue(attrivalue)/*,
				new ReplaceableAttribute().withName("Distance").withValue(
						"26.2")*/));
		sdb.batchPutAttributes(new BatchPutAttributesRequest(domain, data));
	}

	public static void queryDomain(String domain) {
		String qry = "select * from "+domain;
		SelectRequest selectRequest = new SelectRequest(qry);
		for (Item item : sdb.select(selectRequest).getItems()) {
			System.out.println("item name: " + item.getName());
		}
	}
}