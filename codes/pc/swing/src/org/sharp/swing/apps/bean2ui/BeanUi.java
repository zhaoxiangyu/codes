package org.sharp.swing.apps.bean2ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.beanutils.PropertyUtils;
import org.sharp.jdkex.Utils;

public class BeanUi{
	
	Container fartherUi;
	ArrayList uiList = new ArrayList();
	Container currentUi;
	
	public BeanUi(){
		Utils.log.info("new Converter constructed.");
	}
	
	public void updateUI(Object obj) {	
		if(currentUi!=null){
			/*currentUi.setVisible(false);*/
			fartherUi.remove(currentUi);
		}
		if(fartherUi!=null){
			Container newUI = getUI(obj);
			currentUi = newUI;
			uiList.add(newUI);
			
			fartherUi.add(newUI);
			fartherUi.validate();
		}else
			Utils.log.info("fartherUi is null");
	}

	public void setFartherUi(Container ui) {
		this.fartherUi = ui;
	}
	
	public Container getUI(Object obj){
		/*Container ui = new Panel(new BorderLayout());*/
		Utils.log.info("getUI(obj="+obj+")");
		Container ui = new JPanel();
		
		if(obj.getClass().isArray()){
			Object[] objs = (Object[])obj;
			if(objs.length > 0)
				obj = objs[0];
			log(objs);
			
			JButton up = new JButton("up");
			up.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					Utils.log.info("up button pressed.");
					int i = uiList.indexOf(currentUi);
					uiList.remove(currentUi);
					if(i >= 1){
						fartherUi.remove(currentUi);
						/*currentUi.setVisible(false);*/
						currentUi = (Container)uiList.get(--i);
						fartherUi.add(currentUi);
						fartherUi.validate();
					}
				}
			});
			ui.add(up);
		}else if(obj instanceof Class){
			JButton constructors = new JButton("constructors");
			constructors.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					Utils.log.info("constructors button pressed.");
				}
			});
			ui.add(constructors);
		}

		ui.add(new JLabel(obj.getClass().getCanonicalName())/*,BorderLayout.NORTH*/);
		JButton prev = new JButton("prev");
		prev.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Utils.log.info("prev button pressed.");
				int i = uiList.indexOf(currentUi);
				uiList.remove(currentUi);
				if(i >= 1){
					fartherUi.remove(currentUi);
					/*currentUi.setVisible(false);*/
					currentUi = (Container)uiList.get(--i);
					fartherUi.add(currentUi);
					fartherUi.validate();
				}
			}
		});
		ui.add(prev/*,BorderLayout.WEST*/);
		
		JTable table = null;
		if(obj instanceof Class){
			table = getConstructorTable((Class)obj);/*getCertainMethodTable((Class)obj,1);*/
		}else{
			table = getJTable(obj);			
		}
		
		table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        table.setFillsViewportHeight(true);

		JScrollPane scrlp = null;
		if (table!=null){
			scrlp = new JScrollPane(table);
			/*scrlp = new JScrollPane();
			scrlp.add(table);*/
		}
		
		ui.add(scrlp/*,BorderLayout.CENTER*/);
		Utils.log.info("return ui "+ui);
		return ui;
	}

	public void log(Object[] oa) {
		for (int i = 0; i < oa.length; i++) {
			Utils.log.info(oa[i]+",");
		}
	}
	
	public JTable getCertainMethodTable(Class clazz, int type){
		JTable table = null;
		Method[] ms = clazz.getDeclaredMethods();
		ArrayList sms = new ArrayList();
		DefaultTableModel model = new DefaultTableModel();
		if(type == 1)
			model.addColumn("static method");
		else if(type == 2)
			model.addColumn("...");
		
		for (int i = 0; i < ms.length; i++) {
			if(type ==1){
				if(Modifier.isStatic(ms[i].getModifiers())
						&& Modifier.isPublic(ms[i].getModifiers())){
					sms.add(ms[i]);
				}
			}else if(type == 2){
			}
		}
		/*Collections.sort(sms, new Comparator() {
			
			public int compare(Object arg0, Object arg1) {
				int ret = 0;
				Method m0 = (Method)arg0;
				Method m1 = (Method)arg1;
				int mm0 = m0.getModifiers();
				int mm1 = m1.getModifiers();
				if(Modifier.isPublic(mm0)){
					if(Modifier.isPublic(mm1))
						ret = m0.getName().compareTo(m1.getName());
					else
						ret = -1;
				}
				return ret;
			}
		});*/
		
		for (Iterator iterator = sms.iterator(); iterator.hasNext();) {
			Method m = (Method) iterator.next();
			model.addRow(new String[]{m.getName()});			
		}
		
		table = new JTable(model);
		return table;
	}

	public JTable getConstructorTable(Class clazz){
		JTable table = null;
		Constructor[] cs = clazz.getConstructors();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("constructors");
		
		for (int i = 0; i < cs.length; i++) {
			if(/*cs[i].isAccessible()*/Modifier.isPublic(cs[i].getModifiers()))
				model.addRow(new Constructor[]{cs[i]});			
		}
	
		table = new JTable(model);
		return table;
	}

	public JTable getJTable(Object obj){
		JTable table = null;
		
		PropertyDescriptor[] propertyDescriptors;
		/*if(obj instanceof Class)
			propertyDescriptors = PropertyUtils.getPropertyDescriptors((Class)obj);
		else*/
			propertyDescriptors = PropertyUtils.getPropertyDescriptors(obj);
		
		DefaultTableModel model = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column) {
				return super.isCellEditable(row, column) && column!=0;
			}
		};
		model.addColumn("property name");
		model.addColumn("proper value");
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor p = propertyDescriptors[i];
			String pName = p.getName();
			Class pt = p.getPropertyType();
			Utils.log.info("property:"+(pt==null?"indexed_property":pt.getSimpleName())+" "+pName);
			if(!"class".equalsIgnoreCase(pName) &&
					p.getReadMethod()!=null)
				try {
					Object pValue = PropertyUtils.getProperty(obj, pName);
					model.addRow(new Object[]{pName,pValue});
				} catch (Exception e) {
					Utils.log.error("", e);
				}
		}
		
		table = new JTable(model){
			private TableCellRenderer jumpUiRender = new JumpUiRender(BeanUi.this);
			private TableCellEditor jumpUiEditor = new JumpUiEditor(BeanUi.this);

			/*public Component prepareEditor(javax.swing.table.TableCellEditor editor, int row, int column) {
				Component c = super.prepareEditor(editor, row, column);
				c.setBackground(new Color(128, 128, 255));
				Console.log.info("Editor's component type:"+c.getClass().getCanonicalName());
				return c;
			};*/
					
			public javax.swing.table.TableCellEditor getCellEditor(int row, int column) {
				Object value = getModel().getValueAt(row, column);
				if(value!=null)
					if(value instanceof Class ||
							value.getClass().isArray() ||
							(!value.getClass().isAnnotation() &&
							!value.getClass().getPackage().getName().contains("java.lang")))
						return jumpUiEditor;
					else
						return getDefaultEditor(value.getClass());
						/*return super.getCellEditor(row, column);*/
				else
					return super.getCellEditor(row, column);
			};
			
			public javax.swing.table.TableCellRenderer getCellRenderer(int row, int column) {
				Object value = getModel().getValueAt(row, column);
				if(value!=null){
					/*Console.log.info(""+value.getClass());
					Console.log.info(""+value.getClass().getPackage());*/
					/*if(!value.getClass().isPrimitive() &&
							!(value instanceof String)
							value instanceof Class ||
							value.getClass().isArray() ||
							(!value.getClass().isAnnotation() &&
							!value.getClass().getPackage().getName().contains("java.lang")))
						return jumpUiRender;
					else;*/
						return getDefaultRenderer(/*String.class*/value.getClass());
				}else
					return super.getCellRenderer(row, column);
			};
		};
//		ui.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		return table;
	}
	
	class JumpUiRender extends JButton implements TableCellRenderer{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4831727688758196486L;
		private BeanUi converter;

		public JumpUiRender(BeanUi converter) {
			this.converter = converter;
		}

		public Component getTableCellRendererComponent(final JTable table,
				Object value, boolean isSelected, boolean hasFocus, final int row,
				final int column) {
			/*JButton more = new JButton("more...");*/
			setText("more...");
			addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					Utils.log.info("more button pressed.");
					converter.updateUI(table.getModel().getValueAt(row,column));
				}
			});
			return this;
		}
		
	}
		
	class JumpUiEditor extends AbstractCellEditor 
		implements TableCellEditor/*,ActionListener*/{
		/**
		 * 
		 */
		private static final long serialVersionUID = 7092102448606144004L;
		private final BeanUi converter;
		/*private JButton more*/;

		public JumpUiEditor(final BeanUi converter) {
			this.converter = converter;
			/*more = new JButton("more...");*/
			/*more.setActionCommand("more");*/
		}

		/*public void actionPerformed(ActionEvent e) {
	        if ("more".equals(e.getActionCommand())) {
				Console.log.info("more button pressed.");
	            fireEditingStopped(); //Make the renderer reappear.
	        }
	    }*/

		public Object getCellEditorValue() {
	        return "editor value";
	    }

		public Component getTableCellEditorComponent(final JTable table,
				Object value, boolean hasFocus, final int row,
				final int column) {
			JButton more = new JButton("more...");
			more.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					Utils.log.info("more button("+row+","+column+") pressed.");
					converter.updateUI(table.getModel().getValueAt(row,column));
				}
			});
			return more;
		}
		
	}
}
