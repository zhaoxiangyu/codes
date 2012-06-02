package org.sharp.swing.apps.webdict.beans;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sharp.jdkex.Utils;

public class Config {
	String voiceLibBaseDir;
	String termsBaseDir;
	
	String vocIndexFilePath;
	
	public Config(){
		/*voiceLibBaseDir = System.getProperty("user.dir");*/
		voiceLibBaseDir ="e:/xp/voice";
		termsBaseDir = FilenameUtils.concat(System.getProperty("user.dir"),
				"terms");
		vocIndexFilePath = FilenameUtils.concat(System.getProperty("user.dir"),
			"voc-index.txt");
	}
	
	public Config save(String file){
		return (Config)Utils.saveJox(this, file);
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public String getVoiceLibBaseDir() {
		return voiceLibBaseDir;
	}

	public void setVoiceLibBaseDir(String voiceLibBaseDir) {
		this.voiceLibBaseDir = voiceLibBaseDir;
	}

	public String getTermsBaseDir() {
		return termsBaseDir;
	}

	public void setTermsBaseDir(String termsBaseDir) {
		this.termsBaseDir = termsBaseDir;
	}

	public String getVocIndexFilePath() {
		return vocIndexFilePath;
	}

	public void setVocIndexFilePath(String vocIndexFilePath) {
		this.vocIndexFilePath = vocIndexFilePath;
	}

}
