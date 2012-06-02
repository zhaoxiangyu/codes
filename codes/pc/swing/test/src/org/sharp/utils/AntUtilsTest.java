package org.sharp.utils;

import org.sharp.jdkex.LangUtils;
import org.sharp.jdkex.Utils;

import junit.framework.TestCase;

public class AntUtilsTest extends TestCase {
	
	public void testListFile(){
		String[] includes = AntUtils.listFiles(".", new String[]{"**/beans/*.class"},null);
	}

	public void testListDir(){
		String[] includes = AntUtils.listDirs("E:/xp/codes/projects/console/run/articles", "*.China's population");
		System.out.println(LangUtils.join(includes,","));
	}
}
