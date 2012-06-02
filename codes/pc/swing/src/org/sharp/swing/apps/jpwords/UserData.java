package org.sharp.swing.apps.jpwords;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.jpwords.beans.Config;
import org.sharp.utils.FsUtils;

public class UserData {
	static Config config = new Config();	//TODO get it from AppContext
	
	public static final int termRepeatCount = 5;
	List<JpTerm> wordList;
	
	private UserData(){
		wordList = new ArrayList<JpTerm>();
	}
	
	public UserData addNewTerm(JpTerm term){
		if(!wordList.contains(term))
			wordList.add(term);
		return this;
	}
	
	public static UserData scanJpwords(){
		UserData userdata = new UserData();
		File vocdir = new File(config.getJptermsBaseDir());
		if(!vocdir.exists() || !vocdir.isDirectory()){
			return userdata;
		}
		String[] words = vocdir.list();
		Utils.log.debug("total "+words.length+" words in termsbasedir.");
		for (int i = 0; i < words.length; i++) {
			userdata.addNewTerm(JpTerm.fromDisk(config.getJptermsBaseDir(), words[i]));
		}
		return userdata;
	}
	
	public static UserData searchJpwords() {
		UserData userdata = new UserData();
		String[] jpwordsDirs = config.getJpwordsDirs();
		for (int i = 0; i < jpwordsDirs.length; i++) {
			File vocdir = new File(jpwordsDirs[i]);
			if(!vocdir.exists() || !vocdir.isDirectory()){
				continue;
			}
			File[] mp3files = FsUtils.findMp3WaveFiles(vocdir.getPath());
			for (int j = 0; j < mp3files.length; j++) {
				userdata.addNewTerm(JpTerm.createByMp3(config.getJptermsBaseDir(), mp3files[j]));
			}
		}
		return userdata;
	}

	public static UserData load(String file){
		UserData userdata = (UserData)Utils.loadJox2(file, UserData.class);
		return userdata!=null?userdata:new UserData();
	}
	
	public UserData save(String file){
		return (UserData)Utils.saveJox(this, file);
	}

	public List<JpTerm> getWordList(int level) {
		List<JpTerm> terms = new ArrayList<JpTerm>();
		if(wordList!=null){
			Date now = new Date();
			for (Iterator<JpTerm> iterator = wordList.iterator(); iterator.hasNext();) {
				JpTerm t = iterator.next();
				if(t.info!=null &&/* t.info.shouldReview(now) &&*/
						t.keyvoice !=null && !t.expl().equals("") &&
						t.info.level == level)
					terms.add(t);
			}
		}
		return terms;
	}
	
	public JpTerm lookup(String key){
		JpTerm term = null;
		if(wordList!=null){
			for (Iterator<JpTerm> iterator = wordList.iterator(); iterator.hasNext();) {
				JpTerm t = iterator.next();
				if(t.key.equals(key)){
					term = t;
					break;
				}
			}
		}
		return term;
	}

	public void setWordList(List<JpTerm> wordList) {
		this.wordList = wordList;
	}

}
