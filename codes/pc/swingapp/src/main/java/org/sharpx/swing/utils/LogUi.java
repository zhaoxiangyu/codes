package org.sharpx.swing.utils;

import java.awt.Container;

import javax.swing.JScrollPane;

import org.sharpx.swing.intf.Pluggable;
import org.sharpx.utils.DsUtils;
import org.sharpx.utils.TextAreaAppender;

public class LogUi implements Pluggable {

	private JScrollPane sp;

	public LogUi(){
		TextAreaAppender taa = (TextAreaAppender) DsUtils.log.getAppender(TextAreaAppender.name);
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
