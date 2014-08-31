package org.sharpx.swing.apps.el;

import org.sharpx.swing.apps.el.beans.term.beans.Term;

public interface WebDict {
	public Term lookup(String key);
}