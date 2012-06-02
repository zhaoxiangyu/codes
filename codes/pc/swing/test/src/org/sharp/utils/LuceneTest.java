package org.sharp.utils;

import org.sharp.jdkex.LangUtils;

import junit.framework.TestCase;

public class LuceneTest extends TestCase {

	public void testEnglishStem() {
		System.out.println(Lucene.englishStem("works"));
		System.out.println(Lucene.englishStem("visualizations"));
		System.out.println(Lucene.englishStem("visualize"));
		System.out.println(Lucene.englishStem("This"));
	}
	
	public void testdeleteAllDocs(){
		new Lucene().deleteAll("E:/temp");
	}
	
	public void testHighlight(){
		String[] sa = Lucene.TextHighlighter.highlight("Range", "a Range has meaning of a,but ...");
		System.out.println("matched:"+LangUtils.join(sa, "\n"));
	}

}
