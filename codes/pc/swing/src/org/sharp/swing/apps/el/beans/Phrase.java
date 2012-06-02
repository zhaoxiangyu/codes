package org.sharp.swing.apps.el.beans;

public class Phrase {
	String type;
	String text;
	int offset;
	int length;
	String[] tags;
	String note;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public Phrase(String type, String text, int offset) {
		super();
		this.type = type;
		this.text = text;
		this.offset = offset;
	}

	public Phrase() {
		super();
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}