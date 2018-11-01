package network.quant.example;

import network.quant.OverledgerContext;
import network.quant.api.Account;
import network.quant.api.DLT;
import network.quant.api.NETWORK;
import network.quant.bitcoin.BitcoinAccount;
import network.quant.bitcoin.experimental.BitcoinFaucetHelper;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.exception.ClientResponseException;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

@Slf4j
public class OverledgerWriteBitcoinTransactionToBpi {

    private DefaultOverledgerSDK overledgerManager = DefaultOverledgerSDK.newInstance(NETWORK.TEST);
    private String fromAddress;

    private OverledgerWriteBitcoinTransactionToBpi() {
        this.addAccount();
        this.writeTransaction();
    }

    private void addAccount() {
        Account bitcoinAccount = BitcoinAccount.getInstance(this.overledgerManager.getNetwork());
        this.fromAddress = ((BitcoinAccount)bitcoinAccount).getKey().toAddress(((BitcoinAccount)bitcoinAccount).getNetworkParameters()).toBase58();
        BitcoinFaucetHelper.getInstance("http://faucet.bitcoin.devnet.overledger.io/v1/faucet/fund/{address}/{amount}").fundAccount((BitcoinAccount)bitcoinAccount);
        this.overledgerManager.addAccount(DLT.bitcoin.name(), bitcoinAccount);
        // TODO wait until transaction is confirmed
    }

    private void writeTransaction() {
        try {
            this.overledgerManager.writeTransaction(
                    OverledgerTransactionRequest.builder()
                            .mappId("network.quant.softwarelicensechecker")
                            .dltData(Collections.singletonList(
                                    DltTransactionRequest
                                            .builder()
                                            .dlt(DLT.bitcoin.name())
                                            .fromAddress(this.fromAddress)
                                            .toAddress("2N2LjrjcK4USyKJ8xMBpV19TTVSWBoDru2d")
                                            .message("This is a test message for Bitcoin")
                                            .amount(new BigInteger("1000000"))
                                            .callbackUrl("http://overledger.io/dlt/bitcoin")
                                            .build()
                            ))
                            .build()
            );
        } catch (Exception e) {
            if (e instanceof ClientResponseException) {
                System.out.println(((ClientResponseException)e).getResponseBody());
            } else {
                e.printStackTrace();
            }
        }
    }

    private static void loadContext() {
        try {
            OverledgerContext.loadContext(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties")
            );
        } catch (IOException e) {
            log.error("Fail to load BPI context", e);
        }
    }

    public static void main(String[] args) {
        loadContext();
        new OverledgerWriteBitcoinTransactionToBpi();
    }

}
