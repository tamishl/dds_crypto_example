package nielsj.crypto.model;

import java.security.AlgorithmParameterGenerator;
import java.math.BigInteger;
import static java.math.BigInteger.ZERO;
import java.security.AlgorithmParameters;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.util.Random;

import nielsj.crypto.Crypto;
public class DiffieHellman {
    public DiffieHellman(Member a, Member b, int keysize) {
        this.a = a;
        this.b = b;
        this.a.other = b;
        this.b.other = a;
        this.keysize = keysize;
        try {
            paramGen = AlgorithmParameterGenerator.getInstance("DH", "BC");
            paramGen.init(keysize);
            params = AlgorithmParameters.getInstance("DH", "BC"); 
            DHSPEC2048 = new DHParameterSpec(p2048, g2048);
            DHSPEC4 = new DHParameterSpec(p4,g4);
        } catch (Exception e) {
            System.out.println("DH constructor exception");
            System.out.println(e);
        }
    }

    // variables
    public Member a, b;

    // int keysize is used to keep track of the DH keysize,
    // since I don't know how to query to Java/Bouncycastle DH objects about keysize
    // 256 bit is smallest keysize allowed by BC
    // 2048 bit is smallest keysize considered safe
    private static int keysize = 256;
    private AlgorithmParameterGenerator paramGen;
    private AlgorithmParameters params;
    private DHParameterSpec dhSpec;
    private static DHParameterSpec DHSPEC2048, DHSPEC4;
      // dhSpec2048 is for explicitly defining fixed, large parameters using RFC 5114
      // recall that class AlgorithmParameters is an "opaque" wrapper
      // whereas DHParameterSpec is "transparent" and provides direct access to p, g

    private static final String pRFC5114 =
        "87A8E61D B4B6663C FFBBD19C 65195999 8CEEF608 660DD0F2"
      + "5D2CEED4 435E3B00 E00DF8F1 D61957D4 FAF7DF45 61B2AA30"
      + "16C3D911 34096FAA 3BF4296D 830E9A7C 209E0C64 97517ABD"
      + "5A8A9D30 6BCF67ED 91F9E672 5B4758C0 22E0B1EF 4275BF7B"
      + "6C5BFC11 D45F9088 B941F54E B1E59BB8 BC39A0BF 12307F5C"
      + "4FDB70C5 81B23F76 B63ACAE1 CAA6B790 2D525267 35488A0E"
      + "F13C6D9A 51BFA4AB 3AD83477 96524D8E F6A167B5 A41825D9"
      + "67E144E5 14056425 1CCACB83 E6B486F6 B3CA3F79 71506026"
      + "C0B857F6 89962856 DED4010A BD0BE621 C3A3960A 54E710C3"
      + "75F26375 D7014103 A4B54330 C198AF12 6116D227 6E11715F"
      + "693877FA D7EF09CA DB094AE9 1E1A1597";
    private static final String gRFC5114 =
        "3FB32C9B 73134D0B 2E775066 60EDBD48 4CA7B18F 21EF2054"
      + "07F4793A 1A0BA125 10DBC150 77BE463F FF4FED4A AC0BB555"
      + "BE3A6C1B 0C6B47B1 BC3773BF 7E8C6F62 901228F8 C28CBB18"
      + "A55AE313 41000A65 0196F931 C77A57F2 DDF463E5 E9EC144B"
      + "777DE62A AAB8A862 8AC376D2 82D6ED38 64E67982 428EBC83"
      + "1D14348F 6F2F9193 B5045AF2 767164E1 DFC967C1 FB3F2E55"
      + "A4BD1BFF E83B9C80 D052B985 D182EA0A DB2A3B73 13D3FE14"
      + "C8484B1E 052588B9 B7D2BBD2 DF016199 ECD06E15 57CD0915"
      + "B3353BBB 64E0EC37 7FD02837 0DF92B52 C7891428 CDC67EB6"
      + "184B523D 1DB246C3 2F630784 90F00EF8 D647D148 D4795451"
      + "5E2327CF EF98C582 664B4C0F 6CC41659";

    private static final String p2048Hex = pRFC5114.replaceAll("\\s", ""),
                                g2048Hex = gRFC5114.replaceAll("\\s", "");
    private static final BigInteger p2048 = new BigInteger(p2048Hex, 16),
                                    g2048 = new BigInteger(g2048Hex, 16),
                                    p4 = new BigInteger("13"),
                                    g4 = new BigInteger("11");

    // methods
    public void generateDHParameters() {
        // modify params, to use (optionally) predefined p, g
        // as indicated in
        // stackoverflow.com/questions/4082268/diffie-hellman-set-generator-parameter-in-bouncycastle
        // use BigInteger and RFC 5114
        // Remember: consider keysize - predefined p, g are meant for 2048

        try {
          if (keysize == 2048) params.init(DHSPEC2048);
          else if (keysize == 4) params.init(DHSPEC4);
          else params = paramGen.generateParameters();
          dhSpec = params.getParameterSpec(DHParameterSpec.class);
        } catch (Exception e) {
            System.out.println("DH param generation unsuccessful");
            System.out.println(e);
        }
        b.dhSpec = dhSpec;
        a.dhSpec = dhSpec;
    }
    public static int getKeySize() {
        return keysize;
    }
       // no setter method; keysize-setting
       // is by a parameter to the DiffieHellman constructor

    public String getBase() {
        BigInteger base = dhSpec.getG();
        return base.toString();
    }

    public String getModulus() {
        BigInteger modulus = dhSpec.getP();
        return modulus.toString();
    }

    public String getProvider() {
        return paramGen.getProvider().toString();
    }
    public static class Member {
        // Members share a set of DH parameters
        // and for simplicity, they also reuse the same objects for DH-computations
        // variables
        private DHParameterSpec dhSpec;
        private KeyPair keyPair;
        private Member other;
        private BigInteger privateValue = ZERO;
        public BigInteger publicValue = ZERO;
        private static Random rand = new Random();
        // methods
        public void generateValues() {
            if (keysize >= 256) try {
                KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH");
                keyPairGen.initialize(dhSpec);
                keyPair = keyPairGen.generateKeyPair();
                privateValue = ((DHPrivateKey) keyPair.getPrivate()).getX();
                publicValue = ((DHPublicKey) keyPair.getPublic()).getY();
            } catch (Exception e) {}
          else if (keysize == 4) {
                // doing computation of private+public value 'by hand'
                // since DH provider doesn't support keysize 4
                int p4int = p4.intValue();
                int privValue = 1 + rand.nextInt(p4int-1);
                privateValue = new BigInteger(String.valueOf(privValue));
                BigInteger g = dhSpec.getG();
                publicValue = g.modPow(privateValue, dhSpec.getP());
            }
        }

        public String getPrivateValue() {
            return privateValue.toString();
        }

        public String getPublicValue() {
            return publicValue.toString();
        }
        public String getSharedSecret() {
            BigInteger y = other.publicValue;
            BigInteger s = y.modPow(privateValue, dhSpec.getP());
            return s.toString();
        }
    };
}