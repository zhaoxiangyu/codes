package org.sharp.swing.apps.el.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.sharp.intf.FileHandler;
import org.sharp.intf.ItemSelected;
import org.sharp.jdkex.LangUtils;
import org.sharp.swing.apps.el.WebDict;
import org.sharp.swing.apps.el.beans.Article;
import org.sharp.swing.apps.el.beans.NewWord;
import org.sharp.swing.apps.el.intf.ArticlePersistHandler;
import org.sharp.swing.apps.el.intf.LuceneSearch;
import org.sharp.swing.apps.el.intf.NewWordHandler;
import org.sharp.swing.apps.el.intf.RecentFilePathsFetcher;
import org.sharp.swing.apps.el.intf.VoicePronouncer;
import org.sharp.swing.apps.el.ui.widget.WordInfoPanel;
import org.sharp.swing.apps.el.ui.widget.WordInfoPanel.TermHandler;
import org.sharp.swing.apps.el.utils.NewWordsUtils;
import org.sharp.swing.base.BasePanel;
import org.sharp.swing.utils.SwingUtils;
import org.sharp.swing.utils.SwingUtils.JRadioGroup;
import org.sharp.swing.utils.SwingUtils.JTableUtilsX;
import org.sharp.swing.utils.SwingUtils.NewInputHandler;
import org.sharp.swing.utils.SwingUtils.JTableUtilsX.JTableHandler;
import org.sharp.swing.widget.WordInputPanel;
import org.sharp.utils.Lucene;
import org.sharp.utils.Lucene.Indexable;
import org.sharp.utils.Lucene.SearchResult;
import org.sharp.utils.Lucene.TextHighlighter;

public class DocNewWordsPanel extends BasePanel {
	private static final long serialVersionUID = 758376158099512144L;

	public static interface WordListGenerator {
		List<NewWord> allWords();
		List<NewWord> pronunWords();
		List<NewWord> wordsWithDocName(String docName);
		String[] docNames();
		List<NewWord> spellWords();
		List<NewWord> explWords();
	}

	private List<NewWord> newWords = new ArrayList<NewWord>();
	private List<NewWord> nwsSearched = new ArrayList<NewWord>();
	private String text;
	private VoicePronouncer pronouncer;
	private NewWordHandler nwh;
	//JTextArea notes;
	JEditorPane article;
	WordInfoPanel wordPanel;
	/*private NewWord nw;*/
	private FileHandler fileHandler;
	private RecentFilePathsFetcher fpsFetcher;
	JComboBox recentFilesCB;
	JComboBox<String> docsCB;
	JTableHandler<NewWord> wordTable;

	WordInputPanel wordInputPanel;

	private JCheckBox pron;

	private WordListGenerator wlg;
	private ArticlePersistHandler aph;
	private WebDict dict;
	private TermHandler th;

	public DocNewWordsPanel(VoicePronouncer vp,
			NewWordHandler localNewWordStore, 
			RecentFilePathsFetcher fpsf, FileHandler fh,
			final LuceneSearch se, WordListGenerator wlge,
			ArticlePersistHandler apha,WebDict wdict,
			TermHandler tha) {
		fpsFetcher = fpsf;
		fileHandler = fh;
		pronouncer = vp;
		nwh = localNewWordStore;
		wlg = wlge;
		aph = apha;
		dict = wdict;
		th = tha;

		setLayout(new BorderLayout());
		add(controlPanel(se), BorderLayout.NORTH);

		article = SwingUtils.newEditorPane("text/html",
				"<html><font color=red>File's text showed here.</font></html>", 
				false,
				true);
		add(new JScrollPane(article), BorderLayout.CENTER);
		
		wordPanel = new WordInfoPanel(nwh,dict,th);
		add(wordPanel, BorderLayout.EAST);
		
		JScrollPane jsp = new JScrollPane();
		wordTable = JTableUtilsX.newJTable(jsp,
				newWords, NewWordsUtils.inDocView(),
				5,ListSelectionModel.SINGLE_SELECTION,
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						int row = wordTable.jTable().getSelectedRow();
						List<NewWord> nwl = wordTable.data();
						if(row >=0 && row < nwl.size()){
							NewWord word = nwl.get(row);
							System.out.println("word:"+word.getSpell());
							wordPanel.showWord(word);
							if(!StringUtils.isEmpty(word.getTermName()))
								nwh.pronounce(word);
							String txt = DocNewWordsPanel.this.articleText(word.getDocName());
							if(!LangUtils.isEmpty(txt)){
								String highlightWord = StringUtils.defaultIfBlank(
										word.getSpell(), word.getTermName());
								String[] mstr = TextHighlighter.highlight(highlightWord, txt);
								showText(LangUtils.join(mstr, "<br><br>"));
							}
						}
					}
				},true,null,null,null,null);
		wordTable.jTable().setFocusable(false);
		wordTable.jTable().setRequestFocusEnabled(false);
		add(jsp, BorderLayout.WEST);
	}
	
	private JPanel controlPanel(final LuceneSearch se) {
		JPanel panel = new JPanel();
		/*recentFilesCB = SwingUtils.newComboBox(fpsFetcher.recentFiles(), 0, false, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item i = (Item) recentFilesCB.getSelectedItem();
				if(i!=null)
					fileHandler.setInput(new File(i.getItem()));
				pron.setSelected(false);
			}
		});
		panel.add(recentFilesCB);*/
		
		wordInputPanel = new WordInputPanel("similar words:",true,new NewInputHandler() {
			public void newInput(String text) {
				if(LangUtils.equals(text, "")){
					wordTable.set(newWords, NewWordsUtils.inDocView(), null);
				}else
					se.searchKeyword(text);
			}
		});
		panel.add(wordInputPanel);

		/*pron = SwingUtils.newCheckBox("word having pronunciation", false, new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(pron.isSelected()){
					List<NewWord> pwl = wlg.pronunWords();
					NewWordsUtils.sortByLastVt(pwl);
					wordTable.set(pwl, NewWordsUtils.searchResultView(), null);
				}else{
					wordTable.set(newWords, NewWordsUtils.inDocView(), null);
				}
			}
		});
		panel.add(pron);*/
		JRadioGroup jrp = SwingUtils.newRadioGroup(new String[]{"all","pron","spell","expl"}, 0, 
				new ItemSelected<String>() {
					public void itemSelected(String o) {
						chooseWordList(o);
					}
				});
		panel.add(jrp.jPanel());
		
		docsCB = SwingUtils.newComboBox(wlg.docNames(), 0, false, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String docName = docsCB.getItemAt(docsCB.getSelectedIndex());
				List<NewWord> wl = wlg.wordsWithDocName(docName);
				wordTable.set(wl, NewWordsUtils.inDocView(), null);
				String txt = articleText(docName);
				if(!LangUtils.isEmpty(txt))
					showText(txt);
			}
		});
		panel.add(docsCB);

		return panel;
	}
	
	private void chooseWordList(String criteria){
		List<NewWord> pwl = null; 
		if(criteria.equals("all")){
			pwl = wlg.allWords();
		}else if(criteria.equals("pron")){
			pwl = wlg.pronunWords();
		}else if(criteria.equals("spell")){
			pwl = wlg.spellWords();
		}else if(criteria.equals("expl")){
			pwl = wlg.explWords();
		}
		NewWordsUtils.sortByLastVt(pwl);
		wordTable.set(pwl, NewWordsUtils.searchResultView(), null);
	}
	
	private String articleText(String docName){
		Article art = aph.load(docName);
		if(art!=null){
			return art.getText();
		}else{
			showText("Article named "+docName+ " not found.");
			return null;
		}
	}
	
	public void fillNewWords(List<NewWord> words,String txt) {
		newWords = words;
		wordTable.set(newWords, NewWordsUtils.inDocView(), null);
		showText(txt);
	}
	
	private void showText(String txt){
		text = txt;
		article.setText(text);
		article.setCaretPosition(0);
	}
	
	public void fillSearchResult(SearchResult sr){
		TopDocs hits = sr.hits();
		ScoreDoc[] scoreDocs = hits.scoreDocs;
		
		nwsSearched.clear();
		for(int i = 0; i< scoreDocs.length; i++){
			Document doc = sr.doc(scoreDocs[i].doc);
			NewWord nw = new NewWord();
			for(Fieldable f : doc.getFields()){
				if(f.name().equals(Indexable.FN_ID)){
					Pair<String, String> p = Lucene.parseDocId(f.stringValue());
					if(p!=null)
						nw = nwh.findNewWordByIndexId(p.getRight());
				}
			}
			if(nw!=null)
				nwsSearched.add(nw);
		}
		
		NewWordsUtils.sortByLastVt(nwsSearched);
		wordTable.set(nwsSearched, NewWordsUtils.searchResultView(), null);
	}

}