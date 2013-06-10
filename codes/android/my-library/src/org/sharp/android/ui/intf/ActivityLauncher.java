package org.sharp.android.ui.intf;

import org.sharp.android.ui.PluggableUI;
import org.sharp.android.ui.intf.ActivityLauncher.ResultHandler;

import android.content.Intent;

public interface ActivityLauncher {

	public interface ResultHandler {

		void onActivityResult(int resultCode, Intent data);

	}
	void onActivityResult(int resultCode,Intent data);
	void startActivityForResult(PluggableUI acti,Intent intent);
	int requestCode();
	ResultHandler resultHandler();
}