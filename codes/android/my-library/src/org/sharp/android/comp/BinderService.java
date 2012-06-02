package org.sharp.android.comp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public abstract class BinderService<T> extends Service {

	abstract protected T service();
	
	//client side code
	private void test(IBinder ib){
		LocalBinder lb = (LocalBinder)ib;
		T t = lb.service();
		//call public method of t
	}
	
	public class LocalBinder extends Binder {
		public T service(){
			return BinderService.this.service();
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return new LocalBinder();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

}
