package org.sharp.android.intf;

import org.sharp.android.view.PluggableUI;

import android.content.Intent;

public interface ActivityLauncher {
	public static interface ResultHandler {
		void onActivityResult(int resultCode,Intent data);
	}
	
	int requestCode(); 
	ResultHandler resultHandler();
	void startActivityForResult(PluggableUI acti,Intent intent,ActivityLauncher.ResultHandler rh);
}