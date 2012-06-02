package org.sharp.swing.apps.jpwords.beans;

import org.apache.commons.io.FilenameUtils;

public class Config {

	String jptermsBaseDir;
	String[] jpwordsDirs;

	public Config(){
		jptermsBaseDir = FilenameUtils.concat(System.getProperty("user.dir"),
			"jpterms");
		jpwordsDirs = new String[]{"W:/jp/新版中日交流标准日本语.单词/上册/unit6"};
	}
	
	public String getJptermsBaseDir() {
		return jptermsBaseDir;
	}

	public void setJptermsBaseDir(String jptermsBaseDir) {
		this.jptermsBaseDir = jptermsBaseDir;
	}

	public String[] getJpwordsDirs() {
		return this.jpwordsDirs;
	}

	public void setJpwordsDirs(String[] jpwordsDirs) {
		this.jpwordsDirs = jpwordsDirs;
	}

}
