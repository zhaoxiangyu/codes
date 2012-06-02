package org.sharp.android.autils;

import android.test.AndroidTestCase;
import android.util.Log;

public class APhoneUtilsTest extends AndroidTestCase {

	public void testMdn(){
		String mdn = APhoneUtils.fetchMdn(getContext());
		Log.d("test", "mdn:"+mdn);
	}

	public void testImei(){
		String imei = APhoneUtils.fetchImei(getContext());
		Log.d("test", "imei:"+imei);
	}

	public void testImsi(){
		String imsi = APhoneUtils.fetchImsi(getContext());
		Log.d("test", "imsi:"+imsi);
	}
}
