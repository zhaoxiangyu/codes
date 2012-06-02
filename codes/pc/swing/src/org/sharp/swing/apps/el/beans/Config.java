package org.sharp.swing.apps.el.beans;

import java.io.File;

import org.sharp.swing.apps.beans.Constants;
import org.sharp.swing.apps.beans.RecentItems;

public class Config {
	String indexPath="./index";
	String lastFilePath;
	String mp3LibPath="./voice";
	String termBasePath="./term3";
	String newWordsBasePath = "./newwords";
	String articlesPath="./articles";
	String docsPath=Constants.userDir;/*"D:/Documents/Web Articles";*/
	RecentItems recentIndexedFiles = new RecentItems(5);
	RecentItems recentMatchedFiles = new RecentItems(5);

	public String getIndexPath() {
		return indexPath;
	}

	public String getMp3LibPath() {
		return mp3LibPath;
	}

	public void setMp3LibPath(String mp3LibPath) {
		this.mp3LibPath = mp3LibPath;
	}

	public String getTermBasePath() {
		return termBasePath;
	}

	public void setTermBasePath(String termBasePath) {
		this.termBasePath = termBasePath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

	public String getLastFilePath() {
		return lastFilePath;
	}

	public void setLastFilePath(String lastFilePath) {
		this.lastFilePath = lastFilePath;
	}

	public RecentItems getRecentIndexedFiles() {
		return recentIndexedFiles;
	}

	public void setRecentIndexedFiles(RecentItems recentIndexedFiles) {
		this.recentIndexedFiles = recentIndexedFiles;
	}

	public RecentItems getRecentMatchedFiles() {
		return recentMatchedFiles;
	}

	public void setRecentMatchedFiles(RecentItems recentMatchedFiles) {
		this.recentMatchedFiles = recentMatchedFiles;
	}

	public String getArticlesPath() {
		return articlesPath;
	}

	public void setArticlesPath(String articlesPath) {
		this.articlesPath = articlesPath;
	}

	public String getNewWordsBasePath() {
		File dir = new File(newWordsBasePath);
		if(!dir.exists())
			dir.mkdirs();
			
		return newWordsBasePath;
	}

	public void setNewWordsBasePath(String newWordsBasePath) {
		this.newWordsBasePath = newWordsBasePath;
	}

	public String getDocsPath() {
		return docsPath;
	}

	public void setDocsPath(String docsPath) {
		this.docsPath = docsPath;
	}
}