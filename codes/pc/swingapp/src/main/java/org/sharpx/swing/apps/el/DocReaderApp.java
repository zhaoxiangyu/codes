package org.sharpx.swing.apps.el;

import java.awt.Container;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.sharpx.swing.intf.AppContext;
import org.sharpx.utils.intf.Converter;
import org.sharpx.swing.intf.FileHandler;
import org.sharpx.swing.intf.Pluggable;
import org.sharpx.utils.FjUtils;
import org.sharpx.utils.LangUtils;
import org.sharpx.utils.FsUtils;
import org.sharpx.utils.DsUtils;
import org.sharpx.swing.beans.RecentItems.Item;
import org.sharpx.swing.apps.el.beans.Article;
import org.sharpx.swing.apps.el.beans.Config;
import org.sharpx.swing.apps.el.beans.NewWord;
import org.sharpx.swing.apps.el.beans.Tag;
import org.sharpx.swing.apps.el.beans.term.beans.Meaning;
import org.sharpx.swing.apps.el.beans.term.beans.Term;
import org.sharpx.swing.apps.el.intf.ArticleOpener;
import org.sharpx.swing.apps.el.intf.ArticlePersistHandler;
import org.sharpx.swing.apps.el.intf.LuceneSearch;
import org.sharpx.swing.apps.el.intf.NewWordHandler;
import org.sharpx.swing.apps.el.intf.RecentFilePathsFetcher;
import org.sharpx.swing.apps.el.intf.TagHandler;
import org.sharpx.swing.apps.el.intf.VoicePronouncer;
import org.sharpx.swing.apps.el.ui.ConfigPanel;
import org.sharpx.swing.apps.el.ui.DocNewWordsPanel;
import org.sharpx.swing.apps.el.ui.DocPhrasesPanel;
import org.sharpx.swing.apps.el.ui.DocSearchPanel;
import org.sharpx.swing.apps.el.ui.FileChoosePanel;
import org.sharpx.swing.apps.el.ui.HtmlArticlePanel;
import org.sharpx.swing.apps.el.ui.HtmlDictPanel;
import org.sharpx.swing.apps.el.ui.OxfordA7Panel;
import org.sharpx.swing.apps.el.ui.WordTagsPanel;
import org.sharpx.swing.apps.el.ui.ConfigPanel.AdminHandler;
import org.sharpx.swing.apps.el.ui.DocNewWordsPanel.WordListGenerator;
import org.sharpx.swing.apps.el.ui.FileChoosePanel.ArticlesLoader;
import org.sharpx.swing.apps.el.ui.widget.FileListPanel;
import org.sharpx.swing.apps.el.ui.widget.WordInfoPanel.TermHandler;
import org.sharpx.swing.apps.el.utils.ArticleUtils;
import org.sharpx.swing.apps.el.utils.NewWordsUtils;
import org.sharpx.swing.apps.el.utils.TermUtils;
import org.sharpx.swing.apps.el.utils.VocChecker;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.utils.AntUtils;
import org.sharpx.utils.FsUtils;
import org.sharpx.utils.FsUtils.DirSnaps;
import org.sharpx.utils.Lucene;
import org.sharpx.utils.Lucene.Indexable;
import org.sharpx.utils.Lucene.SearchResult;
import org.sharpx.utils.PoiUtils;
import org.sharpx.utils.beans.DirSnap;
//import org.sharpx.utils.legacy.Mp3Player;
//import org.sharpx.utils.s4j.ScalaUtils;

public class DocReaderApp implements Pluggable {

	private Runtime runtime;
	private AppLifeCycle alc;

	public DocReaderApp() {
		//Utils.log.info("new DocReaderApp started.");
		runtime = new Runtime();
	}

	public static class Runtime {
		FileChoosePanel fcp;
		HtmlArticlePanel dtp;
		DocPhrasesPanel dpp;
		DocSearchPanel dsp;
		DocNewWordsPanel dnwp;
		WordTagsPanel wtp;
		ConfigPanel cp;
		OxfordA7Panel oa7p;
		WebDict dict;

		Config config;
		AppContext appCtx;
		SearchResult sr;
		Tag wordsTag;
		List<NewWord> newWords;
		List<Article> articles;
		protected Lucene lucene;
		
		private void freshNewWordsPanel(/*File file,*/Article arti){
			//dnwp.fillNewWords(NewWordsUtils.filterWords(newWords, file.getName()),arti.getText());
			dnwp.fillNewWords(NewWordsUtils.genWords(newWords, arti.getNewWords(),
					ArticleUtils.docName(arti)),
				arti.getText());
		}
		
		private void freshPhrasesPanel(/*File file,*/Article article){
			dpp.fillArticle(article);
			dpp.updateNewFileItem(new Item(article.getFilePath(),new Date()));
		}
		
		private boolean lookupVoice(NewWord newWord){
			DirSnap ds = appCtx.requestObject(DirSnaps.class).fromFile(config.getMp3LibPath());
			String termKey = newWord.getTermName();
			if(termKey!=null && !LangUtils.equals(termKey, "")){
				String ret = VocChecker.lookUpAndCopy(ds, termKey,
						TermUtils.termSavedFullDir(config.getTermBasePath(),termKey));
				if(ret!=null)
					return true;
			}
			return false;
		}
		
		private void readWordVoice(String word){
			if(word == null || word.length()==0)
				return;
			File[] files = FsUtils.findMp3WaveFiles(
					TermUtils.termSavedFullDir(config.getTermBasePath(),word));
			if(files!=null && files.length > 0){
				//Utils.log.info("About to play file:"+files[0]);
				new Mp3Player(null).play(files[0].getPath());
			}else{
				//Utils.log.info("word's file not found:"+word);
			}
		}
		
		void loadArticlesToPreview(){
			String[] files = AntUtils.listFiles(config.getDocsPath(),
					new String[]{"**/*.doc","**/*.DOC"},new String[]{"**/read/*"});
			docs = FjUtils.toFiles(config.getDocsPath(),files);
			pArticles = ArticleUtils.loadArticles(docs,new Converter<File,Article>(){
				public Article to(File f) {
					try {
						Article arti = new ScalaUtils().doc2Article(f);
						arti.getRi().setLastrvt(new Date(f.lastModified()));
						return arti;
					}catch(Exception e){
						e.printStackTrace();
					}
					return null;
				}
			});
		}
		
		void loadImportedArticle(){
			articles = ArticleUtils.loadArticles(config.getArticlesPath());
		}
		
		LuceneSearch luceneFileSearcher = new LuceneSearch() {
			public void searchKeyword(String keyword) {
				try {
					sr = Lucene.queryDocs(config.getIndexPath(), Indexable.DT_FILE,
							keyword, Indexable.FN_CONTENTS);
					dsp.fillSearchResult(sr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		LuceneSearch luceneNWSearcher = new LuceneSearch() {
			public void searchKeyword(String keyword) {
				try {
					sr = Lucene.queryDocs(config.getIndexPath(),
							NewWord.class.getName(),
							keyword, new String[]{Indexable.FN_CONTENTS, Indexable.FN_NW_EXPL});
					dnwp.fillSearchResult(sr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		NewWordHandler wordHandler = new NewWordHandler(){

			public void saveWord(NewWord nw) {
				if(nw == null)
					return;
				for(NewWord lnw:newWords){
					if(NewWordsUtils.sameEntry(lnw, nw)){
						lnw.setPos(nw.getPos());
						lnw.setPartOfSpeech(nw.getPartOfSpeech());
						lnw.setSeqNo(nw.getSeqNo());
						NewWordsUtils.saveWord(config.getNewWordsBasePath(), lnw);
						return;
					}
				}
				newWords.add(nw);
				NewWordsUtils.saveWord(config.getNewWordsBasePath(), nw);
			}

			@Deprecated
			public NewWord loadWord(String key) {
				/*return FsUtils.loadJox2(FilenameUtils.concat(config.getTermBasePath(), 
						key+"/"+key+".xml"), NewWord.class, null);*/
				NewWord w = null;
				for(NewWord nw:newWords){
					if(nw.getSpell().equals(key)){
						w = nw;
						break;
					}
				}
				return w;
			}

			public void indexWord(final NewWord nw) {
				lucene.indexDoc(config.getIndexPath(), new Lucene.Indexable() {
					public Document toDocument() {
						Document doc = new Document();

						NumericField modifiedField = new NumericField(FN_MODIFIED);
						modifiedField.setLongValue(System.currentTimeMillis());
						doc.add(modifiedField);

						doc.add(new Field(FN_NW_EXPL, 
								DsUtils.defaultIfNull(nw.getNotes(),""), 
								Field.Store.YES,
								Field.Index.ANALYZED,
								Field.TermVector.WITH_POSITIONS_OFFSETS ));
						
						doc.add(new Field(FN_NW_TAG, 
								DsUtils.defaultIfNull(NewWordsUtils.getTagString(nw),"") ,
								Field.Store.YES,
								Field.Index.ANALYZED,
								Field.TermVector.WITH_POSITIONS_OFFSETS ));
						return doc;
					}
					public String id() {
						return NewWordsUtils.newWordIndexId(nw);
					}
					public void close() {
					}
					public String docType() {
						return nw.getClass().getName();
					}
				});
			}

			public void removeWordTag(String tag) {
        		NewWordsUtils.removeWordsTag(config.getNewWordsBasePath(),newWords, tag);
			}

			public boolean removeWord(NewWord nw) {
				newWords.remove(nw);
				return NewWordsUtils.removeWord(config.getNewWordsBasePath(),nw);
			}
			
			public boolean lookupVoice(NewWord nw) {
				return Runtime.this.lookupVoice(nw);
			}
			
			public String localExpl(NewWord nw, String def){
				String termName = nw.getTermName();
				if(!LangUtils.isEmpty(termName)){
					Term t = termHandler.load(termName);
					if(t == null){
						System.out.println("term not found for "+termName);
						return def;
					}
					
					Meaning m = TermUtils.meaning(t,nw.getPartOfSpeech(),
							nw.getTermEntryNo()-1);
					if(m != null){
						return m.explString();
					}
				}
				System.out.println("meaning not found for ["+termName+","
						+nw.getPartOfSpeech()+","+nw.getTermEntryNo()+"]");
				return def;
			}

			public NewWord findNewWordByIndexId(String id) {
				return NewWordsUtils.findNewWordByIndexId(newWords, id);
			}
			
			public void pronounce(NewWord nw){
				if(nw != null)
					readWordVoice(nw.getTermName());
			}
			
		};
		
		VoicePronouncer vp = new VoicePronouncer() {
			public void readVoice(String word) {
				readWordVoice(word);
			}
		};
		
		FileHandler fileSelectedInSearchResultTable = new FileHandler() {
			public void setInput(File file) {
				try {
					/*config.getRecentMatchedFiles().addItem(file.getPath());*/
					Article article = ArticleUtils.loadArticle(config.getArticlesPath(),
							FilenameUtils.getBaseName(file.getPath()));
					freshNewWordsPanel(/*file,*/article);
					freshPhrasesPanel(/*file,*/article);
					//Lucene.termFreqVector(config.indexPath, file.getPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		RecentFilePathsFetcher rfpf = new RecentFilePathsFetcher() {
			
			public Item[] recentFiles() {
				return config.getRecentIndexedFiles().fetchItems();
			}
		};
		
		TagHandler tagHandler = new TagHandler(){
			public void saveTag(Tag tag) {
				FsUtils.saveJox2(FilenameUtils.concat(appCtx.dataDirPath(),
						"Tags_"+tag.getName()+".xml"), tag);
			}
			
			public Tag loadTag(String rootName){
				Tag t = FsUtils.loadJox2(FilenameUtils.concat(appCtx.dataDirPath(),
						"Tags_"+rootName+".xml"), Tag.class, null);
				if(t==null)
					t = new Tag(rootName);
				return t;
			}
		};

		FileHandler fileChoosedByUser = new FileHandler() {
			public void setInput(File file) {
				config.setLastFilePath(file.getParent());
				config.getRecentIndexedFiles().addItem(file.getPath());
				file = FsUtils.copyFileToDirectory(file,
						FilenameUtils.concat(config.getArticlesPath(),
								FilenameUtils.getBaseName(file.getPath())),
						null);
				lucene.indexDoc(config.getIndexPath(),
						new Lucene.IndexableFile(file,
								PoiUtils.newWorddocConentParser()));
				Article article = new ScalaUtils().doc2Article(file);
				ArticleUtils.fillArticle(config.getArticlesPath(),article);
				freshNewWordsPanel(/*file,*/article);
				freshPhrasesPanel(/*file,*/article);
				article.getRi().touch(new Date(), 0, 0);
				ArticleUtils.saveArticle(config.getArticlesPath(), article);
				DsUtils.add(articles, article, false);
				fcp.freshImportedArticles(articles, ArticleUtils.fullView());
			}
		};
		
		ArticlePersistHandler articleHandler = new ArticlePersistHandler() {
			public void save(Article article) {
				ArticleUtils.saveArticle(config.getArticlesPath(),article);
			}

			public Article load(String docName) {
				String nDocName = ArticleUtils.findMatchedArticleDir(config.getArticlesPath(), docName);
				if(nDocName==null)
					return null;
				return ArticleUtils.loadArticle(config.getArticlesPath(), nDocName);
			}
		};
		
		WordListGenerator wordListGenerator = new WordListGenerator() {
			public List<NewWord> allWords() {
				return newWords;
			}
			
			public List<NewWord> pronunWords() {
				return NewWordsUtils.filterWordsWithPronun(newWords,true);
			}

			public List<NewWord> wordsWithDocName(String docName) {
				return NewWordsUtils.filterWords(newWords, docName);
			}

			public String[] docNames() {
				return NewWordsUtils.docNames(newWords);
			}

			@Override
			public List<NewWord> spellWords() {
				return NewWordsUtils.filterWordsWithSpell(newWords,true);
			}

			@Override
			public List<NewWord> explWords() {
				return NewWordsUtils.filterWordsWithExpl(newWords,true);
			}
		};
		
		AdminHandler admin = new AdminHandler() {
			public void clearLuceneIndex() {
				lucene.deleteAll(config.getIndexPath());
			}

			public void importWords() {
				NewWordsUtils.migrateWords(config.getTermBasePath(), config.getNewWordsBasePath());
			}

			public void indexNewWords() {
				for (NewWord nw : NewWordsUtils.filterIndexableNewWords(newWords)) {
					wordHandler.indexWord(nw);
				}
			}

			public void removeOldWords() {
				NewWordsUtils.removeOldWords(config.getTermBasePath());
			}

			public void lookupVoices() {
				for (NewWord nw : newWords) {
					wordHandler.lookupVoice(nw);
				}
			}

			public void renameTermsExt() {
				String[] fps = AntUtils.listFiles(config.getTermBasePath(), 
						new String[]{"**/.xml"});
				File[] fs = FjUtils.toFiles(config.getTermBasePath(), fps);
				for (File file : fs) {
					String oldPath = FilenameUtils.getFullPath(
							file.getPath());
					oldPath = FilenameUtils.normalizeNoEndSeparator(oldPath);
					String newPath = FilenameUtils.concat(oldPath, 
							FilenameUtils.getName(oldPath)+".xml");
					//Utils.log.debug("rename:"+oldPath+"-->"+newPath);
					file.renameTo(new File(newPath));
				}
			}

			@Override
			public void setNewwordsNote() {
				for (NewWord nw : newWords) {
					String expl = wordHandler.localExpl(nw,nw.getNotes());
					nw.setNotes(expl);
				}
			}
		};
		
		protected ArticleOpener articleOpener = new ArticleOpener() {
			public void open(Article arti) {
				freshNewWordsPanel(/*null,*/ arti);
				freshPhrasesPanel(arti);
				tabbedPane.setSelectedComponent(dnwp);
			}
		};
		
		protected ArticlesLoader articleLoader = new ArticlesLoader() {
			public void reloadPreviewd() {
				loadArticlesToPreview();
				fcp.freshPreviewedArticles(pArticles, ArticleUtils.fullView());
			}
			public void reloadImported() {
				loadImportedArticle();
				fcp.freshImportedArticles(articles, ArticleUtils.fullView());
			}
		};
		
		protected ArticleOpener pArticleOpener = new ArticleOpener() {
			public void open(Article arti) {
				freshNewWordsPanel(/*null,*/ arti);
				freshPhrasesPanel(arti);
				//tabbedPane.setSelectedComponent(dnwp);
				SwingUtils.deskOpen(FsUtils.toCanonicalPath(arti.getFilePath()));
			}
		};
		
		protected TermHandler termHandler = new TermHandler(){

			public void save(Term term) {
				TermUtils.save(TermUtils.termSavedFullDir(
						config.getTermBasePath(),term), term);
			}
			
			public Term load(String key){
				return TermUtils.load(TermUtils.termSavedFullDir(
						config.getTermBasePath(),key), key);
			}
		};
		protected JTabbedPane tabbedPane;
		protected File[] docs;
		protected FileListPanel flp;
		protected List<Article> pArticles;
		protected HtmlDictPanel hdp;
	}

	public AppLifeCycle lifeCycle() {
		if(alc == null)
			alc = new AppLifeCycle(){
				public void init(AppContext ctx) {
					runtime.appCtx = ctx;
					runtime.config = runtime.appCtx.getConfig(DocReaderApp.class.getSimpleName(), Config.class);
					runtime.wordsTag = runtime.tagHandler.loadTag(Tag.WORDSTAG);
					runtime.loadImportedArticle();
					runtime.newWords = NewWordsUtils.loadWords(runtime.config.getNewWordsBasePath());
					runtime.lucene = new Lucene();
					runtime.dict = new OxfordA7WebDict();
					runtime.loadArticlesToPreview();
				}
		
				public void exit() {
					runtime.appCtx.saveConfig(DocReaderApp.class.getSimpleName(), runtime.config);
					if(runtime.sr!=null)
						runtime.sr.close();
					Lucene.close(runtime.lucene);
				}
			};

		return alc;
	}

	public TabbedUI tabbedUI() {
		return new TabbedUI(){
			public String tabName() {
				return ".Doc reader";
			}
	
			public String tabDescription() {
				return "ms doc file reader";
			}
	
			public Container getUI() {
				final JTabbedPane tabbedPane = new JTabbedPane();
				runtime.tabbedPane = tabbedPane;
				runtime.cp = new ConfigPanel(runtime.config, runtime.admin);
				tabbedPane.addTab("config", null, runtime.cp, "configurations");
	
				runtime.fcp = new FileChoosePanel(tabbedPane, runtime.fileChoosedByUser,
						runtime.config.getLastFilePath(),
						runtime.articles,runtime.pArticles,
						ArticleUtils.fullView(),
						runtime.articleOpener,runtime.pArticleOpener,
						runtime.articleLoader);
				tabbedPane.addTab(".docfile", null, runtime.fcp, "choose doc file");
				// tabbedPane.setTabComponentAt(0, new ClosableTab(tabbedPane));
	
				runtime.dtp = new HtmlArticlePanel();
				tabbedPane.addTab(".doccontent", null, runtime.dtp,
						"parse a word document");
				// tabbedPane.setTabComponentAt(1, new ClosableTab(tabbedPane));
	
				runtime.dpp = new DocPhrasesPanel(runtime.rfpf,runtime.fileSelectedInSearchResultTable,
						runtime.articleHandler);
				tabbedPane.addTab("phrases", null, runtime.dpp, "phrases in the doc");
	
				runtime.dnwp = new DocNewWordsPanel(runtime.vp,runtime.wordHandler,
						runtime.rfpf,runtime.fileSelectedInSearchResultTable,
						runtime.luceneNWSearcher,runtime.wordListGenerator,
						runtime.articleHandler,runtime.dict, runtime.termHandler);
				tabbedPane.addTab("wordlist", null, runtime.dnwp, "new words in the doc");
	
				runtime.dsp = new DocSearchPanel(runtime.luceneFileSearcher,
						runtime.fileSelectedInSearchResultTable);
				tabbedPane.addTab("search", null, runtime.dsp, "lucene search");
	
				runtime.wtp = new WordTagsPanel(runtime.wordsTag,
						runtime.tagHandler, runtime.wordHandler, runtime.newWords);
				tabbedPane.addTab("word tags", null, runtime.wtp, "word tags");

				runtime.oa7p = new OxfordA7Panel(runtime.dict);
				tabbedPane.addTab("oxa7", null, runtime.oa7p, "oxfordA7 dict");

				runtime.flp = new FileListPanel("Files *.doc under "+
						/*FilenameUtils.getFullPath(runtime.config.getDocsPath()),*/
						FsUtils.toCanonicalPath(runtime.config.getDocsPath()),
						DsUtils.asList(runtime.docs),
						SwingUtils.fileView());
				tabbedPane.addTab("docs", null, runtime.flp, "new docs");
				
				runtime.hdp = new HtmlDictPanel();
				tabbedPane.addTab("htmldict", null, runtime.hdp, "html dict");

				tabbedPane.setSelectedComponent(runtime.fcp);
				tabbedPane.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						System.out.println("tabbedPane changed.");
						tabbedPane.getSelectedComponent().requestFocusInWindow();
					}
				});
	
				return tabbedPane;
			}
		};
	}

}
