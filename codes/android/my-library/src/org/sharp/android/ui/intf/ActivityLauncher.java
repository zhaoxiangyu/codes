package org.sharp.android.ui.intf;

import org.sharp.android.ui.PluggableUI;

import android.content.Intent;

public interface ActivityLauncher {

	void onActivityResult(int resultCode,Intent data);
	void startActivityForResult(PluggableUI acti,Intent intent);
	int requestCode();
}