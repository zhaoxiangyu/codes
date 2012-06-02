package org.sharp.android.intf;


import org.sharp.intf.TextConsumer;

import android.os.Bundle;

public interface LogPlugin {
	TextConsumer textConsumer();
	MenuOperation menuOperation();
	void onCreate(Bundle savedInstanceState);
}