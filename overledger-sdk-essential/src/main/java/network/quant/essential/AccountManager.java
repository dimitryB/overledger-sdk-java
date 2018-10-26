package network.quant.essential;

import network.quant.api.Account;
import network.quant.essential.exception.EmptyAccountException;
import network.quant.essential.exception.IllegalKeyException;
import java.util.HashMap;
import java.util.Map;

/**
 * Overledger account manager
 * Used for storing and dispatching account of different DLTs by {@link DefaultOverledgerSDK}
 */
class AccountManager {

    private Map<String, Account> accountMap;

    private AccountManager() {}

    /**
     * Register a account into the manager
     * @param dlt String containing DLT type
     * @param account Account containing account instance
     */
    void registerAccount(String dlt, Account account) throws IllegalKeyException, EmptyAccountException {
        if (null == dlt) {
            throw new IllegalKeyException();
        } else if (null == account) {
            throw new EmptyAccountException();
        }
        if (null == this.accountMap) {
            this.accountMap = new HashMap<>();
        }
        this.accountMap.put(dlt, account);
    }

    /**
     * Get give DLT wallet
     * @param dlt String containing DLT type
     * @return Account containing give DLT wallet
     */
    Account getAccount(String dlt) {
        if (null != this.accountMap) {
            return this.accountMap.get(dlt);
        }
        return null;
    }

    static AccountManager newInstance() {
        return new AccountManager();
    }

}
