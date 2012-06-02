package org.sharp.android.autils;

import org.sharp.vocreader.view.VocReaderActi;

import android.content.Context;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class HWInfoTest extends ActivityInstrumentationTestCase2<VocReaderActi> {

	private VocReaderActi mActivity;

	public HWInfoTest() {
	    super("sharpx.vocreader", VocReaderActi.class);
	}
	
	public HWInfoTest(String pkg, Class<VocReaderActi> activityClass) {
		super(pkg, activityClass);
	}

	@Override
	protected void setUp() throws Exception {
	    super.setUp();
	    setActivityInitialTouchMode(false);
	    mActivity = getActivity();
	  }
	
	public void testStorageInfo() {
		String storageInfo = HWInfo.storageInfo((Context)mActivity);
		Log.d("test","storageInfo:\n"+storageInfo);
	}

	public void testStorageSpaceInfo() {
		String path = Environment.getDataDirectory().getPath();
		String spaceInfo = HWInfo.storageSpaceInfo(path);
		Log.d("test",spaceInfo);
	}

	public void testStorageSpaceInfo2() {
		String path = Environment.getRootDirectory().getPath();
		String spaceInfo = HWInfo.storageSpaceInfo(path);
		Log.d("test",spaceInfo);
	}

	public void testStorageSpaceInfo3() {
		String path = Environment.getExternalStorageDirectory().getPath();
		String spaceInfo = HWInfo.storageSpaceInfo(path);
		Log.d("test",spaceInfo);
	}
}
