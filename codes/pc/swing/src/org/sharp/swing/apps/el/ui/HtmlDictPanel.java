package org.sharp.swing.apps.el.ui;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JTabbedPane;

import org.sharp.intf.Pluggable.TabbedUI;
import org.sharp.jdkex.LangUtils;
import org.sharp.swing.apps.el.ui.widget.HtmlPanel;
import org.sharp.swing.base.BasePanel;
import org.sharp.swing.utils.SwingUtils;
import org.sharp.swing.utils.SwingUtils.NewInputHandler;
import org.sharp.swing.widget.WordInputPanel;

public class HtmlDictPanel extends BasePanel {
	
	private OxfordA7Web2Dict dict = new OxfordA7Web2Dict();
	private JTabbedPane tabPane;

	public HtmlDictPanel(){
		setLayout(new BorderLayout());
		WordInputPanel tip = new WordInputPanel("Term to lookup:", false, new NewInputHandler() {

			public void newInput(String text) {
				Map<String, String> entry = dict.lookup2(text);
				freshUi(entry);
			}
		});
		add(tip, BorderLayout.NORTH);
		
		freshUi(null);
	}
	
	private void freshUi(Map<String, String> entry){
		if(entry == null){
			if(tabPane != null)
				remove(tabPane);
			tabPane = new JTabbedPane();
			add(tabPane,BorderLayout.CENTER);
			return;
		}
		
		TabbedUI[] tuia = new TabbedUI[0];
		for(String key:entry.keySet()){
			HtmlPanel posHp = new HtmlPanel("text/html",entry.get(key));
			TabbedUI alpT = SwingUtils.toTabbedUI(posHp, key, key);
			tuia = LangUtils.add(tuia, alpT);
		}

		if(tabPane != null)
			remove(tabPane);
		tabPane = SwingUtils.newTabbedPane(
				tuia, 
				null, 0, null);
		add(tabPane,BorderLayout.CENTER);
		updateUI();
	}
	
}