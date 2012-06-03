package org.sharp.android.comp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BaseBroadcastReceiver extends BroadcastReceiver {

	public void bootCompleted(Context ctx, Intent intent){
		
	}
	
	public void classMatched(Context context, Intent intent){
		
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		final String action = intent.getAction();  
        if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
        	bootCompleted(context,intent);
        }if(getClass().equals(intent.getClass())){
        	classMatched(context,intent);
        }
	}

}
