package org.sharp.android.ui.base;

import org.sharp.android.ui.intf.ContentViewer;
import org.sharp.android.viewlet.intf.HintsDisplayer;
import org.sharp.android.viewlet.intf.HintsSource;

import android.content.Context;
import android.view.View;

public abstract class BaseViewer implements ContentViewer {

	protected Context ctx;
	
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

	protected void freshView(){
	}

	/*@Override
	public HintsSource hintsSource() {
		return null;
	}*/
}
