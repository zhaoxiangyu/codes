package org.sharpx.utils.jdkex;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JdkUtils {

	public static boolean regExMatched(String text, String regex){
		Pattern patt = Pattern.compile(regex);
		Matcher matcher = patt.matcher(text);
		return matcher.matches();
	}

	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static class KeyValue<K,V> {
		K key;
		V value;
		
		public KeyValue(K key,V value){
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
		public void setKey(K key) {
			this.key = key;
		}
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	public static class ArrayWrapper<O> {
		O[] object;

		public O[] getObject() {
			return object;
		}

		public void setObject(O[] object) {
			this.object = object;
		}
	}

	public static void shutdownPC() throws IOException {
		String command;
		String osName = System.getProperty("os.name");
	
		if (osName.startsWith("Win")) {
		  command = "shutdown.exe -s -t 0";
		} else if (osName.startsWith("Linux") || osName.startsWith("Mac")) {
		  command = "shutdown -h now";
		} else {
			throw new RuntimeException("Unsupported operating system.");
		}
	
		Runtime.getRuntime().exec(command);
		System.exit(0);
	}
	
	public static Reader getResourceAsReader(String path){
		return new InputStreamReader(JdkUtils.class.getClassLoader().
				getResourceAsStream(path));
	}

}
