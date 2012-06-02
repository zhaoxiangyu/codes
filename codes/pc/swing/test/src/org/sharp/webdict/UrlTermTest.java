package org.sharp.webdict;

import java.util.regex.Pattern;

import junit.framework.TestCase;

public class UrlTermTest extends TestCase {

	public void testLookup() {
		/*new UrlTerm().lookupDictCN("big");*/
		/*fail("Not yet implemented");*/
	}

	public void testLookupOxford() {
		/*new UrlTerm().lookupOxford("man");*/
		/*fail("Not yet implemented");*/
	}
	
	public void testRegExp() {
		Pattern.compile("[\u4e00-\u9fa5]+" +
				"[\u3000-\u303F\u4e00-\u9fa5\uff00-\uffef" +
				"\\p{Punct}\\p{Blank}&&[^\\*\\(\\=]]*|/[^/]*/");
	}

}
