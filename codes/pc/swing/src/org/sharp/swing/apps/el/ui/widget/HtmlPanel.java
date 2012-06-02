package org.sharp.swing.apps.el.ui.widget;

import javax.swing.JPanel;

import org.sharp.swing.utils.SwingUtils;
import org.sharp.swing.utils.SwingUtils.EditorPaneHandler;

public class HtmlPanel extends JPanel {
	
	public HtmlPanel(String type,String text){
		EditorPaneHandler eph = SwingUtils.newEditorPane();
		eph.editorPane().setContentType(type);
		eph.editorPane().setText(text);
		add(eph.scrollPane());
	}
}
