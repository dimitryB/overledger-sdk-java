package network.quant.api;

import java.util.List;
import java.util.UUID;

/**
 * Definition of Overledger transaction
 * This is the basic Overledger message definition that BPI layer accepts
 */
public interface OverledgerTransaction {

    /**
     * Get Overledger transaction ID (Optional: new transaction does not required tx ID)
     * @return UUID containing Overledger transaction ID
     */
    UUID getOverledgerTransactionId();

    /**
     * Get Mapp ID
     * @return String containing Mapp ID
     */
    String getMappId();

    /**
     * Get timestamp (Optional: new transaction does not required timestamp)
     * @return String containing timestamp
     */
    String getTimestamp();

    /**
     * Get DLT transaction list
     * @return DltTransaction list containing DLT transaction list
     */
    <T extends DltTransaction> List<T> getDltData();

}
