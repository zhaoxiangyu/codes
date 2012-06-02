package org.sharp.android.base;

import org.sharp.android.intf.ActivityLauncher;
import org.sharp.android.intf.ActiveSensor;
import org.sharp.android.intf.DestroySensor;
import org.sharp.android.intf.ForeGroundSensor;
import org.sharp.android.intf.MenuOperation;
import org.sharp.android.intf.Plugin;
import org.sharp.android.intf.PostCreator;

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

}
