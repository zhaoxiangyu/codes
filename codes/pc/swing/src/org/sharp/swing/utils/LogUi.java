package org.sharp.swing.utils;

import java.awt.Container;

import javax.swing.JScrollPane;

import org.sharp.intf.Pluggable;
import org.sharp.jdkex.TextAreaAppender;
import org.sharp.jdkex.Utils;

public class LogUi implements Pluggable {

	private JScrollPane sp;

	public LogUi(){
		TextAreaAppender taa = (TextAreaAppender) Utils.log.getAppender(TextAreaAppender.name);
		if(taa!=null)
			sp = new JScrollPane(taa.getTextArea());
	}

	public AppLifeCycle lifeCycle() {
		return null;
	}

	public TabbedUI tabbedUI() {
		return new TabbedUI() {
			public Container getUI() {
				return sp;
			}
			public String tabName() {
				return "Log";
			}
			public String tabDescription() {
				return "Log";
			}
		};
	}
}
