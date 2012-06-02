package org.sharp.swing.apps.webdict2;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang3.ObjectUtils;
import org.sharp.jdkex.LangUtils;
import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.webdict2.beans.Config;
import org.sharp.swing.apps.webdict2.beans.PartOfSpeech;
import org.sharp.swing.apps.webdict2.beans.Term;
import org.sharp.swing.apps.webdict2.beans.TermExtra;
import org.sharp.swing.apps.webdict2.utils.VocChecker;
import org.sharp.utils.FsUtils;
import org.sharp.utils.FsUtils.DirSnaps;
import org.sharp.utils.XmlUtils;
import org.sharp.utils.beans.DirSnap;

public class OxfordUrlTerm implements Comparable<OxfordUrlTerm> {

	Term term = new Term();
	TermExtra info = new TermExtra();//(TermExtra)Proxies.exceptionLogProxy(new TermExtra());
	String[] samples;

	File termdir;
	
	public boolean attachVoiceSave(DirSnaps dss, Config config){
		DirSnap ds = dss.get(config.getVoiceLibBaseDir());
		String fp = VocChecker.lookUpAndCopy(ds,term.getKey(), termdir.getPath());
		
		if(fp != null){
			term.setKeyvoice(fp);
			info.touch(new Date(),new Date(),0, 0);
			return true;
		}
		return false;
	}

	public void toDisk(/*String basedir*/) {
		if(term.getKey() == null)
			return;
		term.toDisk(termdir.getPath());
		//FsUtils.saveJox(term, FilenameUtils.concat(termdir.getPath(),term.getKey()+".txt"));
		info.toDisk(termdir.getPath());
		//FsUtils.saveJox(info, FilenameUtils.concat(termdir.getPath(),"info.txt"));
	}
	
	/*private static void save2DB(OxfordUrlTerm t){
		OrientDB.db().save(t);
	}
	
	public static List<OxfordUrlTerm> queryDB(String query){
		return OrientDB.db().query(
				new OSQLSynchQuery<OxfordUrlTerm>(query));
	}*/
	
	public void saveInfo(/*String basedir*/) {
		if(term.getKey() == null)
			return;
		/*File termdir = new File(basedir,key);*/
		info.toDisk(termdir.getPath());
		//FsUtils.saveJox(info, FilenameUtils.concat(termdir.getPath(),"info.txt"));
	}
	
	public static OxfordUrlTerm fromDisk(/*String dir,*/String word){
		OxfordUrlTerm t = new OxfordUrlTerm();
		t.termdir = new File(
				FilenameUtils.concat(FilenameUtils.concat(System.getProperty("user.dir"),"terms2"), 
						word));
		
		t.term = OxfordUrlTerm.fromDisk(word,t.termdir.getPath());
		//t.term = (Term)FsUtils.loadJox(FilenameUtils.concat(t.termdir.getPath(),word+".txt"), Term.class);
		t.info = TermExtra.fromDisk(t.termdir.getPath());
		/*Date lookuptime = new Date(t.termdir.lastModified());
		TermExtra info = (TermExtra)FsUtils.loadJox(
				FilenameUtils.concat(t.termdir.getPath(),"info.txt"), 
				TermExtra.class);
		if(info!=null){
			t.info = info;
			if(t.info.lastrvt==null)
				t.info.setLastrvt(lookuptime);
		}else{
			t.info.setLookupt(lookuptime);
			t.info.setLastrvt(lookuptime);
		}*/
		/*t.sg = FileUtils.readFileToString(
				new File(t.termdir.getPath(),t.key+".txt"),"EUC_CN");*/
		/*if(new File(t.termdir.getPath(),t.term.getKey()+".wav").exists())
			t.term.setKeyvoice(new File(t.termdir.getPath(),
				t.term.getKey()+".wav").getPath());
		else if(new File(t.termdir.getPath(),t.term.getKey()+".mp3").exists())
			t.term.setKeyvoice(new File(t.termdir.getPath(),
				t.term.getKey()+".mp3").getPath());*/
		
		//save2DB(t);
		t.samples = t.defsAndExamples2();
		return t;//(OxfordUrlTerm)Proxies.exceptionLogProxy(t);/*(OxfordUrlTerm)CacheProxy.cachedCallProxy(t)*/
	}

	public int compareTo(OxfordUrlTerm t) {
		return info.compareTo(t.info);
	}

	public boolean shouldReviewNow(){
		return info!=null && info.shouldReviewAfter()<=0;
	}
	
	public void rmFromDisk() {
		try {
			FileUtils.deleteDirectory(termdir);
		} catch (IOException e) {
			Utils.log.error("",e);
		}
	}

	public String toString(){
		return LangUtils.reflectionToString(this);
	}
	
	public String explString(){
		final StringBuffer ret = new StringBuffer(term.getKey()+"\n"+term.getPronunciation());
//		List<PartOfSpeech> psesl = Arrays.asList(term.getPses());
		List<PartOfSpeech> psesl = Arrays.asList((PartOfSpeech[])ObjectUtils.defaultIfNull(term.getPses(),new PartOfSpeech[0]));
		CollectionUtils.forAllDo(psesl, new Closure(){
			public void execute(Object input) {
				ret.append("\n\n").append(((PartOfSpeech)input).explString());
			}
		});
		return ret.toString();
	}
	
	public String htmlString(){
		final StringBuffer ret = new StringBuffer(term.getKey()+"<br>"+term.getPronunciation());
		List<PartOfSpeech> psesl = Arrays.asList(term.getPses());
		CollectionUtils.forAllDo(psesl, new Closure(){
			public void execute(Object input) {
				ret.append("<br><br>").append(((PartOfSpeech)input).htmlString());
			}
		});
		return ret.toString();
	}

	String[] defsAndExamples(){
		JXPathContext c = JXPathContext.newContext(this);
		//c.setLenient(true);
		Iterator<String> i = c.iterate("//pses[0]/meaning[0]/definition");
		return (String[]) IteratorUtils.toArray(i,String.class);
	}

	String[] defsAndExamples2(){
		String fp = FilenameUtils.concat(termdir.getPath(),term.getKey()+".txt");
		try {
			//String fc = FileUtils.readFileToString(new File(fp));
			return XmlUtils.xpath(fp,"//pses/meaning/definition/text() | //pses/meaning/examples/text()");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Term fromDisk(String key,String dir) {
		Term t = (Term)Utils.loadJox2(FilenameUtils.concat(dir,key+".txt"), Term.class);
		File[] audioFiles = FsUtils.findMp3WaveFiles(dir);
		if(audioFiles!=null && audioFiles.length >0)
			t.setKeyvoice(audioFiles[0].getPath());
		return t;
	}
	
}


