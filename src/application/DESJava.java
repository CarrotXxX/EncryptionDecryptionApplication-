package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


public class DESJava {

    private SecretKey secretkey;
    
  

   
    public DESJava() throws NoSuchAlgorithmException {

    }

    /**
     * Step 1. Generate a DES key using KeyGenerator
     */
    public void generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        this.setSecretKey(keyGen.generateKey());
    }

    public byte[] encrypt(String strDataToEncrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher desCipher = Cipher.getInstance("DES"); // Must specify the mode explicitly as most JCE providers default to ECB mode!!
        desCipher.init(Cipher.ENCRYPT_MODE, this.getSecretKey());
        byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
        byte[] byteCipherText = desCipher.doFinal(byteDataToEncrypt);
        return byteCipherText;
    }

    public String decrypt(byte[] strCipherText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher desCipher = Cipher.getInstance("DES"); // Must specify the mode explicitly as most JCE providers default to ECB mode!!
        desCipher.init(Cipher.DECRYPT_MODE, this.getSecretKey());
        byte[] byteDecryptedText = desCipher.doFinal(strCipherText);
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
