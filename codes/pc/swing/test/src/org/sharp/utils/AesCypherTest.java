package org.sharp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

public class AesCypherTest extends TestCase {

	public void test1() {

		try {
			// Generate a temporary key. In practice, you would save this key.
			// See also e464 Encrypting with DES Using a Pass Phrase.

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);
			SecretKey key = kgen.generateKey();

			// Create encrypter/decrypter class
			AesCypher encrypter = new AesCypher(key);

			// Encrypt
			encrypter.encrypt(new FileInputStream("DESTest.txt"),
					new FileOutputStream("Encrypted.txt"));
			// Decrypt
			encrypter.decrypt(new FileInputStream("Encrypted.txt"),
					new FileOutputStream("Decrypted.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test2() throws Exception{
		File f1 = new File("test/data/Untitled 18.avi");
		File temp = new File("test/data/Untitled 18.avi.ec");
		File f2 = new File("test/data/Untitled 18.avi.2");
		AesCypher.encrypt(f1, 
				temp, "password");
		AesCypher.decrypt(temp,
				f2,"password");
		byte[] b1 = FileUtils.readFileToByteArray(f1);
		byte[] b2 = FileUtils.readFileToByteArray(f2);
		assertTrue(Arrays.equals(b1, b2));
		f2.delete();
		temp.delete();
	}
}
