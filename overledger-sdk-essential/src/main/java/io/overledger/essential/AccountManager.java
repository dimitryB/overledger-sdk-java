package io.overledger.essential;

import io.overledger.api.Account;
import java.util.HashMap;
import java.util.Map;

/**
 * Overledger account manager
 * OverledgerSDK store and dispatch wallet for different DLTs
 */
final class AccountManager {

    private Map<String, Account> walletList;

    private AccountManager() {}

    /**
     * Register a account into the manager
     * @param dlt String containing DLT type
     * @param account Account containing account instance
     */
    void registerAccount(String dlt, Account account) {
        if (null == this.walletList) {
            this.walletList = new HashMap<>();
        }
        this.walletList.put(dlt, account);
    }

    /**
     * Get give DLT wallet
     * @param dlt String containing DLT type
     * @return Account containing give DLT wallet
     */
    Account getAccount(String dlt) {
        if (null != this.walletList) {
            return this.walletList.get(dlt);
        }
        return null;
    }

    static AccountManager newInstance() {
        return new AccountManager();
    }

}
