package org.sharp.swing.apps.el.intf;

import org.sharp.swing.apps.el.beans.NewWord;

public interface NewWordHandler {
	void removeWordTag(String tag);
	void saveWord(NewWord nw);		
	NewWord loadWord(String key);
	void indexWord(NewWord nw);	
	boolean removeWord(NewWord nw);
	boolean lookupVoice(NewWord nw);
	public String localExpl(NewWord nw,String def);
	NewWord findNewWordByIndexId(String right);
	void pronounce(NewWord nw);
}