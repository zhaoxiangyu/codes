package org.sharpx.swing.apps.el.ui;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JTabbedPane;

import org.sharpx.swing.intf.Pluggable.TabbedUI;
import org.sharpx.utils.LangUtils;
import org.sharpx.swing.apps.el.ui.widget.HtmlPanel;
import org.sharpx.swing.base.BasePanel;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.NewInputHandler;
import org.sharpx.swing.widget.WordInputPanel;

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