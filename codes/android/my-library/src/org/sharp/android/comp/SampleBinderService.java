package org.sharp.android.comp;

import org.sharp.android.intf.ContentViewer;

import android.os.IBinder;

public class SampleBinderService extends BinderService<ContentViewer> {

	//client side code
	private void test(IBinder ib){
		BinderService<ContentViewer>.LocalBinder lb = (BinderService<ContentViewer>.LocalBinder)ib;
		ContentViewer t = lb.service();
		//call public method of t
	}

	protected ContentViewer service(){
		return null;
	}
	
}
