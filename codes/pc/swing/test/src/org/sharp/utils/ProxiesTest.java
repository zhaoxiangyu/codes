package org.sharp.utils;

import junit.framework.TestCase;

public class ProxiesTest extends TestCase {

	public void testExceptionLogProxy(){
		Example ex = (Example)Proxies.exceptionLogProxy(new Example());
		ex.a(77);
	}
	
	public void testExceptionLogProxy2(){
		Example ex = (Example)Proxies.exceptionLogProxy(new Example());
		ex.a2(77,55.3);
	}
	
	static class Example {
		int a1 = 1;
		int a2 = 2;
		public Example(){
		}
		
		public int a(int pa){
			throw new NullPointerException();
		}
		public int a2(int pa,double p2){
			throw new NullPointerException();
		}
	}
}
