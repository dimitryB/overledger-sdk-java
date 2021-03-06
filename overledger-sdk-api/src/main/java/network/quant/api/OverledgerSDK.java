package network.quant.api;

import network.quant.util.*;

import java.util.List;
import java.util.UUID;

/**
 * Definition of BPI OverledgerSDK
 * Implement this interface for Overledger manager
 * Overledger manager is the main entry point of SDK
 * It provides methods for accessing BPI layer, it also provides methods for account management
 */
public interface OverledgerSDK {

    /**
     * Initial manager with network address
     * Network ID or Chain ID is used for some DLT to identify blockchain node
     * @param network NETWORK containing connecting network address
     */
    void initial(NETWORK network);

    /**
     * Add account into Overledger manager
     * DLT should be unique
     * @param dlt String containing DLT type
     * @param account Account containing account implementation
     */
    void addAccount(String dlt, Account account);

    /**
     * Write transaction to BPI layer
     * @param ovlTransaction OverledgerTransaction containing overledger transaction request
     * @return Overledger response
     * @throws Exception throw if connection between client and manager is broken
     */
    OverledgerTransaction writeTransaction(OverledgerTransaction ovlTransaction) throws Exception;

    /**
     * Read transaction from BPI layer base on transaction ID
     * @param overledgerTransactionID UUID containing overledger transaction ID
     * @return Overledger response
     * @throws Exception throw if connection between client and manager is broken
     */
    OverledgerTransaction readTransaction(UUID overledgerTransactionID) throws Exception;

    /**
     * Read transactions from BPI layer base on Mapp ID
     * @param mappId String containing Mapp ID
     * @return OverledgerTransaction containing response body from the call
     * @throws Exception throw if connection between client and manager is broken
     */
    List<OverledgerTransaction> readTransactions(String mappId) throws Exception;

    /**
     * Read transactions from BPI layer base on Mapp ID
     * @param mappId String containing Mapp ID
     * @param  page Page containing page
     * @return OverledgerTransaction containing response body from the call
     * @throws Exception throw if connection between client and manager is broken
     */
    PagedResult<OverledgerTransaction> readTransactions(String mappId, Page page) throws Exception;

    /**
     * Read transaction from BPI layer base on txHash and DLT
     * @param dlt String containing DLT type
     * @param transactionHash String containing txhash
     * @return OverledgerTransaction containing response body from the call
     * @throws Exception throw if connection between client and manager is broken
     */
    OverledgerTransaction readTransaction(String dlt, String transactionHash) throws Exception;

    /**
     * Search transaction from BPI layer base on txHash and DLT
     * @param transactionHash String containing txhash
     * @param responseClass Class containing response type
     * @return Transaction containing transaction implementation
     * @throws Exception throw if connection between client and manager is broken
     */
    Transaction searchTransaction(String transactionHash, Class<Transaction> responseClass) throws Exception;

    /**
     * Search address from BPI layer base on txHash and DLT
     * @param address String containing address
     * @param responseClass Class containing response type
     * @return Address containing address implementation
     * @throws Exception throw if connection between client and manager is broken
     */
    Address searchAddress(String address, Class<Address> responseClass);

    /**
     * Search address base on address string
     * @param dlt String containing DLT type
     * @param blockhash String containing block hash
     * @param responseClass Class containing response class
     * @return Block containing the block implementation
     */
    Block searchBlock(String dlt, String blockhash, Class<Block> responseClass);

}
