package org.sharpx.swing.apps.el.intf;

import org.sharpx.swing.apps.el.beans.Article;

public interface ArticlePersistHandler{
	void save(Article article);
	Article load(String docName);
}