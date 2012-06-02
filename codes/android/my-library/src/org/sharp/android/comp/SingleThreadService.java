package org.sharp.android.comp;

import android.app.IntentService;
import android.content.Intent;

public class SingleThreadService extends IntentService {

	public SingleThreadService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
	}

}
