package org.sharp.android.test;

import org.sharp.android.autils.HttpUtils;
import org.sharp.intf.Response;

import android.test.AndroidTestCase;

public class HttpUitlsTest extends AndroidTestCase {

	public void testGet() {
		Response rs = HttpUtils.get("http://wd4web.sinaapp.com/new-appuse.php?appname=abc&userid=abcuser");
		assertEquals(Response.StatusCode.NORMAL, rs.statusCode());
	}
}
