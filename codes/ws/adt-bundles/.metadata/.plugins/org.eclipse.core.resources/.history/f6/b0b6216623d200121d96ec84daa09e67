package org.sharp.pm.comp;

import java.lang.reflect.ParameterizedType;

import org.sharp.android.autils.AUtils;
import org.sharp.android.comp.BootBroadcastReceiver;

import android.app.Service;
import android.content.Context;

public class ServiceAutoBooter<T extends Service> extends BootBroadcastReceiver {

	@SuppressWarnings("unchecked")
	@Override
	public void bootCompleted(Context ctx) {
    	AUtils.startService(ctx, 
    			(Class<T>)((ParameterizedType)getClass().getGenericSuperclass())
    					.getActualTypeArguments()[0]);
	}
	
}
