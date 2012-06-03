package org.sharp.android.viewlet;

import org.sharp.android.viewlet.intf.CheckableViewlet;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

public class CheckableText implements CheckableViewlet {

	TextView tv;
	boolean checked;
	String text;
	Drawable checkedDrawable;
	Drawable uncheckedDrawable;
	
	public CheckableText(Context ctx,String text, Drawable checkedDrawable, 
			Drawable uncheckedDrawable, boolean checked){
		tv = new TextView(ctx);
		this.text = text;
		this.checked = checked;
		this.checkedDrawable = checkedDrawable;
		this.uncheckedDrawable = uncheckedDrawable;
		freshView();
	}
	
	@Override
	public View getView(){
		return tv;
	}
	
	private void freshView(){
		tv.setText(text);
		if(checked){
			tv.setBackgroundDrawable(checkedDrawable);
		}else{
			tv.setBackgroundDrawable(uncheckedDrawable);
		}
	}
	
	@Override
	public void toggle(){
		checked = !checked;
		freshView();
	}

	@Override
	public boolean isChecked() {
		return checked;
	}

	@Override
	public void setChecked(boolean checked) {
		this.checked = checked;
		freshView();
	}

	@Override
	public void setText(String text) {
		this.text = text;
		freshView();
	}
}
