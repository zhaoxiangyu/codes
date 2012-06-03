package org.sharp.android.ui;


import org.sharp.android.ui.base.BaseViewer;
import org.sharp.android.ui.intf.ContentViewer;

import my.library.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TextInputViewer extends BaseViewer {
	
	private String mName;
	private EditText et;
	
	public TextInputViewer(Context ctx,String name){
		super(ctx);
		mName = name;
	}
	
	@Override
	public View newContentView() {
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.text_input, null);
        TextView tv = (TextView)root.findViewById(R.id.name);
        tv.setText(mName);
        et = (EditText)root.findViewById(R.id.text);
        return root;
	}
	
	public String inputtedText(){
		return et.getText().toString();
	}
}
