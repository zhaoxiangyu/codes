package org.sharp.android.comp;

import java.util.HashMap;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;

public class ThreadPerStarterService extends Service {

	Map<Integer,HandlerThread> threads;
	
	@Override
	public void onCreate() {
		threads = new HashMap<Integer,HandlerThread>();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		HandlerThread ht = threads.get(startId);
		if(ht == null){
			ht = new HandlerThread("THREAD[startId="+startId+"]", 
					android.os.Process.THREAD_PRIORITY_BACKGROUND);
			threads.put(startId, ht);
		}
		Message msg = null;
		new Handler(ht.getLooper()){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
			}
		}.sendMessage(msg);
			
		return START_STICKY; 
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
