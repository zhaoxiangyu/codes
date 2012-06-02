package org.sharp.android.autils;

import org.sharp.vocreader.view.VocReaderActi;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageButton;

public class JpwordVocreaderTest extends
		ActivityInstrumentationTestCase2<VocReaderActi> {

	private VocReaderActi mActivity;
	ImageButton playRepeat;
	Instrumentation mInstr = getInstrumentation();
	
	public JpwordVocreaderTest() {
		super("sharpx.vocreader", VocReaderActi.class);
	}

	public JpwordVocreaderTest(String pkg, Class<VocReaderActi> activityClass) {
		super(pkg, activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		mActivity = getActivity();
		playRepeat = (ImageButton)mActivity.findViewById(sharpx.vocreader.R.id.play_repeat);
	}

	public void testPreConditions() {
		assertTrue(mActivity != null);
		assertTrue(playRepeat != null);
	} // end of testPreConditions() method definition

	public void testPlaySound() {
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
			}
		});
		TouchUtils.clickView(this, playRepeat);
	}
	
	public void testAppOffer(){
		sendKeys(KeyEvent.KEYCODE_MENU);
		sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
	}
	
	public void testState(){
		mInstr.callActivityOnPause(mActivity);
		mInstr.callActivityOnResume(mActivity);
	}
	
	public void testVolumnRestoreAfterDestroy(){
		sendKeys(KeyEvent.KEYCODE_VOLUME_UP);
		// equivalent to restart activity
		mActivity.finish();
		mActivity = getActivity();
	}
}
