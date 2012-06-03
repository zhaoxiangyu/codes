package org.sharp.android.viewlet;

import org.sharp.android.viewlet.intf.CheckableViewlet;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class CheckableBGCText implements CheckableViewlet {

	TextView tv;
	boolean checked;
	String text;
	int checkedBGC;
	int uncheckedBGC;
	
	public CheckableBGCText(Context ctx,String text, boolean checked){
		//this(ctx,text,Color.GRAY,Color.argb(0xff, 0x11, 0x11, 0x11), checked);
		this(ctx,text,Color.BLUE, Color.argb(0xff, 0x00, 0x00, 0x77)
				/*Color.BLACK*/, checked);
	}
	
	private CheckableBGCText(Context ctx,String text, int checkedBGC, 
			int uncheckedBGC, boolean checked){
		tv = new TextView(ctx);
		this.text = text;
		this.checked = checked;
		this.checkedBGC = checkedBGC;
		this.uncheckedBGC = uncheckedBGC;
		freshView();
	}
	
	@Override
	public View getView(){
		return tv;
	}
	
	private void freshView(){
		tv.setGravity(Gravity.CENTER);
		tv.setText(text);
		if(checked){
			tv.setBackgroundColor(checkedBGC);
		}else{
			tv.setBackgroundColor(uncheckedBGC);
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
