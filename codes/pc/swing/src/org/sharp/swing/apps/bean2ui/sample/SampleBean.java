package org.sharp.swing.apps.bean2ui.sample;

public class SampleBean {

	Integer integer = new Integer(10000);
	String string = "he";
	public static String staticm;
	Boolean readonly = new Boolean(false);
	boolean writeable;
	public int[] iarray;
	public int publicint = 10001;
	
	public int[] getIarray() {
		return iarray;
	}
	public Boolean getReadonly() {
		return readonly;
	}
	public void setWriteable(boolean writeable) {
		this.writeable = writeable;
	}
	public Integer getId() {
		return integer;
	}
	public void setId(Integer id) {
		this.integer = id;
	}
	public String getName() {
		return string;
	}
	public void setName(String name) {
		this.string = name;
	}
}
