package org.sharp.utils;

import org.sharp.jdkex.SecurityUtils;

import junit.framework.TestCase;

public class SecurityUtilsTest extends TestCase {

	public void testGenKey() {
		String key = SecurityUtils.genKey("192.168.1.100");
		System.out.println(key);
	}

	public void testGenKeyHeWin7() {
		String key = SecurityUtils.genKey("002564861ffd");
		System.out.println(key);//a31ff6055198bf311413c4691840c13b
	}

	public void testRegisterNumber() {
		String rn = SecurityUtils.registerNumber();
		System.out.println(rn);
	}

	public void testCheckKey() {
		String key = SecurityUtils.genKey("192.168.1.100");
		assertFalse(SecurityUtils.checkKey("192.158.33.1", key));
		assertTrue(SecurityUtils.checkKey("192.168.1.100", key));
	}

}
