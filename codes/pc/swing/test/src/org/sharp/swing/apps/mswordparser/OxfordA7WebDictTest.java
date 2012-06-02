package org.sharp.swing.apps.mswordparser;

import java.io.Reader;

import junit.framework.TestCase;

import org.sharp.jdkex.JdkUtils;
import org.sharp.swing.apps.el.OxfordA7WebDict;
import org.sharp.swing.apps.el.beans.term.beans.PartOfSpeech;
import org.sharp.swing.apps.el.beans.term.beans.Term;

public class OxfordA7WebDictTest extends TestCase {

	public void testFetchHtml() {
		Reader r = OxfordA7WebDict.fetchHtml("bout");
		System.out.println(JdkUtils.readerToString(r));
	}
	
	public void testParseLocalBout(){
		PartOfSpeech pos = new OxfordA7WebDict.Parser().parse(JdkUtils.getResourceAsReader(
				"org/sharp/swing/apps/mswordparser/res/bout.html"));
		System.out.println(">>>>>>"+pos.toString());
	}

	public void testParseLocalTake(){
		new OxfordA7WebDict.Parser().parse(JdkUtils.getResourceAsReader(
				"org/sharp/swing/apps/mswordparser/res/take.html"));
	}

	/*public void testParseLocalTake2(){
		String content = new OxfordA7WebDict.Parser().parse2(JdkUtils.getResourceAsReader(
				"org/sharp/swing/apps/mswordparser/res/take.html"));
		System.out.println(content);
	}*/

	public void testParseLocalTake3(){
		PartOfSpeech pos = new OxfordA7WebDict.Parser().parse(JdkUtils.getResourceAsReader(
				"org/sharp/swing/apps/mswordparser/res/take.html"));
		System.out.println(">>>>>>"+pos.toString());
	}

	public void testParseLocalIntermediate(){
		PartOfSpeech pos =new OxfordA7WebDict.Parser().parse(JdkUtils.getResourceAsReader(
				"org/sharp/swing/apps/mswordparser/res/intermediate.html"));
		System.out.println(">>>>>>"+pos.toString());
	}
	
	public void testParseLocalIntermediatePOS(){
		new OxfordA7WebDict.Parser().parsePOSs(JdkUtils.getResourceAsReader(
				"org/sharp/swing/apps/mswordparser/res/intermediate.html"));
	}
	
	public void testlookupIntermediate(){
		Term t = new OxfordA7WebDict().lookup("intermediate");
		System.out.print(t.toString());
	}
	
	public void testlookupWrongWord(){
		Term t = new OxfordA7WebDict().lookup("pdocastti");
		assertEquals(t, null);
	}
	
	public void testlookupTake(){
		Term t = new OxfordA7WebDict().lookup("take");
		System.out.print(t.toString());
	}
}
