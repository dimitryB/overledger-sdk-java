package network.quant.essential.util;

import network.quant.api.ENCRYPT_TYPE;
import network.quant.api.Encryptor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

/**
 * RSA Encryption implementation of Encryptor
 * This utility can be used for data message encryption and decryption
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RsaEncryptor implements Encryptor {

    private static final int KEY_LENGTH = 1024;
    private static final String ALGORITHM = "RSA";
    private static final String ALGORITHM_METHOD_PADDING = ALGORITHM + "/ECB/PKCS1Padding";
    PrivateKey privateKey;
    PublicKey publicKey;

    /**
     * Default constructor, a new pair of keys would be generated
     */
    public RsaEncryptor() {
        this.generateKey();
    }

    /**
     * New RSA encryptor with given public and private keys
     * @param privateKey String containing the private key in Base64 encoding
     * @param publicKey String containing the private key in Base64 encoding
     */
    public RsaEncryptor(String privateKey, String publicKey) {
        this.getPrivateKey(privateKey);
        this.getPublicKey(publicKey);
    }

    private void generateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_LENGTH);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            log.error("Unable to generate key", e);
        }
    }

    private void getPrivateKey(String privateKey) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey.getBytes()));
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            this.privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            log.error("Unable to generate private key", e);
        }
    }

    private void getPublicKey(String publicKey) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey.getBytes()));
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            this.publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            log.error("Unable to generate public key", e);
        }
    }

    private byte[] process(byte[] bytes, int mode, Key key) {
        byte result[];
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_METHOD_PADDING);
            cipher.init(mode, key);
            result = cipher.doFinal(bytes);
        } catch (Exception e) {
            log.error("Unable to process target", e);
            result = null;
        }
        return result;
    }

    /**
     * Get current public key in Encoded format
     * @return String containing the public key
     */
    public String getPublicKey() {
        return Optional
                .ofNullable(Base64.getEncoder().encodeToString(this.publicKey.getEncoded()))
                .orElse(null);
    }

    /**
     * Get current private key in Encoded format
     * @return String containing the private key
     */
    public String getPrivateKey() {
        return Optional
                .ofNullable(Base64.getEncoder().encodeToString(this.privateKey.getEncoded()))
                .orElse(null);
    }

    @Override
    public ENCRYPT_TYPE getType() {
        return ENCRYPT_TYPE.RSA;
    }

    @Override
    public byte[] encrypt(byte[] bytes) {
        if (null == this.publicKey) {
            return null;
        }
        return this.process(bytes, Cipher.ENCRYPT_MODE, this.publicKey);
    }

    @Override
    public byte[] decrypt(byte[] bytes) {
        if (null == this.privateKey) {
            return null;
        }
        return this.process(bytes, Cipher.DECRYPT_MODE, this.privateKey);
    }

}
