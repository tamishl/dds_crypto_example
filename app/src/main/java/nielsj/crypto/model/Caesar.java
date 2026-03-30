package nielsj.crypto.model;

import java.util.ArrayList;
import java.util.List;

public class Caesar {
  // variables
  private int key = 0;

  // methods

  // key getter and setter (with key validation)
  public String getKey() {
    return Integer.toString(key);
  }

  public void setKey(String keyString) {
    int k = Integer.parseInt(keyString);
    if (k < 0 || k > 25) key = 0;
    else key = k;
  }

  // encryption and decryption

  public String encrypt(String plaintext) {
    return caesarEncrypt(plaintext, key);
  }

  public String decrypt(String ciphertext) {
    return caesarEncrypt(ciphertext, 26-key);
  }

  public ArrayList<String> crack(String ciphertext) {
    ArrayList<String> cracked = new ArrayList<String>();
    for (int i = 1; i < 26; i++){
      cracked.add(caesarEncrypt(ciphertext, 26-i));
    }

    return cracked;
  }

  // private methods supporting en/decryption

  private String caesarEncrypt(String plaintext, int key) {
    int length = plaintext.length();
    char[] cs = new char[length];
    for (int i = 0; i < length; i++) {
      char c = plaintext.charAt(i);
      cs[i] = caesarEncrypt(c, key);
    }
      return new String(cs);
  }

  // encrypt and decrypt a single character
  private char caesarEncrypt(char ch, int key) {
    int plainChar = ch - 65; // ASCII A-Z are 65-90
    // if character is out of range, it is simply returned
    if (plainChar < 0 || plainChar > 25) {
      System.out.println("Caesar: error: letter '" + ch + "' is out of range 'A-Z'");
      return ch;
    } else {
      // the actual encryption
      int cipherChar = plainChar - key;
      if (cipherChar < 0) {
        cipherChar = cipherChar + 26;
      }
      return (char) (cipherChar + 65);
    }
  }

}



