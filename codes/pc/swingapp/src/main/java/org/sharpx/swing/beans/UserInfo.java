package org.sharpx.swing.beans;

import java.io.Serializable;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = 2114866953626096397L;

	long firstRun;
	long firstReg;
	String userKey;

	public long getFirstRun() {
		return firstRun;
	}

	public void setFirstRun(long firstRun) {
		this.firstRun = firstRun;
	}

	public long getFirstReg() {
		return firstReg;
	}

	public void setFirstReg(long firstReg) {
		this.firstReg = firstReg;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
