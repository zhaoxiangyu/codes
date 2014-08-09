package org.sharpx.swing.intf;

public interface AppContext {

	String dataDirPath();
	
	<T>T getConfig(String name,Class<T> clazz);
	
	<T> void saveConfig(String name, T config);

	<T>T requestObject(Class<T> clazz);
	
	void shareObject(Object service);
}
