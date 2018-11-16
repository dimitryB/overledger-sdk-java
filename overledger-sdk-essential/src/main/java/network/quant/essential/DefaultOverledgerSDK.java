package network.quant.essential;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.quant.api.*;
import network.quant.essential.dto.DltBytesTransactionRequest;
import network.quant.essential.dto.DltStreamTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.essential.exception.DltNotSupportedException;
import network.quant.essential.exception.EmptyAccountException;
import network.quant.essential.exception.EmptyDltException;
import network.quant.essential.exception.IllegalKeyException;
import lombok.extern.slf4j.Slf4j;
import network.quant.util.CommonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Basic implementation of OverledgerSDK
 */
@Slf4j
public final class DefaultOverledgerSDK implements OverledgerSDK {

    private static final int KB = 1024;
    private NETWORK network;
    private AccountManager accountManager;
    private Client client;

    private DefaultOverledgerSDK(NETWORK network) {
        this(network, AccountManager.newInstance(),OverledgerClient.getInstance());
    }

    private DefaultOverledgerSDK(NETWORK network, AccountManager accountManager, Client client) {
        this.initial(network);
        this.accountManager = accountManager;
        this.client = client;
    }

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
        try {
            this.accountManager.registerAccount(dlt, account.withNetwork(this.network));
        } catch (IllegalKeyException e) {
            log.error(e.toString(), e);
        } catch (EmptyAccountException e) {
            log.error(e.toString(), e);
        }
    }

    @Override
    public OverledgerTransaction writeTransaction(OverledgerTransaction ovlTransaction) throws Exception {
        if (null == ovlTransaction) {
            throw new NullPointerException();
        }
        if (null == ovlTransaction.getDltData()) {
            throw new EmptyDltException();
        }
        if (!this.verifySupportAllDLTs(ovlTransaction)) {
            throw new DltNotSupportedException();
        }
        OverledgerTransaction overledgerTransaction = null;
        try {
            ovlTransaction.getDltData().stream()
                    .map(dltTransaction -> (DltTransactionRequest)dltTransaction)
                    .map(dltTransactionRequest -> {
                        if (dltTransactionRequest instanceof DltStreamTransactionRequest) {
                            this.accountManager.getAccount(dltTransactionRequest.getDlt()).sign(
                                    dltTransactionRequest.getFromAddress(),
                                    dltTransactionRequest.getToAddress(),
                                    ((DltStreamTransactionRequest) dltTransactionRequest).getInputStream(),
                                    dltTransactionRequest
                            );
                        } else if (dltTransactionRequest instanceof DltBytesTransactionRequest) {
                            this.accountManager.getAccount(dltTransactionRequest.getDlt()).sign(
                                    dltTransactionRequest.getFromAddress(),
                                    dltTransactionRequest.getToAddress(),
                                    ((DltBytesTransactionRequest) dltTransactionRequest).getBytes(),
                                    dltTransactionRequest
                            );
                        } else {
                            this.accountManager.getAccount(dltTransactionRequest.getDlt()).sign(
                                    dltTransactionRequest.getFromAddress(),
                                    dltTransactionRequest.getToAddress(),
                                    dltTransactionRequest.getMessage(),
                                    dltTransactionRequest
                            );
                        }
                        return dltTransactionRequest;
                    }).collect(Collectors.toList());
            overledgerTransaction = (OverledgerTransactionResponse)this.client.postTransaction(ovlTransaction, OverledgerTransactionRequest.class, OverledgerTransactionResponse.class);

            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(overledgerTransaction));
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    @Override
    public OverledgerTransaction readTransaction(UUID overledgerTransactionID) throws Exception {
        OverledgerTransaction overledgerTransaction = null;
        try {
            overledgerTransaction = (OverledgerTransactionResponse)this.client.getTransaction(overledgerTransactionID, OverledgerTransactionResponse.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    @Override
    public List<OverledgerTransaction> readTransactions(String mappId) throws Exception {
        List<OverledgerTransaction> overledgerTransactionList = null;
        try {
            overledgerTransactionList = this.client.getTransactions(mappId, OverledgerTransactionResponse.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransactionList;
    }

    @Override
    public OverledgerTransaction readTransaction(String dlt, String transactionHash) throws Exception {
        OverledgerTransaction overledgerTransaction = null;
        try {
            overledgerTransaction = (OverledgerTransactionResponse)this.client.getTransaction(dlt, transactionHash, OverledgerTransactionResponse.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    /**
     * Write transaction to BPI layer from byte array
     * @param ovlTransaction OverledgerTransaction containing overledger transaction request
     * @param data byte array containing the data
     * @return Overledger response
     * @throws Exception throw if connection between client and manager is broken
     */
    public OverledgerTransaction writeTransaction(OverledgerTransaction ovlTransaction, byte[] data) throws Exception {
        if (null == ovlTransaction) {
            throw new NullPointerException();
        }
        if (null == ovlTransaction.getDltData()) {
            throw new EmptyDltException();
        }
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
                                data,
                                dltTransactionRequest
                        );
                        return dltTransactionRequest;
                    }).collect(Collectors.toList());
            overledgerTransaction = (OverledgerTransactionResponse)this.client.postTransaction(ovlTransaction, OverledgerTransactionRequest.class, OverledgerTransactionResponse.class);
        } catch (Exception e) {
            this.throwCauseException(e);
        }
        return overledgerTransaction;
    }

    /**
     * Write transaction to BPI layer from input stream
     * @param ovlTransaction OverledgerTransaction containing overledger transaction request
     * @param inputStream InputSteam containing data stream
     * @return Overledger response
     * @throws Exception throw if connection between client and manager is broken
     */
    public OverledgerTransaction writeTransaction(OverledgerTransaction ovlTransaction, InputStream inputStream) throws Exception {
        return this.writeTransaction(ovlTransaction, CommonUtil.getStream(inputStream));
    }

    public static DefaultOverledgerSDK newInstance(NETWORK network) {
        return new DefaultOverledgerSDK(network);
    }

    public static DefaultOverledgerSDK newInstance(NETWORK network, AccountManager accountManager, Client client) {
        return new DefaultOverledgerSDK(network, accountManager, client);
    }

}
