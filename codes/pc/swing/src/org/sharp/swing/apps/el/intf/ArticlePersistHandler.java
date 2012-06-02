package org.sharp.swing.apps.el.intf;

import org.sharp.swing.apps.el.beans.Article;

public interface ArticlePersistHandler{
	void save(Article article);
	Article load(String docName);
}