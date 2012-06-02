package org.sharp.swing.apps.bean2ui;


import java.awt.Container;
import java.net.Socket;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class VMUI {

	Container appWorkArea;
	private JDesktopPane jdp;
	
	public VMUI(Container contentPanel){
		/*this.appFrame = new JFrame("Intropector UI");*/
		appWorkArea = contentPanel;
	}
	
	public void start(){
		
		jdp = new JDesktopPane();
		JInternalFrame classUiIF = new JInternalFrame("ClassUI",true,true,true,true);
		classUiIF.setVisible(true);
		classUiIF.setBounds(10, 10, 700, 500);
		classUiIF.getContentPane().add(new ClassUi2_1().getUI(Socket.class));
		jdp.add(classUiIF);
		
		appWorkArea.add(jdp);
	}
}
