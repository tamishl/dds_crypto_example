package nielsj.crypto.model;

import nielsj.crypto.Crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.Signature;
import android.util.Log;

import nielsj.crypto.view.Hex;

public class RSA {

  public RSA() {
    try {
      generator = KeyPairGenerator.getInstance("RSA", "BC");
      generator.initialize(getKeySize());
      // using default provider for signatures,
      // since BC not working, probably related to SHA256 problem
      signerVerifier = Signature.getInstance("SHA256withRSA", Crypto.provider);
    } catch (Exception e) {
      System.out.println("RSA: error in constructor");
      System.out.println(e);
    }
  }

  // variables

  // 24 bits is smallest keysize that will work with BC provider
  // 512 bits is smallest keysize that allows for signing (SHA 256)
  // 2048 bits is smallest keysize considered safe
  private final int keysize = 24;

  public int getKeySize() {
    return keysize;
  }
  public String getProvider() {
    return generator.getProvider().toString();
  }

  KeyPairGenerator generator;
  Signature signerVerifier;

  public BigInteger modulus, privateKeyExponent, publicKeyExponent;
  RSAPrivateKey privateKey;
  RSAPublicKey publicKey;

  Log log;

  public void generateKeyPair() {
    KeyPair keyPair = null;
    try {
      keyPair = generator.generateKeyPair();
    } catch (Exception e) {
      System.out.println("RSA: key pair generation error");
      e.printStackTrace();
    }
    privateKey = (RSAPrivateKey) keyPair.getPrivate();
    publicKey = (RSAPublicKey) keyPair.getPublic();
    modulus = publicKey.getModulus();
    privateKeyExponent = privateKey.getPrivateExponent();
    publicKeyExponent = publicKey.getPublicExponent();
  }

  public String sign(String message) {
    byte[] input = message.getBytes();
    byte[] output = {};
    try {
      signerVerifier.initSign(privateKey);
      signerVerifier.update(input);
      output = signerVerifier.sign();
    } catch (Exception e) {
      System.out.println("RSA: signing error");
      System.out.println(e);
      log.e("rsa", e.toString());
      return "(error)";
    }
    String signature = Hex.byteArrayToHexString(output);
    return signature;
  }

  public boolean verify(String signature, String message) {
    boolean b = false;
    try {
      byte[] messageBytes = message.getBytes();
      if (messageBytes.length == 0) System.out.println("RSA: message is empty");
      byte[] signatureBytes = Hex.hexStringToByteArray(signature);
      signerVerifier.initVerify(publicKey);
      signerVerifier.update(messageBytes);
      b = signerVerifier.verify(signatureBytes);
    } catch (Exception e) {
      System.out.println("RSA: verfication error");
      System.out.println(e);
    }
    return b;

  }

}