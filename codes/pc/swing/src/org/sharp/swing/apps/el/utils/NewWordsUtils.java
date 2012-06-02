package org.sharp.swing.apps.el.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.sharp.jdkex.LangUtils;
import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.el.beans.NewWord;
import org.sharp.swing.apps.el.beans.Phrase;
import org.sharp.swing.utils.SwingUtils.JTableUtilsX;
import org.sharp.swing.utils.SwingUtils.JTableUtilsX.ColumnsSupportX;
import org.sharp.utils.AntUtils;

public class NewWordsUtils {
	private static final String NEWWORDS_DIRNAME = "newwords";

	public static List<NewWord> migrateWords(String oldDir,String newDir){
		List<NewWord> list = new ArrayList<NewWord>();
		String[] newWordFiles = AntUtils.listFiles(oldDir, 
				new String[]{"*/*.xml"});
		for (String fp : newWordFiles) {
			NewWord nw = loadWord(oldDir, fp);
			NewWord newW = new NewWord();
			newW.setSpell("");
			newW.setTermName(nw.getSpell());
			newW.setDocName(nw.getDocName());
			newW.setLearnPronun(nw.getLearnPronun());
			newW.setNotes(nw.getNotes());
			newW.setPartOfSpeech(nw.getPartOfSpeech());
			newW.setPos(nw.getPos());
			newW.setSeqNo(nw.getSeqNo());
			newW.setTags(nw.getTags());
			if(StringUtils.isEmpty(newW.getDocName())){
				newW.setDocName("MissingDocs");
			}
			saveWord(newDir, newW);
		}
		return list;
	}
	
	public static List<NewWord> loadWords(String baseDir){
		List<NewWord> list = new ArrayList<NewWord>();
		String[] newWordFiles = AntUtils.listFiles(baseDir, 
				new String[]{"*/"+NEWWORDS_DIRNAME+"/"+"*_*.xml"});
		for (String fp : newWordFiles) {
			NewWord nw = loadWord(baseDir, fp);
			if(nw != null)
				list.add(nw);
			else
				Utils.log.debug("Error load newword from "+fp);
		}
		return list;
	}
	
	private static NewWord loadWord(String baseDir,String filePath){
		return Utils.loadJox2(FilenameUtils.concat(baseDir, filePath), 
				NewWord.class, null);
	}
	
	public static void saveWord(String baseDir,NewWord nw){
		if(newWordSaveable(nw))
			Utils.saveJox2(FilenameUtils.concat(baseDir, newWordSavedPath(nw)), 
				nw);
	}
	
	private static boolean newWordSaveable(NewWord nw){
		if(StringUtils.isEmpty(nw.getDocName()) ||
				StringUtils.isEmpty(nw.getTermName()) ){
				return false;
		}
		return true;
	}
	
	public static boolean sameEntry(NewWord nw1, NewWord nw2){
		if(nw1 == null || nw2 == null)
			return false;
		return LangUtils.equals(nw1.getDocName(), nw2.getDocName()) &&
			LangUtils.equals(nw1.getTermName(), nw2.getTermName()) &&
			LangUtils.equals(nw1.getSeqNo(), nw2.getSeqNo());
	}
	
	public static String newWordSavedFullDir(String baseDir,NewWord nw){
		String ret = FilenameUtils.concat(baseDir, newWordSavedDir(nw));
		return ret;
	}
	
	public static String newWordSavedDir(NewWord nw){
		String ret = null;
		if(newWordSaveable(nw)){
			ret = nw.getDocName()+File.separator+
			NEWWORDS_DIRNAME;
		}else{
			new RuntimeException("docName or termName is empty.").printStackTrace();
		}
		return ret;
	}
	
	private static String newWordSavedPath(NewWord nw){
		String ret = null;
		if(newWordSaveable(nw)){
			ret = newWordSavedDir(nw)+File.separator+
			nw.getSeqNo()+"_"+nw.getTermName()+".xml";
		}else{
			new RuntimeException("docName or termName is empty.").printStackTrace();
		}
		return ret;
	}
	
	public static List<NewWord> filterWords(List<NewWord> wl,final String docName){
		return (List<NewWord>) CollectionUtils.select(wl, new Predicate() {
			public boolean evaluate(Object o) {
				if(o!=null && o instanceof NewWord)
					return ObjectUtils.equals(((NewWord)o).getDocName(), docName);
				else
					return false;
			}
		});
	}
	
	public static String[] docNames(List<NewWord> wl){
		if(wl == null)
			return new String[0];
		
		List<String> dnl = (List<String>) CollectionUtils.collect(wl, new Transformer() {
			public Object transform(Object o) {
				if(o!=null && o instanceof NewWord){
					return ((NewWord)o).getDocName();
				}else
					return null;
			}
		});
		
		Set<String> dns = new HashSet<String>(dnl);
		return dns.toArray(new String[0]);
	}

	public static List<NewWord> filterWords(List<NewWord> wl,final String[] tags, final boolean include){
		return (List<NewWord>) CollectionUtils.select(wl, new Predicate() {
			public boolean evaluate(Object o) {
				if(o!=null && o instanceof NewWord){
					String[] wts = ((NewWord)o).getTags();
					ArrayList<String> wtsL = new ArrayList<String>();
					if(wts!=null)
						CollectionUtils.addAll(wtsL, wts);
					else
						return !include;
					ArrayList<String> tagsL = new ArrayList<String>();
					if(tags!=null)
						CollectionUtils.addAll(tagsL, tags);
					return wtsL.containsAll(tagsL)?include:!include;
				}else
					return false;
			}
		});
	}

	public static List<NewWord> filterWords(List<NewWord> wl,final boolean hasNotes){
		return (List<NewWord>) CollectionUtils.select(wl, new Predicate() {
			public boolean evaluate(Object o) {
				if(o!=null && o instanceof NewWord){
					String notes = ((NewWord)o).getNotes();
					return LangUtils.isEmpty(notes)?hasNotes:!hasNotes;
				}else
					return false;
			}
		});
	}

	public static void removeWordsTag(final String baseDir,List<NewWord> wl,final String tag){
		CollectionUtils.forAllDo(wl, new Closure() {
			public void execute(Object o) {
				if(o instanceof NewWord){
					if(((NewWord)o).removeTag(tag)){
						saveWord(baseDir,(NewWord)o);
					}
				}
			}
		});
	}

	public static String partOfSpeechAbbre(String pos){
		String abbre = "";
		if("noun".equalsIgnoreCase(pos)){
			abbre = "n";
		}else if("verb".equalsIgnoreCase(pos)){
			abbre = "v";
		}else if("adj".equalsIgnoreCase(pos) || "adjective".equalsIgnoreCase(pos)){
			abbre = "a";
		}else if("adv".equalsIgnoreCase(pos) || "adverb".equalsIgnoreCase(pos)){
			abbre = "av";
		}
		return abbre;
	}

	public static JTableUtilsX.ColumnsSupportX<NewWord> fullView(){
		return new JTableUtilsX.ColumnsSupportX<NewWord>(){
			public Object[] columnValues(NewWord nw) {
				return new Object[]{nw.getTermName(),partOfSpeechAbbre(nw.getPartOfSpeech()),
						nw.getNotes(),
					ArrayUtils.toString(nw.getTags(), "")};
			}
			public String[] columnNames() {
				return new String[]{"Term","PartOfSpeech",
						"Notes","Tags"};
			}
			public Integer[] columnWidths() {
				return new Integer[]{8*15,8*2,
						8*30,8*20};
			}
			public int defaultSortColumn() {
				return 1;
			}
			public boolean defaultAsc() {
				return true;
			}};
	}

	public static JTableUtilsX.ColumnsSupportX<NewWord> inDocView(){
		return new JTableUtilsX.ColumnsSupportX<NewWord>(){
			public Object[] columnValues(NewWord nw) {
				return new Object[]{nw.getTermName(),partOfSpeechAbbre(nw.getPartOfSpeech()),nw.getSeqNo(),
						nw.getNotes(),ArrayUtils.toString(nw.getTags(), "")};
			}
			public String[] columnNames() {
				return new String[]{"Term","PartOfSpeech","Ordi",
						"Notes","Tags"};
			}
			public Integer[] columnWidths() {
				return new Integer[]{8*15,10*1,10*2,8*2,10*15};
			}
			public int defaultSortColumn() {
				return 2;
			}
			public boolean defaultAsc() {
				return true;
			}};
	}

	public static JTableUtilsX.ColumnsSupportX<NewWord> addTagView(){
		return new JTableUtilsX.ColumnsSupportX<NewWord>(){
			public Object[] columnValues(NewWord nw) {
				return new Object[]{nw.getTermName(),
						partOfSpeechAbbre(nw.getPartOfSpeech()),
						nw.getNotes()};
			}
			public String[] columnNames() {
				return new String[]{"Spell","PartOfSpeech",
						"Notes"};
			}
			public Integer[] columnWidths() {
				return new Integer[]{8*15,10*1,8*1};
			}
			public int defaultSortColumn() {
				return 2;
			}
			public boolean defaultAsc() {
				return true;
			}};
	}

	public static boolean removeWord(String termBasePath, NewWord nw) {
		try {
			FileUtils.deleteDirectory(new File(
					FilenameUtils.concat(termBasePath, nw.getSpell())) );
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ColumnsSupportX<NewWord> searchResultView() {
		return new JTableUtilsX.ColumnsSupportX<NewWord>(){
			public Object[] columnValues(NewWord nw) {
				return new Object[]{nw.getTermName(),partOfSpeechAbbre(nw.getPartOfSpeech()),nw.getPos(),
						nw.getNotes(),ArrayUtils.toString(nw.getTags(), "")};
			}
			public String[] columnNames() {
				return new String[]{"Spell","PartOfSpeech","Ordi",
						"Notes","Tags"};
			}
			public Integer[] columnWidths() {
				return new Integer[]{8*15,10*1,10*2,8*2,10*15};
			}
			public int defaultSortColumn() {
				return NO_SORT;
			}
			public boolean defaultAsc() {
				return true;
			}};
	}

	public static List<NewWord> filterWordsWithPronun(List<NewWord> newWords,
			final boolean learnPronun) {
		List<NewWord> pwl = (List<NewWord>) CollectionUtils.select(newWords, new Predicate() {
			public boolean evaluate(Object o) {
				if(o!=null && o instanceof NewWord){
					boolean lp = ((NewWord)o).getLearnPronun();
					return lp?learnPronun:!learnPronun;
				}else
					return false;
			}
		});
		return pwl;
	}
	
	public static void sortByLastVt(List<NewWord> newWords) {
		Collections.sort(newWords, newLastVtComparator(false));
	}
	
	public static Comparator<NewWord> newLastVtComparator(final boolean asc){
		return new Comparator<NewWord>() {
			public int compare(NewWord o1, NewWord o2) {
				int o =  LangUtils.compareTo(o1.getRi().getLastrvt(),
						o2.getRi().getLastrvt());
				return asc?o:-o;
			}
		};
	}

	public static List<NewWord> filterIndexableNewWords(List<NewWord> newWords) {
		return (List<NewWord>) CollectionUtils.select(newWords, new Predicate() {
			public boolean evaluate(Object o) {
				if(o!=null && o instanceof NewWord){
					NewWord nw =(NewWord)o;
					return isNewWordIndexable(nw);
				}else
					return false;
			}
		});
	}
	
	private static boolean isNewWordIndexable(NewWord nw){
		return !StringUtils.isEmpty(nw.getDocName()) &&
		!StringUtils.isEmpty(nw.getTermName()) &&
		!StringUtils.isEmpty(nw.getNotes());
	}

	public static List<NewWord> genWords(List<NewWord> newWords, Phrase[] words, String docName) {
		List<NewWord> wl = new ArrayList<NewWord>();
		int seqNo = 0;
		for (Phrase word : words) {
			boolean found = false;
			for (NewWord newWord : newWords) {
				if(LangUtils.equals(newWord.getSpell(), word.getText())&&
						LangUtils.equals(newWord.getDocName(), docName)){
					wl.add(newWord);
					found = true;
					break;
				}
			}
			if(!found){/*String spell, String docName, String partOfSpeech,
				int seqNo,int pos, String notes*/
				NewWord nw = new NewWord(word.getText(),docName,"",seqNo,word.getOffset(),"");
				wl.add(nw);
			}
			seqNo ++;
		}
		return wl;
	}

	public static String newWordIndexId(NewWord nw) {
		return nw.getDocName()+"::"+nw.getTermName()+"::"+nw.getSeqNo();
	}
	
	public static NewWord findNewWordByIndexId(List<NewWord> newWords,String indexId){
		String[] parts = indexId.split("::");
		final String docName = parts[0];
		final String termName = parts[1];
		final String seqNo = parts[2];
		return (NewWord) CollectionUtils.find(newWords, new Predicate() {
			public boolean evaluate(Object o) {
				if(o!=null && o instanceof NewWord){
					NewWord nw = (NewWord)o;
					if(StringUtils.equals(docName, nw.getDocName()) &&
						StringUtils.equals(termName, nw.getTermName()) &&
						NumberUtils.toInt(seqNo) == nw.getSeqNo())
						return true;
					else
						return false;
				}else
					return false;
			}
		});
	}

	public static void removeOldWords(String termBasePath) {
		List<NewWord> list = new ArrayList<NewWord>();
		String[] newWordFiles = AntUtils.listFiles(termBasePath, 
				new String[]{"*/*.xml"});
		for (String fp : newWordFiles) {
			NewWord nw = loadWord(termBasePath, fp);
			removeWord(termBasePath, nw);
		}
	}
	
	public static void setTagString(NewWord nw, String tagString){
		String[] tags = LangUtils.split(tagString, ",");
		nw.setTags(tags);
	}

	public static String getTagString(NewWord nw){
		String tagStr = LangUtils.join(nw.getTags(), ",");
		return tagStr;
	}

	public static List<NewWord> filterWordsWithSpell(List<NewWord> newWords,
			final boolean learnSpell) {
		List<NewWord> pwl = (List<NewWord>) CollectionUtils.select(newWords, new Predicate() {
			public boolean evaluate(Object o) {
				if(o!=null && o instanceof NewWord){
					boolean ls = ((NewWord)o).isLearnSpell();
					return ls?learnSpell:!learnSpell;
				}else
					return false;
			}
		});
		return pwl;
	}

	public static List<NewWord> filterWordsWithExpl(List<NewWord> newWords,
			final boolean learnExpl) {
		List<NewWord> pwl = (List<NewWord>) CollectionUtils.select(newWords, new Predicate() {
			public boolean evaluate(Object o) {
				if(o!=null && o instanceof NewWord){
					boolean le = ((NewWord)o).isLearnExpl();
					return le?learnExpl:!learnExpl;
				}else
					return false;
			}
		});
		return pwl;
	}
}
