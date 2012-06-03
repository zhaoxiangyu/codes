package sharpx.jni.ubuntu;

public class Test2JniImpl implements Test2Intf {

    static {
        System.loadLibrary("jnilearn");
    }

    native static void staticHaha();
	
	private String who = "jni";
	
	private String[] subjects = new String[]{"english","japanese","chinese"};
	int whichSub = 0;
	
	@Override
	public native void haha();

	private void hahaCallback() {
		who = "java";
		haha();
		System.out.println("javaCode finished.");
	}
	
	@Override
	public native String abc(int whichSubject);

	private void abcCallback(final String subject) {
		callBackJ(new Test2CallBackIntf() {
			@Override
			public void callBack() {
				System.out.println(subject+"["+ whichSub +"]:"+subject+" 's level is abc.");
			}
		});
	}

	private native void callBackJ(Test2CallBackIntf t2cbi);

}
