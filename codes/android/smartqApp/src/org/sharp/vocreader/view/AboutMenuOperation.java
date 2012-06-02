package org.sharp.vocreader.view;

import org.sharp.android.base.BaseMenuOperation;
import org.sharp.android.view.ViewUtils;

import android.content.Context;

public class AboutMenuOperation extends BaseMenuOperation {
	public AboutMenuOperation(Context ctx){
		super(ctx, sharpx.vocreader.R.string.menu_about);
	}
	
	@Override
	public boolean menuItemSelected() {
		ViewUtils.showCustomInfoDialog(mCtx, 
				mCtx.getString(sharpx.vocreader.R.string.dlg_title_about), 
				org.sharp.vocreader.view.ViewUtils.aboutViewer(mCtx).contentView());
		return true;
	}
	
}
