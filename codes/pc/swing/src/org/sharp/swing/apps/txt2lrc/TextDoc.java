package org.sharp.swing.apps.txt2lrc;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.sharp.jdkex.Utils;

public class TextDoc {
	
	BufferedReader r;
	
	int parastart = 0;
	int paralen = 0;
	List<Sentence> sentences = new ArrayList<Sentence>();
	
	boolean streamend = false;
	
	public TextDoc(String path){
		try {
			InputStream is = new FileInputStream(new File(path));
			r = new BufferedReader(new InputStreamReader(is));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	public TextDoc(InputStream is){
		r = new BufferedReader(new InputStreamReader(is));
	}
	
	public TextDoc(Reader rd){
		r = new BufferedReader(rd);
	}
	
	public String nextWord(){
		return "";
	}

	public void sentenceAt(double progress,MutableInt begin,MutableInt end){
		int len = parastart + paralen + sentences.size()-1;
		int pos = new Double(len*progress).intValue();
		Utils.log.debug("len:"+len+",pos:"+pos);
		for (int i=0; i<sentences.size();i++) {
			int sb1 = sentences.get(i).posindoc+i;
			int sb2 = 0;
			if(i+1 != sentences.size())
				sb2 = sentences.get(i+1).posindoc+i;
			else
				sb2 = len;
			
			if(pos >= sb1 && pos < sb2){
				begin.setValue(sb1);
				end.setValue(sb2);
				break;
			}
			pos ++;
		}
	}
	
	public boolean hasNextPara(){
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
	
	public Sentence[] nextPara(){
		Sentence[] ret = null;
		try {
			String line = r.readLine();
			if(line == null){
				streamend = true;
				return new Sentence[0];
			}
			paralen = line.length();
			Pattern p = Pattern.compile("[\\.!\\?]+");
			Matcher m = p.matcher(line);
			List sl = new ArrayList();
			int ss = 0;
			boolean matched = false;
			while(m.find()){
				matched = true;
				String se = line.substring(ss,m.end());
				Utils.log.debug(se);
				sl.add(new Sentence(se,parastart+ss,this));
				ss = m.end();
			}
			if(!matched){
				Sentence s = new Sentence(line,parastart,this);
				ret = new Sentence[]{s};
				sentences.add(s);
			}else{
				ret = (Sentence[])sl.toArray(new Sentence[]{});
				sentences.addAll(sl);
			}
			parastart += paralen;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static String format(String input,int maxwidth){
		TextDoc doc = new TextDoc(new StringReader(input));
		StringBuffer buf = new StringBuffer();
		while(doc.hasNextPara()){
			Sentence[] ses = doc.nextPara();
			for (int i = 0; i < ses.length; i++) {
				/*String[] ps = ses[i].toPhrases(maxwidth);*/
				/*buf.append("["+((Sentence)ses[i]).posinodc+"]\n");*/
				/*for (int j = 0; j < ps.length; j++) {
					buf.append(ps[j]+"\n");
				}*/
				buf.append(ses[i]+"\n");
			}
			buf.append("\n");
		}
		return buf.toString();
	}
}

class Sentence {
	String[] words;
	public int posindoc;
	TextDoc doc;
	
	public Sentence(String s,int posindoc,TextDoc doc){
		this.words = s.split("\\s");
		this.posindoc = posindoc;
		this.doc = doc;
	}
	
	public String toString(){
		return StringUtils.join(words,' ');
	}
	
	public String[] toPhrases(int len){
		StringBuffer sb = new StringBuffer();
		List li = new ArrayList();
		for (int i = 0; i < words.length; i++) {
			int explen = 0;
			if(i!=words.length-1){
				explen = sb.length()+words[i].length()+1;
			}else{
				explen = sb.length()+words[i].length();
			}
			if(explen>len){
				li.add(sb.toString());
				sb = new StringBuffer();
			}
			
			if(i!=words.length-1)
				sb.append(words[i]+" ");
			else{
				sb.append(words[i]);
				li.add(sb.toString());
			}
		}
		return (String[])li.toArray(new String[]{});
	}
}
