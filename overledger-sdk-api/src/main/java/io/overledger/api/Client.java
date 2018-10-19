package io.overledger.api;

import java.util.List;
import java.util.UUID;

/**
 * Definition of BPI client
 * Implement methods in this interface for connecting to BPI service
 */
public interface Client {

    /**
     * Write transaction to Overledger BPI layer
     * HTTP POST method to BPI layer
     * @param ovlTransaction OverledgerTransaction containing the actual request body
     * @param requestClass Class containing request body class type
     * @param responseClass Class containing response body class type
     * @return OverledgerTransaction containing actual response body
     */
    OverledgerTransaction postTransaction(OverledgerTransaction ovlTransaction, Class<OverledgerTransaction> requestClass, Class<OverledgerTransaction> responseClass);

    /**
     * Read transaction from BPI layer
     * HTTP GET method to BPI layer
     * @param overledgerTransactionID UUID containing Overledger transaction ID
     * @param responseClass Class containing response body class type
     * @return OverledgerTransaction containing actual response body
     */
    OverledgerTransaction getTransaction(UUID overledgerTransactionID, Class<OverledgerTransaction> responseClass);

    /**
     * Read transactions from BPI layer
     * HTTP GET method to BPI layer
     * @param mappId String containing Overledger Mapp ID
     * @param responseClass Class containing response body class type
     * @return List of OverledgerTransaction}containing actual response body list
     */
    List<OverledgerTransaction> getTransactions(String mappId, Class<OverledgerTransaction> responseClass);

    /**
     * Read transaction from BPI layer
     * HTTP GET method to BPI layer
     * @param dlt String containing DLT type. e.g: bitcoin, ethereum, ripple. Make sure the DLT type is accepted by BPI
     * @param transactionHash String containing DLT transaction hash value
     * @param responseClass Class containing response body class type
     * @return OverledgerTransaction containing actual response body
     */
    OverledgerTransaction getTransaction(String dlt, String transactionHash, Class<OverledgerTransaction> responseClass);

}
