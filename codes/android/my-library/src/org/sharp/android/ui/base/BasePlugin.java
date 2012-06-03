package org.sharp.android.ui.base;

import org.sharp.android.ui.intf.ActiveSensor;
import org.sharp.android.ui.intf.ActivityLauncher;
import org.sharp.android.ui.intf.ContentViewer;
import org.sharp.android.ui.intf.DestroySensor;
import org.sharp.android.ui.intf.ForeGroundSensor;
import org.sharp.android.ui.intf.MenuOperation;
import org.sharp.android.ui.intf.Plugin;
import org.sharp.android.ui.intf.PostCreator;
import org.sharp.android.ui.intf.ViewFragment;

public class BasePlugin implements Plugin {

	@Override
	public ActiveSensor activeSensor() {
		return null;
	}

	@Override
	public MenuOperation menuOperation() {
		return null;
	}

	@Override
	public ForeGroundSensor foreGroundSensor() {
		return null;
	}

	@Override
	public ActivityLauncher activityLauncher() {
		return null;
	}

	@Override
	public DestroySensor destroySensor() {
		return null;
	}

	@Override
	public PostCreator postCreator() {
		return null;
	}

	@Override
	public ContentViewer viewer() {
		return null;
	}

	@Override
	public ViewFragment viewFragment() {
		return null;
	}

}
