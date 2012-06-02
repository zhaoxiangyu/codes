package org.sharp.cloud.aws;

import junit.framework.TestCase;

import org.sharp.utils.SdbUtils;

public class SDBUtilsTest extends TestCase {

	public void testDomain(){
		SdbUtils.init();
		SdbUtils.queryDomain("config");
	}
}
