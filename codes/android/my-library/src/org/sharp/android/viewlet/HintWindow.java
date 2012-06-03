package org.sharp.android.viewlet;

import org.sharp.android.viewlet.intf.FloatWindowViewlet;

import android.view.View;

public class HintWindow implements FloatWindowViewlet {
	
	public HintWindow(View view, String hintText){
		
	}

	@Override
	public int xOffset() {
		return 0;
	}

	@Override
	public int yOffset() {
		return 0;
	}

	@Override
	public View getFloatWindowView() {
		return null;
	}

}
