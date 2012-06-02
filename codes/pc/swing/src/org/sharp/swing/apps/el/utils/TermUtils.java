package org.sharp.swing.apps.el.utils;

import org.apache.commons.io.FilenameUtils;
import org.sharp.jdkex.LangUtils;
import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.el.beans.NewWord;
import org.sharp.swing.apps.el.beans.term.beans.Meaning;
import org.sharp.swing.apps.el.beans.term.beans.PartOfSpeech;
import org.sharp.swing.apps.el.beans.term.beans.Term;

import fj.F;
import fj.data.Array;
import fj.data.Option;

public class TermUtils {
	public static String[] partOfSpeeches(Term t){
		PartOfSpeech[] poses = t.getPses();
		Array<PartOfSpeech> ar = Array.array(poses);
		return ar.map(new F<PartOfSpeech,String>(){
			@Override
			public String f(PartOfSpeech pos) {
				return pos.getPartofspeech();
			}}).array(String[].class);
	}

	public static PartOfSpeech partOfSpeech(Term t, final String selectedPos) {
		PartOfSpeech[] poses = t.getPses();
		Array<PartOfSpeech> ar = Array.array(poses);
		Option<PartOfSpeech> o = ar.find(new F<PartOfSpeech,Boolean>(){//TODO BUG FIX npe
			@Override
			public Boolean f(PartOfSpeech pos) {
				return LangUtils.equals(pos.getPartofspeech(), selectedPos);
			}});
		if(o.isNone())
			System.out.println(selectedPos+" of "+t.getKey()+" not found.");
		return o.isSome()?o.some():null;
		 
	}
	
	public static int meaningsCount(PartOfSpeech pos,int def){
		if(pos == null || pos.getMeaning() == null)
			return def;
		return pos.getMeaning().length;
	}

	public static Meaning meaning(PartOfSpeech pos, int num, Meaning def) {
		if(pos == null)
			return def;
		return Utils.elementAt(pos.getMeaning(), num, def);
	}
	
	public static Meaning meaning(Term t, String partOfSpeech, int num){
		return meaning(partOfSpeech(t, partOfSpeech),num,null);
	}
	
	public static String termSavedDir(Term term){
		return term.getKey();
	}

	public static String termSavedFullDir(String baseDir,Term term){
		return FilenameUtils.concat(baseDir, termSavedDir(term));
	}

	public static String termSavedFullDir(String baseDir,String key){
		return FilenameUtils.concat(baseDir, key);
	}

	public static void save(String termSavedFullDir, Term t) {
		Utils.saveJox2(FilenameUtils.concat(termSavedFullDir,t.getKey()+".xml"),t);
	}
	
	public static Term load(String termSavedFullDir, String key) {
		return Utils.loadJox2(FilenameUtils.concat(termSavedFullDir,key+".xml"),Term.class,null);
	}
}
