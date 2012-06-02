package org.sharp.beans;

import android.content.Intent;

public class TabItem {
	public Integer imageResId;
	public String label;
	public Intent intent;
	
	public TabItem(String label, Intent intent, Integer imageResId) {
		super();
		this.imageResId = imageResId;
		this.label = label;
		this.intent = intent;
	}
}
