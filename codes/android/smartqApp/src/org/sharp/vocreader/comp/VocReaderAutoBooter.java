package org.sharp.vocreader.comp;

import org.sharp.android.autils.AUtils;
import org.sharp.android.comp.BaseBroadcastReceiver;

import android.content.Context;
import android.content.Intent;

public class VocReaderAutoBooter extends BaseBroadcastReceiver {

	@Override
	public void bootCompleted(Context ctx, Intent intent) {
    	AUtils.startActivity(ctx, VocReaderActi.class);
	}

}
