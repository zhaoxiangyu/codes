package org.sharp.android.comp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class BootBroadcastReceiver extends BroadcastReceiver {

	public abstract void bootCompleted(Context ctx);
	
	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();  
        if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
        	bootCompleted(context);
        }
	}

}
