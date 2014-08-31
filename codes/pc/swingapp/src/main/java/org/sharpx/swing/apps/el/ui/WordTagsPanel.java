package org.sharpx.swing.apps.el.ui;

import static org.sharpx.swing.apps.el.utils.NewWordsUtils.addTagView;
import static org.sharpx.swing.apps.el.utils.NewWordsUtils.filterWords;
import static org.sharpx.swing.apps.el.utils.NewWordsUtils.fullView;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.sharpx.swing.apps.el.beans.NewWord;
import org.sharpx.swing.apps.el.beans.Tag;
import org.sharpx.swing.apps.el.intf.NewWordHandler;
import org.sharpx.swing.apps.el.intf.TagHandler;
import org.sharpx.swing.apps.el.ui.widget.TagListPanel;
import org.sharpx.swing.apps.el.utils.NewWordsUtils;
import org.sharpx.swing.base.BasePanel;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.JTableUtilsX.JTableHandler;

@SuppressWarnings("serial")
public class WordTagsPanel extends BasePanel {
	JTableHandler<NewWord> wt;
	JTableHandler<NewWord> twt;
	private NewWordHandler newWordHandler;
	
	private List<NewWord> words;
	private List<NewWord> noTagWords;
	private List<NewWord> tagWords;
	private TagListPanel<NewWord> tl;
	
	public WordTagsPanel(Tag root, TagHandler handler,
			NewWordHandler nwh,
			List<NewWord> nws){
		if(root==null)
			return;
		words = nws;
		newWordHandler = nwh;
		setLayout(new BorderLayout());
		
		add(controlPanel(),BorderLayout.NORTH);
		
		JScrollPane jsp = new JScrollPane();
		wt = SwingUtils.JTableUtilsX.newJTable(jsp, null, 
				NewWordsUtils.addTagView(), 10, 
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, null,
				true, DropMode.ON, null, null, null);
		SwingUtils.addKeyListener(wt.jTable(), new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
		        int id = e.getID();
		        if (id == KeyEvent.KEY_RELEASED) {
		            int kc = e.getKeyCode();
		            String kt = KeyEvent.getKeyText(kc);
		            if(kc == KeyEvent.VK_DELETE){
		            	int row = wt.jTable().getSelectedRow();
		            	if(row >=0 && row < noTagWords.size()){
		            		NewWord nw = noTagWords.get(row);
		            		newWordHandler.removeWord(nw);
		            		freshWordTable(tl.tag().getName());
		            	}
		            }
		            //Utils.log.debug("key:"+kt+" released.");
		        }
		        super.keyTyped(e);
			}
		});
		add(jsp,BorderLayout.WEST);

		add(contentPanel(root,handler),BorderLayout.CENTER);

		//tl.showTag(tl.tag());
		freshWordTable(tl.tag().getName());
	}
	
	private JPanel contentPanel(Tag root, TagHandler handler){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		tl = new TagListPanel<NewWord>(root,handler,
				new TagListPanel.TagEventHandler<List<NewWord>>() {
					public void tagDelete(String tagName) {
			    		newWordHandler.removeWordTag(tagName);
					}
					public void dataDropOnTag(List<NewWord> list, String tagName, String pTagName) {
			        	if(list == null)
			        		return;
			        	for(NewWord nw:list){
			        		nw.addTag(tagName);
				        	newWordHandler.saveWord(nw);
				        	System.out.println("word:"+nw.getSpell()+
				        			" is added new tag:"+tagName);
			        	}
			        	freshWordTable(pTagName);
					}
					public void tagSwitched(String tagName) {
						freshWordTable(tagName);
					}
			},NewWord.class, null);
		panel.add(new JScrollPane(tl), 
			BorderLayout.CENTER);
		
		JScrollPane jsp = new JScrollPane();
		twt = SwingUtils.JTableUtilsX.newJTable(jsp, null, 
				NewWordsUtils.fullView(), 7, 
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION, null,
				true, DropMode.ON, null ,null, null);
		SwingUtils.addKeyListener(twt.jTable(), new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
		        int id = e.getID();
		        if (id == KeyEvent.KEY_RELEASED) {
		            int kc = e.getKeyCode();
		            String kt = KeyEvent.getKeyText(kc);
		            if(kc == KeyEvent.VK_DELETE){
		            	int row = twt.jTable().getSelectedRow();
		            	if(row >=0 && row < tagWords.size()){
		            		NewWord nw = tagWords.get(row);
		            		nw.removeTag(tl.tag().getName());
		            		newWordHandler.saveWord(nw);
		            		freshWordTable(tl.tag().getName());
		            	}
		            }
		            //Utils.log.debug("key:"+kt+" released.");
		        }
		        super.keyTyped(e);
			}
		});
		panel.add(jsp, BorderLayout.SOUTH);
		return panel;
	}
	
	private JPanel controlPanel(){
		JPanel panel = new JPanel();
		return panel;
	}
	
	private void freshWordTable(String tagName){
		noTagWords = filterWords(words, new String[0],false);
		noTagWords = filterWords(noTagWords, false);
		wt.set(noTagWords, addTagView(), 
				SwingUtils.newJTableTranserHandlerDrag(noTagWords,NewWord.class));

		tagWords = filterWords(words, new String[]{tagName},true);
		twt.set(tagWords, fullView(),
				SwingUtils.newJTableTranserHandlerDrag(tagWords,NewWord.class));

	}
}