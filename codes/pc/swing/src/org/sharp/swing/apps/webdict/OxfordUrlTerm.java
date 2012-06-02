package org.sharp.swing.apps.webdict;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.sharp.jdkex.LangUtils;
import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.webdict.Expl.Sentence;
import org.sharp.utils.FsUtils;
import org.sharp.utils.HttpCli;

public class OxfordUrlTerm implements Comparable<OxfordUrlTerm> {

	String key;
	
	String keyvoice;
	
	String expl="";
	
	String be = "";
	
	Sentence[] nsamples;
	
	SentenceHint[] samples;
	
	TermExtra info = new TermExtra();
	
	public static final int[] REVIEW_DAYS = {1,2,4,8,16,32,64,128,256,512};

	private List<Sentence> asl;

	private File termdir;
	
	public static OxfordUrlTerm lookupOxford(String baseDir, final String word){
		final OxfordUrlTerm term = new OxfordUrlTerm();
		String url = "http://bbs.sxu.edu.cn/cgi-bin/oxdict.pl?"+word;
		term.key = word;
		term.expl = "";
		term.termdir = new File(
				FilenameUtils.concat(baseDir, term.key));
		try {
			InputStreamReader in = new InputStreamReader(
					OxfordUrlTerm.class.getClassLoader().getResourceAsStream("org/sharp/webdict/oxford.html"));
			new ParserDelegator().parse(/*in*/HttpCli.reader(url), new ParserCallback(){
				boolean explbegin;
				Tag lastStart;
				int brcount=0;
				String lastText;
				
				public void handleText(char[] data, int pos) {
					Utils.log.debug("text:"+new String(data));
					if(explbegin){
						Utils.log.debug("br count:"+brcount);
						if(brcount%3==1){
							String ikey = lastText;
							String iexpl = new String(data);
							if(ikey!=null && ikey.equalsIgnoreCase(word)){
								term.expl += ikey +"\n"+iexpl+"\n";
								Utils.log.debug("expl:"+term.expl);
							}
						}
						lastText = new String(data);
					}
				}

				public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
					Utils.log.debug("starttag:"+t);
					if(t==HTML.Tag.UL && lastStart==HTML.Tag.H2){
						explbegin = true;
						Utils.log.info("explain part begin.");
					}
					lastStart = t;
				}

				public void handleSimpleTag(Tag t, MutableAttributeSet a,
						int pos) {
					Utils.log.debug("simpletag:"+t);
					if(t==HTML.Tag.BR && explbegin){
						brcount++;
					}
						
					super.handleSimpleTag(t, a, pos);
				}

				public void handleError(String errorMsg, int pos) {
					Utils.log.debug("error:"+errorMsg);
					super.handleError(errorMsg, pos);
				}
				
			}, true/*false*/);
			term.format();
		} catch (Exception e) {
			Utils.log.debug("",e);
			return null;
		}
		return term;
	}
	
	public boolean attachVoiceSave(String baseDir){
		boolean suc = false;
		File[] fa = FsUtils.findWavFile(baseDir, key);
		if(fa!=null && fa.length > 0){
			keyvoice = fa[0].getPath();
		}
		if(!"".equals(expl) || keyvoice!= null){
			Date currenttime = new Date();
			info.setLookupt(currenttime);
			info.setLastrvt(currenttime);
			info.setScore(0);
			toDisk(/*Console.config.getTermsBaseDir()*/);
			suc = true;
		}
		return suc;
	}

	private void format(){
		
		Expl expl = Expl.format(this.expl);
		be = expl.formated;
		Sentence[] ses = expl.ses;
		List<Sentence> nsl = new ArrayList<Sentence>();
		asl = new ArrayList<Sentence>();
		List<SentenceHint> sl = new ArrayList<SentenceHint>();
		for(int i=0;i<ses.length;i++){
			if(ses[i].cpart!=null && LangUtils.containsWord(ses[i].sentence,key)) {
				SentenceHint sh = new SentenceHint(ses[i],key);
				sl.add(sh);
				asl.add(sh);
			} else{
				nsl.add(ses[i]);
				asl.add(ses[i]);
			}
		}
		nsamples = (Sentence[])nsl.toArray(new Sentence[0]);
		samples = (SentenceHint[])sl.toArray(new SentenceHint[0]);
	}
	
	public String defof(SentenceHint sh){
		String def = "",temp = "";
		for(int i=0;i<asl.size();i++){
			Sentence se = asl.get(i);
			if(se instanceof SentenceHint){
				if(se==sh){
					def = temp;
					break;
				}else{
					temp += se+"\n";
				}
			}else{
				temp += se+"\n";
			}	
		}
		return def;
	}
	/*public void lookupDictCN(String word){
		String url = "http://dict.cn/"+word;
		try {
			InputStreamReader in = new InputStreamReader(
					getClass().getClassLoader().getResourceAsStream("org/sharp/webdict/dict.cn.html"));
			new ParserDelegator().parse(inClient.reader(url), new ParserCallback(){
				boolean keyFound;
				
				public void handleText(char[] data, int pos) {
					if(keyFound){
						key = new String(data);
						Console.log.info("key term is "+key);
						keyFound = false;
					}
				}

				public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
					if(t==HTML.Tag.H1){
						Console.log.debug("start tag "+t);
						Object att = a.getAttribute("id"HTML.Attribute.ID);
						if(att!=null && "word".equals(att)){
							keyFound = true;
							Console.log.info("key term found.");
						}
						Console.log.debug("atributes "+a);
						Console.log.debug("atribute id "+att);						
						Console.log.debug("id defined "+a.isDefined(HTML.Attribute.ID));
						Enumeration attributeNames = a.getAttributeNames();
						while(attributeNames.hasMoreElements()){
							Object name = attributeNames.nextElement();
							Console.log.debug("attribute name: $"+name+"$");
							att = a.getAttribute(name);
							Console.log.debug("attribute value: "+att);
							if(att!=null && "word".equals(att)){
								keyFound = true;
								Console.log.info("key term found.");
							}
						}
					}
				}

				public void handleEndTag(Tag t, int pos) {
					super.handleEndTag(t, pos);
				}
				
			}, truefalse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void lookupIciba(String word){
		String url = "http://www.iciba.com/drudgery/";
	}
	
	public void lookupLongman(String word){
		String url = "http://www.ldoceonline.com/dictionary/kingpin";
	}*/
	
	private void toDisk(/*String basedir*/) {
		if(key == null)
			return;
		/*File termdir = new File(basedir,key);*/
		try {
			if(keyvoice!=null){
				File voicefile = new File(keyvoice);
				FileUtils.copyFileToDirectory(voicefile, termdir);
				keyvoice = FilenameUtils.concat(termdir.getPath(), voicefile.getName());
			}
			FileUtils.writeStringToFile(new File(termdir,key+".txt"), expl, "EUC_CN");
			Utils.saveJox(info, FilenameUtils.concat(termdir.getPath(),"info.txt"));
		} catch (IOException e) {
			Utils.log.error(e);
		}
	}
	
	public void saveInfo(/*String basedir*/) {
		if(key == null)
			return;
		/*File termdir = new File(basedir,key);*/
		Utils.saveJox(info, FilenameUtils.concat(termdir.getPath(),"info.txt"));
	}
	
	public static OxfordUrlTerm fromDisk(String dir,String word){
		OxfordUrlTerm t = new OxfordUrlTerm();
		t.key = word;
		try {
			t.termdir = new File(
					FilenameUtils.concat(dir, t.key));
			Date lookuptime = new Date(t.termdir.lastModified());
			TermExtra info = (TermExtra)Utils.loadJox2(
					FilenameUtils.concat(t.termdir.getPath(),"info.txt"), 
					TermExtra.class);
			if(info!=null){
				t.info = info;
				if(t.info.lastrvt==null)
					t.info.setLastrvt(lookuptime);
			}else{
				t.info.setLookupt(lookuptime);
				t.info.setLastrvt(lookuptime);
			}
			t.expl = FileUtils.readFileToString(
					new File(t.termdir.getPath(),t.key+".txt"),"EUC_CN");
			if(new File(t.termdir.getPath(),t.key+".wav").exists())
				t.keyvoice = new File(t.termdir.getPath(),
					t.key+".wav").getPath();
			else if(new File(t.termdir.getPath(),t.key+".mp3").exists())
				t.keyvoice = new File(t.termdir.getPath(),
					t.key+".mp3").getPath();
		} catch (IOException e) {
			Utils.log.error("", e);
			return null;
		}
		t.format();
		return t;
	}

	public int compareTo(OxfordUrlTerm t) {
		int ret = -1;
		ret = info.rvc - t.info.rvc;
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

	public static class TermExtra{
		
		Date lookupt;
		int score = 0;
		int rvc = 0;
		Date lastrvt;
		Set<SentenceHint> samplesSelected = new HashSet<SentenceHint>();
		
		public void setSamplesSelected(SentenceHint[] sh){
			samplesSelected.addAll(Arrays.asList(sh));
		}
		
		public SentenceHint[] getSamplesSelected(){
			return (SentenceHint[])samplesSelected.toArray(new SentenceHint[0]);
		}
		
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
		public int getRvc() {
			return rvc;
		}
		public void setRvc(int rvc) {
			this.rvc = rvc;
		}
		public Date getLastrvt() {
			return lastrvt;
		}
		public void setLastrvt(Date lastrvt) {
			this.lastrvt = lastrvt;
		}
		public void oneMoreReview() {
			if(rvc+1 <REVIEW_DAYS.length){
				rvc++;
				lastrvt = new Date();
			}
		}
		public void oneLessReview() {
			if(rvc-1 >=0){
				rvc--;
				lastrvt = new Date();
			}
		}
		public int schRvc(){
			Date at = new Date();
			int days = (int) ((at.getTime()-lastrvt.getTime())/(24*3600*1000L));
			int ret = 0;
			if(days>0){
				for (int i = 0; i < REVIEW_DAYS.length; i++) {
					if(days<REVIEW_DAYS[i]){
						ret = i;
						break;
					}
				}
			}
			return ret;
		}
		public int shouldReviewAfter() {
			if(rvc<REVIEW_DAYS.length){
				long sche = lastrvt.getTime()+(REVIEW_DAYS[rvc]*24*3600*1000L);
				if(sche >= System.currentTimeMillis()){
					return (int) ((sche-System.currentTimeMillis())/(24*3600*1000L))+1;
				}else
					return -1;
			}else{
				return -1;
			}
		}
	}

	public static class SentenceHint extends Sentence{
		String term;
		
		public SentenceHint(){
			super("");
		}
		
		public SentenceHint(Sentence s,String term) {
			super(s.sentence);
			this.term = term;
		}
		
		public String hint(int deg){
			return cpart+"\n"+epart.replace(term, hintword(term,deg));
		}
		
		private String hintword(String word,int deg){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<word.length();i++){
				int rand = Utils.nextInt(100);
				if(rand < deg || i==0){
					sb.append(word.charAt(i));
				}else{
					sb.append('*');
				}
			}
			return sb.toString();
		}
		
	}

	public void rmFromDisk() {
		try {
			FileUtils.deleteDirectory(termdir);
		} catch (IOException e) {
			Utils.log.error("",e);
		}
	}

	public boolean havingKeyword(String keyword) {
		return expl.contains(keyword);
	}
}


