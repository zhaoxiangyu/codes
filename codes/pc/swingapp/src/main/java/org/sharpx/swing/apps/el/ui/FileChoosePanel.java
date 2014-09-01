package org.sharpx.swing.apps.el.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.sharpx.swing.intf.FileHandler;
import org.sharpx.swing.intf.Pluggable.TabbedUI;
import org.sharpx.utils.DsUtils;
import org.sharpx.swing.apps.el.beans.Article;
import org.sharpx.swing.apps.el.intf.ArticleOpener;
import org.sharpx.swing.apps.el.ui.widget.ArticleListPanel;
import org.sharpx.swing.apps.el.ui.widget.ArticleListPanel.SelectionChangeListener;
import org.sharpx.swing.base.BasePanel;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.JTableUtilsX.ColumnsSupportX;

public class FileChoosePanel extends BasePanel {
	
	private JFileChooser fc = new JFileChooser();;
	private JButton btnChooseFile = new JButton("Choose File");
	private JLabel fileLocation = new JLabel();
	private ArticleListPanel importedArticles;
	private ArticleListPanel articlesToPreview;
	protected List<Article> sal;
	protected Article sa;
	private JButton importB;
	private JPanel cPanel;
	private JButton doB;
	
	public static interface ArticlesLoader {
		void reloadImported();
		void reloadPreviewd();
	}
	
	public FileChoosePanel(final Component parent, final FileHandler fh, final String currentDir, 
			final List<Article> ial, final List<Article> pal, 
			ColumnsSupportX<Article> cs,
			final ArticleOpener iao,final ArticleOpener pao,
			final ArticlesLoader alp){
		setLayout(new BorderLayout());
		btnChooseFile.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				showFcDialog(parent,fh, currentDir);
			}
		});
		cPanel = new JPanel();
		cPanel.add(btnChooseFile);
		cPanel.add(fileLocation);
		cPanel.add(SwingUtils.newButton("Refresh", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alp.reloadImported();
				alp.reloadPreviewd();
			}
		}));
		importB = SwingUtils.newButton("Import", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Article> sal = articlesToPreview.selectedArticles();
				for (Article ar : sal) {
					fh.setInput(new File(ar.getFilePath()));
				}
			}
		});
		doB = SwingUtils.newButton("DeskOpen", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtils.deskOpen(sa.getFilePath());
			}
		});
		add(cPanel,BorderLayout.NORTH);
		
		SelectionChangeListener sl = new SelectionChangeListener() {
			public void selectionChange(Article fa) {
				setSelectedArticles(fa,importedArticles);
			}
		};
		importedArticles = new ArticleListPanel(ial,cs,iao, sl);
		TabbedUI alpT = SwingUtils.toTabbedUI(importedArticles, "imported", "imported articles");

		sl = new SelectionChangeListener() {
			public void selectionChange(Article fa) {
				setSelectedArticles(fa,articlesToPreview);
			}
		};
		articlesToPreview = new ArticleListPanel(pal,cs,pao, sl);
		TabbedUI alpT2 = SwingUtils.toTabbedUI(articlesToPreview, 
				"preview", "articles to preview");

		JTabbedPane alpTabPane = SwingUtils.newTabbedPane(
				new TabbedUI[]{alpT,alpT2}, 
				null, 0, null);
		add(alpTabPane,BorderLayout.CENTER);
	}
	
	public void freshImportedArticles(final List<Article> data,ColumnsSupportX<Article> cs){
		importedArticles.freshData(data, cs);
	}
	
	public void freshPreviewedArticles(final List<Article> data,ColumnsSupportX<Article> cs){
		articlesToPreview.freshData(data, cs);
	}
	
	private void showFcDialog(final Component parent,
			final FileHandler fh, final String currentDir){
		try {
			if(parent == null)
				throw new Exception("Parent Component is null.");
			if(currentDir!=null)
				fc.setCurrentDirectory(new File(currentDir));
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fc.setMultiSelectionEnabled(true);
			int returnVal = fc.showOpenDialog(parent);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	File[] selectedFiles = fc.getSelectedFiles();
	        	if(fh != null && selectedFiles != null){
	        		for (File file : selectedFiles) {
		        		fh.setInput(file);
			        	if(file != null){
				        	fileLocation.setText(file.getCanonicalPath());
			        	}
					}
	        	}
	        } else {
	        }

		}catch(Exception e2){
			e2.printStackTrace();
		}
	}
	
	private void setSelectedArticles(Article fa,ArticleListPanel alp){
		sa = fa;
		SwingUtils.setVisible(doB,cPanel,!DsUtils.isNull(sa));
		
		sal = alp.selectedArticles();
		DsUtils.add(sal, sa, false);
		SwingUtils.setVisible(importB,cPanel,!DsUtils.isEmpty(sal));
	}
}