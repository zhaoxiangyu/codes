package org.sharpx.swing.intf;

public interface Pluggable {
	public interface AppLifeCycle {
		void init(AppContext context);
		void exit();
	}
	public interface TabbedUI extends UI {
		String tabName();
		String tabDescription();
	}
	AppLifeCycle lifeCycle();
	TabbedUI tabbedUI();
}
