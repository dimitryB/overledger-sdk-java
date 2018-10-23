package io.overledger.api;

import java.math.BigInteger;

/**
 * Definition of account, DLT account should implement this interface
 * Implementation of account could hold spendable addresses or transactions of DLT
 */
public interface Account {

    /**
     * Set account network
     * @param network NETWORK containing network enum
     * @return Account itself
     */
    Account withNetwork(NETWORK network);

    /**
     * Set account secret key
     * @param key BigInteger containing secret key
     */
    void setPrivateKey(BigInteger key);

    /**
     * Get account secret key - do NOT save secret key, keep it very safe, keep it away from hard drive
     * @return BigInteger containing the key
     */
    BigInteger getPrivateKey();

    /**
     * Sign given transaction, update DltTransaction with signedTransaction field/property
     * @param fromAddress String containing From address
     * @param toAddress String containing To address
     * @param message String containing the message
     * @param dltTransaction DltTransaction containing the original transaction
     */
    void sign(String fromAddress, String toAddress, String message, DltTransaction dltTransaction);

    /**
     * Sign given transaction, update DltTransaction with signedTransaction field/property
     * @param fromAddress String containing From address
     * @param toAddress String containing To address
     * @param message byte array containing the message
     * @param dltTransaction DltTransaction containing the original transaction
     */
    void sign(String fromAddress, String toAddress, byte[] message, DltTransaction dltTransaction);

}
