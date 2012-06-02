package org.sharp.swing.apps.webdict2.utils;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.webdict2.beans.Meaning;
import org.sharp.swing.apps.webdict2.beans.PartOfSpeech;
import org.sharp.swing.apps.webdict2.beans.Term;
import org.sharp.swing.apps.webdict2.beans.WebDict;
import org.sharp.utils.HttpCli;

public class OxfordWebDict implements WebDict {

	public Term lookup(String word) {
		final Term t = new Term();

		Reader input = fetchHtml(word);
		try {
			Source source = new Source(input);
			Element el = source.getElementById("pagetitle");
			if("span".equals(el.getName())){
				t.setKey(el.getContent().toString());
			}else{
				Utils.log.debug(el.toString());
				return null;
			}
			
			Element pro = source.getFirstElementByClass("pronunciation")
				.getContent().getFirstElement("span");
			t.setPronunciation(pro.getContent().toString());
			
			Segment senseGroup = (Segment)ObjectUtils.defaultIfNull(
					source.getNextElementByClass(pro.getEnd(),"senseGroup"),
					source);
			List<Element> poss = senseGroup.getAllElementsByClass("partOfSpeech");
			List<Element> poss2 = new ArrayList<Element>();
			for (Iterator<Element> iterator = poss.iterator(); iterator.hasNext();) {
				Element element = iterator.next();
				if(element.getName().equalsIgnoreCase("h3")){
					poss2.add(element);
				}
			}
			List<Element> ses = senseGroup.getAllElementsByClass("sense-entry");
			for(int i=0;i < poss2.size()&&poss2.size()==ses.size();i++){
				Element partOfSpeech = poss2.get(i);
				Element senseEntries = ses.get(i);
				
				PartOfSpeech pos = new PartOfSpeech();
				pos.setPartofspeech(partOfSpeech
						.getContent()
						.getFirstElement("span")
						.getContent()
						.toString());
				List<Element> meanings = senseEntries.getAllElementsByClass("senseInnerWrapper");
				for (Iterator<Element> iterator = meanings.iterator(); iterator
						.hasNext();) {
					Element meaning = iterator.next();
					Meaning mean = new Meaning();
					Element iteration = meaning.getFirstElementByClass("iteration");
					if(iteration!=null&&iteration.getContent()!=null)
						mean.setNo(iteration.getContent().toString());
					else
						mean.setNo("*");
					mean.setDefinition(meaning.getFirstElementByClass("definition").getContent().toString());
					
					List<Element> examples = meaning.getAllElementsByClass("example");
					for (Iterator<Element> iterator2 = examples.iterator(); iterator2
							.hasNext();) {
						Element element = iterator2.next();
						String examp = element.getContent().toString();
						mean.setExamples((String[]) ArrayUtils.add(mean.getExamples(), examp));
					}
					pos.setMeaning((Meaning[]) ArrayUtils.add(pos.getMeaning(), mean));
				}
				
				t.setPses((PartOfSpeech[]) ArrayUtils.add(t.getPses(), pos));
			}
			/*t.termdir = new File(
					FilenameUtils.concat(FilenameUtils.concat(System.getProperty("user.dir"),"terms2"), 
					t.getKey()));*/
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Utils.log.info(t);
		return t/*(OxfordUrlTerm)CacheProxy.cachedCallProxy(t)*/;
	}

	static Reader fetchHtml(String word){
		String uri = "http://oxforddictionaries.com/search";
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchType", "dictionary");
		map.put("isWritersAndEditors", "true");
		map.put("searchUri", "all");
		map.put("contentVersion", "WORLD");
		map.put("q", word);
		Reader input = HttpCli.post(uri, map);
		return input;
	}
	
}
