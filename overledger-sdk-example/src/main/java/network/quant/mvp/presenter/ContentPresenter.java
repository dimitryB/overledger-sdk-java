package network.quant.mvp.presenter;

import network.quant.api.OverledgerTransaction;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.event.ApplicationHistoryChangeHandler;
import java.io.File;
import java.util.List;
import java.util.UUID;

public interface ContentPresenter extends Presenter, ApplicationHistoryChangeHandler {

    void loadSettingsPropertiesFromFile(File file);

    void loadSettings(String bpiKey, String mappId, String writeTransactions, String readTransactionsByMappId, String readTransactionsByTransactionId, String readTransactionsByTransactionHash);

    void onGotoMainWithUpdate(String bpiKey, String mappId, String writeTransactions, String readTransactionsByMappId, String readTransactionsByTransactionId, String readTransactionsByTransactionHash);

    void onGotoMain();

    void generate(String name);

    void receive(String name);

    void loadWallet(String type, String secretKey, String address);

    void loadWallet(String type, int amount);

    void onGotoDetails(int index);

    void onPurchase(String payment, String reg, File contactFile, String policyId, String premium, String annual);

    void purchaseFailed(String message);

    void purchaseSuccess(OverledgerTransaction transaction);

    void purchaseSuccess(UUID overledgerTransactionId);

    void loadOrders(List<OverledgerTransaction> readTransactions);

    void loadOrders(OverledgerTransactionResponse[] writeOverledgerTransactionResponses);

}
