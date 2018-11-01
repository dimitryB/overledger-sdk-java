package network.quant.event;

import network.quant.api.OverledgerTransaction;
import network.quant.essential.dto.OverledgerTransactionResponse;

import java.util.List;
import java.util.UUID;

public interface ApplicationDataHandler {

    void onLoadSetting(String bpiKey, String mappId, String writeTransactions, String readTransactionsByMappId, String readTransactionsByTransactionId, String readTransactionsByTransactionHash);

    void onAccountGenerated(String type, String secretKey, String address);

    void onAccountReceived(String type, int amount);

    void onPurchaseFailed(String account_not_found);

    void onPurchaseSuccess(OverledgerTransaction writeTransaction);

    void onPurchaseSuccess(UUID overledgerTransactionId);

    void onLoadOrders(List<OverledgerTransaction> readTransactions);

    void onLoadOrders(OverledgerTransactionResponse[] writeOverledgerTransactionResponses);

}
