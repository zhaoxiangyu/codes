package org.sharp.pm.comp;

import java.lang.reflect.ParameterizedType;

import org.sharp.android.autils.AUtils;
import org.sharp.android.comp.BaseBroadcastReceiver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

public class ServiceAutoBooter<T extends Service> extends BaseBroadcastReceiver {

	@SuppressWarnings("unchecked")
	@Override
	public void bootCompleted(Context ctx, Intent intent) {
    	AUtils.startService(ctx, 
    			(Class<T>)((ParameterizedType)getClass().getGenericSuperclass())
    					.getActualTypeArguments()[0]);
	}
	
}
