package network.quant.api;

import network.quant.util.Address;
import network.quant.util.Page;
import network.quant.util.PagedResult;
import network.quant.util.Transaction;

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
     * Read transactions from BPI layer
     * HTTP GET method to BPI layer
     * @param mappId String containing Overledger Mapp ID
     * @param  page Page containing paging information
     * @return PagedResult containing actual response body
     */
    PagedResult getTransactions(String mappId, Page page);

    /**
     * Read transaction from BPI layer
     * HTTP GET method to BPI layer
     * @param dlt String containing DLT type. e.g: bitcoin, ethereum, ripple. Make sure the DLT type is accepted by BPI
     * @param transactionHash String containing DLT transaction hash value
     * @param responseClass Class containing response body class type
     * @return S containing actual response body
     */
    S getTransaction(String dlt, String transactionHash, Class<S> responseClass);

    /**
     * Search transaction base on transaction hash
     * @param transactionHash String containing transaction hash
     * @param responseClass Class containing response class
     * @return Transaction containing the transaction
     */
    Transaction searchTransaction(String transactionHash, Class<Transaction> responseClass);

    /**
     * Search address base on address string
     * @param address String containing address
     * @param responseClass Class containing response class
     * @return Address containing the address
     */
    Address searchAddress(String address, Class<Address> responseClass);

    void getLicenceCheck();

}
