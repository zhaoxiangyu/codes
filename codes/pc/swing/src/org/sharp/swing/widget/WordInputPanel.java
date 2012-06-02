package org.sharp.swing.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sharp.swing.utils.SwingUtils;
import org.sharp.swing.utils.SwingUtils.NewInputHandler;

public class WordInputPanel extends JPanel {

	JLabel title;
	private JTextField keywordBox;
	protected String newInput;
	
	public WordInputPanel(String label,boolean trigEveryType,final NewInputHandler nih){
		//searchLabel = new JLabel("<html><font color=red>Keyword:</font></html>");
		//setLayout(new FlowLayout(FlowLayout.LEADING));
		title = new JLabel(label);
		add(title);
		keywordBox = SwingUtils.newTextField(null, 20, true, null);
		if(trigEveryType){
			SwingUtils.addKeyListener(keywordBox,new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					if(nih!=null){
						String input = SwingUtils.applyKeyEventOnText(e, 
								keywordBox.getText(), 
								keywordBox.getCaretPosition());
						nih.newInput(input);
					}
				}
			});
		}else{
			keywordBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					newInput = keywordBox.getText();
					if(nih!=null)
						nih.newInput(keywordBox.getText());
				}
			});
		}
		add(keywordBox);
		addFocusListener(SwingUtils.defaultFocusRequester(keywordBox));
	}
	
	public String newInput(){
		return newInput;
	}
}
