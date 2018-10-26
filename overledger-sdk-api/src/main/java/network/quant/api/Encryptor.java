package network.quant.api;

/**
 * Definition of Encryptor
 * Implement this interface for data encryption support
 * The encryption process has to be OFFLINE,
 * make sure to implement matched encrypt and decrypt methods,
 * BPI layer has no information about encryptor, it can NOT encrypt or decrypt data automatically
 * Account could use encryptor for data encryption.
 */
public interface Encryptor {

    /**
     * Get encryption type
     * @return ENCRYPT_TYPE containing encryption type
     */
    ENCRYPT_TYPE getType();

    /**
     * Encrypt data
     * @param bytes containing plain data
     * @return bytes containing encrypted data
     */
    byte[] encrypt(byte bytes[]);

    /**
     * Decrypt data
     * @param bytes containing encrypted data
     * @return bytes containing plain data
     */
    byte[] decrypt(byte bytes[]);

}
