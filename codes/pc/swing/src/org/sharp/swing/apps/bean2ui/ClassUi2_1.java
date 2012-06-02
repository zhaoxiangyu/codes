package org.sharp.swing.apps.bean2ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.sharp.jdkex.Utils;

public class ClassUi2_1 {
	
	public interface EventListener {
		void event(ClassUi2_1 cu);
	}

	private JScrollPane scrlp;
	private Class clazs;
	private EventListener listener;
	
	public ClassUi2_1(){
		Utils.log.info("new ClassUi2 constructed.");
	}
	
	public Container getUI(Class clazz){
		clazs = clazz;
		Utils.log.info("getUI(obj="+clazz+")");
		
		Container ui = new JPanel(new BorderLayout());
		JPanel top = new JPanel();
		ui.add(top,BorderLayout.NORTH);
		top.add(new JLabel(clazz.getCanonicalName()));
		
		JButton constructors = new JButton("constructors");
		constructors.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Utils.log.info("constructors button pressed.");
			}
		});
		top.add(constructors);

		JButton staticMethods = new JButton("staticMethods");
		staticMethods.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Utils.log.info("staticMethods button pressed.");
				scrlp.setViewportView(getMethodTable(clazs, 1));
				/*scrlp.validate();*/
				/*uif.validate();*/
			}
		});
		top.add(staticMethods);
	
		JPanel panel = null;
		panel = /*getConstructorTable((Class)clazz);*/getMethodTable(clazz,1);
		
		scrlp = null;
		if (panel!=null){
			scrlp = new JScrollPane(panel);
		}
		
		ui.add(scrlp,BorderLayout.CENTER);
		Utils.log.info("return ui "+ui);
		return ui;
	}

	public JPanel getMethodTable(Class clazz, int type){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		Method[] ms = clazz.getDeclaredMethods();
		ArrayList sms = new ArrayList();
		
		for (int i = 0; i < ms.length; i++) {
			if(type ==1){
				if(Modifier.isStatic(ms[i].getModifiers())
						&& Modifier.isPublic(ms[i].getModifiers())){
					sms.add(ms[i]);
				}
			}else if(type == 2){
			}
		}
		
		GridBagConstraints gbc = null;
		int i = 0;
		for (Iterator iterator = sms.iterator(); iterator.hasNext();) {
			Method m = (Method) iterator.next();
			gbc = new GridBagConstraints(0,i,1,1,
					0.001,i/3.0,
					GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.NONE,
					new Insets(2, 10, 2, 2),0,0);
			panel.add(new JButton("i"),gbc);

			gbc = new GridBagConstraints(1,i,1,1,
					0.003,i/3.0,
					GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.NONE,
					new Insets(2, 2, 2, 2),0,0);
			panel.add(new JLabel(m.toGenericString()),gbc);

			gbc = new GridBagConstraints(1+1,i,1,1,
					0.999,i/3.0,
					GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.NONE,
					new Insets(2, 2, 2, 2),0,0);
			panel.add(new JLabel(""),gbc);
			
			i++;
		}
		
		return panel;
	}

	public void setListener(EventListener listener) {
		this.listener = listener;
	}

	public EventListener getListener() {
		return listener;
	}

}
