package org.sharp.swing.apps.tools;

import java.awt.Container;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.sharp.intf.AppContext;
import org.sharp.intf.Pluggable;
import org.sharp.swing.apps.el.DocReaderApp;
import org.sharp.swing.apps.tools.beans.Config;
import org.sharp.swing.apps.tools.ui.FileRenamerPanel;

public class ToolsApp implements Pluggable {

	private Runtime runtime;
	private AppLifeCycle alc;

	public static class Runtime {
		Config config;
		AppContext appCtx;
		protected JTabbedPane tabbedPane;
		protected FileRenamerPanel frp;

	}
	
	@Override
	public AppLifeCycle lifeCycle() {
		if(alc == null)
			alc = new AppLifeCycle(){
				public void init(AppContext ctx) {
					runtime.appCtx = ctx;
					runtime.config = runtime.appCtx.getConfig(ToolsApp.class.getSimpleName(), Config.class);
				}
		
				public void exit() {
					runtime.appCtx.saveConfig(DocReaderApp.class.getSimpleName(), runtime.config);
				}
			};

		return alc;
	}

	@Override
	public TabbedUI tabbedUI() {
		return new TabbedUI(){
			public String tabName() {
				return "System tools";
			}
	
			public String tabDescription() {
				return "System tools";
			}
	
			public Container getUI() {
				final JTabbedPane tabbedPane = new JTabbedPane();
				runtime.tabbedPane = tabbedPane;
				runtime.frp = new FileRenamerPanel();

				tabbedPane.setSelectedComponent(runtime.frp);
				tabbedPane.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						System.out.println("tabbedPane changed.");
						tabbedPane.getSelectedComponent().requestFocusInWindow();
					}
				});
	
				return tabbedPane;
			}
		};
	}

}
