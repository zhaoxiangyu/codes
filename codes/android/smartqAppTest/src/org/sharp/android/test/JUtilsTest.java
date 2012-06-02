package org.sharp.android.test;

import org.sharp.android.autils.AUtils;
import org.sharp.vocreader.beans.AudioInfo;

import android.test.AndroidTestCase;
import android.util.Log;

public class JUtilsTest extends AndroidTestCase {

	public void testStringArray(){
		AudioInfo ai1 = new AudioInfo();
		ai1.courseNo = 1;
		ai1.level = 1;
		ai1.mp3Path = "course1/xx.mp3";
		ai1.name = "xx";
		ai1.unitNo = 1;

		AudioInfo ai2 = new AudioInfo();
		ai2.courseNo = 2;
		ai2.level = 2;
		ai2.mp3Path = "course1/xx2.mp3";
		ai2.name = "xx2";
		ai2.unitNo = 2;
		
		String str = AUtils.toString(new AudioInfo[]{ai1,ai2});
		Log.d("test", "str:"+str);
		AudioInfo[] aia = (AudioInfo[])AUtils.fromString(str, new AudioInfo[0].getClass());
		assertEquals(aia.length, 2);
		assertEquals(aia[1].name,ai2.name);
		assertEquals(aia[1].courseNo,ai2.courseNo);
		assertEquals(aia[1].level,ai2.level);
	}

	public void testStringBean(){
		AudioInfo ai1 = new AudioInfo();
		ai1.courseNo = 1;
		ai1.level = 1;
		ai1.mp3Path = "course1/xx.mp3";
		ai1.name = "xx";
		ai1.unitNo = 1;

		String str = AUtils.toString(ai1);
		Log.d("test", "str:"+str);
		AudioInfo ai = (AudioInfo)AUtils.fromString(str, AudioInfo.class);
		assertEquals(ai1.name, ai.name);
	}
}
