package org.sharp.swing.apps.jpwords;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.sharp.jdkex.Utils;

public class JpTerm implements Comparable<JpTerm> {

	public static final int[] REVIEW_DAYS = {1,2,3,5,10,30,90,180,360};

	String key;

	String keyvoice;
	
	TermExtra info = new TermExtra();
	
	private File termdir;

	Expl oExpl;
	
	public static JpTerm createByMp3(String baseDir, final File mp3file){
		final JpTerm term = new JpTerm();
		term.key = FilenameUtils.getBaseName(mp3file.getPath());
		term.keyvoice = mp3file.getPath();
		term.oExpl = Expl.parse(term.key);
		term.termdir = new File(
				FilenameUtils.concat(baseDir, term.key));
		
		if(!"".equals(term.expl()) || term.keyvoice!= null){
			Date currenttime = new Date();
			term.info.setLookupt(currenttime);
			term.info.setScore(0);
			term.cpVoice();
			term.save();
		}
		return term;
	}
	
	private void cpVoice(/*String basedir*/) {
		if(key == null)
			return;
		try {
			if(keyvoice!=null){
				File voicefile = new File(keyvoice);
				FileUtils.copyFileToDirectory(voicefile, termdir);
				keyvoice = FilenameUtils.concat(termdir.getPath(), voicefile.getName());
			}
			/*FileUtils.writeStringToFile(new File(termdir,key+".txt"), expl, "utf-8");*/
		} catch (IOException e) {
			Utils.log.error(e);
		}
	}
	
	public void save(/*String basedir*/) {
		if(key == null)
			return;
		Utils.saveJox(info, FilenameUtils.concat(termdir.getPath(),"info.txt"));
		Utils.saveJox(oExpl, FilenameUtils.concat(termdir.getPath(),"expl.txt"));
	}
	
	public static JpTerm fromDisk(String dir,String word){
		JpTerm t = new JpTerm();
		t.key = word;
		try {
			t.termdir = new File(
					FilenameUtils.concat(dir, t.key));
			Date lookuptime = new Date(t.termdir.lastModified());
			TermExtra info = (TermExtra)Utils.loadJox2(
					FilenameUtils.concat(t.termdir.getPath(),"info.txt"), 
					TermExtra.class);
			if(info!=null)
				t.info = info;
			else{
				t.info.setLookupt(lookuptime);
			}
			Expl expl = (Expl)Utils.loadJox2(
					FilenameUtils.concat(t.termdir.getPath(),"expl.txt"), 
					Expl.class);
			if(expl!=null)
				t.oExpl = expl;
			else{
				t.oExpl = Expl.parse(t.key);
			}
			t.keyvoice = new File(t.termdir.getPath(),
					t.key+".mp3").getPath();
		} catch (Exception e) {
			Utils.log.error("", e);
			return null;
		}
		return t;
	}

	public int compareTo(JpTerm t) {
		int ret = -1;
		ret = info.level - t.info.level;
		if(ret == 0){
			if(info.lastrvt!=null){
				if(t.info.lastrvt!=null)
					ret = info.lastrvt.compareTo(t.info.lastrvt);
				else
					ret = 1;
			}else{
				if(t.info!=null)
					ret = -1;
				else
					ret = 0;
			}
		}
		if(ret == 0)
			ret = t.info.lookupt.compareTo(info.lookupt);
		return ret;
	}

	public void rmFromDisk() {
		try {
			FileUtils.deleteDirectory(termdir);
		} catch (IOException e) {
			Utils.log.error("",e);
		}
	}
	
	public String expl() {
		return oExpl.toString()+"\n"+key;
	}
	
	public String writting(){
		return oExpl.writting;
	}
	
	public String spellHint(){
		return oExpl.hiragana;
	}

	public static class Expl{
		
		private String hiragana="";
		private String katakana="";
		private String meaning="";

		private String writting="";
		private String partofspeech="";
		private boolean error = false;
		
		private Expl(){
		}
		
		public static Expl parse(String input){
			Expl doc = new Expl();
			Matcher m = Pattern.compile("^[\u3040-\u309F]+").matcher(input);
			if(m.find()){
				doc.hiragana = m.group();
			}
			
			m = Pattern.compile("^[\u30A0-\u30FF]+").matcher(input);
			if(m.find()){
				doc.katakana = m.group();
			}
			
			m = Pattern.compile("\uff08([^\uff09]+)\uff09").matcher(input);
			if(m.find()){
				doc.writting = m.group(1);
			}
			
			m = Pattern.compile("\u3014([^\u3015]+)\u3015").matcher(input);
			if(m.find()){
				doc.partofspeech = m.group(1);
			}

			m = Pattern.compile("\u3015\\s*([^\u3015\uff09\\s]+)$").matcher(input);
			if(m.find()){
				doc.meaning = m.group(1);
			}
			m = Pattern.compile("\uff09\\s*([^\u3015\uff09\\s]+)$").matcher(input);
			if(m.find()){
				doc.meaning = m.group(1);
			}
			return doc;
		}
		
		public String toString(){
			return meaning;
		}

		public String getHiragana() {
			return hiragana;
		}

		public void setHiragana(String hiragana) {
			this.hiragana = hiragana;
		}

		public String getKatakana() {
			return katakana;
		}

		public void setKatakana(String katakana) {
			this.katakana = katakana;
		}

		public String getMeaning() {
			return meaning;
		}

		public void setMeaning(String meaning) {
			this.meaning = meaning;
		}

		public String getWritting() {
			return writting;
		}

		public void setWritting(String writting) {
			this.writting = writting;
		}

		public String getPartofspeech() {
			return partofspeech;
		}

		public void setPartofspeech(String partofspeech) {
			this.partofspeech = partofspeech;
		}

		public boolean isError() {
			return error;
		}

		public void setError(boolean error) {
			this.error = error;
		}

	}

	public static class TermExtra{
		
		Date lookupt;
		int score = 0;
		int level = 0;
		Date lastrvt;
		public Date getLookupt() {
			return lookupt;
		}
		public void setLookupt(Date lookupt) {
			this.lookupt = lookupt;
		}
		public int getScore() {
			return score;
		}
		public void setScore(int score) {
			this.score = score;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public Date getLastrvt() {
			return lastrvt;
		}
		public void setLastrvt(Date lastrvt) {
			this.lastrvt = lastrvt;
		}
		public void oneLevelUp() {
			level++;
			lastrvt = new Date();
		}
		public void oneLevelDown() {
			if(level-1 >=0){
				level--;
				lastrvt = new Date();
			}
		}
		public int schRvc(){
			Date at = new Date();
			int days = (int) ((at.getTime()-lookupt.getTime())/(24*3600*1000L));
			int ret = 0;
			if(days>0){
				for (int i = 0; i < REVIEW_DAYS.length; i++) {
					if(days < REVIEW_DAYS[i]){
						ret = i;
						break;
					}
				}
			}
			return ret;
		}
	}
	
}

