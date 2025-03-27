package nielsj.crypto.model;

import java.security.MessageDigest;
import nielsj.crypto.Crypto;
import nielsj.crypto.view.Hex;
import nielsj.crypto.view.Util;
public class SHA256 {
  public SHA256() {

    try {
      digest = MessageDigest.getInstance("SHA-256", Crypto.provider);
       // using default provider
       // since BouncyCastle/BC is not working, for some reason
    } catch (Exception e) {
      System.out.println("SHA256: error in constructor");
      System.out.println(e);
    }
  }

  MessageDigest digest;

  public String getProvider() {
    return digest.getProvider().toString();
  }

  public String hash(String inputText){
    byte[] input = inputText.getBytes();
    byte[] hashValue = sha256Hash(input);
    String hashValueText = Hex.byteArrayToHexString(hashValue);
    String blocks = Util.stringToMultiLine(hashValueText,16);
    return blocks;
  }

  private byte[] sha256Hash(byte[] input) {
    byte[] hashValue = digest.digest(input);
    return hashValue;
  }
}
