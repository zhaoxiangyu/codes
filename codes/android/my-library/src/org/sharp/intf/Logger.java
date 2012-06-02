package org.sharp.intf;

public interface Logger {
	void log(String msg, Throwable e);
	void log(String msg);
}
