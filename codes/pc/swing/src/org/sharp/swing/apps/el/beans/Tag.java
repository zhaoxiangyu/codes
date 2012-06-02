package org.sharp.swing.apps.el.beans;

import org.sharp.jdkex.LangUtils;

public class Tag implements Comparable<Tag> {
	
	public static final String WORDSTAG = "wordsTag";
	public static final String ARTICLESTAG = "articlesTag";
	Tag[] children;
	String name;
	
	public String toString(){
		return name;
	}

	public Tag[] getChildren() {
		return children;
	}

	public void setChildren(Tag[] children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tag(String name, Tag[] children) {
		super();
		this.children = children;
		this.name = name;
	}
	
	public Tag(String name) {
		this(name,null);
	}

	public Tag() {
		super();
	}

	public void removeChild(Tag s) {
		children = LangUtils.remove(children, s);
	}

	public int compareTo(Tag o) {
		return LangUtils.compareTo(this.getName(), o.getName());
	}
}
