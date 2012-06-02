package org.sharp.swing.apps.el.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.sharp.intf.FileHandler;
import org.sharp.intf.ItemSelected;
import org.sharp.swing.apps.beans.RecentItems.Item;
import org.sharp.swing.apps.el.beans.Article;
import org.sharp.swing.apps.el.beans.Phrase;
import org.sharp.swing.apps.el.beans.Tag;
import org.sharp.swing.apps.el.intf.ArticlePersistHandler;
import org.sharp.swing.apps.el.intf.RecentFilePathsFetcher;
import org.sharp.swing.apps.el.ui.widget.TagListPanel;
import org.sharp.swing.apps.el.utils.ArticleUtils;
import org.sharp.swing.apps.el.utils.PhrasesUtils;
import org.sharp.swing.base.BasePanel;
import org.sharp.swing.utils.SwingUtils;
import org.sharp.swing.utils.SwingUtils.JListItemClickListener;
import org.sharp.swing.utils.SwingUtils.JRadioGroup;
import org.sharp.swing.utils.SwingUtils.NewInputHandler;
import org.sharp.swing.utils.SwingUtils.JTableUtilsX.JTableHandler;

public class DocPhrasesPanel extends BasePanel {
	private static final long serialVersionUID = -692330154887078355L;
	private Article arti;
	private List<Phrase> phrases;
	private Phrase phrase;
	JTextArea article;
	private ArticlePersistHandler articleHandler;
	/*JList<Phrase> phrasesL;*/
	private JScrollPane articleSP;
	
	private FileHandler fileHandler;
	private RecentFilePathsFetcher fpsFetcher;
	JComboBox recentFilesCB;
	JTextArea notes;
	private TagListPanel<Phrase> tlp;
	private JTableHandler<Phrase> pth;
	private JListItemClickListener jlicl = new JListItemClickListener() {
		public void clicked(Integer index) {
			freshPhrasesData(pt.noun,pt.verb,pt.adj,pt.adv,pt.newwords,pt.all);
			if(index -1 >= 0){
				Tag st = tlp.tag().getChildren()[index-1];
				phrases = PhrasesUtils.filterWords(phrases,new String[]{st.getName()},true);
			}
			freshPhrasesTable();
		}
	};
	private class PhraseTypes {
		boolean newwords,noun,verb,adj,adv, all;
		void set(boolean noun,boolean verb,boolean adj,boolean adv, boolean newwords,boolean all){
			this.noun = noun;
			this.verb = verb;
			this.adj = adj;
			this.adv = adv;
			this.newwords = newwords;
			this.all = all;
		}
	}
	
	PhraseTypes pt;
	
	public DocPhrasesPanel(RecentFilePathsFetcher fpsf,FileHandler fh,
			ArticlePersistHandler aph) {
		setLayout(new MigLayout("aligny top"));

		fpsFetcher = fpsf;
		fileHandler = fh;
		articleHandler = aph;
		pt = new PhraseTypes();
		
		add(controlPanel(), "span,align 50% 50%");

		pth = SwingUtils.JTableUtilsX.newJTable(PhrasesUtils.briefView(),
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION,
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting())
							return;
						int row = pth.jTable().getSelectedRow();
						if(row >=0 && row < phrases.size()){
							phrase = phrases.get(row);
							System.out.println("phrase:"+phrase.getText());
							if(phrase != null){
								SwingUtils.highlightText(article, 
										phrase.getOffset(), 
										phrase.getOffset()+phrase.getText().length());
								SwingUtils.scrollTo(articleSP, article, 
										phrase.getOffset());
								notes.setText(phrase.getNote());
								//notes.requestFocusInWindow();
							}
						}
					}
				});
		add(pth.jScrollPane(),"span 1 2,width 40%");
		article = SwingUtils.newTextArea(
				"Masked showed here.", true, true, false, null);
		articleSP = new JScrollPane(article);
		add(articleSP,"width 40%,height 40%:40%:,grow");
		tlp = new TagListPanel<Phrase>(null, null, 
					new TagListPanel.TagEventHandler<List<Phrase>>() {
						public void tagDelete(String tagName) {
							PhrasesUtils.removeTag(arti.getPhrases(),tagName);
							articleHandler.save(arti);
						}
						public void tagSwitched(String tagName) {
						}
						public void dataDropOnTag(List<Phrase> data,
								String tagName, String cTagName) {
							for(Phrase phr:data){
								System.out.println(phr.getText()+" dropped on tag:"+tagName);
								PhrasesUtils.addTag(phr,tagName);
							}
							articleHandler.save(arti);
						}
			}, Phrase.class, jlicl);
		add(phraseInfoPanel(),"width 20%,span 1 2,wrap,height ::80%");
		add(new JScrollPane(tlp),"grow");
		
	}
	
	/*public DocPhrasesPanel(Runtime.RecentFilePathsFetcher fpsf,FileHandler fh,
			ArticlePersistHandler aph) {
		setLayout(new BorderLayout());
		String layoutDef = 
		    "(COLUMN (LEAF name=controlPanel weight=0.1) " +
			    "(ROW weight=0.9 (LEAF name=phraseList weight=0.4) " +
			    	"(COLUMN weight=0.4 (LEAF name=article weight=0.2) " +
			    		"(LEAF name=tag weight=0.8))"+
			    	"(LEAF name=phraseInfo weight=0.2)) "+
			")";
		MultiSplitPane multiSplitPane = SwingUtils.newMutliSplitPane(null, layoutDef);

		fpsFetcher = fpsf;
		fileHandler = fh;
		articleHandler = aph;
		
		multiSplitPane.add(controlPanel(), "controlPanel");

		article = SwingUtils.newTextArea(
				"Masked showed here.", true, true, false, null);
		articleSP = new JScrollPane(article);
		multiSplitPane.add(articleSP, "article");
		pth = SwingUtils.JTableUtilsX.newJTable(PhrasesUtils.briefView(),
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION,
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting())
							return;
						int row = pth.jTable().getSelectedRow();
						if(row >=0 && row < phrases.size()){
							Phrase phrase = phrases.get(row);
							System.out.println("phrase:"+phrase.getText());
							if(phrase != null){
								SwingUtils.highlightText(article, 
										phrase.getOffset(), 
										phrase.getOffset()+phrase.getText().length());
								SwingUtils.scrollTo(articleSP, article, 
										phrase.getOffset());
							}
						}
					}
				});
		multiSplitPane.add(pth.jScrollPane(), "phraseList");
		tlp = new TagListPanel<Phrase>(null, null, 
					new TagListPanel.TagEventHandler<List<Phrase>>() {
						public void tagDelete(String tagName) {
							PhrasesUtils.removeTag(arti.getPhrases(),tagName);
							articleHandler.save(arti);
						}
						public void tagSwitched(String tagName) {
						}
						public void dataDropOnTag(List<Phrase> data,
								String tagName, String cTagName) {
							for(Phrase phr:data){
								System.out.println(phr.getText()+" dropped on tag:"+tagName);
								PhrasesUtils.addTag(phr,tagName);
							}
							articleHandler.save(arti);
						}
			}, Phrase.class, jlicl);
		multiSplitPane.add(new JScrollPane(tlp), "tag");
		
		multiSplitPane.add(phraseInfoPanel(),"phraseInfo");
		
		add(multiSplitPane,BorderLayout.CENTER);
	}*/
	
	private JPanel controlPanel() {
		JPanel controlPanel = new JPanel();
		recentFilesCB = SwingUtils.newComboBox(fpsFetcher.recentFiles(), 0, false, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Item i = (Item) recentFilesCB.getSelectedItem();
				if(i!=null)
					fileHandler.setInput(new File(i.getItem()));
			}
		});
		controlPanel.add(recentFilesCB);
		
		JRadioGroup jrp = SwingUtils.newRadioGroup(new String[]{"newwords","noun","verb","adj","adv","all"}, 0, 
				new ItemSelected<String>() {
					public void itemSelected(String o) {
						if(o.equals("noun")){
							freshPhrasesData(true,false,false,false,false,false);
						}else if(o.equals("verb")){
							freshPhrasesData(false,true,false,false,false,false);
						}else if(o.equals("adj")){
							freshPhrasesData(false,false,true,false,false,false);
						}else if(o.equals("adv")){
							freshPhrasesData(false,false,false,true,false,false);
						}else if(o.equals("newwords")){
							freshPhrasesData(false,false,false,false,true,false);
						}else if(o.equals("all")){
							freshPhrasesData(false,false,false,false,false,true);
						}
						freshPhrasesTable();
					}
				});
		controlPanel.add(jrp.jPanel());
		return controlPanel;
	}
	
	private JPanel phraseInfoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(new JLabel("Input notes here."),"wrap");
		notes = SwingUtils.newTextArea("",
				true, true, true, new NewInputHandler() {
					public void newInput(String text) {
						if(phrase!=null){
							phrase.setNote(text);
							articleHandler.save(arti);
						}
					}
				});
		JScrollPane jsp = new JScrollPane(notes);
		panel.add(jsp,"width 90%:90%:,height 90%:90%:90%,grow");
		addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				
			}
			
			public void focusGained(FocusEvent e) {
				notes.requestFocusInWindow();
			}
		});
		return panel;
	}

	private void freshPhrasesData(boolean noun,boolean verb,boolean adj,boolean adv, boolean newwords,boolean all){
		pt.set(noun, verb, adj, adv, newwords,all);
		String maskedText = ArticleUtils.getMaskedArticleText(arti, !noun, 
	    		!verb, !adj,
	    		!adv);
	    article.setText(maskedText);
	    phrases = ArticleUtils.getPhrases(arti, noun, verb,adj,adv,newwords, all);
	}
	
	private void freshPhrasesTable(){
	    pth.set(phrases, PhrasesUtils.briefView(), null);
	    pth.set(true, DropMode.ON, SwingUtils.newJTableTranserHandlerDrag(
	    		phrases,Phrase.class));
		article.setCaretPosition(0);
	}

	public void fillArticle(Article art) {
		if(art == null){
			article.setText("Article not found.");
			return;
		}
		arti = art;
		freshPhrasesData(false,false,false,false,true,false);
		freshPhrasesTable();
		tlp.showRootTag(arti.getPhraseTag());
	}
	
	public void updateNewFileItem(Item item){
		if(recentFilesCB.getItemCount()>=0){
			Item i = (Item) recentFilesCB.getItemAt(0);
			if(!i.getItem().equals(item.getItem()))
				recentFilesCB.insertItemAt(item, 0);
			else
				i.setVisitTime(item.getVisitTime());
		}else{
			recentFilesCB.insertItemAt(item, 0);
		}
	}
}