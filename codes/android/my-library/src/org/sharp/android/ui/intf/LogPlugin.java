package org.sharp.android.ui.intf;


import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.intf.TextConsumer;

import android.os.Bundle;

public interface LogPlugin {
	TextConsumer textConsumer();
	MenuOperation menuOperation();
	void onCreate(Bundle savedInstanceState);
}