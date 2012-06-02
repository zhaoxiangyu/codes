package sharpx.jni;

public class Test {
	public native String testJniCpp(int i);
	public void callBack(String echo){
		System.out.println("Test.callBack(String echo) called by C,"+echo);
	}
}
