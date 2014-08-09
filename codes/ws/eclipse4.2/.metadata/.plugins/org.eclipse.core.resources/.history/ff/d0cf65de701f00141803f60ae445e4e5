package org.sharpx.swing.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerNumberModel;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.sharpx.swing.intf.FileHandler;
import org.sharpx.swing.intf.ItemSelected;
import org.sharpx.swing.intf.Pluggable.TabbedUI;
import org.sharpx.utils.jdkex.LangUtils;
import org.sharpx.utils.jdkex.Utils;

public class SwingUtils {

	public static class JTableUtilsX {

		public static interface ColumnsSupportX<T> {
			Object[] columnValues(T o);
			String[] columnNames();
			Integer[] columnWidths();
			int defaultSortColumn();
			boolean defaultAsc();
			int NO_SORT = -1;
		}

		public static interface JTableModelX<T> {
			void set(List<T> data,ColumnsSupportX<T> cs);
			
			Object getValueAt(int rowIndex, int columnIndex);

			int getRowCount();

			TableColumn[] getColumns();

			TableCellRenderer headerRender = new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					if (table != null) {
						JTableHeader header = table.getTableHeader();
						if (header != null) {
							setForeground(header.getForeground());
							setBackground(header.getBackground());
							setFont(header.getFont());
						}
					}
					setText((value == null) ? "" : value.toString());
					setBorder(UIManager.getBorder("TableHeader.cellBorder"));
					setHorizontalAlignment(JLabel.CENTER);
					return this;
				}
			};

		}

		public static interface JTableSortingSupport<T> {
			void set(List<T> data,ColumnsSupportX<T> cs);

			boolean reorderingAllowed();

			int sortCol();

			void setSortCol(int colIndex);

			boolean reverseOrder();

			void sortData();

			Icon getColumnIcon(int index);

		}

		private static AbstractTableModel newAbstractTableModel(
				final JTableModelX tm) {
			return new AbstractTableModel() {
				public Object getValueAt(int rowIndex, int columnIndex) {
					return tm.getValueAt(rowIndex, columnIndex);
				}

				public int getRowCount() {
					return tm.getRowCount();
				}

				public int getColumnCount() {
					return tm.getColumns().length;
				}
			};
		}

		private static <T> JTableModelX<T> newJTableModelX(final List<T> data,
				final ColumnsSupportX<T> cs) {
			return new JTableModelX<T>() {
				TableColumn[] cols;
				List<T> data_ = data;
				ColumnsSupportX<T> cs_ = cs;
				
				public void set(List<T> dat,ColumnsSupportX<T> c){
					data_ = dat;
					cs_ = c;
				}

				public Object getValueAt(int rowIndex, int columnIndex) {
					T t = data_.get(rowIndex);
					if(t==null)
						return null;
					else
						return cs_.columnValues(t)[columnIndex];
				}

				public int getRowCount() {
					if(data_ == null)
						return 0;
					return data_.size();
				}

				public TableColumn[] getColumns() {
					if (cols == null && cs_ != null) {
						List<TableColumn> colsl = new ArrayList<TableColumn>();
						for (int i = 0; i < cs_.columnNames().length; i++) {
							TableColumn tc = new TableColumn(i,
									cs_.columnWidths()[i],
									new DefaultTableCellRenderer(), null);
							tc.setHeaderValue(cs_.columnNames()[i]);
							tc.sizeWidthToFit();
							colsl.add(tc);
						}
						cols = colsl.toArray(new TableColumn[0]);
					}else if(cs_ == null){
						cols = new TableColumn[0];
					}
						
					return cols;
				}

			};
		}

		private static <T> JTableSortingSupport<T> newJTableSortingSupport(
				final List<T> data, final ColumnsSupportX<T> cs) {
			return new JTableSortingSupport<T>() {
				int sortCol = cs.defaultSortColumn();
				boolean sortAsc = cs.defaultAsc();
				boolean reorderingAllowed = true;
				
				List<T> data_ = data;
				ColumnsSupportX<T> cs_ = cs;
				
				public void set(List<T> dat,ColumnsSupportX<T> c){
					data_ = dat;
					cs_ = c;
					sortCol = c.defaultSortColumn();
					sortAsc = c.defaultAsc();
				}

				public void sortData() {
					if(sortCol == ColumnsSupportX.NO_SORT)
						return;
					
					Collections.sort(data_, new Comparator<T>() {

						public int compare(T t1, T t2) {
							return LangUtils.newComparator(sortAsc).compare(
									cs_.columnValues(t1)[sortCol], 
									cs_.columnValues(t2)[sortCol]);
						}
					});
				}

				public int sortCol() {
					return sortCol;
				}

				public void setSortCol(int colIndex) {
					sortCol = colIndex;
				}

				public boolean reverseOrder() {
					sortAsc = !sortAsc;
					return sortAsc;
				}

				public boolean reorderingAllowed() {
					return reorderingAllowed;
				}

				public Icon getColumnIcon(int index) {
					return null;/* cs.columnIcon(index, sortAsc); */
				}
			};
		}

		private static <T> MouseAdapter newJTableModelXMouseListener(
				final JTable table, final JTableSortingSupport<T> ss,
				final JTableModelX<T> tmx) {
			return new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					TableColumnModel colModel = table.getColumnModel();
					int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
					int modelIndex = colModel.getColumn(columnModelIndex)
							.getModelIndex();
					if (modelIndex < 0)
						return;
					if (ss.sortCol() == modelIndex)
						ss.reverseOrder();
					else
						ss.setSortCol(modelIndex);
					for (int i = 0; i < colModel.getColumnCount()/*
																 * mx.getColumns(
																 * ).length
																 */; i++) {
						TableColumn column = colModel.getColumn(i);
						int index = column.getModelIndex();
						JLabel renderer = (JLabel) column.getHeaderRenderer();
						renderer.setIcon(ss.getColumnIcon(index));
					}
					table.getTableHeader().repaint();
					ss.sortData();
					table.tableChanged(new TableModelEvent(
							newAbstractTableModel(tmx)));
					table.repaint();
				}
			};

		}
		
		/*private static <T> void setData(JTable table,List<T> data,ColumnsSupportX<T> cs){
			JTableModelX tmx = newJTableModelX(data, cs);
			JTableSortingSupport ss = newJTableSortingSupport(data, cs);
			
			table.setModel(newAbstractTableModel(tmx));
			JTableHeader header = table.getTableHeader();
			header.setReorderingAllowed(ss.reorderingAllowed());
			if (ss.reorderingAllowed()) {
				for(MouseListener ml:header.getMouseListeners())
					header.removeMouseListener(ml);
				
				header.addMouseListener(newJTableModelXMouseListener(table, ss,
						tmx));
			}
		}*/
		
		public static interface JTableHandler<T>{
			JTable jTable();
			JScrollPane jScrollPane();
			void set(List<T> data,ColumnsSupportX<T> cs,TransferHandler th);
			void set(Boolean dragEnabled, DropMode dm, TransferHandler th);
			void sort();
			List<T> data();
		}

		public static <T> JTableHandler<T> newJTable(ColumnsSupportX<T> cs,
				final JListItemDblClickListener jlidcl, final JListItemClickListener jlicl,
				ListSelectionListener sl) {
			return newJTable(new JScrollPane(),null,
					cs,null,
					null,sl,
					null,null,null,
					jlidcl,jlicl);
		}
		
		public static <T> JTableHandler<T> newJTable(ColumnsSupportX<T> cs,
				Integer selectionMode,ListSelectionListener sl) {
			return newJTable(new JScrollPane(),null,
					cs,null,
					selectionMode,sl,
					null,null,null,null,null);
		}
		
		public static <T> JTableHandler<T> newJTable(final JScrollPane jsp, List<T> data,
				ColumnsSupportX<T> cs, Integer visibleRows,
				Integer selectionMode,ListSelectionListener sl,
				Boolean dragEnabled, DropMode dm, TransferHandler th,
				final JListItemDblClickListener jlidcl, final JListItemClickListener jlicl) {
			
			final JTableModelX<T> newJTableModelX = newJTableModelX(data, cs);
			final JTableSortingSupport<T> newJTableSortingSupport = newJTableSortingSupport(data, cs);
			final JTable table = newJTable(newJTableModelX,
					newJTableSortingSupport);
			if (selectionMode != null)
				table.setSelectionMode(selectionMode);
			if (sl != null)
				table.getSelectionModel().addListSelectionListener(sl);
			if (dragEnabled != null)
				table.setDragEnabled(dragEnabled);
			if(dm!=null)
				table.setDropMode(dm);
			if(th!=null)
				table.setTransferHandler(th);
			if(jsp!=null)
				jsp.getViewport().add(table);
			table.setFillsViewportHeight(true);
			table.addMouseListener(newMouseAdaptor(table,jlidcl,jlicl));
			/*table.setFocusable(false);*/
			if (visibleRows != null && jsp !=null){
				Dimension preferredSize = new Dimension(table.getColumnCount()*40, visibleRows
						* table.getRowHeight()
						+ table.getTableHeader().getHeight());
				System.out.println("preferredSize:"+preferredSize);
				jsp.setPreferredSize(preferredSize);
			}
			/*
			 * table.setAutoResizeMode(resizemode);
			 * 
			 */
			return new JTableHandler<T>(){
				List<T> dataL;
				public JTable jTable() {
					return table;
				}
				
				public List<T> data(){
					return dataL;
				}
				
				public void set(List<T> data, ColumnsSupportX<T> cs,TransferHandler th) {
					dataL = data;
					newJTableModelX.set(data, cs);
					newJTableSortingSupport.set(data, cs);
					/*newJTableSortingSupport.sortData();*/
					jTable().setTransferHandler(th);
					sort();
					table.tableChanged(null);
					table.repaint();
				}
				public JScrollPane jScrollPane() {
					return jsp;
				}
				
				public void set(Boolean dragEnabled, DropMode dm,
						TransferHandler th) {
					if(dragEnabled!=null)
						jTable().setDragEnabled(dragEnabled);
					if(dm!=null)
						jTable().setDropMode(dm);
					if(th!=null)
						jTable().setTransferHandler(th);
				}
				
				public void sort() {
					newJTableSortingSupport.sortData();
					table.tableChanged(null);
					table.repaint();
				}
			};
		}

		private static <T> JTable newJTable(JTableModelX<T> model,
				JTableSortingSupport<T> ss) {
			JTable table = new JTable();
			table.setAutoCreateColumnsFromModel(false);
			table.setModel(newAbstractTableModel(model));
			for (int k = 0; k < model.getColumns().length; k++) {
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				/* renderer.setHorizontalAlignment(); */
				TableColumn column = model.getColumns()[k];
				column.setHeaderRenderer(model.headerRender);
				table.addColumn(column);
			}
			JTableHeader header = table.getTableHeader();
			/* header.setUpdateTableInRealTime(false); */
			header.setReorderingAllowed(ss.reorderingAllowed());
			header.setResizingAllowed(true);
			if (ss.reorderingAllowed()) {
				header.addMouseListener(newJTableModelXMouseListener(table, ss,
						model));
			}

			return table;
		}
	}
	
	public static class JTableUtils {

		public static interface ColumnsSupport<T> {
			int columnCount();

			Object column(T row, int column);

			String columnName(int column);

			int columnWidth(int column);

			/* Icon columnIcon(int column,boolean asc); */
			Comparator<T> comparator(int column, boolean asc);
		}

		public static interface JTableModelX {
			Object getValueAt(int rowIndex, int columnIndex);

			int getRowCount();

			TableColumn[] getColumns();

			TableCellRenderer headerRender = new DefaultTableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					if (table != null) {
						JTableHeader header = table.getTableHeader();
						if (header != null) {
							setForeground(header.getForeground());
							setBackground(header.getBackground());
							setFont(header.getFont());
						}
					}
					setText((value == null) ? "" : value.toString());
					setBorder(UIManager.getBorder("TableHeader.cellBorder"));
					setHorizontalAlignment(JLabel.CENTER);
					return this;
				}
			};

		}

		public static interface JTableSortingSupport {
			boolean reorderingAllowed();

			int sortCol();

			void setSortCol(int colIndex);

			boolean reverseOrder();

			void sortData();

			Icon getColumnIcon(int index);

		}

		private static AbstractTableModel newAbstractTableModel(
				final JTableModelX tm) {
			return new AbstractTableModel() {
				public Object getValueAt(int rowIndex, int columnIndex) {
					return tm.getValueAt(rowIndex, columnIndex);
				}

				public int getRowCount() {
					return tm.getRowCount();
				}

				public int getColumnCount() {
					return tm.getColumns().length;
				}
			};
		}

		private static <T> JTableModelX newJTableModelX(final List<T> data,
				final ColumnsSupport<T> cs) {
			return new JTableModelX() {
				TableColumn[] cols;

				public Object getValueAt(int rowIndex, int columnIndex) {
					return cs.column(data.get(rowIndex), columnIndex);
				}

				public int getRowCount() {
					return data.size();
				}

				public TableColumn[] getColumns() {
					if (cols == null) {
						List<TableColumn> colsl = new ArrayList<TableColumn>();
						for (int i = 0; i < cs.columnCount(); i++) {
							TableColumn tc = new TableColumn(i,
									cs.columnWidth(i),
									new DefaultTableCellRenderer(), null);
							tc.setHeaderValue(cs.columnName(i));
							tc.sizeWidthToFit();
							colsl.add(tc);
						}
						cols = colsl.toArray(new TableColumn[0]);
					}
					return cols;
				}

			};
		}

		private static <T> JTableSortingSupport newJTableSortingSupport(
				final List<T> data, final ColumnsSupport<T> cs) {
			return new JTableSortingSupport() {
				int sortCol = 0;
				boolean sortAsc = true;
				boolean reorderingAllowed = true;
				TableColumn[] cols;

				public void sortData() {
					Collections.sort(data, cs.comparator(sortCol, sortAsc));
				}

				public int sortCol() {
					return sortCol;
				}

				public void setSortCol(int colIndex) {
					sortCol = colIndex;
				}

				public boolean reverseOrder() {
					sortAsc = !sortAsc;
					return sortAsc;
				}

				public boolean reorderingAllowed() {
					return reorderingAllowed;
				}

				public Icon getColumnIcon(int index) {
					return null;/* cs.columnIcon(index, sortAsc); */
				}
			};
		}

		private static MouseAdapter newJTableModelXMouseListener(
				final JTable table, final JTableSortingSupport ss,
				final JTableModelX tmx) {
			return new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					TableColumnModel colModel = table.getColumnModel();
					int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
					int modelIndex = colModel.getColumn(columnModelIndex)
							.getModelIndex();
					if (modelIndex < 0)
						return;
					if (ss.sortCol() == modelIndex)
						ss.reverseOrder();
					else
						ss.setSortCol(modelIndex);
					for (int i = 0; i < colModel.getColumnCount()/*
																 * mx.getColumns(
																 * ).length
																 */; i++) {
						TableColumn column = colModel.getColumn(i);
						int index = column.getModelIndex();
						JLabel renderer = (JLabel) column.getHeaderRenderer();
						renderer.setIcon(ss.getColumnIcon(index));
					}
					table.getTableHeader().repaint();
					ss.sortData();
					table.tableChanged(new TableModelEvent(
							newAbstractTableModel(tmx)));
					table.repaint();
				}
			};

		}

		public static <T> JTable addJTable(JScrollPane jsp, List<T> data,
				ColumnsSupport<T> cs, Integer visibleRows,
				Integer selectionMode,ListSelectionListener sl,
				Boolean dragEnabled) {
			JTable table = newJTable(newJTableModelX(data, cs),
					newJTableSortingSupport(data, cs));
			if (selectionMode != null)
				table.setSelectionMode(selectionMode);
			if (sl != null)
				table.getSelectionModel().addListSelectionListener(sl);
			if (dragEnabled != null)
				table.setDragEnabled(dragEnabled);
			/*table.setdr*/
			jsp.getViewport().add(table);
			table.setFillsViewportHeight(true);
			if (visibleRows != null)
				jsp.setPreferredSize(new Dimension(150, visibleRows
						* table.getRowHeight()
						+ table.getTableHeader().getHeight()));
			/*
			 * table.setAutoResizeMode(resizemode);
			 * 
			 */
			return table;
		}

		private static JTable newJTable(JTableModelX model,
				JTableSortingSupport ss) {
			JTable table = new JTable();
			table.setAutoCreateColumnsFromModel(false);
			table.setModel(newAbstractTableModel(model));
			for (int k = 0; k < model.getColumns().length; k++) {
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				/* renderer.setHorizontalAlignment(); */
				TableColumn column = model.getColumns()[k];
				column.setHeaderRenderer(model.headerRender);
				table.addColumn(column);
			}
			JTableHeader header = table.getTableHeader();
			/* header.setUpdateTableInRealTime(false); */
			header.setReorderingAllowed(ss.reorderingAllowed());
			header.setResizingAllowed(true);
			if (ss.reorderingAllowed()) {
				header.addMouseListener(newJTableModelXMouseListener(table, ss,
						model));
			}

			return table;
		}

		public static JTable newJTable(int selectionModel,
				ListSelectionListener sl, int resizemode) {
			JTable table = new JTable();
			table.setSelectionMode(selectionModel);
			table.getSelectionModel().addListSelectionListener(sl);
			table.setAutoResizeMode(resizemode);
			return table;
		}
	}

	public static void scrollTo(JScrollPane jsp, Integer lineNo, Double percent) {
		JScrollBar vsb = jsp.getVerticalScrollBar();
		int srows = vsb.getMaximum() / vsb.getUnitIncrement(1);
		int srowspp = vsb.getBlockIncrement(1) / vsb.getUnitIncrement(1);

		int line = 0;
		if (lineNo != null)
			line = lineNo;
		else if (percent != null)
			line = (int) (srows * percent);
		else
			throw new RuntimeException(
					"both parameter lineNo and percent null.");

		if (line >= srows - srowspp)
			line = srows - srowspp;

		/*
		 * System.out.println("scroll bar:"+vsb.getMinimum()+"/"+vsb.getMaximum()
		 * + ":"+vsb.getVisibleAmount()+":"+vsb.getUnitIncrement(1)+"/"+vsb.
		 * getBlockIncrement(1));
		 * System.out.println("scroll bar:"+slineNo+"/"+srows
		 * +",rows per page:"+srowspp);
		 * System.out.println("vsb:"+vsb.getValue()+"/"+vsb.getMaximum());
		 */
		int newValue = line * vsb.getUnitIncrement(1);
		vsb.setValue(newValue);
		System.out.println("new value:" + newValue);
		vsb.updateUI();
	}

	private static void scrollTo(JScrollPane jsp, Integer value,
			int lineNumBefore) {
		JScrollBar vsb = jsp.getVerticalScrollBar();
		if (value == null)
			throw new RuntimeException("parameter value is null.");
		if (lineNumBefore >= 0)
			value = value - lineNumBefore * vsb.getUnitIncrement(1);
		if (value < 0)
			value = 0;
		vsb.setValue(value);
		/* System.out.println("new value:"+value); */
		vsb.updateUI();
	}

	public static void scrollTo(JScrollPane jsp, JTextArea jt, int pos) {
		System.out.println("pos:" + pos);
		try {
			Rectangle rect = jt.modelToView(pos);
			scrollTo(jsp, rect.y, 3);
			System.out.println("rect:" + rect);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

	public static <T> JList<T> newJList(ListSelectionListener listener,
			Integer visibleRowCount, Integer width) {
		return newJList(listener,null,visibleRowCount, width, null,null,null,
				null, null,
				true,null,null);
	}

	public static <T> JList<T> newJList(ListSelectionListener listener) {
		return newJList(listener,null,null, null, null,null,null,
				null, null,
				false,null,null);
	}

	public static <T> JList<T> newJList4Tag(T[] data,ListSelectionListener listener,
			JListItemDblClickListener dcl,JListItemClickListener cl,
			TransferHandler th) {
		JList<T> jl = newJList(listener, data,
				null, null, /*JList.VERTICAL_WRAP,*/JList.HORIZONTAL_WRAP,
				/*null,null,*/80,80,dcl,cl,
				true, DropMode.ON, th);
		return jl;
	}
	
	public interface JListItemDblClickListener {
		void doubleClicked(Integer index);
	}
	
	public interface JListItemClickListener {
		void clicked(Integer index);
	}
	
	public interface DnDSupport<T,S>{
		void droppedOn(T t,List<S> s);
	}

	public static <T,S> TransferHandler newJTableTranserHandlerDrag(final List<S> s,
			final Class<S> sc){
		return new TransferHandler(){
		    public int getSourceActions(JComponent c) {
		        return TransferHandler.COPY;
		    }
		    
		    protected Transferable createTransferable(JComponent c) {
		    	JTable table = (JTable)c;
		    	final ArrayList<S> al = new ArrayList<S>();
		    	int[] rows = table.getSelectedRows();
		    	if(rows==null)
		    		return null;
		    	for(int row: rows){
			        S value = s.get(row);
			        al.add(value);
		    	}
		        return new Transferable() {
					public boolean isDataFlavorSupported(DataFlavor flavor) {
						if(flavor.equals(new DataFlavor(sc,"")))
							return true;
						return false;
					}
					
					public DataFlavor[] getTransferDataFlavors() {
						return new DataFlavor[]{new DataFlavor(sc,"")};
					}
					
					public Object getTransferData(DataFlavor flavor)
							throws UnsupportedFlavorException, IOException {
						return al;
					}
				};
		    }
		    
		    protected void exportDone(JComponent c, Transferable data, int action) {
		    	JTable source = (JTable)c;
		        if (action == TransferHandler.MOVE) {
		        }
		        source.tableChanged(null);
		    }
		};
	};
	
	public static <T,S> TransferHandler newJTableTranserHandlerDrop(final List<T> t,
			final Class<S> sc,final DnDSupport<T,S> dndS){
		return new TransferHandler(){
		    public boolean canImport(TransferHandler.TransferSupport info) {
		        if (!info.isDataFlavorSupported(new DataFlavor(sc,"sc can be imported."))) {
		            return false;
		        }
		        return true;
		    }
	
		    public boolean importData(TransferHandler.TransferSupport info) {
		        if (!info.isDrop()) {
		            return false;
		        }
	
		        JTable table = (JTable)info.getComponent();
		        JTable.DropLocation dl = (JTable.DropLocation)info.getDropLocation();
		        int row = dl.getRow();
		        T target = t.get(row);
	
		        boolean ir = dl.isInsertRow();
		        if(ir)
		        	return false;
	
		        Transferable t = info.getTransferable();
		        List<S> source;
		        try {
		            source = (List<S>)t.getTransferData(new DataFlavor(sc,""));
		        } catch (Exception e) { 
		        	return false; 
		        }
		        if(target!=null)
		        	dndS.droppedOn(target, source);
		                                
		        return true;
		    }
	
		    public int getSourceActions(JComponent c) {
		        return TransferHandler.COPY;
		    }
		    
		};
	};
	
	private static <T> JList<T> newJList(ListSelectionListener listener,T[] data,
			Integer visibleRowCount, Integer width, Integer layoutOri,
			Integer fixedCellWidth, Integer fixedCellHeight, 
			final JListItemDblClickListener jlidcl, final JListItemClickListener jlicl,
			Boolean dragEnabled, DropMode dm, TransferHandler th) {
		final JList<T> jl = new JList<T>();
		if(data!=null)
			jl.setListData(data);
		if (listener != null)
			jl.addListSelectionListener(listener);
		if (visibleRowCount != null)
			jl.setVisibleRowCount(visibleRowCount);
		else
			jl.setVisibleRowCount(-1);
		if (width != null)
			jl.setMinimumSize(new Dimension(width, 200));
		if (layoutOri!=null){
			System.out.println("setLayoutOrientation("+layoutOri+")");
			jl.setLayoutOrientation(layoutOri);
			System.out.println("getLayoutOrientation return "+jl.getLayoutOrientation()+"");
		}
		if(dragEnabled!=null)
			jl.setDragEnabled(dragEnabled);
		if(dm!=null)
			jl.setDropMode(dm);
		if(th!=null)
			jl.setTransferHandler(th);
		jl.addMouseListener(newMouseAdaptor(jl,jlidcl,jlicl));
		if(fixedCellWidth!=null)
			jl.setFixedCellWidth(fixedCellWidth);
		if(fixedCellHeight!=null)
			jl.setFixedCellHeight(fixedCellHeight);
		showSize(jl);
		return jl;
	}
	
	private static<T> MouseAdapter newMouseAdaptor(final JList<T> jl,
			final JListItemDblClickListener jlidcl, 
			final JListItemClickListener jlicl){
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
		    	  int cellIndex = jl.locationToIndex(e.getPoint());
			      if (e.getClickCount() == 2) {
			    	  if(jlidcl!=null)
			    		  jlidcl.doubleClicked(cellIndex);
			      }else if(e.getClickCount() == 1){
			    	  if(jlicl != null)
			    		  jlicl.clicked(cellIndex);
			    	  System.out.println("list clicked once.");
			      }
			    }
			};
	}
	
	private static<T> MouseAdapter newMouseAdaptor(final JTable jt,
			final JListItemDblClickListener jlidcl, 
			final JListItemClickListener jlicl){
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
		    	  int ri = jt.rowAtPoint(e.getPoint());
			      if (e.getClickCount() == 2) {
			    	  if(jlidcl!=null && ri >=0 && ri < jt.getRowCount()){
			    		  jlidcl.doubleClicked(ri);
			    	  }
			      }else if(e.getClickCount() == 1 && ri >=0 && ri < jt.getRowCount()){
			    	  if(jlicl != null)
			    		  jlicl.clicked(ri);
			    	  System.out.println("list clicked once.");
			      }
			    }
			};
	}
	
	public static interface NewIntegerHandler {
		void newInt(int num);
	}
	
	public static interface SpinnerHandler {
		JSpinner jSpinner();
		void setMax(int i);
	}
	
	public static SpinnerHandler newSpinner(Integer value,Integer max,final NewIntegerHandler nih){
		if(value < 1 || value > max)
			value = 1;
		final SpinnerNumberModel model = new SpinnerNumberModel(value,
				new Integer(1),max,new Integer(1));
		final JSpinner spinner = new JSpinner(model);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				nih.newInt(model.getNumber().intValue());
			}
		});
		return new SpinnerHandler(){
			public JSpinner jSpinner() {
				return spinner;
			}
			public void setMax(int max) {
				model.setMaximum(max);
				model.setValue(1);
			}
		};
	}
	
	public static Component setPreferredSize(Component c, int w,
			int h) {
		c.setPreferredSize(new Dimension(w,h));
		showSize(c);
		return c;
	}
	
	public static Component setMinSize(Component c, int w,
			int h) {
		c.setMinimumSize(new Dimension(w,h));
		showSize(c);
		return c;
	}
	
	public static Component showSize(Component c){
		String cName = c.getClass().getSimpleName();
		System.out.println(cName+".getMaximumSize():"+c.getMaximumSize());
		System.out.println(cName+".getPreferredSize():"+c.getPreferredSize());
		System.out.println(cName+".getMinimumSize():"+c.getMinimumSize());
		/*if(c instanceof JList)
			System.out.println(""+(JList)c);*/
		return c;
	}
	
	public static void setText(JTextComponent tc,String text){
		tc.setText(text);
		tc.setCaretPosition(0);
	}

	public static JFileChooser showFileChooseDialog(JFileChooser jfc,
			boolean openOrSave, FileHandler fh, Component parent,
			String currentDir, Integer mode, Boolean multi, String title) {

		JFileChooser fc = new JFileChooser();
		if (jfc != null)
			fc = jfc;

		if (currentDir != null)
			fc.setCurrentDirectory(new File(currentDir));
		if (mode != null)
			fc.setFileSelectionMode(mode);
		if (multi != null)
			fc.setMultiSelectionEnabled(multi);
		if (title != null)
			fc.setDialogTitle(title);

		int returnVal = 0;
		if (openOrSave)
			fc.showOpenDialog(parent);
		else
			fc.showSaveDialog(parent);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();
			if (fh != null)
				fh.setInput(selectedFile);
		}
		return fc;
	}

	public static <T> JComboBox<T> newComboBox(T[] labels, int selectedIndex,
			boolean editable, ActionListener al) {
		JComboBox<T> jcb = new JComboBox<T>(labels);
		if (labels != null && labels.length > selectedIndex)
			jcb.setSelectedIndex(selectedIndex);
		jcb.setEditable(editable);
		jcb.addActionListener(al);
		return jcb;
	}
	
	public static interface JRadioGroup {
		JPanel jPanel();
		void setSelected(String label);
	}

	public static JRadioGroup newRadioGroup(String[] labels,
			int is, final ItemSelected<String> eh) {
		final ButtonGroup group = new ButtonGroup();
		final JPanel panel = new JPanel();
		for (int i = 0; i < labels.length; i++) {
			final JRadioButton jb = new JRadioButton(labels[i]);
			/* jb.setMnemonic(KeyEvent.VK_B); */
			jb.setActionCommand(labels[i]);
			if (i == is)
				jb.setSelected(true);
			jb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eh.itemSelected(jb.getActionCommand());
				}
			});

			group.add(jb);
			panel.add(jb);
		}
		return new JRadioGroup() {
			public void setSelected(String label) {
				Enumeration<AbstractButton> eles = group.getElements();
				while(eles.hasMoreElements()){
					AbstractButton ab = eles.nextElement();
					if(LangUtils.equals(ab.getText(),label)){
						group.setSelected(ab.getModel(), true);
					}
				}
			}
			public JPanel jPanel() {
				return panel;
			}
		};
	}

	public static JCheckBox newCheckBox(String label, boolean selected,
			ItemListener il) {
		JCheckBox jcb = new JCheckBox(label);
		jcb.setSelected(selected);
		jcb.addItemListener(il);
		return jcb;
	}
	
	public static interface NewInputHandler {
		void newInput(String text);
	}

	public static JTextArea newTextArea(String text, boolean linewrap,
			boolean wrapSyleWord, boolean editable, final NewInputHandler kth) {
		return newTextArea(text, null, null, linewrap, wrapSyleWord, editable,kth);
	}

	public static JTextArea newTextArea(int rows, int columns,
			boolean linewrap, boolean wrapSyleWord) {
		return newTextArea("", rows, columns, linewrap, wrapSyleWord, false, null);
	}
	
	private static void beep(){
		Toolkit.getDefaultToolkit().beep();
	}
	
	public static String applyKeyEventOnText(KeyEvent e,String text,int caretPosition){
		char c = e.getKeyChar();
		Utils.log.debug("keychar:"+CharUtils.unicodeEscaped(c));
		StringBuffer newInput = new StringBuffer(text);
		if(c == '\u0008'){	//back space
			if(caretPosition-1 < 0)
				beep();
			else{
				newInput.deleteCharAt(caretPosition-1);
			}
		}else if(c =='\u007f'){ //DEL key
			//newInput.deleteCharAt(caretPosition);
		}else{
			newInput.insert(caretPosition, c);
		}
		Utils.log.debug("keychar:"+newInput.toString());
		return newInput.toString();
	}

	private static JTextArea newTextArea(String text, Integer rows,
			Integer columns, boolean linewrap, boolean wrapSyleWord,
			boolean editable, final NewInputHandler ktl) {
		final JTextArea jta = new JTextArea(text);
		if (rows != null)
			jta.setRows(rows);
		if(columns != null)
			jta.setColumns(columns);

		jta.setLineWrap(linewrap);
		jta.setWrapStyleWord(wrapSyleWord);
		jta.setEditable(editable);
		if(ktl!=null)
			jta.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
					ktl.newInput(applyKeyEventOnText(e,jta.getText(),jta.getCaretPosition()));
				}
				public void keyReleased(KeyEvent e) {
				}
				public void keyPressed(KeyEvent e) {
				}
			});
		return jta;
	}

	public static JEditorPane newEditorPane(String contentType, String text,
			boolean editable, boolean wrapSyleWord) {
		JEditorPane jep;
		jep = new JEditorPane(contentType, text);
		jep.setEditable(editable);
		return jep;
	}
	
	public static interface EditorPaneHandler {
		JEditorPane editorPane();
		JScrollPane scrollPane();
	}

	public static EditorPaneHandler newEditorPane() {
		final JEditorPane jep = new JEditorPane();
		final JScrollPane jsp = new JScrollPane(jep);
		return new EditorPaneHandler() {
			public JEditorPane editorPane(){
				return jep;
			}
			public JScrollPane scrollPane(){
				return jsp;
			}
		};
	}

	public static interface TextPaneHandler {
		void setText(String text);
		JTextPane jTextPane();
	}
	
	public static TextPaneHandler newTextPane(String contentType, String text,
			boolean editable) {
		final JTextPane jtp = new JTextPane();
		jtp.setContentType(contentType);
		jtp.setText(text);
		jtp.setEditable(editable);
		return new TextPaneHandler() {
			public void setText(String text) {
				jtp.setText(text);
				jtp.setCaretPosition(0);
			}
			public JTextPane jTextPane() {
				return jtp;
			}
		};
	}

	public static JSplitPane newSplitPane(int ori, Component c1, Component c2) {
		JSplitPane splitPane = new JSplitPane(ori, c1, c2);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(100);

		return splitPane;
	}

	public static JTree newJtree(DefaultTreeModel tm, TreeSelectionListener sl) {
		JTree tree = new JTree(tm);
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(sl);
		tree.setRootVisible(true);
		tree.setEditable(true);
		tree.setShowsRootHandles(true);

		return tree;
	}

	public static DefaultTreeModel newDefaultTreeModel(TreeNode rootNode,
			TreeModelListener tml) {
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(tml);
		return treeModel;
	}

	public DefaultMutableTreeNode toTreeNode(Element e) {
		DefaultMutableTreeNode result = new DefaultMutableTreeNode(e.getName());		
		for (Object o : e.elements()) {
			Element child = (Element) o;
			result.add(toTreeNode(child));
		}

		return result;
	}

	public JTree toJTree(String pathToXml) throws Exception {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(pathToXml);
		return new JTree(toTreeNode(doc.getRootElement()));
	}
	
	public static ListCellRenderer newListCellRenderer(final String iconPath){
		return new ListCellRenderer(){
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if(value == null)
					return null;
				String s = value.toString();
				JLabel label = newJLabel(iconPath, s,JLabel.BOTTOM,JLabel.CENTER);
				if (isSelected) {
					label.setBackground(list.getSelectionBackground());
					label.setForeground(list.getSelectionForeground());
				} else {
					label.setBackground(list.getBackground());
					label.setForeground(list.getForeground());
				}
				label.setEnabled(list.isEnabled());
				label.setFont(list.getFont());
				label.setOpaque(true);
				return label;
			}
		};
	}

	public static ImageIcon newImageIcon(String iconPath){
        URL url = Object.class.getResource(iconPath);
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        ImageIcon ic =new ImageIcon(image);
        return ic;
	}
	
	public static JLabel newJLabel(String iconPath,String text,Integer vtp,Integer htp){
        JLabel imgLabel = new JLabel();
        if(iconPath!=null)
        	imgLabel.setIcon(newImageIcon(iconPath));
        imgLabel.setText(text);
        if(vtp!=null)
        	imgLabel.setVerticalTextPosition(vtp);
        if(htp!=null)
        	imgLabel.setHorizontalTextPosition(htp);
        imgLabel.setVerticalAlignment(JLabel.CENTER);
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
		return imgLabel;
	}
	
	public static TreeModel newXmlTreeModel(TreeModelListener tml) {
		TreeModel treeModel = null;/*
									 * new TreeModel(){ public Object getRoot()
									 * { return null; } public Object
									 * getChild(Object parent, int index) {
									 * return null; } public int
									 * getChildCount(Object parent) { return 0;
									 * } public boolean isLeaf(Object node) {
									 * return false; } public void
									 * valueForPathChanged(TreePath path, Object
									 * newValue) { } public int
									 * getIndexOfChild(Object parent, Object
									 * child) { return 0; } public void
									 * addTreeModelListener(TreeModelListener l)
									 * { } public void
									 * removeTreeModelListener(TreeModelListener
									 * l) { } };
									 */
		treeModel.addTreeModelListener(tml);
		return treeModel;
	}
	
	public static interface FocusLostHandler {
		void focusLost(String text);
	}

	public static JTextField newTextField(String text,Integer columns,final Boolean autoClear,final FocusLostHandler flh) {
		final JTextField tf = new JTextField();
		if(text!=null)
			tf.setText(text);
		if(columns!=null)
			tf.setColumns(columns);
		tf.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if(flh!=null)
					flh.focusLost(tf.getText());
			}
			
			public void focusGained(FocusEvent e) {
				if(BooleanUtils.isTrue(autoClear))
					tf.setText("");
			}
		});
		return tf;
	}
	
	public static void addKeyListener(Component c,KeyAdapter ka){
		c.addKeyListener(ka);
	}

	public static String pathStr(javax.swing.text.Element el){
		Stack<javax.swing.text.Element> st = new Stack<javax.swing.text.Element>();
		goRoot(el,st);
		return toString(st);
	}
	
	private static void goRoot(javax.swing.text.Element el, Stack<javax.swing.text.Element> ell){
		if(el!=null){
			ell.push(el);
			goRoot(el.getParentElement(),ell);
		}
	}

	private static String toString(Stack<javax.swing.text.Element> ell){
		StringBuffer strBuf = new StringBuffer();
		if(ell!=null){
			while(!ell.isEmpty() && ell.peek()!=null){
				javax.swing.text.Element el = ell.pop();
				strBuf.append(el.getName()+":"+
						el.getAttributes().getAttribute("id")+"/");
			}
		}
		return strBuf.toString();
	}
	
	public static void highlightText(JTextComponent tc,int p0, int p1){
		Highlighter highlighter = tc.getHighlighter();
		highlighter.removeAllHighlights();
		try {
			highlighter.addHighlight(p0, p1, 
					new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public static void deskOpen(String file){
		if(Desktop.isDesktopSupported()){
			Desktop desk = Desktop.getDesktop();
			try {
				desk.open(new File(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Font deriveFont(Font f, float ratio, Font def){
		Font ret = null;
		if(f!=null)
			ret = f.deriveFont(f.getSize()*ratio);
		if(ret ==null)
			ret = def;
		return ret;
	}
	
	public static FocusListener defaultFocusRequester(final Component comp){
		return new FocusListener() {
			public void focusLost(FocusEvent e) {
			}
			public void focusGained(FocusEvent e) {
				comp.requestFocusInWindow();
			}
		};
	}

	public static JButton newButton(String text,ActionListener al) {
		JButton jb = new JButton(text);
		if(al!=null)
			jb.addActionListener(al);
		return jb;
	}
	
	public static void newSplitPane(int newOrientation){
		JScrollPane left = new JScrollPane();
		JScrollPane right = new JScrollPane();
		JSplitPane jsp = new JSplitPane(newOrientation,left,right);
		jsp.setOneTouchExpandable(true);
		jsp.setDividerLocation(0.2);
		
		Dimension minimumSize = new Dimension(0, 0);
		left.setMinimumSize(minimumSize);
		right.setMinimumSize(minimumSize);
		//TODO
	}
	
	public static TabbedUI toTabbedUI(final Container ct,final String tabName,final String tabDesc){
		return new TabbedUI() {
			public Container getUI() {
				return ct;
			}
			public String tabName() {
				return tabName;
			}
			public String tabDescription() {
				return tabDesc;
			}
		};
	}
	
	public static JTabbedPane newTabbedPane(TabbedUI[] tus,Integer tabPlacement,
			Integer selected, JTabbedPane def){
		if(tus==null)
			return def;
		final JTabbedPane jtp = new JTabbedPane();
		if(tabPlacement != null)
			jtp.setTabPlacement(tabPlacement);
		for (TabbedUI tabbedUI : tus) {
			jtp.addTab(tabbedUI.tabName(), null, tabbedUI.getUI(), tabbedUI.tabDescription());
		}
		if(selected != null && selected < tus.length)
			jtp.setSelectedComponent(tus[selected].getUI());
		
		jtp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				jtp.getSelectedComponent().requestFocusInWindow();
			}
		});
		return jtp;
	}
	
	public static Dimension screenSize(){
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public static JPanel newPanel(LayoutManager lm){
		JPanel panel = new JPanel();
		if(lm!=null)
			panel.setLayout(lm);
		else
			panel.setLayout(new MigLayout());
		return panel;
	}
	
	public static JTextComponent newUi(Class type, Object obj){
		JTextComponent comp = null;
		if(obj instanceof String){
			comp = newTextField((String)obj, 10, false, null);
		}else if(obj instanceof Number){
			comp = newTextField((String)obj, 10, false, null);
		}
		return comp;
	}

	private static void hideComponent(Component c,Container p) {
		p.remove(c);
	}

	public static void setVisible(Component c,Container p, boolean visible) {
		hideComponent(c,p);
		if(visible)
			showComponent(c,p);
	}

	private static void showComponent(Component c,Container p) {
		p.add(c);
	}

}
