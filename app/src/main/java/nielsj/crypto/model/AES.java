package nielsj.crypto.model;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import nielsj.crypto.Crypto;
import nielsj.crypto.view.*;

public class AES {

  public AES() {
    // constructor for simple, ECB-based AES as in assignment #2
    this("ECB","PKCS5Padding");
  }
  public AES(String operation, String padding) {
    // constructor for experimenting with AES
    this.operation = operation;
    String transformation = "AES/" + operation + "/" + padding;
    try {
      cipher = Cipher.getInstance(transformation, "BC");
    } catch (Exception e) {
      System.out.println("AES: cipher generation error");
      System.out.println(e);
    }
  }

  // variables

  // The class Cipher instance does the actual encryption
  private Cipher cipher;

  private static String operation; // will be set to "ECB" or "CBC"

  // The encryption key.
  // Variable keyHexString is printed on the Android's screen.
  // Then from the screen it is passed to encrypt()
  // (possibly after modification)

  private static String keyHexString = "000102030405060708090A0B0C0D0E0F";


  // In CBC operation mode, AES uses an iv
  private static String ivHexString = "9F741FDB5D8845BDB48A94394E84F8A3";



     /***********************
     * Encryption methods  *
     ***********************/

  // encrypt() is a wrapper with text i/o
  public String encrypt(String plaintext, String hexKey) {
    byte[] plainBytes = plaintext.getBytes();
    SecretKeySpec key = generateKey(hexKey);
    byte[] cipherBytes = aesEncrypt(plainBytes, key);
    String ciphertext = Hex.byteArrayToHexString(cipherBytes);
    String blocks = Util.stringToMultiLine(ciphertext,32);
    // The following line is for experimenting
    // with *not* using hexes
    // blocks = new String(cipherBytes);
    return blocks;
  }

  // aesEncrypt() is the actual encryption
  public byte[] aesEncrypt(byte[] plainBytes, SecretKeySpec key) {
    byte[] cipherBytes = {};
    try {
      switch (operation) {
        case "ECB":
          cipher.init(Cipher.ENCRYPT_MODE, key);
          break;
        case "CBC":
          byte[] iv = Hex.hexStringToByteArray(ivHexString);
          cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv) );
          break;
      } // end switch
      cipherBytes = cipher.doFinal(plainBytes);
    } catch (Exception e) {
      System.out.println("AES: encryption error");
      System.out.println(e);
    }
    return cipherBytes;
  }

  // decrypt() is a wrapper just like encrypt()
  public String decrypt(String ct, String keyString) {
    String ciphertext = Util.stringToSingleLine(ct);
    byte[] cipherBytes = Hex.hexStringToByteArray(ciphertext);
    SecretKeySpec key = generateKey(keyString);
    byte[] plainBytes = aesDecrypt(cipherBytes, key);
    String plaintext = new String(plainBytes);
    return plaintext;
  }

  // aesDecrypt is the actual decryption

  public byte[] aesDecrypt(byte[] cipherBytes, SecretKeySpec key) {
    byte[] plainBytes = {};
    try {
      switch (operation) {
        case "ECB":
          cipher.init(Cipher.DECRYPT_MODE, key);
          break;
        case "CBC":
          byte[] iv = Hex.hexStringToByteArray(ivHexString);
          cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
          break;
      } // end switch
      plainBytes = cipher.doFinal(cipherBytes);
    } catch (Exception e) {
      System.out.println("AES: decryption error");
      System.out.println(e);
    }
    return plainBytes;
  }

  // generateKey()
  // transformation of the key from String format (hexes)
  // to a format that class Cipher understands
  private SecretKeySpec generateKey(String hexKey) {
    SecretKeySpec key = null;
    try {
      byte[] keyBytes = Hex.hexStringToByteArray(hexKey);
      key = new SecretKeySpec(keyBytes, "AES");
    } catch (Exception e) {
      System.out.println("AES: key generation error");
      System.out.println(e);
    }
    return key;
  }

  public static String getKey() {
    return keyHexString;
  }
  public static String getIV() {
    return ivHexString;
  }
  public static String getOperation() {return operation; }
  public static void setIV(String iv) {
    ivHexString = iv;
  }

  public String getProvider() {
    return cipher.getProvider().toString();
  }

}
