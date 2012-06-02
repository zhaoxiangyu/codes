package org.sharp.android.comp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;

public class ForeGroundService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY; 
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
