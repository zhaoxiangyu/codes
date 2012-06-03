package org.sharp.android.ui.intf;

import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ActivityLauncher;
import org.sharp.android.ui.intf.ContentViewer;
import org.sharp.android.ui.intf.DestroySensor;
import org.sharp.android.ui.intf.ForeGroundSensor;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.android.ui.intf.PostCreator;
import org.sharp.android.ui.intf.ViewFragment;



public interface Plugin {
	ActiveSensor activeSensor();
	MenuOperation menuOperation();
	ForeGroundSensor foreGroundSensor();
	ActivityLauncher activityLauncher();
	DestroySensor destroySensor();
	PostCreator postCreator();
	ContentViewer viewer();
	ViewFragment viewFragment();
}