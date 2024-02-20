package application;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESJava {

	private SecretKey secretkey;

	public AESJava() throws NoSuchAlgorithmException {

	}

	// Step 1. Generate a AES key using KeyGenerator

	 public void generateKey() throws NoSuchAlgorithmException {
	        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	        keyGen.init(128);    
	        this.setSecretKey(keyGen.generateKey());
	    }
	

	public byte[] encrypt(String strDataToEncrypt) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher aesCipher = Cipher.getInstance("AES"); // Must specify the mode explicitly as most JCE providers default
														// to ECB mode!!
		aesCipher.init(Cipher.ENCRYPT_MODE, this.getSecretKey());
		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteCipherText = aesCipher.doFinal(byteDataToEncrypt);
		return byteCipherText;
	}

	public String decrypt(byte[] strCipherText) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher aesCipher = Cipher.getInstance("AES"); // Must specify the mode explicitly as most JCE providers default
														// to ECB mode!!
		aesCipher.init(Cipher.DECRYPT_MODE, this.getSecretKey());
		byte[] byteDecryptedText = aesCipher.doFinal(strCipherText);
		return new String(byteDecryptedText);
	}

	/**
	 * Retrieve the secret key
	 *
	 * @return the secret key
	 */
	public SecretKey getSecretKey() {
		return secretkey;
	}

	/**
	 * Set the secret key
	 *
	 * @param secretKey the secret key to set
	 */
	public void setSecretKey(SecretKey secretKey) {
		this.secretkey = secretKey;
	}
}
