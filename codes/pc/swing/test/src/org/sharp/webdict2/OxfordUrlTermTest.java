package org.sharp.webdict2;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

public class OxfordUrlTermTest extends TestCase {

	/*public void testLookupOxford() {
		OxfordUrlTerm.lookupOxford("strut");
	}*/

	public void testHttpDownload() throws IOException{
		URL url = new URL("http://web-word-list.googlecode.com/files/readme.txt");
		FileUtils.copyURLToFile(url, new File("c:/Temp/code.google.txt"));
		url = new URL("http://code.google.com/p/web-word-list/downloads/detail?name=readme.txt&can=2&q=");
		FileUtils.copyURLToFile(url, new File("c:/Temp/code.google2.txt"));
	}

	/*public void testFetchHtml() throws IOException{
		Reader input = OxfordUrlTerm.fetchHtml("strut");
		IOUtils.copy(input, System.out);
	}*/
}
