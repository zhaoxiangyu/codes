package org.sharp.swing.apps.el.utils;

import static org.sharp.jdkex.LangUtils.length;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.core.CollectionUtils;
import net.sf.cglib.core.Transformer;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.sharp.intf.Converter;
import org.sharp.jdkex.FjUtils;
import org.sharp.jdkex.LangUtils;
import org.sharp.jdkex.Utils;
import org.sharp.swing.apps.el.beans.Article;
import org.sharp.swing.apps.el.beans.NewWord;
import org.sharp.swing.apps.el.beans.Phrase;
import org.sharp.swing.utils.SwingUtils.JTableUtilsX;
import org.sharp.utils.AntUtils;
import org.sharp.utils.FsUtils;

public class ArticleUtils {

	public static JTableUtilsX.ColumnsSupportX<Article> fullView(){
		return new JTableUtilsX.ColumnsSupportX<Article>(){
			public Object[] columnValues(Article art) {
				return new Object[]{art.getTitle(),art.getWordCount(),
						length(art.getPhrases()),length(art.getNewWords()),
						(double)length(art.getNewWords())/art.getWordCount(),
						Utils.toShortStr(art.getRi().getLastrvt())};
			}
			public String[] columnNames() {
				return new String[]{"title","words",
						"phrases","newwords","newwords/words",
						"lastVistTime"};
			}
			public Integer[] columnWidths() {
				return new Integer[]{8*15, 8*3, 8*3,8*3,8*5,8*10};
			}
			public int defaultSortColumn() {
				return 5;
			}
			public boolean defaultAsc() {
				return false;
			}
		};
	}

	public static void fillArticle(String baseDir, Article article) {
		Article inxml = loadArticle(baseDir, docName(article.getFilePath()));
		if(inxml == null)
			return;
		article.setPhraseTag(inxml.getPhraseTag());
		copyPhrasesProperties(article, inxml);
		article.setRi(inxml.getRi());
	}
	
	private static void copyPhrasesProperties(Article t,Article s){
		Phrase[] sps = s.getPhrases();
		for (Phrase sp : sps) {
			for(Phrase tp: t.getPhrases()){
				if(sp.getText().equals(tp.getText())){
					tp.setTags(sp.getTags());
					break;
				}
			}
		}
	}

	public static List<Article> loadArticles(String articlesPath) {
		List<Article> list = new ArrayList<Article>();
		File articlesdir = new File(articlesPath);
		if(!articlesdir.exists() || !articlesdir.isDirectory()){
			return list;
		}
		String[] dirNames = articlesdir.list();
		for (int i = 0; i < dirNames.length; i++) {
			Article nw = loadArticle(articlesdir.getPath(),dirNames[i]);
			if(nw!=null)
				list.add(nw);
		}
		return list;
	}

	public static Article loadArticle(String baseDir, String dirName) {
		String fn = FilenameUtils.concat(baseDir, articleFileName(dirName));
		/*return Utils.loadJox2(fn, 
				Article.class, null);*/
		return (Article) FsUtils.loadJson(fn, Article.class, null);
	}
	
	public static void convert(String baseDir, String dirName){
		String fn = FilenameUtils.concat(baseDir, articleFileNamex(dirName));
		Article art = Utils.loadJox2(fn, 
				Article.class, null);
		FsUtils.saveJson(art , FilenameUtils.concat(baseDir, articleFileName(dirName)));
	}

	public static Article saveArticle(String filePath,List<NewWord> nwl,
			String baseDir) {
		Article art = new Article();
		art.setFilePath(filePath);
		List<String> nwSpells = CollectionUtils.transform(nwl, new Transformer() {
			public Object transform(Object nw) {
				return ((NewWord)nw).getSpell();
			}
		});
		art.setNewWords(nwSpells.toArray(new Phrase[0]));
		/*Utils.saveJox2(FilenameUtils.concat(baseDir, 
				genRelativeFilePath(filePath)),art);*/
		FsUtils.saveJson(art, FilenameUtils.concat(baseDir, 
				genRelativeFilePath(filePath)));
		return art;
	}
	
	private static String genRelativeFilePath(String filePath){
		String baseName = docName(filePath);
		return articleFileName(baseName);
	}
	
	private static String articleFileName(String dirName){
		return dirName+"/"+"article.jsn";
	}

	private static String articleFileNamex(String dirName){
		return dirName+"/"+"article.xml";
	}

	public static Article saveArticle(String baseDir,Article art) {
		/*Utils.saveJox2(FilenameUtils.concat(baseDir, genRelativeFilePath(art.getFilePath())),
				art);*/
		FsUtils.saveJson(art, FilenameUtils.concat(baseDir, 
				genRelativeFilePath(art.getFilePath())));
		return art;
	}

	public static String getMaskedArticleText(Article arti, boolean noun,
			boolean verb, boolean adj, boolean adv) {
		return arti.getText();
	}

	public static List<Phrase> getPhrases(Article arti, boolean noun,
			boolean verb, boolean adj, boolean adv, boolean newwords, boolean all) {
		List<Phrase> pl = new ArrayList<Phrase>();
		for (Phrase phrase : arti.getPhrases()) {
			if(phrase.getType().equals("noun")&&noun ||
				phrase.getType().equals("verb")&&verb ||
				phrase.getType().equals("adj")&&adj ||
				phrase.getType().equals("adv")&&adv ||
				all){
				pl.add(phrase);
			}
			if(newwords){
				for(Phrase nw : arti.getNewWords()) {
					if(StringUtils.contains(phrase.getText(), nw.getText())){
						pl.add(phrase);
						break;
					}
				}
			}
		}
		return pl;
	}

	private static String docName(String filePath) {
		return FilenameUtils.getBaseName(filePath);
	}
	
	public static String docName(Article arti) {
		return docName(arti.getFilePath());
	}

	private static String normalizeDocName(String docName) {
		if(docName == null)//docName like: II.china's population.doc
			return "";
		docName = StringUtils.removeEndIgnoreCase(docName, ".doc");
		String nDocName = docName;	
		//String[] parts = docName.split("\\.");
		String[] parts = StringUtils.split(docName, '.');
		if(parts.length >= 2){
			nDocName = LangUtils.join(
					ArrayUtils.subarray(parts, 1, parts.length), 
				".");
		}
		
		System.out.println("docName: "+docName+" ==> "+nDocName);
		return nDocName;
	}
	
	public static String findMatchedArticleDir(String baseDir,String docName){
		String[] matched = AntUtils.listDirs(baseDir, "*"+normalizeDocName(docName));
		return Utils.elementAt(matched,0);
	}

	public static List<Article> loadArticles(File[] docs,Converter<File,Article> c) {

		Article[] aa = FjUtils.convertTo(docs, Article[].class, c);
		aa = Utils.removeNull(aa, Article.class);
		return Utils.asList(aa);
	}

}
