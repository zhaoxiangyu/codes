package org.sharp.beans;

import android.view.View;

public class TabItem2 {
	public String tag;
	public View indicator;
	public View content;
	
	public TabItem2(String tag, View indicator, View content) {
		super();
		this.tag = tag;
		this.indicator = indicator;
		this.content = content;
	}
}
