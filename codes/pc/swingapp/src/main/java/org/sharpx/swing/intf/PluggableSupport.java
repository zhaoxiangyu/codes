package org.sharpx.swing.intf;

import org.sharpx.swing.intf.Pluggable.TabbedUI;

public interface PluggableSupport {
	public interface TabbedUISupport {
		TabbedUI tabbedUi(Integer usage);
	}
	Pluggable pluggable();
}
