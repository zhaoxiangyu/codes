package org.sharp.android.ui.base;

import org.sharp.android.ui.PluggableUI;
import org.sharp.android.ui.intf.ActivityLauncher;
import org.sharp.utils.Utils;

import android.content.Intent;

public class BaseActivityLauncher implements ActivityLauncher {
	private int mRequestCode = Utils.uniqueInt();
	@Override
	public void startActivityForResult(PluggableUI acti, Intent intent) {
		acti.startActivityForResult(intent, mRequestCode);
	}
	
	@Override
	public void onActivityResult(int resultCode, Intent data) {
	}

	@Override
	public final int requestCode() {
		return mRequestCode;
	}

}
