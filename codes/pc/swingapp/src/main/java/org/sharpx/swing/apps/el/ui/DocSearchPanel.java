package org.sharpx.swing.apps.el.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.sharpx.swing.intf.FileHandler;
import org.sharpx.utils.DsUtils;
import org.sharpx.swing.apps.el.intf.LuceneSearch;
import org.sharpx.swing.base.BasePanel;
import org.sharpx.swing.utils.SwingUtils;
import org.sharpx.swing.utils.SwingUtils.JTableUtils;
import org.sharpx.swing.utils.SwingUtils.NewInputHandler;
import org.sharpx.swing.widget.WordInputPanel;
import org.sharpx.utils.Lucene;
import org.sharpx.utils.Lucene.SearchResult;
import org.sharpx.utils.Lucene.TextHighlighter;

@SuppressWarnings("serial")
public class DocSearchPanel extends BasePanel {
	JTable hitsDocs;
	JList<String> matchedFragments;
	FileHandler fileHandler;
	private WordInputPanel keyWordPanel;
	
	public DocSearchPanel(final LuceneSearch se,final FileHandler f) {
		fileHandler = f;
		setLayout(new BorderLayout());
		
		keyWordPanel = new WordInputPanel("keyword:",false,new NewInputHandler() {
			public void newInput(String text) {
				se.searchKeyword(text);			}
		});
		add(keyWordPanel,BorderLayout.NORTH);
		
		ListSelectionListener lsl = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int row = hitsDocs.getSelectedRow();/*e.getFirstIndex();*/
				searchResultItemSelected(row);
			}
		};
		hitsDocs = JTableUtils.newJTable(
				ListSelectionModel.SINGLE_SELECTION, lsl, 
				JTable.AUTO_RESIZE_ALL_COLUMNS);
		add(new JScrollPane(hitsDocs),BorderLayout.CENTER);
		
		matchedFragments = new JList<String>();
		matchedFragments.setCellRenderer(new ListCellRenderer<String>() {
			public Component getListCellRendererComponent(
					JList<? extends String> list, String value, int index,
					boolean isSelected, boolean cellHasFocus) {
				/*System.out.println("rendering "+value);*/
				JLabel label = new JLabel();
				label.setText("<html>"+value+"</html>");
				return label;
			}
		});
		add(new JScrollPane(matchedFragments),BorderLayout.SOUTH);
		
		addFocusListener(SwingUtils.defaultFocusRequester(keyWordPanel));
	}
	
	private void searchResultItemSelected(int row){
		if(row >=0 && row < hitsDocs.getRowCount()){
			String id = (String)hitsDocs.getValueAt(row, 1);
			Pair<String, String> p = Lucene.parseDocId(id);
			fileHandler.setInput(new File(p.getRight()));
			String docContent = (String)hitsDocs.getValueAt(row, 0);
			//Utils.log.info("refreshing highlighted text.row:"+row);
			String[] res = TextHighlighter.highlight(keyWordPanel.newInput(), docContent);
			matchedFragments.setListData(res);
			/*searchLabel.setText("<html>"+res[0]+"</html>");*/
		}
	}
	
	public void fillSearchResult(SearchResult sr){
		TopDocs hits = sr.hits();
		ScoreDoc[] scoreDocs = hits.scoreDocs;
		
		DefaultTableModel model = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
				return super.isCellEditable(row, column) && column!=0;
			}
		};
		model.setRowCount(scoreDocs.length);
		for(int i = 0; i< scoreDocs.length; i++){
			Document doc = sr.doc(scoreDocs[i].doc);
			for(Fieldable f : doc.getFields()){
				int ci = model.findColumn(f.name());
				if(-1==ci){
					model.addColumn(f.name());
					ci = model.findColumn(f.name());
				}
				//JdkUtils.log.info("model.setValueAt("+f.stringValue()+","+ i +","+ ci+")");
				model.setValueAt(f.stringValue(), i, ci);
			}
		}
		hitsDocs.setModel(model);
		/*if(scoreDocs.length > 0)
			hitsDocs.setRowSelectionInterval(0, 0);*/
	}
}