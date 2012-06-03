package sharpx.jni.ubuntu;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test2Intf t2i = new Test2JniImpl();
		t2i.haha();
		t2i.abc(1);
	}

}
