package org.sharp.swing.apps.el.intf;

import org.sharp.swing.apps.el.beans.Tag;

public interface TagHandler {
	Tag loadTag(String rootName);
	void saveTag(Tag tag);
}