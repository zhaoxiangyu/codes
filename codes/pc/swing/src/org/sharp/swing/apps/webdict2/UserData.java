package org.sharp.swing.apps.webdict2;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.sharp.intf.AudioPlayer;
import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.webdict2.beans.Config;
import org.sharp.swing.apps.webdict2.beans.Term;
import org.sharp.swing.apps.webdict2.beans.WebDict;
import org.sharp.utils.FsUtils.DirSnaps;

public class UserData {
	private static final int termRepeatCount = 5;
	private List<OxfordUrlTerm> wl;
	public int cpi;
	public int smpi;
	
	private static UserData wholeData;
	private Config conf;
	private AudioPlayer ap;
	private WebDict wd;
	
	private UserData(){
		wl = new ArrayList<OxfordUrlTerm>();
	}
	
	private UserData addNewTerm(OxfordUrlTerm term){
		if(!wl.contains(term))
			wl.add(term);
		return this;
	}
	
	private static UserData scanTerms(Config conf){
		if(wholeData!=null)
			return wholeData;
		
		wholeData = new UserData();
		File vocdir = new File(conf.getTermsBaseDir());
		if(!vocdir.exists() || !vocdir.isDirectory()){
			return wholeData;
		}
		String[] words = vocdir.list();
		for (int i = 0; i < words.length; i++) {
			wholeData.addNewTerm(OxfordUrlTerm.fromDisk(words[i]));
		}
		return wholeData;
	}
	
	public static UserData getWordList4Review(WebDict wd, AudioPlayer ap, Config config) {
		wholeData = scanTerms(config);
		UserData ud = new UserData();
		List<OxfordUrlTerm> terms = new ArrayList<OxfordUrlTerm>();
		if(wholeData.wl!=null){
			for (Iterator<OxfordUrlTerm> iterator = wholeData.wl.iterator(); iterator.hasNext();) {
				OxfordUrlTerm t = iterator.next();
				if(t.shouldReviewNow() &&
						t.term.getKeyvoice() !=null)
					terms.add(t);
			}
		}
		ud.wl = terms;
		
		if(ud.wl.size()>=1){
			Collections.sort(ud.wl, new Comparator<OxfordUrlTerm>() {
				
				public int compare(OxfordUrlTerm arg0, OxfordUrlTerm arg1) {
					return arg0.compareTo(arg1);
				}
			});
			ud.cpi = 0;
			ud.smpi = 0;
		}else{
			ud.cpi = -1;
			ud.smpi = -1;
		}
		
		ud.ap = ap;
		ud.wd = wd;
		ud.conf = config;
		return ud;
	}
	
	List<OxfordUrlTerm> getWordList4Key(String keyword) {
		return new ArrayList<OxfordUrlTerm>();
	}
	
	public int wordCount(){
		return wl!=null?wl.size():0;
	}
	
	public OxfordUrlTerm term(int ci){
		OxfordUrlTerm term = null;
		if(ci >= 0 && ci< wl.size()){
			/*userdata.cpi = ci;*/
			term = (OxfordUrlTerm)wl.get(ci);
		}
		return term;
	}
	
	private void saveTerm(){
		OxfordUrlTerm term = term(cpi);
		if(term!=null)
			term.saveInfo();
	}

	private OxfordUrlTerm lookupLocal(String key){
		OxfordUrlTerm term = null;
		if(wl!=null){
			for (Iterator<OxfordUrlTerm> iterator = wl.iterator(); iterator.hasNext();) {
				OxfordUrlTerm t = iterator.next();
				if(t.term.getKey().equals(key)){
					term = t;
					break;
				}
			}
		}
		return term;
	}
	
	public RetCode lookup(DirSnaps dss,String key){
		OxfordUrlTerm term = lookupLocal(key);
		if(term == null){
			Term wt = wd.lookup(key);
			if(wt == null){
				return RetCode.WEB_NOT_FOUND;
			}else{
				term = new OxfordUrlTerm();
				term.term = wt;
				term.termdir = new File(
						FilenameUtils.concat(FilenameUtils.concat(System.getProperty("user.dir"),"terms2"), 
								wt.getKey()));
				if(term.attachVoiceSave(dss,conf)){
					wl.add(term);
					cpi = wl.size()-1;
					return RetCode.VOICE_FOUND;
				}else{
					return RetCode.VOICE_NOT_FOUND;
				}
			}
		}else{
			cpi = wl.indexOf(term);
			return RetCode.LOCAL_FOUND;
		}
	}
	
	public final RetCode playVoice(){
		if(term(cpi)!=null){
			ap.play(term(cpi).term.getKeyvoice());
			return RetCode.OK;
		}else{
			return RetCode.TERM_NOT_EXIST;
		}
	}
	
	public boolean hasNext(){
		return cpi+1<wl.size()&& cpi+1>=0 ;
	}

	public boolean hasPrevious(){
		return cpi-1<wl.size()&& cpi-1>=0 ;
	}

	public boolean toRandom(){
		int ncpi = Utils.nextInt(wl.size());
		if(ncpi<wl.size()&& ncpi>=0){
			cpi = ncpi;
			return true;
		}
		return false;
	}
	
	public RetCode exam(String input){
		OxfordUrlTerm term = term(cpi);
		boolean inputerror = false;
		if(term!=null&& term.term.getKey().equalsIgnoreCase(input)){
			smpi++;
		}else{
			inputerror = true;
		}
		
		String[] sh = term.samples;
		RetCode examResult;
		if(sh.length > 0){
			if(smpi>=0&&smpi<sh.length){
				if(smpi < UserData.termRepeatCount || smpi %UserData.termRepeatCount != 0){
					examResult = RetCode.EXAM_NORMAL;
				}else{
					examResult = RetCode.EXAM_LEARN_ENOUGH;
					term.info.oneMoreReview();
				}
				if(inputerror){
					examResult = RetCode.EXAM_INPUTERROR;
					term.info.oneLessReview();
				}			
			}else{
				examResult = RetCode.EXAM_LEARN_ALL;
				if(smpi %UserData.termRepeatCount == 0 || smpi < UserData.termRepeatCount){
					term.info.oneMoreReview();
				}
				smpi=0;
				smpi = 0;
			}
		}else{
			/*msg.setForeground(Color.black);
			if(smpi>0){
				message("you've learned all sample sentences.", Color.red);
				term.info.oneMoreReview();
				userdata.smpi = 0;
			}else{
				if(inputerror){
					message("input error,total review times subtracted by 1!", Color.red);
					term.info.oneLessReview();
				}else{
					message(DEFAULT_MSG, Color.black);
				}
			}*/
			examResult = RetCode.EXAM_NO_SENTENCE;
		}
		saveTerm();
		return examResult;
	}

	public static enum RetCode {
		WEB_NOT_FOUND,VOICE_FOUND,VOICE_NOT_FOUND,LOCAL_FOUND,TERM_NOT_EXIST,OK,
		EXAM_NORMAL,EXAM_LEARN_ENOUGH,EXAM_INPUTERROR,EXAM_LEARN_ALL,EXAM_NO_SENTENCE
	}
}
