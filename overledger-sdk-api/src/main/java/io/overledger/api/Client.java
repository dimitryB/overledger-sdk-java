package io.overledger.api;

import java.util.List;
import java.util.UUID;

/**
 * Definition of BPI client
 * Implement methods in this interface for connecting to BPI service
 */
public interface Client<T, S> {

    /**
     * Write transaction to Overledger BPI layer
     * HTTP POST method to BPI layer
     * @param ovlTransaction T containing the actual request body
     * @param requestClass Class containing request body class type
     * @param responseClass Class containing response body class type
     * @return S containing actual response body
     */
    S postTransaction(T ovlTransaction, Class<T> requestClass, Class<S> responseClass);

    /**
     * Read transaction from BPI layer
     * HTTP GET method to BPI layer
     * @param overledgerTransactionID UUID containing Overledger transaction ID
     * @param responseClass Class containing response body class type
     * @return S containing actual response body
     */
    S getTransaction(UUID overledgerTransactionID, Class<S> responseClass);

    /**
     * Read transactions from BPI layer
     * HTTP GET method to BPI layer
     * @param mappId String containing Overledger Mapp ID
     * @param responseClass Class containing response body class type
     * @return List of S containing actual response body list
     */
    List<S> getTransactions(String mappId, Class<S> responseClass);

    /**
     * Read transaction from BPI layer
     * HTTP GET method to BPI layer
     * @param dlt String containing DLT type. e.g: bitcoin, ethereum, ripple. Make sure the DLT type is accepted by BPI
     * @param transactionHash String containing DLT transaction hash value
     * @param responseClass Class containing response body class type
     * @return S containing actual response body
     */
    S getTransaction(String dlt, String transactionHash, Class<S> responseClass);

}
