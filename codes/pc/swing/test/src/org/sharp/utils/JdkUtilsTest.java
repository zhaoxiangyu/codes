package org.sharp.utils;

import junit.framework.TestCase;

import org.sharp.jdkex.JdkUtils.ArrayWrapper;
import org.sharp.jdkex.JdkUtils.KeyValue;

public class JdkUtilsTest extends TestCase {

	public void testGeneric(){
		ArrayWrapper<String> w = new ArrayWrapper<String>();
		w.setObject(new String[]{"hello","word"});
		System.out.println(w.getObject().getClass());
	}

	public void testGeneric2(){
		KeyValue<String,String> w = new KeyValue<String,String>("hellow","world");
		System.out.println(w.getKey().getClass());
	}

	public void testGeneric3(){
		KeyValue<Object,String> w = new KeyValue<Object,String>("hellow","world");
		System.out.println(w.getKey().getClass());
	}

	public void testGeneric4(){
		KeyValue<Object,String> w = new KeyValue<Object,String>((Object)"hellow","world");
		System.out.println(w.getKey().getClass());
	}
}
