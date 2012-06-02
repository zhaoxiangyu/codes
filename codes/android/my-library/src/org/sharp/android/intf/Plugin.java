package org.sharp.android.intf;



public interface Plugin {
	ActiveSensor activeSensor();
	MenuOperation menuOperation();
	ForeGroundSensor foreGroundSensor();
	ActivityLauncher activityLauncher();
	DestroySensor destroySensor();
	PostCreator postCreator();
}