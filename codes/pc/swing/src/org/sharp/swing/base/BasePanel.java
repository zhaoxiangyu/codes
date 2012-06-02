package org.sharp.swing.base;

import javax.swing.JPanel;

import org.sharp.intf.Pluggable.TabbedUI;
import org.sharp.intf.PluggableSupport.TabbedUISupport;
import org.sharp.swing.utils.SwingUtils;

public class BasePanel extends JPanel implements TabbedUISupport {

	public TabbedUI tabbedUi(Integer usage) {
		if(usage == null)
			return SwingUtils.toTabbedUI(this, 
					this.getClass().getSimpleName(), 
					this.getClass().getCanonicalName());
		else
			return null;
	}

}
