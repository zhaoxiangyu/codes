package org.sharp.swing.apps.win.dcom;

import java.awt.Container;
import java.net.UnknownHostException;

import javax.swing.JScrollPane;

import org.jinterop.dcom.common.JIException;

public class LnkUi {

	public Container getUI() {
		return new JScrollPane();
	}

	//TODO call ShellView interface
	public LnkUi() throws JIException, UnknownHostException {
		System.out.println("LnkUi().");
		/*JISystem.setAutoRegisteration(true);
		JISession session = JISession.createSession("localhost", 
				"admin", "hlongh");
		JIComServer comServer = new JIComServer(
				JIClsid.valueOf("88A05C00-F000-11CE-8350-444553540000"),
				session);
		IJIComObject shellLink = comServer.createInstance();
		System.out.println("shell link created.");
		JISession.destroySession(session);*/
	}
}
