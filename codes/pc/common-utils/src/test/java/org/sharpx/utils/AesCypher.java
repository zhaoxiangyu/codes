package org.sharpx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCypher {
	Cipher ecipher;
	Cipher dcipher;

	// Buffer used to transport the bytes from one stream to another
	byte[] buf = new byte[1024];

	AesCypher(String pass) {
		try {
	        /*Security.addProvider(new org.bouncycastle.jce.provider
	                .BouncyCastleProvider());*/

	        byte[] iv = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
					0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
			IvParameterSpec paramSpec = new IvParameterSpec(iv);

			SecretKeySpec key = new SecretKeySpec(pass.getBytes("UTF-8"), "AES");
			
			ecipher = Cipher.getInstance("AES/CTR/NoPadding");
			dcipher = Cipher.getInstance("AES/CTR/NoPadding");

			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	AesCypher(SecretKey key) {
		byte[] iv = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
				0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };

		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		try {
			ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// CBC requires an initialization vector
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	AesCypher(byte[] seed) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(
				secretKey(seed).getEncoded(), "AES");
		//ecipher = Cipher.getInstance("AES");
		ecipher = Cipher.getInstance("AES/CTR/NoPadding");
		ecipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		//dcipher = Cipher.getInstance("AES");
		dcipher = Cipher.getInstance("/CBC/PKCS5Padding");
		dcipher.init(Cipher.DECRYPT_MODE, skeySpec);
	}

	public static void encrypt(File in,File out,String pass) throws Exception{
		FileInputStream is = new FileInputStream(in);
		if(!out.exists()){
			out.createNewFile();
		}
		FileOutputStream os = new FileOutputStream(out);
		new AesCypher(pass.getBytes("UTF-8")).encrypt(is, os);
//		new AesCypher(pass).encrypt(is, os);
	}
	
	void encrypt(InputStream in, OutputStream out) {
		try {
			// Bytes written to out will be encrypted
			out = new CipherOutputStream(out, ecipher);

			// Read in the cleartext bytes and write to out to encrypt
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			out.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	public static void decrypt(File in,File out,String pass) throws Exception {
		FileInputStream is = new FileInputStream(in);
		if(!out.exists()){
			out.createNewFile();
		}
		FileOutputStream os = new FileOutputStream(out);
		new AesCypher(pass.getBytes("UTF-8")).decrypt(is, os);
//		new AesCypher(pass).decrypt(is, os);
	}
	
	void decrypt(InputStream in, OutputStream out) {
		try {
			// Bytes read from in will be decrypted
			in = new CipherInputStream(in, dcipher);

			// Read in the decrypted bytes and write the cleartext to out
			int numRead = 0;
			while ((numRead = in.read(buf)) >= 0) {
				out.write(buf, 0, numRead);
			}
			out.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}
	
	private SecretKey secretKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		return kgen.generateKey();
	}
}
