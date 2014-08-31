package org.sharpx.swing.base;

import javax.swing.JPanel;

import org.sharpx.swing.intf.Pluggable.TabbedUI;
import org.sharpx.swing.intf.PluggableSupport.TabbedUISupport;
import org.sharpx.swing.utils.SwingUtils;

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
