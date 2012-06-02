package org.sharp.swing.apps.el.beans.term.beans;


import org.apache.commons.io.FilenameUtils;
import org.sharp.jdkex.FjUtils;
import org.sharp.jdkex.Utils;

public class Term {
	public Term() {
		super();
	}
	
	private String key;
	private String keyvoice;
	private String pronunciation;
	private PartOfSpeech[] pses;
	
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setKeyvoice(String keyvoice) {
		this.keyvoice = keyvoice;
	}
	public String getKeyvoice() {
		return keyvoice;
	}
	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}
	public String getPronunciation() {
		return pronunciation;
	}
	public void setPses(PartOfSpeech[] pses) {
		this.pses = pses;
	}
	public PartOfSpeech[] getPses() {
		return pses;
	}
	public void toDisk(String dir) {
		Utils.saveJox(this, FilenameUtils.concat(dir,getKey()+".txt"));
	}
	public String toHtml(){
		StringBuffer strBuf = new StringBuffer();
		Utils.introspect(this, Utils.htmlGenerator(strBuf));
		return strBuf.toString();
	}
	
	public String toString(){
		return FjUtils.join(pses, PartOfSpeech.class, "\n");
	}
}