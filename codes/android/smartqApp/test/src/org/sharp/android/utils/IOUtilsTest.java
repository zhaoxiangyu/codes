package org.sharp.android.utils;

import java.io.File;
import java.io.IOException;

import org.sharp.utils.IOUtils;
import org.sharp.utils.Utils;

import junit.framework.TestCase;

public class IOUtilsTest extends TestCase {
	public void testRelativePath() {
		String rp = IOUtils.relativePath("/sdcard/1st/jp/jpword", "/sdcard");
		assertEquals("1st/jp/jpword", rp);
	}

	public void testFullPath() {
		String fp = IOUtils.fullPath("/sdcard", "1st/jp/jpword");
		assertEquals("/sdcard/1st/jp/jpword", fp);
	}

}
