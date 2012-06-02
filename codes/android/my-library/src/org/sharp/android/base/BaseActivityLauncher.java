package org.sharp.android.base;

import org.sharp.android.intf.ActivityLauncher;
import org.sharp.android.intf.ActivityLauncher.ResultHandler;
import org.sharp.android.view.PluggableUI;
import org.sharp.utils.Utils;

import android.content.Intent;

public class BaseActivityLauncher implements ActivityLauncher {
	private int mRequestCode = Utils.uniqueInt();
	private ResultHandler mRh;

	@Override
	public void startActivityForResult(PluggableUI acti, Intent intent,
			ResultHandler rh) {
		acti.startActivityForResult(intent, mRequestCode);
		mRh = rh;
	}

	@Override
	public int requestCode() {
		return mRequestCode;
	}

	@Override
	public ResultHandler resultHandler() {
		return mRh;
	}
}
