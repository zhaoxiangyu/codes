package org.sharp.android.widget;

import my.library.R;

import org.sharp.android.view.ViewUtils;
import org.sharp.beans.TabItem;
import org.sharp.beans.TabItem2;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path.FillType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.ToggleButton;

@SuppressWarnings("unused")
public class WidgetUtils {

	public static TabHost newTabHost(Context ctx, LocalActivityManager lam, TabItem[] tabItems){
		TabHost tabHost = newTabHost(ctx);
		tabHost.setup(lam);
		
		for(TabItem ti:tabItems){
			ViewUtils.populateTabItem2(tabHost, ctx, ti);
		}
		return tabHost;
	}

	private static TabHost newTabHost(Context ctx){
		TabHost tabHost = new TabHost(ctx);
		LinearLayout ll = new LinearLayout(ctx);
		TabWidget tw = new TabWidget(ctx);
		tw.setId(android.R.id.tabs);
		FrameLayout fl = new FrameLayout(ctx);
		fl.setId(android.R.id.tabcontent);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.addView(fl,
				new LayoutParams(
						android.widget.LinearLayout.LayoutParams.FILL_PARENT, 
						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		ll.addView(tw,
				new LayoutParams(
						android.widget.LinearLayout.LayoutParams.FILL_PARENT, 
						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		tabHost.addView(ll);
		tabHost.setup();

		return tabHost;
	}

	public static TabHost newTabHost(Context ctx,TabItem2[] tabItems){
		TabHost tabHost = newTabHost(ctx);

		for(TabItem2 ti:tabItems){
			ViewUtils.populateTabItem(tabHost, ctx, ti);
		}
		return tabHost;
	}
	
	public static ViewGroup newTabItem(Context ctx, int imageResId, String text){
		ViewGroup vg = new LinearLayout(ctx);
		vg.addView(newImageView(ctx,imageResId));
		vg.addView(newTextView(ctx, text));
		return vg;
	}
	
	public static ImageView newImageView(Context ctx, int imageResId){
		ImageView iv = new ImageView(ctx);
		iv.setImageResource(imageResId);
		return iv;
	}
	
	public static GridView newSingleLineGridView(final Context ctx,final String[] labels, final ClickListener cl) {
		final GridView gv = new GridView(ctx);
		gv.setGravity(Gravity.CENTER);
		gv.setNumColumns(labels.length);
		gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gv.setAdapter(new ArrayAdapter<String>(ctx, 0, labels){
			@Override
			public View getView(final int position, View convertView, ViewGroup parent){
				Button btn =  WidgetUtils.newButton(ctx, labels[position],
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								gv.setSelection(position);
								if(cl != null)
									cl.OnClick(v, position);
							}
						},
						R.drawable.button_bg);
				return btn;
			}
		});
		return gv;
	}
	
	public static TextView newTextViewBGCClickable(Context ctx, 
			String text,
			int color, 
			OnClickListener listener){
		//TODO HELONG
		return null;
	}
	
	public static TextView newTextView(Context ctx, String text){
		TextView tv = new TextView(ctx);
		tv.setText(text);
		return tv;
	}
	
	public static TextView newTextView(Context ctx, String text, int gravity){
		TextView tv = new TextView(ctx);
		tv.setText(text);
		return tv;
	}
	
	public static Button newButton(Context ctx, String label, OnClickListener ocl,
			int bgDrawableResId){
		Button btn = new Button(ctx);
		btn.setText(label);
		btn.setOnClickListener(ocl);
		Resources res = ctx.getResources();
		btn.setBackgroundDrawable(res.getDrawable(bgDrawableResId));
		return btn;
	}
	
	public static Button newButton(Context ctx, String label, OnClickListener ocl){
		Button btn = new Button(ctx);
		btn.setText(label);
		btn.setOnClickListener(ocl);
		return btn;
	}
	
	public static ToggleButton newToggleButton(Context ctx, 
			String textOn, String textOff, OnClickListener ocl,
			boolean checked){
		ToggleButton btn = new ToggleButton(ctx);
		btn.setTextOn(textOn);
		btn.setTextOff(textOff);
		if(ocl!=null)
			btn.setOnClickListener(ocl);
		btn.setChecked(checked);
		return btn;
	}
	
	public static RadioGroup newRadioGroup(Context ctx, String[] labels, OnCheckedChangeListener occl){
		RadioGroup rg = new RadioGroup(ctx);
		rg.setOrientation(LinearLayout.HORIZONTAL);
		for(int i=0;i<labels.length;i++){
			String label = labels[i];
			RadioButton rb = newRadioButton(ctx, label, null);
			rb.setId(i);
			rg.addView(rb);
		}
		if(occl != null)
			rg.setOnCheckedChangeListener(occl);
		return rg;
	}
	
	public static RadioButton newRadioButton(Context ctx, String label, OnClickListener ocl){
		RadioButton rb = new RadioButton(ctx);
		rb.setText(label);
		if(ocl != null)
			rb.setOnClickListener(ocl);
		return rb;
	}

	public static LinearLayout newLinearLayout(Context mCtx, boolean b,
			View[] views) {
		// TODO HELONG
		return null;
	}

	public static void attachColorFrame(Context mCtx, View value, int i,
			int yellow) {
		// TODO HELONG
		
	}

	public static void showHintView(Context mCtx, View value, String adhesion) {
		// TODO HELONG
		
	}

	public static void detachColorFrame(Context mCtx, View value) {
		// TODO HELONG
		
	}

	public static void hideHintView(Context mCtx, View value) {
		// TODO HELONG
		
	}

	public static void setup(SingleLineGridView singleLineRadioTexts,
			int center, int length, int stretchColumnWidth) {
		// TODO HELONG
		
	}
	
}
