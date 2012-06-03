package org.sharp.android.viewlet;

import org.sharp.android.viewlet.intf.CheckableViewlet;

import android.content.Context;

public class ViewletUtils {
	
	public static CheckableViewlet newCheckable(Context ctx, String text, boolean checked){
		//return new CheckableBCText(ctx, text, checked);
		return new CheckableBGCText(ctx, text, checked);
	}

}
