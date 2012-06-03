package org.sharp.android.viewlet;

import org.sharp.android.viewlet.intf.CheckableViewlet;
import org.sharp.android.widget.BorderTextView;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class CheckableBCText implements CheckableViewlet {
	final static int DEF_BORDER_WIDTH = 1;
	
	BorderTextView tv;
	/*FrameLayout fl;*/
	boolean checked;
	String text;
	int borderColor;
	int borderWidth;
	/*private LayoutParams lParams;*/
	
	public CheckableBCText(Context ctx,String text, boolean checked){
		this(ctx, text, Color.BLUE, DEF_BORDER_WIDTH, checked);
		//this(ctx, text, Color.argb(0xff, 0x77, 0x77, 0x77), DEF_BORDER_WIDTH, checked);
	}
	
	private CheckableBCText(Context ctx,String text, int borderColor, 
			int borderWidth, boolean checked){
		tv = new BorderTextView(ctx, borderColor, borderWidth);
		/*fl = new FrameLayout(ctx);
		fl.setBackgroundColor(borderColor);
		lParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 
			ViewGroup.LayoutParams.WRAP_CONTENT, 
			Gravity.CENTER);*/

		this.text = text;
		this.checked = checked;
		this.borderWidth = borderWidth;
		this.borderColor = borderColor;
		
		freshView();
	}
	
	@Override
	public View getView(){
		return tv;
	}
	
	private void freshView(){
		/*fl.removeAllViews();*/
		
		tv.setText(text);
		if(checked)
			tv.setBorderColor(borderColor);
		else
			tv.setBorderWidth(0);
		tv.invalidate();
		/*if(checked){
			lParams.setMargins(borderWidth, borderWidth, borderWidth, borderWidth);
		}else{
			lParams.setMargins(0, 0, 0, 0);
		}
		fl.addView(tv, lParams);
		fl.requestLayout();*/
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
