package org.sharp.swing.apps.webdict;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.webdict.beans.Config;

public class UserData {
	public static final int termRepeatCount = 5;
	private List<OxfordUrlTerm> wordList;
	
	private UserData(Config config){
		wordList = new ArrayList<OxfordUrlTerm>();
	}
	
	public UserData addNewTerm(OxfordUrlTerm term){
		if(!wordList.contains(term))
			wordList.add(term);
		return this;
	}
	
	public static UserData scanTerms(Config config){
		UserData userdata = new UserData(config);
		File vocdir = new File(config.getTermsBaseDir());
		if(!vocdir.exists() || !vocdir.isDirectory()){
			return userdata;
		}
		String[] words = vocdir.list();
		for (int i = 0; i < words.length; i++) {
			userdata.addNewTerm(OxfordUrlTerm.fromDisk(config.getTermsBaseDir(),words[i]));
		}
		return userdata;
	}
	
	public UserData save(String file){
		return (UserData)Utils.saveJox(this, file);
	}

	public List<OxfordUrlTerm> getWordList4Review() {
		List<OxfordUrlTerm> terms = new ArrayList<OxfordUrlTerm>();
		if(wordList!=null){
			Date now = new Date();
			for (Iterator<OxfordUrlTerm> iterator = wordList.iterator(); iterator.hasNext();) {
				OxfordUrlTerm t = iterator.next();
				if(t!=null && t.info!=null && t.info.shouldReviewAfter()<0 &&
						t.keyvoice !=null && !t.expl.equals(""))
					terms.add(t);
			}
		}
		return terms;
	}
	
	public List<OxfordUrlTerm> getWordList4Key(String keyword) {
		List<OxfordUrlTerm> terms = new ArrayList<OxfordUrlTerm>();
		if(wordList!=null){
			Date now = new Date();
			for (Iterator<OxfordUrlTerm> iterator = wordList.iterator(); iterator.hasNext();) {
				OxfordUrlTerm t = iterator.next();
				if(t.havingKeyword(keyword))
					terms.add(t);
			}
		}
		return terms;
	}
	
	public int totalwords(){
		return wordList!=null?wordList.size():0;
	}
	
	public OxfordUrlTerm lookup(String key){
		OxfordUrlTerm term = null;
		if(wordList!=null){
			for (Iterator<OxfordUrlTerm> iterator = wordList.iterator(); iterator.hasNext();) {
				OxfordUrlTerm t = iterator.next();
				if(t.key.equals(key)){
					term = t;
					break;
				}
			}
		}
		return term;
	}

	public void setWordList(List<OxfordUrlTerm> wordList) {
		this.wordList = wordList;
	}

}
