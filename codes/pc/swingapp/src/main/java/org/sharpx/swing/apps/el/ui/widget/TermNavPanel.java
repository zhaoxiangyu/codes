package org.sharpx.swing.apps.el.ui.widget;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang3.math.NumberUtils;
import org.sharpx.swing.intf.ItemSelected;
import org.sharpx.utils.jdkex.JdkUtils;
import org.sharpx.utils.DsUtils;
import org.sharpx.swing.apps.el.WebDict;
import org.sharpx.swing.apps.el.beans.term.beans.Meaning;
import org.sharpx.swing.apps.el.beans.term.beans.PartOfSpeech;
import org.sharpx.swing.apps.el.beans.term.beans.Term;
import org.sharpx.swing.apps.el.utils.TermUtils;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.JRadioGroup;
import org.sharpx.swing.utils.SwingUtils.NewIntegerHandler;
import org.sharpx.swing.utils.SwingUtils.SpinnerHandler;

public class TermNavPanel extends JPanel {
	private PartOfSpeech pos;
	private Meaning meaning;
	private TermNavEventHandler nch;
	private SpinnerHandler sph;
	private JTextArea expl;
	private boolean showExpl = true;
	private Term term;
	
	public static interface TermNavEventHandler {
		void meaningSelected(String meaning);
	}
	
	public TermNavPanel(Term t,TermNavEventHandler nih, boolean showExpla){
		nch = nih;
		showExpl = showExpla;
		setLayout(new MigLayout());
		setTerm(t);
	}
	
	public Term lookupTerm(WebDict dict,String keyword){
		Term t = dict.lookup(keyword);
		if(t!=null){
			setTerm(t);
			nch.meaningSelected(meaning.explString());
		}else{
			nch.meaningSelected("The word you typed '"+keyword+"' not found on web.");
		}
		return t;
	}
	
	public int getMeaningNo(int def){
		Meaning m = meaning;
		if(m!=null){
			String meaningNo = m.getNo();
			return NumberUtils.toInt(meaningNo, 1);
		}
		return def;
	}
	
	public Meaning getMeaning(){
		return meaning;
	}
	
	public String getPartOfSpeech(String def){
		if(pos!=null)
			return DsUtils.defaultIfNull(pos.getPartofspeech(), def);
		return def;
	}
	
	private void setTerm(final Term t){
		setTerm(t,null,1);
	}
	
	private String meaningStr(Meaning m, String def){
		if(m!=null){
			String explText = m.exampleString();
			explText += "\n\n" + m.explString();
			return explText;
		}
		return def;
	}
	
	public void setTerm(final Term t, String wPos, int wEntryNo){
		removeAll();
		term = t;
		
		//add(new JLabel("Part of speech:"));	//TODO term "well" has 5 part of speech
		if(t!=null){
			pos = TermUtils.partOfSpeech(t,wPos);
			if(pos == null)
				pos = DsUtils.elementAt(t.getPses(), 0);
			int posi = DsUtils.at(t.getPses(), pos, 0);
			JRadioGroup partOfSpeech = SwingUtils.newRadioGroup(
					TermUtils.partOfSpeeches(t), posi, 
					new ItemSelected<String>() {
						public void itemSelected(String selectedPos) {
							pos = TermUtils.partOfSpeech(t,selectedPos);
							sph.setMax(TermUtils.meaningsCount(pos,1));
							meaning = TermUtils.meaning(pos, 0, null);
							nch.meaningSelected(meaning.explString());
							SwingUtils.setText(expl, meaningStr(meaning,"explanation in dict."));
						}
					});
			add(partOfSpeech.jPanel(),"spanx,wrap");
		}
			
		add(new JLabel("NO:"));
		meaning = TermUtils.meaning(pos, wEntryNo - 1, null);
		sph = SwingUtils.newSpinner(wEntryNo,TermUtils.meaningsCount(pos,1),
				new NewIntegerHandler(){
			public void newInt(int num) {
				meaning = TermUtils.meaning(pos,num-1, meaning);
				nch.meaningSelected(meaning.explString());
				SwingUtils.setText(expl, meaningStr(meaning,"explanation in dict."));
			}
		});
		add(sph.jSpinner(),"wrap");
		
		if(pos!=null)
			add(new JLabel(pos.proString()),"spanx,wrap");

		expl = SwingUtils.newTextArea(meaningStr(meaning,"explanation in dict."),
				true, true, false, null);
		if(showExpl){
			add(new JScrollPane(expl),"spanx,width 95%!,grow,wrap");
		}

		updateUI();
	}
}