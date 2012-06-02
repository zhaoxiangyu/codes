package org.sharp.pm.comp;

import org.sharp.android.autils.AUtils;
import org.sharp.android.comp.BootBroadcastReceiver;
import org.sharp.vocreader.view.VocReaderActi;

import android.content.Context;

public class VocReaderAutoBooter extends BootBroadcastReceiver {

	@Override
	public void bootCompleted(Context ctx) {
    	AUtils.startActivity(ctx, VocReaderActi.class);
	}

}
