package org.sharp.intf;

import org.sharp.intf.Pluggable.TabbedUI;

public interface PluggableSupport {
	public interface TabbedUISupport {
		TabbedUI tabbedUi(Integer usage);
	}
	Pluggable pluggable();
}
