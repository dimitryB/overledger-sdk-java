package network.quant.example;

import network.quant.OverledgerContext;
import network.quant.api.NETWORK;
import network.quant.api.OverledgerTransaction;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.exception.ClientResponseException;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.List;

@Slf4j
public class OverledgerReadTransactionFromMapp {

    private static void loadContext() {
        try {
            OverledgerContext.loadContext(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("context.properties")
            );
        } catch (IOException e) {
            log.error("Fail to load BPI context", e);
        }
    }

    private static void tryToReadTransactionFromMappId() {
        DefaultOverledgerSDK overledgerManager = DefaultOverledgerSDK.newInstance(NETWORK.TEST);
        try {
            List<OverledgerTransaction> overledgerTransactionList = overledgerManager.readTransactions("network.quant.softwarelicensechecker");
            overledgerTransactionList.forEach(System.out::println);
        } catch (Exception e) {
            if (e instanceof ClientResponseException) {
                ClientResponseException clientResponseException = (ClientResponseException) e;
                System.out.println(clientResponseException.getResponseBody());
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        loadContext();
        tryToReadTransactionFromMappId();
    }

}
