package org.sharp.swing.apps.webdict2.beans;

import org.sharp.jdkex.Utils;

public class Config {

	public final static String NAME = "webdict2";
	String voiceLibBaseDir="./voice";
	String termsBaseDir="./term2";
	private String[] courseNames = new String[]{"Legal English","Friends","How to interview"};

	public String[] getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(String[] courseNames) {
		this.courseNames = courseNames;
	}
	
	public void addCourseName(String courseName){
		courseNames = Utils.append(courseNames, courseName);
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
	
}