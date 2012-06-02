package org.sharp.console.jni;

public class Win32 {
	static {
		System.loadLibrary("build/c/org_sharp_jni_Win32");
	}
	public static native String sayHello(String s);
	public static native String lnkTarget(String lnk);
	
	public static void main(String[] argv){
		System.out.println(sayHello("Hello World2."));
	}
}
