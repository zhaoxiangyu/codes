package org.sharp.swing.apps.tools.ui;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sharp.swing.base.BasePanel;
import org.sharp.swing.utils.SwingUtils;
import org.sharp.swing.utils.SwingUtils.JTableUtils;
import org.sharp.swing.utils.SwingUtils.NewInputHandler;
import org.sharp.swing.widget.WordInputPanel;

public class FileRenamerPanel extends BasePanel {
	JTable hitsFiles;

	private WordInputPanel keyWordPanel;
	
	public FileRenamerPanel() {
		setLayout(new BorderLayout());
		
		keyWordPanel = new WordInputPanel("keyword:",false,new NewInputHandler() {
			public void newInput(String text) {
				;
			}
		});
		add(keyWordPanel,BorderLayout.NORTH);
		
		ListSelectionListener lsl = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int row = hitsFiles.getSelectedRow();/*e.getFirstIndex();*/
				//TODO
			}
		};
		hitsFiles = JTableUtils.newJTable(
				ListSelectionModel.SINGLE_SELECTION, lsl, 
				JTable.AUTO_RESIZE_ALL_COLUMNS);
		add(new JScrollPane(hitsFiles),BorderLayout.CENTER);
		
		//add(new JScrollPane(matchedFragments),BorderLayout.SOUTH);
		
		addFocusListener(SwingUtils.defaultFocusRequester(keyWordPanel));
	}
	
}
