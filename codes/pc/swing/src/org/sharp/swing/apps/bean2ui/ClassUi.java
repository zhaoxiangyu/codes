package org.sharp.swing.apps.bean2ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Socket;
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

import org.sharp.intf.Pluggable;
import org.sharp.jdkex.Utils;

public class ClassUi implements Pluggable {
	
	public interface EventListener {
		void event(ClassUi cu);
	}

	private JScrollPane scrlp;
	private Class clazs;
	private EventListener listener;
	
	public ClassUi(){
		clazs = Socket.class;
		Utils.log.info("new ClassUi constructed.");
	}
	public ClassUi(Class clazz){
		clazs = clazz;
		Utils.log.info("new ClassUi constructed.");
	}
	
	public Container getUi(){
		Utils.log.info("getUI(obj="+clazs+")");
		Container ui = new JPanel(new BorderLayout());
		JPanel top = new JPanel();
		ui.add(top,BorderLayout.NORTH);
		top.add(new JLabel(clazs.getCanonicalName()));
		
		JButton constructors = new JButton("constructors");
		constructors.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Utils.log.info("constructors button pressed.");
				scrlp.setViewportView(getConstructorTable(clazs));
			}
		});
		top.add(constructors);

		JButton staticMethods = new JButton("staticMethods");
		staticMethods.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Utils.log.info("staticMethods button pressed.");
				scrlp.setViewportView(getCertainMethodTable(clazs, 1));
				/*scrlp.validate();*/
				/*uif.validate();*/
			}
		});
		top.add(staticMethods);
	
		JTable table = null;
		if(clazs instanceof Class){
			table = /*getConstructorTable((Class)clazz);*/getCertainMethodTable(clazs,1);
		}
		
		table.setPreferredScrollableViewportSize(new Dimension(700, 500));
        table.setFillsViewportHeight(true);

		scrlp = null;
		if (table!=null){
			scrlp = new JScrollPane(table);
		}
		
		ui.add(scrlp/*,BorderLayout.CENTER*/);
		Utils.log.info("return ui "+ui);
		return ui;
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
			model.addRow(new Method[]{m});			
		}
		
		table = new JTable(model){
			
			public TableCellRenderer getCellRenderer(int row, int column) {
				return new MethodUiRendor();
			};
			
			public TableCellEditor getCellEditor(int row, int column){
				return new MethodUiEditor();
			}
		};
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

	
	class MethodUiRendor implements TableCellRenderer {

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Method obj = (Method)table.getModel().getValueAt(row,column);
			JButton jb = new JButton();
			jb.setText(obj.getName());
			return jb;
		}

	}
	
	class MethodUiEditor extends AbstractCellEditor implements TableCellEditor {

		public Object getCellEditorValue() {
			return null;
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			Method obj = (Method)table.getModel().getValueAt(row,column);
			JButton jb = new JButton();
			jb.setText(obj.getName());
			jb.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					Utils.log.info("Method button pressed.");					
				}
			});
			return jb;
		}

	}
	
	public void setListener(EventListener listener) {
		this.listener = listener;
	}

	public EventListener getListener() {
		return listener;
	}

	public AppLifeCycle lifeCycle() {
		return null;
	}

	public TabbedUI tabbedUI() {
		return new TabbedUI() {
			public Container getUI() {
				return getUi();
			}
			public String tabName() {
				return "Class";
			}
			public String tabDescription() {
				return "Class";
			}
		};
	}
}
