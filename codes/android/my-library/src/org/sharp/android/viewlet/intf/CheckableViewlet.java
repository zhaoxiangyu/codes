package org.sharp.android.viewlet.intf;

import android.view.View;

public interface CheckableViewlet {

	public View getView();

	public void toggle();
	
	public void setText(String text);

	public boolean isChecked();

	public void setChecked(boolean checked);

}