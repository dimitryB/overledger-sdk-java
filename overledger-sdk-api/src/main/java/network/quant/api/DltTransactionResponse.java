package network.quant.api;

/**
 * Definition of DLT transaction response
 * This is the basic DLT request message definition that BPI layer response
 */
public interface DltTransactionResponse extends DltTransaction {

    /**
     * Get sender address
     * @return String containing the address
     */
    String getTransactionHash();

    /**
     * Get receiver address
     * @return String containing the address
     */
    Integer getBlockHeight();

    /**
     * Get change address (optional)
     * @return String containing the address
     */
    DLTStatus getStatus();

}
