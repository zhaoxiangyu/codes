package org.sharpx.swing.apps.el.ui.widget;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang3.StringUtils;
import org.sharpx.utils.LangUtils;
//import org.sharpx.utils.Utils;
import org.sharpx.swing.apps.el.WebDict;
import org.sharpx.swing.apps.el.beans.NewWord;
import org.sharpx.swing.apps.el.beans.term.beans.Meaning;
import org.sharpx.swing.apps.el.beans.term.beans.Term;
import org.sharpx.swing.apps.el.intf.NewWordHandler;
import org.sharpx.swing.apps.el.ui.widget.TermNavPanel.TermNavEventHandler;
import org.sharpx.swing.apps.el.utils.NewWordsUtils;
import org.sharpx.swing.apps.el.utils.TermUtils;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.NewInputHandler;

public class WordInfoPanel extends JPanel {

	private JLabel newWord;
	private JTextArea notes;
	private NewWord nw;
	private NewWordHandler nwh;
	private JCheckBox pron;
	private JTextField termName;
	private JLabel docName;
	private TermNavPanel tnp;
	private WebDict dict;
	private JTextArea keyWords;
	private TermHandler th;
	private JCheckBox spell;
	private JCheckBox expl;
	
	public static interface TermHandler {
		void save(Term term);
		Term load(String key);
	}

	public WordInfoPanel(NewWordHandler newWordHandler,WebDict wdict,TermHandler tha) {
		nwh = newWordHandler;
		dict = wdict;
		th = tha;
		
		setLayout(new MigLayout());
		add(new JLabel("Spell:"));
		newWord = new JLabel("");
		add(newWord,"spanx,wrap");
		
		add(new JLabel("DocName:"));
		docName = new JLabel("");
		add(docName,"width min:pref:max,wrap");

		add(new JLabel("Term:"));
		termName = SwingUtils.newTextField("", 10, false, null);
		termName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!LangUtils.isEmpty(termName.getText())){
					Term t = tnp.lookupTerm(dict, termName.getText());
					if(t != null){
						th.save(t);
						nw.setTermName(termName.getText());
						nwh.lookupVoice(nw);
						nw.getRi().setLookupt(new Date());
						nwh.saveWord(nw);
					}
				}
			}
		});
		add(termName,"split 2,wrap");

		add(learnInfoPanel(),"wrap,spanx");
		
		tnp = new TermNavPanel(null,new TermNavEventHandler() {
			public void meaningSelected(String text) {
				notes.requestFocusInWindow();
				nw.setPartOfSpeech(tnp.getPartOfSpeech("unknow"));
				nw.setTermEntryNo(tnp.getMeaningNo(-1));
				Meaning m = tnp.getMeaning();
				if(m != null){
					SwingUtils.setText(notes, m.explString());
					nw.setNotes(m.explString());
				}else{
					SwingUtils.setText(notes, "no meaning selected.");
				}
				nwh.saveWord(nw);
			}
		},true);
		add(tnp,"spanx,width 100:300:400,height 30%:30%:50%,growprio 105 105,wrap");
		
		notes = SwingUtils.newTextArea("enter word notes here.",
				true, true, true, new NewInputHandler() {
					public void newInput(String text) {
						inputNotes(text);
					}
				});
		JScrollPane jsp = new JScrollPane(notes);
		add(jsp,"spanx,width 95%!,height 30%:30%:90%:push,grow");

		String keyWordsT = "enter key words here.";
		if(nw!=null)
			keyWordsT = NewWordsUtils.getTagString(nw);
		keyWords = SwingUtils.newTextArea(keyWordsT,
				true, true, true, new NewInputHandler() {
					public void newInput(String text) {
						if(nw != null){
							NewWordsUtils.setTagString(nw,text);
							nwh.saveWord(nw);
						}
					}
				});
		add(new JScrollPane(keyWords),"spanx,width 95%!,height 10%:10%:90%:push,grow");

		addFocusListener(SwingUtils.defaultFocusRequester(termName));
	}
	
	private void saveOldNewWord(){
		nwh.indexWord(nw);
		nwh.saveWord(nw);
	}
	
	private void inputNotes(String notes){
		if(nw!=null){
			nw.setNotes(notes);
			nwh.saveWord(nw);
			nwh.indexWord(nw);
		}
	}
	
	private JPanel learnInfoPanel(){
		JPanel panel = SwingUtils.newPanel(new FlowLayout());
		pron = SwingUtils.newCheckBox("pronunciation", false, new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(nw!=null){
					nw.setLearnPronun(pron.isSelected());
					nwh.saveWord(nw);
				}
			}
		});
		pron.setEnabled(false);
		panel.add(pron);
		
		spell = SwingUtils.newCheckBox("spell", false, new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(nw!=null){
					nw.setLearnSpell(spell.isSelected());
					nwh.saveWord(nw);
				}
			}
		});
		spell.setEnabled(false);
		panel.add(spell);

		expl = SwingUtils.newCheckBox("explanation", false, new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(nw!=null){
					nw.setLearnExpl(expl.isSelected());
					nwh.saveWord(nw);
				}
			}
		});
		expl.setEnabled(false);
		panel.add(expl);
		return panel;
	}
	
	public void showWord(NewWord word){
		if(word==null){
			pron.setEnabled(false);
			return;
		}
		saveOldNewWord();
		nw = word;
		
		nw.getRi().setLastrvt(new Date());
		/*partOfSpeechCP.setSelected(nw.getPartOfSpeech());*/
		newWord.setText(nw.getSpell());
		termName.setText(nw.getTermName());
		/*lookupB.setEnabled(true);*/
		String nwn = nwh.localExpl(nw, nw.getNotes());
		if(LangUtils.isEmpty(nw.getNotes()))
			nw.setNotes(nwn);
		SwingUtils.setText(notes, nw.getNotes());
			
		freshLearnInfoPanel();
		
		docName.setText(nw.getDocName());
		if(!StringUtils.isEmpty(nw.getTermName()))
			tnp.setTerm(th.load(nw.getTermName()),
					nw.getPartOfSpeech(),
					nw.getTermEntryNo());
		keyWords.setText(NewWordsUtils.getTagString(nw));
		if(StringUtils.isEmpty(termName.getText()))
			termName.requestFocusInWindow();
		else
			notes.requestFocusInWindow();
	}
	
	private void freshLearnInfoPanel(){
		pron.setEnabled(true);
		pron.setSelected(nw.getLearnPronun());
		spell.setEnabled(true);
		spell.setSelected(nw.isLearnSpell());
		expl.setEnabled(true);
		expl.setSelected(nw.isLearnExpl());
	}
	
}
