package org.sharpx.crawler;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.sharpx.utils.FsUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler {

	private final static Pattern FILTERS = Pattern
			.compile(".*(\\.(css|js|bmp|gif|jpe?g"
					+ "|png|tiff?|mid|mp2|mp3|mp4"
					+ "|wav|avi|mov|mpeg|ram|m4v|pdf"
					+ "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */

	private static String INDEX_PREFX = "http://www.oxfordlearnersdictionaries.com/browse/english/";
	private static String ENTRY_PREFIX = "http://www.oxfordlearnersdictionaries.com/definition/english/";

	private MongoClient mongoClient;

	private DB db;

	private DBCollection coll;

	private Database couchdb;

	private long filenum;

	public Crawler() {
		super();
		//mongoSetup();
		//couchSetup("169.254.224.110","oxford-htmls");
	}

	private void mongoSetup() {
		try {
			mongoClient = new MongoClient("127.0.0.1");
			db = mongoClient.getDB("oxford");
			coll = db.getCollection("htmls");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private void couchSetup(String host,String dbname) {
        //Server server = new ServerImpl(host);  
        //server.createDatabase(dbname);  
          
        couchdb = new Database(host, dbname);  
          
	}

	private void couchSave(String url,String type,String html){
        // create a hash map document with two fields      
        Map<String,String> doc = new HashMap<String, String>();  
        doc.put("url", url);
        doc.put("type", type);
        doc.put("html", html);  
  
        // create the document in couchdb  
        couchdb.createDocument(doc);  		
        //System.out.println("///2");
	}
	
	@Override
	public boolean shouldVisit(WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches()
				&& (href.startsWith(INDEX_PREFX) || href
						.startsWith(ENTRY_PREFIX));
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		System.out.println("xxURL: " + url);

		if (page.getParseData() instanceof HtmlParseData) {
			try{
				HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				String text = htmlParseData.getText();
				String html = htmlParseData.getHtml();
				List<WebURL> links = htmlParseData.getOutgoingUrls();
	
				String type = "";
				if (url.startsWith(INDEX_PREFX)) {
					type = "i";
				} else if (url.startsWith(ENTRY_PREFIX)) {
					type = "e";
				}
	
				System.out.println("///");
				//mongoSave(url, html,type);
				//couchSave(url,type,html);
				fileSave(url,type,html);
				System.out.println("saved to file.");
			}catch(Exception e){
				System.out.println("error save to file.");
			}
		}else{
			System.out.println("???");
		}
	}
	
	private void fileSave(String url,String type, String html) {
        Map<String,String> doc = new HashMap<String, String>();  
        doc.put("url", url);
        doc.put("type", type);
        doc.put("html", html);  
        
        long threadId = Thread.currentThread().getId();
        FsUtils.saveJson(doc, "/media/sf_cross-vm-data/web-dict/oxford/htmls/"+ threadId+"-"+(filenum++) +".json");
	}

	private void mongoSave(String url, String html,String type) {
		BasicDBObject doc = new BasicDBObject("url", url).append("html", html)
				.append("type", type);
		WriteResult ret = coll.insert(doc);
		//if(ret.)
	}
}