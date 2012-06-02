package org.sharp.intf;

public interface PMessage {
	int WHAT_START = 0;
	int WHAT_UPDATE_PROGRESS = 1;
	int WHAT_COMPLETE = 2;
	int WHAT_ERROR = -1;
	int WHAT_CANCELED = 3;
}
