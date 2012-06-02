package org.sharp.swing.apps.el.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sharp.swing.apps.el.beans.Config;
import org.sharp.swing.base.BasePanel;
import org.sharp.swing.utils.BeanUiUtils;
import org.sharp.swing.utils.SwingUtils;

public class ConfigPanel extends BasePanel {
	
	public static interface AdminHandler {
		void clearLuceneIndex();
		void importWords();
		void indexNewWords();
		void removeOldWords();
		void lookupVoices();
		void renameTermsExt();
		void setNewwordsNote();
	}

	private JLabel status;
	
	public ConfigPanel(Config config, final AdminHandler admin){
		setLayout(new BorderLayout());
		
		JPanel operPanel = new JPanel();
		operPanel.add(SwingUtils.newButton("destroy index",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin.clearLuceneIndex();
				status.setText("destroy index finished.");
			}
		}));
		operPanel.add(SwingUtils.newButton("index words",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin.indexNewWords();
				status.setText("index words finished.");
			}
		}));
		operPanel.add(SwingUtils.newButton("import old words",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin.importWords();
				status.setText("import old words finished.");
			}
		}));
		operPanel.add(SwingUtils.newButton("remove old words",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin.removeOldWords();
				status.setText("remove old words finished.");
			}
		}));
		operPanel.add(SwingUtils.newButton("lookup voices",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin.lookupVoices();
				status.setText("lookup voices finished.");
			}
		}));
		operPanel.add(SwingUtils.newButton("term .jsn to .xml",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin.renameTermsExt();
				status.setText("rename from .jsn to .xml finished.");
			}
		}));
		operPanel.add(SwingUtils.newButton("set newword notes",new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				admin.setNewwordsNote();
				status.setText("set newword notes finished.");
			}
		}));
		add(operPanel, BorderLayout.NORTH);
		
		try {
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			//p.add(new JLabel("settings in configuration file"),BorderLayout.NORTH);
			p.add(BeanUiUtils.beanUi(config)/*.tableUi(config)*/,BorderLayout.CENTER);
			add(p,BorderLayout.CENTER);
			status = new JLabel();
			add(status,BorderLayout.SOUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}