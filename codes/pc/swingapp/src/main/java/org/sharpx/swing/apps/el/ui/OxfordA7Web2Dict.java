package org.sharpx.swing.apps.el.ui;

import static org.sharpx.utils.LangUtils.affix;
import static org.sharpx.utils.LangUtils.join;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Source;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.sharpx.utils.LangUtils;
import org.sharpx.utils.DsUtils;
import org.sharpx.swing.apps.el.beans.term.beans.Meaning;
import org.sharpx.swing.apps.el.beans.term.beans.PartOfSpeech;
import org.sharpx.swing.apps.el.beans.term.beans.Term;
import org.sharpx.utils.HttpCli;

public class OxfordA7Web2Dict {
	
	public static class Parser {
		
		PartOfSpeech pos = new PartOfSpeech();
		boolean showLog = false;
		
		public Parser(){
		}
		
		void parseNGs(Element hg){	//meaning
			List<Element> ngs = getChildElements(hg,"n-g");
			for (Iterator<Element> iterator = ngs.iterator(); iterator.hasNext();) {
				Element ng = iterator.next();
				parseNG(ng);
			}
		}
		
		private void parseNG(Element ng){
			Meaning m = new Meaning();
			String no = "";
			String grammar = "";
			String definition = "";
			String[] examples = new String[0];
			
			no = parseValue(ng,"z_n");
			grammar += join("[",parseOptValue(ng,"z_gr"),"]");
			grammar += join(parseChildValues(ng,"cf"), "," ,
					"(" , ")");
			
			definition += join(parseValues(ng,"dc"), "," ,
					"(" , ")");
			String d = parseOptValue(ng,"d");
			String ud = parseOptValue(ng,"ud");
			//String xr = parseOptValue(ng,"xr");	//synonym etc.
			definition += join(new String[]{d,ud},";");
			definition += join(parseOptXRG(ng) , ";", "{" , "}");	//synonym etc.
			examples = parseXGs(ng);
			
			m.setNo(no);
			m.setGrammar(grammar);
			m.setDefinition(definition);
			m.setExamples(examples);
			
			pos.setMeaning(LangUtils.add(pos.getMeaning(), m));
		}
		
		private String[] parseXGs(Element ng) {	//example sentence
			List<String> examples = new ArrayList<String>();
			List<Element> xgs = ng.getAllElementsByClass("x-g");
			for (Iterator<Element> iterator = xgs.iterator(); iterator.hasNext();) {
				Element xg = iterator.next();
				String example = "";
				example += affix(parseOptValue(xg,"cf"),": ");
				example += join("(",parseOptValue(xg,"z_r"),")");	//(informal)
				example += affix(parseOptValue(xg,"z_g")," ");
				example += parseValue(xg,"x");
				
				examples.add(example);
			}
			return DsUtils.toArray(examples, String.class);
		}
		
		private String[] parseOptXRG(Element hg){
			Element xrg = hg.getFirstElementByClass("xr-g");
			if(xrg!=null)
				return parseXRs(xrg);
			else
				return null;
		}
		
		private String[] parseXRs(Element xrg) {	//synonym
			List<String> ret = new ArrayList<String>();
			List<Element> xgs = xrg.getAllElementsByClass("x-g");
			for (Iterator<Element> iterator = xgs.iterator(); iterator.hasNext();) {
				Element xg = iterator.next();
				ret.add(parseValue(xg,"z_xr")+"->"+parseValue(xg,"z_xh_eb"));
			}
			return DsUtils.toArray(ret, String.class);
		}
		
		private String parseOptValue(Element ele,String cla){
			Element el = ele.getFirstElementByClass(cla);
			if(el!=null){
				String value = el.getTextExtractor().toString();
				log("<"+cla+">"+value);
				return DsUtils.defaultIfNull(value,"");
			}
			return "";
		}
		
		private void log(String msg){
			if(showLog)
				System.out.println(msg);
		}
		
		private String[] parseChildValues(Element ele,String cla){
			List<String> strs = new ArrayList<String>();
			List<Element> children = ele.getChildElements();
			for (Iterator<Element> iterator = children.iterator(); iterator.hasNext();) {
				Element child = iterator.next();
				if(child!=null && LangUtils.equals(child.getAttributeValue("class"),cla)){
					String value = child.getTextExtractor().toString();
					log("<"+cla+">"+value);
					if(value != null)
						strs.add(value);
				}
			}
			return DsUtils.toArray(strs,String.class);
		}
		
		public static List<Element> getChildElements(Element ele,final String cla){
			List<Element> children = ele.getChildElements();
			return (List<Element>) CollectionUtils.select(children, new Predicate() {
				public boolean evaluate(Object o) {
					if(o!=null && o instanceof Element && 
							LangUtils.equals(((Element)o).getAttributeValue("class"),cla))
						return true;
					return false;
				}
			});
		}
		
		private String[] parseValues(Element ele,String cla){
			List<String> strs = new ArrayList<String>();
			List<Element> eles = ele.getAllElementsByClass(cla);
			for (Iterator<Element> iterator = eles.iterator(); iterator.hasNext();) {
				Element sub = iterator.next();
				if(sub!=null){
					String value = sub.getTextExtractor().toString();
					log("<"+cla+">"+value);
					if(value!=null)
						strs.add(value);
				}
			}
			return DsUtils.toArray(strs, String.class);
		}
		private String parseValue(Element ele,String cla, String def){
			Element el = ele.getFirstElementByClass(cla);
			if(el!=null){
				String value = el.getTextExtractor().toString();
				log("<"+cla+">"+value);
				return DsUtils.defaultIfNull(value,def);
			}
			return def;
		}
		
		private String parseValue(Element ele,String cla){
			return parseValue(ele,cla, "");
		}
		
		protected void parseHG(Element entry){
			Element hg = entry.getFirstElementByClass("h-g");
			
			parseValue(hg,"h");
			pos.setPartofspeech(parseValue(hg,"pos","alias"));
			parseOptValue(hg,"Ref");	//TODO lookup term "center", refering to term "centre"
			
			Element bri_pro = hg.getContent().getFirstElementByClass("i");
			if(bri_pro!=null){
				Element i_pro = bri_pro.getFirstElementByClass("sound");
				if(i_pro!=null)
					log(bri_pro.getAttributeValue("onclick"));
			}
			
			Element ame_pro =  hg.getContent().getFirstElementByClass("y");
			if(ame_pro!=null){
				Element y_pro =	ame_pro.getFirstElementByClass("sound");
				if(y_pro!=null)
					log(ame_pro.getAttributeValue("onclick"));
			}
			
			parseNGs(hg);
		}
		public PartOfSpeech parse(Reader input) {
			try {
				Source source = new Source(input);
				Element entry = source.getFirstElementByClass("entry");
				parseHG(entry);
				parseSDGs(entry);
				parseNGs(entry);
				if(LangUtils.isEmpty(pos.getMeaning()))
					parseNG(entry);
				//parseIFSG(entry);		//
				//parseIDGs(entry);		//idoms
				//parsePVPGs(entry);		//phrase verbs
				//parseUNBOXs(entry);	//usage notes box
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return pos;
		}

		public static String parse(Reader input,String eleClass) {
			String ret = "";//"entry"
			try {
				Source source = new Source(input);
				Element entry = source.getFirstElementByClass(eleClass);
				//ret = entry.getTextExtractor().toString();
				ret = new Renderer(entry).toString();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return ret;
		}
		
		public static Map<String,String> parsePOSs(Reader input) {
			Map<String,String> poses = new HashMap<String,String>();
			try {
				Source source = new Source(input);
				Element didyoumean = source.getFirstElement("id","didyoumean",true);
				if(didyoumean!=null)	//TODO lookup term "bude"
					return null;
				Element sr = source.getFirstElement("id","search-results",true);
				if(sr!=null)			//TODO lookup term "PPPD"
					return null;
				
				Element relatedEntries = source.getFirstElement("id","relatedentries",true);
				List<Element> el = relatedEntries.getAllElements("li");
				for (Element element : el) {
					String[] pos = parsePOS(element);
					if(pos!=null)
					poses.put(pos[0],pos[1]);
				}
				return poses;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return null;
		}
		
		private static String[] parsePOS(Element el){
			Element link = el.getFirstElement("a");
			Element arl1 = link.getFirstElementByClass("arl1");
			if(arl1 == null)
				return null;
			
			String href = link.getAttributeValue("href");
			Element posE = arl1.getFirstElement("pos");
			String pos = "alias";
			if(posE!=null)
				pos = posE.getTextExtractor().toString();
			
			String[] ret = new String[]{pos,href};
			System.out.println("POS:"+join(ret,","));
			return ret;
		}

		protected void parseSDGs(Element entryContent) {
			List<Element> sdgs = entryContent.getAllElementsByClass("sd-g");
			for (Iterator<Element> iterator = sdgs.iterator(); iterator.hasNext();) {
				Element sdg = iterator.next();
				parseValue(sdg,"sd");
				parseNGs(sdg);
			}
		}
	}
	
	public static Map<String,String> lookup2(String word){
		Reader input = fetchHtml(word);
		Map<String,String> poses = Parser.parsePOSs(input);
		for (String pos : poses.keySet()) {
			String href = poses.get(pos);
			Reader html = fetchHtml(href);
			String entry = Parser.parse(html, "entry");
			poses.put(pos, entry);
		}
		return poses;
	}
	
	public static Reader fetchHtml(String word){
		String uri = wordUrl(word);
		Map<String, String> map = new HashMap<String, String>();
		/*map.put("searchType", "dictionary");*/
		Reader input = HttpCli.post(uri, map);
		return input;
	}
	
	public static String wordUrl(String word){
		String uri = "http://www.oxfordadvancedlearnersdictionary.com/dictionary/"+word;
		return uri;
	}

}
