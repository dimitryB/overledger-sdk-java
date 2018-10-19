package io.overledger.essential;

import io.overledger.api.*;
import io.overledger.essential.exception.DltNotSupportedException;
import java.util.List;
import java.util.UUID;

/**
 * Basic implementation of OverledgerSDK
 */
public final class DefaultOverledgerSDK implements OverledgerSDK {

    private NETWORK network;
    private AccountManager accountManager = AccountManager.newInstance();
    private Client client = OverledgerClient.getInstance();

    private DefaultOverledgerSDK(NETWORK network) { this.initial(network); }

    private void throwCauseException(Exception e) throws Exception {
        Throwable exception = e;
        while (null != exception.getCause()) {
            exception = e.getCause();
        }
        throw (Exception) exception;
    }

    private boolean verifySupportAllDLTs(OverledgerTransaction ovlTransaction) {
        return !ovlTransaction.getDltData().stream().anyMatch(dltTransaction ->
                (null == this.accountManager.getAccount(dltTransaction.getDlt())));
    }

    public NETWORK getNetwork() {
        return this.network;
    }

    @Override
    public void initial(NETWORK network) {
        this.network = network;
    }

    @Override
    public void addAccount(String dlt, Account account) {
        this.accountManager.registerAccount(dlt, account.withNetwork(this.network));
    }

    @Override
    public OverledgerTransaction writeTransaction(OverledgerTransaction ovlTransaction) throws Exception {
        if (!this.verifySupportAllDLTs(ovlTransaction)) {
            throw new DltNotSupportedException();
        }
        OverledgerTransaction overledgerTransaction = null;
        try {
            ovlTransaction.getDltData().stream()
                    .map(dltTransaction -> (DltTransactionRequest)dltTransaction)
                    .map(dltTransactionRequest -> {
                        this.accountManager.getAccount(dltTransactionRequest.getDlt()).sign(
                                dltTransactionRequest.getFromAddress(),
                                dltTransactionRequest.getToAddress(),
                                dltTransactionRequest.getMessage(),
                                dltTransactionRequest
                        );
                        return dltTransactionRequest;
                    });
            overledgerTransaction = this.client.postTransaction(ovlTransaction, OverledgerTransaction.class, OverledgerTransaction.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    @Override
    public OverledgerTransaction readTransaction(UUID overledgerTransactionID) throws Exception {
        OverledgerTransaction overledgerTransaction = null;
        try {
            overledgerTransaction = this.client.getTransaction(overledgerTransactionID, OverledgerTransaction.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    @Override
    public List<OverledgerTransaction> readTransactions(String mappId) throws Exception {
        List<OverledgerTransaction> overledgerTransactionList = null;
        try {
            overledgerTransactionList = this.client.getTransactions(mappId, OverledgerTransaction.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransactionList;
    }

    @Override
    public OverledgerTransaction readTransaction(String dlt, String transactionHash) throws Exception {
        OverledgerTransaction overledgerTransaction = null;
        try {
            overledgerTransaction = this.client.getTransaction(dlt, transactionHash, OverledgerTransaction.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    public static DefaultOverledgerSDK newInstance(NETWORK network) {
        return new DefaultOverledgerSDK(network);
    }

}
