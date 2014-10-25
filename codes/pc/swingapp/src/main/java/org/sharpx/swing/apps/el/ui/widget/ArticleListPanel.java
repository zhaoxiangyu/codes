package org.sharpx.swing.apps.el.ui.widget;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.sharpx.utils.DsUtils;
import org.sharpx.swing.apps.el.beans.Article;
import org.sharpx.swing.apps.el.intf.ArticleOpener;
import org.sharpx.swing.apps.el.utils.ArticleUtils;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.JListItemDblClickListener;
import org.sharpx.swing.utils.SwingUtils.JTableUtilsX.ColumnsSupportX;
import org.sharpx.swing.utils.SwingUtils.JTableUtilsX.JTableHandler;

public class ArticleListPanel extends JPanel {
	
	private JTableHandler<Article> tableHandler;
	private List<Article> articles;
	
	public static interface SelectionChangeListener {
		void selectionChange(Article article);
	}

	public ArticleListPanel(final List<Article> data, ColumnsSupportX<Article> cs,
			final ArticleOpener ao, final SelectionChangeListener scl){
		articles = data;
		setLayout(new BorderLayout());
		/*add(contorlPanel(),BorderLayout.NORTH);*/
		tableHandler = SwingUtils.JTableUtilsX.newJTable(
				ArticleUtils.fullView(),new JListItemDblClickListener(){
					public void doubleClicked(Integer index) {
						Article arti = articles.get(index);
						ao.open(arti);
					}
				},null, new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						int fi = e.getFirstIndex();
						Article a = DsUtils.elementAt(articles, fi, null);
						scl.selectionChange(a);
					}					
				});
		tableHandler.set(articles, cs, null);
		add(tableHandler.jScrollPane(),BorderLayout.CENTER);
	}
	
	/*private JPanel contorlPanel(){
		JPanel pane = SwingUtils.newPanel(null);
		return pane;
	}*/
	
	public void freshData(final List<Article> data,ColumnsSupportX<Article> cs){
		articles = data;
		tableHandler.set(data, cs, null);
	}
	
	public List<Article> selectedArticles(){
		int[] rows = tableHandler.jTable().getSelectedRows();
		return DsUtils.subList(tableHandler.data(),rows);
	}
	
}