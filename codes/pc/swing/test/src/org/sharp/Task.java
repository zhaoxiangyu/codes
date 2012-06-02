package org.sharp;

import junit.framework.TestCase;

import org.sharp.utils.FsUtils;

public class Task extends TestCase {

	public void testJpwordPack() throws Exception{
		FsUtils.packJpword("f:/win7/jpwords", "f:/win7/jpwords/zips", 2, null);
	}

	public void testJpwordUnPack() throws Exception{
		FsUtils.unpackJpword("Y:/win7/jpwords/zips", "Y:/win7/jpwords/zips/un", null);
	}
	
	public void testGoogleCodeUpload() throws Exception{
		FsUtils.uploadJpword("F:/win7/jpwords/zips", "*.zip", "LongM.He", "Gy8wh8ZQ2xM5", "web-word-list");
	}
}
