package org.sharpx;

import java.awt.Toolkit;

public class BeepTask {

	public static void main(String[] args) {
		new BeepTask().execute();
	}

	public void execute(){
		Toolkit.getDefaultToolkit().beep();
	}
}
