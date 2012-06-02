package org.sharp.swing.apps.el.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.sharp.intf.FileHandler;
import org.sharp.jdkex.Utils;
import org.sharp.swing.base.BasePanel;
import org.sharp.swing.utils.SwingUtils;

@SuppressWarnings("serial")
public class HtmlArticlePanel extends BasePanel {
	JTextPane jtextPane;
	JButton jBshowRaw;
	JButton jBsavAs;
	JButton jBsav;
	private HTMLEditorKit editorKit;
	private StyledDocument doc;
	JFileChooser jfc;
	private File fileInEdit;
	JButton jBctc;
	JLabel msgLabel;

	public HtmlArticlePanel() {
		setLayout(new BorderLayout());
		add(controlPanel(), BorderLayout.NORTH);
		add(/*fileText*/new JScrollPane(editorPane()), BorderLayout.CENTER);
		add(statusPanel(), BorderLayout.SOUTH);
		
	}
	
	private Component statusPanel() {
		JPanel panel = SwingUtils.newPanel(null);
		panel.add(SwingUtils.newJLabel(null, "Caret Pos:", null, null));
		msgLabel = SwingUtils.newJLabel(null, "", null, null);
		panel.add(msgLabel);
		return panel;
	}

	private JEditorPane editorPane(){
		String text = "<html><font color=red>File's text showed here.</font><br><p>new paragraph</p></html>";
		//text = OxfordUrlTerm.fromDisk("taxonomy", "E:\\xp\\codes\\projects\\console\\run\\terms2\\taxonomy").toHtml();
		jtextPane = SwingUtils.newTextPane("text/html"/*"text/plain"*/,
				text, 
				true).jTextPane();
		editorKit = (HTMLEditorKit)jtextPane.getEditorKit();
		doc = jtextPane.getStyledDocument();
		jtextPane.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
			    //Get the location in the text
			    int dot = e.getDot();
			    int mark = e.getMark();
			    Element ce = doc.getCharacterElement(dot);
			    if (dot == mark) {// no selection
			        msgLabel.setText("text position: " + dot +
					        " at element:"+SwingUtils.pathStr(ce));
			    } else if (dot < mark) {
			    	msgLabel.setText("selection from: " + dot + " to " + mark);
			    } else {
			    	msgLabel.setText("selection from: " + mark + " to " + dot);
			    }
			}
		});
		/*Action[] actions = jtextPane.getActions();
		for (Action action : actions) {
			JdkUtils.log.info("NAME:"+action.getValue(Action.NAME)+
					";ACTION_COMMAND_KEY:"+action.getValue(Action.ACTION_COMMAND_KEY)+
					";class:"+action.getClass().getName());
		}
		JdkUtils.log.info("HTMLEditorKit.COLOR_ACTION:"+HTMLEditorKit.COLOR_ACTION);*/
		return jtextPane;
	}

	public void showText(String text) {
		jtextPane.setText(text);
	}
	
	private void showRaw() {
		StringWriter sw = new StringWriter();
		try {
			editorKit.write(sw, doc, 0, doc.getLength());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Utils.log.info("Html text:"+sw);
	}
	
	private void saveHtml(File file){
		try {
			editorKit.write(new FileOutputStream(file), doc, 0, doc.getLength());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		fileInEdit = file;
		jBsav.setEnabled(true);
	}
	
	private JPanel controlPanel() {
		JPanel pane = SwingUtils.newPanel(null);
		
		jBshowRaw = new JButton("Show html text");
		jBshowRaw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		pane.add(jBshowRaw);
		
		jBsav = new JButton("save");
		jBsav.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
						saveHtml(fileInEdit);
			}
		});
		jBsav.setEnabled(false);
		pane.add(jBsav);

		jBsavAs = new JButton("save as");
		jBsavAs.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jfc = SwingUtils.showFileChooseDialog(jfc, false, new FileHandler() {
					public void setInput(File file) {
						saveHtml(file);
					}
				}, HtmlArticlePanel.this, null, null, null, null);
			}
		});
		pane.add(jBsavAs);

		jBctc = new JButton("test");
		jBctc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new StyledEditorKit.ForegroundAction(HTMLEditorKit.COLOR_ACTION,Color.blue);
				Element car = editorKit.getCharacterAttributeRun();
				Utils.log.info("current element name:"+car.getName());
				Utils.log.info("current String:"+car);
			}
		});
		pane.add(jBctc);

		return pane;
	}

}