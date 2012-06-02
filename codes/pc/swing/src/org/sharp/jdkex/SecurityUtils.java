package org.sharp.jdkex;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import org.apache.commons.codec.binary.Hex;

public class SecurityUtils {
	
	public static String registerNumber(){
		try {
			byte[] ha = NetworkInterface.getByInetAddress(
					InetAddress.getLocalHost()).getHardwareAddress();
			String strHA = new String(Hex.encodeHex(ha));
			/*String localIp = InetAddress.getLocalHost().getHostAddress();*/
			return strHA;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String genKey(String registerNumber){
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(registerNumber.getBytes());
			byte[] key = md5.digest();
			return new String(Hex.encodeHex(key));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean checkKey(String registerNumber,String key){
		String k = genKey(registerNumber);
		return LangUtils.equals(key, k);
	}
}
