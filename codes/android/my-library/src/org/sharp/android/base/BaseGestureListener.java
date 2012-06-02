package org.sharp.android.base;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class BaseGestureListener implements OnGestureListener {

	@Override
	public boolean onDown(MotionEvent event) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float vx,
			float vy) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent event) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx,
			float dy) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent event) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		return false;
	}

}
