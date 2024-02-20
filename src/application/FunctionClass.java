
package application;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class FunctionClass {
	
	String JDBC_URL = "jdbc:mysql://localhost:3306/CloudandSecurityAssessment";
	String USERNAME = "root";
	String PASSWORD = "";
	
	private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private int key;
	private SecretKey secretkey;
	private static DESJava desJava;
	private static AESJava aesJava;
	
	String DESMasterKey  = "B6Q9TOVhyws=";
	String AESMasterKey  = "wP0aJee2F/zWPPz9jTS3qA==";
	
  
	
	// Save input text and key to a file

	public FunctionClass() {
		try {
			desJava = new DESJava();
			aesJava = new AESJava();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/*
	 * generate key
	 */
	public void setKey(int key) {
		this.key = key;
	}

	public void generateKey(String key) {
		try {
			if (key.equals("DES")) {
				desJava.generateKey();
			} else {
				aesJava.generateKey();
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	

	public SecretKey getDESGeneratedKey() {
		return desJava.getSecretKey();
	}

	public SecretKey getAESGeneratedKey() {
		return aesJava.getSecretKey();
	}

	/*
	 * save DES and AES key to file
	 */
	
	public void saveDESkey(String fileName, String savedKey) throws Exception {

		  ObjectOutputStream outputStr = new ObjectOutputStream(new FileOutputStream(fileName));
		  byte[] savedKeyByte = Base64.getDecoder().decode(savedKey);
		  SecretKey secretKey = new SecretKeySpec(savedKeyByte, "DES");
		  // Write the SecretKey object to the file
		  outputStr.writeObject(secretKey);
		  outputStr.close();
		 }
	
	
	public void saveAESkey(String fileName, String savedKey) throws Exception {

		  ObjectOutputStream outputStr = new ObjectOutputStream(new FileOutputStream(fileName));
		  byte[] savedKeyByte = Base64.getDecoder().decode(savedKey);
		  SecretKey secretKey = new SecretKeySpec(savedKeyByte, "AES");
		  // Write the SecretKey object to the file
		  outputStr.writeObject(secretKey);
		  outputStr.close();
		 }
	
	public String DESMasterKEYFileEncrypt(String DESmasterKey, String inputTXT) {
		  String result = null;
		  
		  try {
		   DESJava des = new DESJava();
		   byte[] keyBytes = Base64.getDecoder().decode(DESmasterKey);
		   SecretKey desMasterKey = new SecretKeySpec(keyBytes, "DES");
		   des.setSecretKey(desMasterKey);
		   System.out.println("Text being encrepty: " + inputTXT);
		   byte[] encText = des.encrypt(inputTXT);
		   result = Base64.getEncoder().encodeToString(encText);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return result;
		 }
	
	public String AESMasterKEYFileEncrypt(String AESmasterKey, String inputTXT) {
		  String result = null;
		  
		  try {
		   AESJava aes = new AESJava();
		   byte[] keyBytes = Base64.getDecoder().decode(AESmasterKey);
		   SecretKey desMasterKey = new SecretKeySpec(keyBytes, "AES");
		   aes.setSecretKey(desMasterKey);
		   System.out.println("Text being encrepty: " + inputTXT);
		   byte[] encText = aes.encrypt(inputTXT);
		   result = Base64.getEncoder().encodeToString(encText);

		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return result;
		 }
	

	
	/*
	 * Caesar cipher 
	 */
	
	public String encryptCaesar(String plainText) {
		// Caesar cipher encryption logic
		String cipherText = "";

		for (int i = 0; i < plainText.length(); i++) {
			char plainCharacter = plainText.charAt(i);

			int position = alphabet.indexOf(plainCharacter);
			int newPosition = (position + key) % alphabet.length();

			char cipherCharacter = alphabet.charAt(newPosition);
			cipherText += cipherCharacter;
		}
		return cipherText;
	}

	public String decryptCaesar(String cipherText) {
		// Caesar cipher decryption logic
		String plainText = "";

		for (int i = 0; i < cipherText.length(); i++) {
			char cipherCharacter = cipherText.charAt(i);

			int position = alphabet.indexOf(cipherCharacter);
			int newPosition = (position - key + alphabet.length()) % alphabet.length();

			char plainCharacter = alphabet.charAt(newPosition);
			plainText += plainCharacter;
		}
		return plainText;
	}

	/*
	 * DES
	 */
	public String encryptDES(String plainText) {
		// Use DES algorithm for encryption
		try {
			byte[] encryptedBytes = desJava.encrypt(plainText);
			SecretKey secretKey = desJava.getSecretKey();
			byte[] keyBytes = secretKey.getEncoded();
			String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);

			return encryptedString;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return "Error during encryption";
		}
	}

	public String decryptDES(String cipherText, String encryptedKey) {
	    // Use DES algorithm for decryption
	    try {
	        String decryptedKey = DESMasterDecrypt(DESMasterKey, encryptedKey);
	        
	        // Decode the decrypted key, not the encrypted key
	        byte[] keyBytes = Base64.getDecoder().decode(decryptedKey);
	        byte[] encryptedBytes = Base64.getDecoder().decode(cipherText);
	        SecretKey secretKey = new SecretKeySpec(keyBytes, "DES");
	        desJava.setSecretKey(secretKey);

	        String decryptedString = desJava.decrypt(encryptedBytes);
	        
	        return decryptedString; 
	    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
	            | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
	        e.printStackTrace();
	        return "Error during decryption";
	    }
	}

	

	/*
	 * AES
	 */

	public String encryptAES(String plainText) {
		try {
			byte[] encryptedBytes = aesJava.encrypt(plainText);
			SecretKey secretKey = aesJava.getSecretKey();
			byte[] keyBytes = secretKey.getEncoded();
			String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);

			return encryptedString;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return "Error during encryption";
		}
	}

	public String decryptAES(String cipherText, String encryptedKey) {
		
		try {
			String decryptedKey = AESMasterDecrypt(AESMasterKey, encryptedKey);
			
			byte[] keyBytes = Base64.getDecoder().decode(decryptedKey);
			byte[] encryptedBytes = Base64.getDecoder().decode(cipherText);

			SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
			aesJava.setSecretKey(secretKey);

			String decryptedString = aesJava.decrypt(encryptedBytes);
			return decryptedString;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			return "Error during decryption";
		}
	}
	

/*
 * Connect to mysql database
 */
	
	
//Register new user
	public boolean registerUser(String userName, String password) {
		String hashedPassword = hashPassword(password);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

			String sql = "INSERT INTO loginData (username, password) VALUES (?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, userName);
				preparedStatement.setString(2, hashedPassword);

				// Execute the insert statement
				int rowsAffected = preparedStatement.executeUpdate();

				// Close the connection
				connection.close();

				return rowsAffected > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Login
	public boolean loginDataConnection(String userName, String passWord) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
			Statement statement = connection.createStatement();

			// Hash the password using MD5 before checking in the database
			String hashedPassword = hashPassword(passWord);

			String sql = "SELECT * FROM loginData where username = \"" + userName + "\" AND password = \""
					+ hashedPassword + "\"";
			ResultSet resultSet = statement.executeQuery(sql);

			// If there is a match, the user is valid
			boolean isValidUser = resultSet.next();

			// Close resources
			resultSet.close();
			statement.close();
			connection.close();

			return isValidUser;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	// Caesar
	public void caesarDataConnection(String caesarKey, String encryptText) {
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

			// Insert data into the "caesarData" table
			String insertcaesarData = "INSERT INTO caesarData (caesarKey, encryptText) VALUES (?, ?)";
			try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(insertcaesarData)) {
				preparedStatement.setString(1, caesarKey);
				preparedStatement.setString(2, encryptText);

				// Execute the insert statement
				preparedStatement.executeUpdate();
			}

			// Close the connection
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// DES
	public void desDataConnection(String desEncryptText) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

			// Generate a key for DES encryption
			SecretKey generatedKey = generateOrRetrieveKey("DES");
			String encodedKey = Base64.getEncoder().encodeToString(generatedKey.getEncoded());
			
			String encryptedDESKey = DESMasterEncrypt(DESMasterKey, encodedKey);


			// Insert data into the "desData" table
			String insertdesData = "INSERT INTO desData (desKey, desEncryptText) VALUES (?, ?)";
			try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(insertdesData)) {
				preparedStatement.setString(1, encryptedDESKey); // Store the generated key
				preparedStatement.setString(2, desEncryptText);


				// Execute the insert statement
				preparedStatement.executeUpdate();
			}

			// Close the connection
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// AES
		public void aesDataConnection(String aesEncryptText) {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

				// Generate a key for DES encryption
				SecretKey generatedKey =  generateOrRetrieveKey("AES");
				String encodedKey = Base64.getEncoder().encodeToString(generatedKey.getEncoded());

				String encryptedDESKey = AESMasterEncrypt(AESMasterKey, encodedKey);

				
				// Insert data into the "desData" table
				String insertdesData = "INSERT INTO aesData (aesKey, aesEncryptText) VALUES (?, ?)";
				try (java.sql.PreparedStatement preparedStatement = connection.prepareStatement(insertdesData)) {
				
					preparedStatement.setString(1, encryptedDESKey); // Store the generated key
					preparedStatement.setString(2, aesEncryptText);
			

					// Execute the insert statement
					preparedStatement.executeUpdate();
				}

				// Close the connection
				connection.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Method to hash the password to save to mysql for more sercure using MD5
		private String hashPassword(String password) {
			
			try {

				MessageDigest md = MessageDigest.getInstance("MD5");

				byte[] messageDigest = md.digest(password.getBytes());
				BigInteger no = new BigInteger(1, messageDigest);

				String hashtext = no.toString(16);

				while (hashtext.length() < 32) {
					hashtext = "0" + hashtext;
				}

				return hashtext;
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
		}
		
		
		public static SecretKey generateOrRetrieveKey(String algorithm) {   //idea from online
		    try {
		        if (algorithm.equalsIgnoreCase("DES") && desJava.getSecretKey() == null) {
		            desJava.generateKey();
		            return desJava.getSecretKey();
		        } else if (algorithm.equalsIgnoreCase("AES") && aesJava.getSecretKey() == null) {
		            aesJava.generateKey();
		            return aesJava.getSecretKey();
		        } else {
		            if (algorithm.equalsIgnoreCase("DES")) {
		                return desJava.getSecretKey();
		            } else if (algorithm.equalsIgnoreCase("AES")) {
		                return aesJava.getSecretKey();
		            } else {
		                throw new IllegalArgumentException("Invalid algorithm: " + algorithm);
		            }
		        }
		    } catch (NoSuchAlgorithmException e) {
		        e.printStackTrace();
		        throw new RuntimeException("Error generating or retrieving key", e);
		    }
		}

				
		//to use desmaster key encrypt key 
		public String DESMasterEncrypt(String DESmasterKey, String keyToEncrypt) {
		    String result = null;

		    try {
		       
		        byte[] masterKeyBytes = Base64.getDecoder().decode(DESmasterKey);

		        
		        SecretKey desMasterKey = new SecretKeySpec(masterKeyBytes, "DES");
		        desJava.setSecretKey(desMasterKey);

		        
		        byte[] encryptedKeyBytes = desJava.encrypt(keyToEncrypt);

		        
		        result = Base64.getEncoder().encodeToString(encryptedKeyBytes);
		        System.out.println("Encrepty key : " + result);
		    } catch (Exception e) {
		        System.out.println("Error in DES: " + e);
		        e.printStackTrace();
		    }
		    return result;
		}
		
		//to use aesmaster key encrypt key 
		public String AESMasterEncrypt(String AESmasterKey, String keyToEncrypt) {
		    String result = null;

		    try {
		        
		        byte[] masterKeyBytes = Base64.getDecoder().decode(AESmasterKey);

		        
		        SecretKey desMasterKey = new SecretKeySpec(masterKeyBytes, "AES");
		        aesJava.setSecretKey(desMasterKey);

		        
		        byte[] encryptedKeyBytes = aesJava.encrypt(keyToEncrypt);

		        
		        result = Base64.getEncoder().encodeToString(encryptedKeyBytes);
		        System.out.println("Encrepty key : " + result);
		    } catch (Exception e) {
		        System.out.println("Error in DES: " + e);
		        e.printStackTrace();
		    }
		    return result;
		}
		

		//to decrypt the encrypt key DES
		public String DESMasterDecrypt(String DESmasterKey, String encryptedKey) {
		    String result = null;

		    try {
		       
		        byte[] masterKeyBytes = Base64.getDecoder().decode(DESmasterKey);

		        
		        SecretKey desMasterKey = new SecretKeySpec(masterKeyBytes, "DES");
		        desJava.setSecretKey(desMasterKey);
		      
		      
		        result = desJava.decrypt(Base64.getDecoder().decode(encryptedKey));

		        System.out.println("Decrypted key: " + result);
		    } catch (Exception e) {
		        System.out.println("Error in DES decryption: " + e);
		        e.printStackTrace();
		    }
		    return result;
		}
		
		//to decrypt the encrypt key AES
		public String AESMasterDecrypt(String AESmasterKey, String encryptedKey) {
		    String result = null;

		    try {
		        
		        byte[] masterKeyBytes = Base64.getDecoder().decode(AESmasterKey);

	
		        SecretKey desMasterKey = new SecretKeySpec(masterKeyBytes, "AES");
		        aesJava.setSecretKey(desMasterKey);
		      
		       
		        result = aesJava.decrypt(Base64.getDecoder().decode(encryptedKey));

		        System.out.println("Decrypted key: " + result);
		    } catch (Exception e) {
		        System.out.println("Error in DES decryption: " + e);
		        e.printStackTrace();
		    }
		    return result;
		}
		
		



}