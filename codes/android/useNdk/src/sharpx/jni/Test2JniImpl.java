package sharpx.jni;

public class Test2JniImpl implements Test2Intf {

	@Override
	public native void haha();

	@Override
	public native String abc(int i);

}
