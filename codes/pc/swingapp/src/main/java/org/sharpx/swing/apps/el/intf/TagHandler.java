package org.sharpx.swing.apps.el.intf;

import org.sharpx.swing.apps.el.beans.Tag;

public interface TagHandler {
	Tag loadTag(String rootName);
	void saveTag(Tag tag);
}