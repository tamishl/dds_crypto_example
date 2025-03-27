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

import nielsj.crypto.Crypto;
public class DiffieHellman {
    public DiffieHellman(Member a, Member b) {
        this.a = a;
        this.b = b;
        this.a.other = b;
        this.b.other = a;
        try {
            paramGen = AlgorithmParameterGenerator.getInstance("DH", "BC");
            paramGen.init(keysize);
        } catch (Exception e) {
            System.out.println("DH constructor exception");
            System.out.println(e);
        }
    }

    // variables
    public Member a, b;

    // 256 bit is smallest keysize allowed by BC
    // 2024 bit is smallest keysize considered safe (but app crashes)
    // 1024 bit largest keysze app can compute (approx. 20 sec)

    private static final int keysize = 256;
    private AlgorithmParameterGenerator paramGen;
    private DHParameterSpec dhSpec;

    // methods

    public void generateDHParameters() {
        AlgorithmParameters params;
        try {
            params = paramGen.generateParameters();
            dhSpec = params.getParameterSpec(DHParameterSpec.class);
            a.dhSpec = dhSpec;
            b.dhSpec = dhSpec;
        } catch (Exception e) {
            System.out.println("DH param generation unsuccessful");
            System.out.println(e);
        }
    }
    public int getKeySize() {
        return keysize;
    }

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

        // variables
        private DHParameterSpec dhSpec;
        private KeyPair keyPair;
        private Member other;
        private BigInteger privateValue = ZERO;
        public BigInteger publicValue = ZERO;

        // methods
        public void generateValues() {
            try {
                KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DH");
                keyPairGen.initialize(dhSpec);
                keyPair = keyPairGen.generateKeyPair();
                privateValue = ((DHPrivateKey) keyPair.getPrivate()).getX();
                publicValue = ((DHPublicKey) keyPair.getPublic()).getY();
            } catch (Exception e) {}
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