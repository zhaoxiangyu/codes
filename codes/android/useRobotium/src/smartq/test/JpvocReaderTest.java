package smartq.test;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

@SuppressWarnings("unchecked")
public class JpvocReaderTest extends ActivityInstrumentationTestCase2 {

	private static final String TARGET_PACKAGE_ID = "sharpx.vocreader";
	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "org.sharp.vocreader.view.VocReaderActi";

	private static Class<?> launcherActivityClass;
	static {
		try {
			launcherActivityClass = Class
					.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public JpvocReaderTest() throws ClassNotFoundException {
		super(TARGET_PACKAGE_ID, launcherActivityClass);
	}

	private Solo solo;
	private int playPrevious = 1;
	private int playRepeat = 3;
	private int playNext = 5;
	private Instrumentation mInstr;
	private Activity mActivity;
	
	
	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		mInstr = getInstrumentation();
		mActivity = getActivity();
		assertTrue(solo != null);
		solo.waitForText("0");
		solo.assertCurrentActivity("current activity not org.sharp.vocreader.view.VocReaderActi", "VocReaderActi");
	}

	public void testOpenCourse() {
		solo.sendKey(Solo.MENU);
		solo.clickOnText(solo.getString(sharpx.vocreader.R.string.menu_choose_course));
		assertTrue(solo.waitForText(solo.getString(sharpx.vocreader.R.string.dlg_title_choose_course)));
		boolean downTextFound = solo.searchText(solo.getString(sharpx.vocreader.R.string.dlg_msg_open_course));
		boolean more = false;
		do{
			if(downTextFound){
				solo.clickOnText(solo.getString(sharpx.vocreader.R.string.dlg_msg_open_course));
				break;
			}else{
				more = solo.scrollUp();
				if(!more){
					assertTrue("scroll to list beginning, no course can be open.", false);
					return;
				}else
					downTextFound = solo.searchText(solo.getString(sharpx.vocreader.R.string.dlg_msg_open_course));
			}
		}while(true);
		solo.waitForDialogToClose(5);
		solo.clickOnImageButton(playRepeat);
	}

	public void testDownCourse(){
		solo.sendKey(Solo.MENU);
		solo.clickOnText(solo.getString(sharpx.vocreader.R.string.menu_choose_course));
		assertTrue(solo.waitForText(solo.getString(sharpx.vocreader.R.string.dlg_title_choose_course)));
		boolean downTextFound = solo.searchText(solo.getString(sharpx.vocreader.R.string.dlg_msg_download_course));
		boolean more = false;
		do{
			if(downTextFound){
				solo.clickOnText(solo.getString(sharpx.vocreader.R.string.dlg_msg_download_course));
				break;
			}else{
				more = solo.scrollDown();
				if(!more){
					assertTrue("scroll to list end, no course can be downlaod.", false);
					return;
				}else
					downTextFound = solo.searchText(solo.getString(sharpx.vocreader.R.string.dlg_msg_download_course));
			}
		}while(true);
		solo.waitForDialogToClose(5);
		assertTrue(solo.waitForText(solo.getString(sharpx.vocreader.R.string.dlg_title_downloading)));
		//assertTrue(solo.waitForText(solo.getString(sharpx.vocreader.R.string.dlg_title_insufficient_points)));
		//assertTrue(solo.waitForText(solo.getString(sharpx.vocreader.R.string.dlg_title_unzipping)));
	}
	
	public void testPlaySound() {
		solo.clickOnImageButton(playNext);
		solo.clickOnImageButton(playPrevious);
	}
	
	public void testAnt(){
		
	}
	
	/*public void testAppOffer(){
		solo.sendKey(Solo.MENU);
		solo.clickOnText(solo.getString(sharpx.vocreader.R.string.menu_earn_points));
		assertTrue(solo.waitForText("下载应用获取积分"));
		solo.goBack();
	}*/
	
	@Override
	public void tearDown() throws Exception {

		try {
			solo.finalize();
		} catch (Throwable e) {

			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();

	}

}