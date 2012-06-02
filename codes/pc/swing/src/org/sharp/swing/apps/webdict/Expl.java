package org.sharp.swing.apps.webdict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sharp.jdkex.Utils;

public class Expl{
	
	public String formated;

	BufferedReader r;
	
	int parastart = 0;
	int paralen = 0;
	
	boolean streamend = false;

	public Sentence[] ses;
	
	private Expl(String path){
		try {
			InputStream is = new FileInputStream(new File(path));
			r = new BufferedReader(new InputStreamReader(is));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	private Expl(InputStream is){
		r = new BufferedReader(new InputStreamReader(is));
	}
	
	private Expl(Reader rd){
		r = new BufferedReader(rd);
	}
	
	private boolean hasNextPara(){
		try {
			boolean ret = r.ready()&&!streamend;
			if(streamend)
				streamend = !streamend;
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private Sentence[] nextPara(){
		Sentence[] ret = null;
		try {
			String line = r.readLine();
			if(line == null){
				streamend = true;
				return new Sentence[0];
			}
			paralen = line.length();// |[\\.!\\?\\:]+
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]+" +
				"[\u3000-\u303F\u4e00-\u9fa5\uff00-\uffef" +
				"\\p{Punct}\\p{Blank}&&[^\\*\\(\\=\\[]]*|^/[^/]*/");
			Matcher m = p.matcher(line);
			List sl = new ArrayList();
			int ss = 0;
			boolean matched = false;
			while(m.find()){
				matched = true;
				String se = line.substring(ss,m.end());
				Utils.log.debug(se);
				sl.add(new Sentence(se));
				ss = m.end();
			}
			if(!matched)
				ret = new Sentence[]{new Sentence(line)};
			else{
				String se = line.substring(ss,line.length());
				if(se.length()>0)
					sl.add(new Sentence(se));
				ret = (Sentence[])sl.toArray(new Sentence[]{});
			}
			parastart += paralen;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static Expl format(String input){
		Expl doc = new Expl(new StringReader(input));
		StringBuffer buf = new StringBuffer();
		List sl = new ArrayList();
		Sentence[] sa;
		while(doc.hasNextPara()){
			sa = doc.nextPara();
			for (int i = 0; i < sa.length; i++) {
				buf.append(sa[i].sentence+"\n");
				sl.add(sa[i]);
			}
			buf.append("\n");
		}
		doc.formated = buf.toString();
		doc.ses = (Sentence[])sl.toArray(new Sentence[0]);
		return doc;
	}

	public static class Sentence {
		String sentence;
		String epart;
		String cpart;
		
		public Sentence(String s){
			sentence = s;
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]+" +
					"[\u3000-\u303F\u4e00-\u9fa5\uff00-\uffef" +
					"\\p{Punct}\\p{Blank}&&[^\\*\\(\\=\\[]]*");
			if(sentence == null)
				return;
			Matcher m = p.matcher(sentence);
			if(m.find()){
				epart = sentence.substring(0,m.start());
				cpart = sentence.substring(m.start(),sentence.length());
			}
		}
		
		public String toString(){
			return sentence;
		}

		public String getEpart() {
			return epart;
		}

		public void setEpart(String epart) {
			this.epart = epart;
		}

		public String getCpart() {
			return cpart;
		}

		public void setCpart(String cpart) {
			this.cpart = cpart;
		}
		
	}
}

