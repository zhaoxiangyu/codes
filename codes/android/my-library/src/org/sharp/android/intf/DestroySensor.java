package org.sharp.android.intf;

import android.os.Bundle;


public interface DestroySensor {
	void onCreate(Bundle savedInstanceState);
	void onDestroy(boolean isFinished);
}