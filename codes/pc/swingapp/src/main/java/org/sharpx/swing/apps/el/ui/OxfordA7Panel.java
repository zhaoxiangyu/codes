package org.sharpx.swing.apps.el.ui;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import org.sharpx.swing.apps.el.WebDict;
import org.sharpx.swing.apps.el.ui.widget.TermNavPanel;
import org.sharpx.swing.apps.el.ui.widget.TermNavPanel.TermNavEventHandler;
import org.sharpx.swing.base.BasePanel;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.NewInputHandler;
import org.sharpx.swing.utils.SwingUtils.TextPaneHandler;
import org.sharpx.swing.widget.WordInputPanel;

public class OxfordA7Panel extends BasePanel {
	
	WebDict dict;
	private TextPaneHandler expl;
	private TermNavPanel termNavPanel;
	
	public OxfordA7Panel(WebDict dic){
		dict = dic;
		setLayout(new BorderLayout());
		
		WordInputPanel tip = new WordInputPanel("Term to lookup:", false, new NewInputHandler() {
			public void newInput(String text) {
				termNavPanel.lookupTerm(dict, text);
			}
		});
		add(tip, BorderLayout.NORTH);
		
		termNavPanel = new TermNavPanel(null, new TermNavEventHandler() {
			public void meaningSelected(String text) {
				expl.setText(text);
			}
		},false);
		add(termNavPanel, BorderLayout.SOUTH);
		
		expl = SwingUtils.newTextPane("text/plain", "explanation shows here.", false);
		add(new JScrollPane(expl.jTextPane()), BorderLayout.CENTER);
		addFocusListener(SwingUtils.defaultFocusRequester(tip));
	}

}
