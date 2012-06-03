package org.sharp.vocreader.comp;

import org.sharp.android.ui.MenuOperationsUtils;
import org.sharp.android.ui.PluggableUI;
import org.sharp.android.ui.intf.ContentViewer;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.samples.TabbedSampleActi;
import org.sharp.vocreader.view.LogFileViewer;


public class LogFileActi extends PluggableUI {

	@Override
	protected MenuOperation[] menuOperations() {
		return new MenuOperation[]{
			MenuOperationsUtils.newActiLauncher(this, "tabbedSample", 
					TabbedSampleActi.class)
		};
	}

	@Override
	public ContentViewer contentViewProvider() {
		return new LogFileViewer(this);
	}
	
	protected boolean addFileHandler(){
		return false;
	}

}
