package org.sharp.android.view;

import org.sharp.android.ui.intf.ContentViewer;

import android.content.Context;
import android.view.View;

public abstract class BaseViewer implements ContentViewer {

	private Context ctx;
	
	public BaseViewer(Context ctx){
		this.ctx = ctx;
	}
	
	View contentView;
	
	@Override
	public View contentView(){
		if(contentView!=null){
			freshView();
			return contentView;
		}else{
			return newContentView();
		}
		
	}
	
	abstract protected View newContentView();

	abstract protected void freshView();

}
