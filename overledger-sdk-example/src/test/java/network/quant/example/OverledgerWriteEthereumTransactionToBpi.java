package network.quant.example;

import network.quant.OverledgerContext;
import network.quant.api.Account;
import network.quant.api.DLT;
import network.quant.api.NETWORK;
import network.quant.essential.DefaultOverledgerSDK;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.ethereum.EthereumAccount;
import network.quant.exception.ClientResponseException;
import lombok.extern.slf4j.Slf4j;
import org.web3j.utils.Numeric;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

@Slf4j
public class OverledgerWriteEthereumTransactionToBpi {

    private static final String PRIVATE_KEY = "9EFA671A7B261FFB675ED31F22A06FB93B2BA1307540D97523586377FEC98B17";

    private DefaultOverledgerSDK overledgerManager = DefaultOverledgerSDK.newInstance(NETWORK.TEST);
    private NETWORK network = NETWORK.TEST;

    private OverledgerWriteEthereumTransactionToBpi() {
        this.addAccount();
        this.writeTransaction();
    }

    private void addAccount() {
        Account wallet = EthereumAccount.getInstance(NETWORK.TEST, Numeric.hexStringToByteArray(PRIVATE_KEY), new BigInteger("2"));
        this.overledgerManager.addAccount(DLT.ethereum.name(), wallet);
    }

    private void writeTransaction() {
        DltTransactionRequest writeDltTransactionRequest = DltTransactionRequest
                .builder()
                .dlt(DLT.ethereum.name())
                .fromAddress("0x1a02f7945a3cdf4283c1a90ac2011ca24927201f")
                .toAddress("0x51513683c429b2eaf67ff1f07499253ed4615c64")
                .message("This is a test message for Ethereum")
                .amount(new BigInteger("10000"))
                .callbackUrl("http://overledger.io/dlt/ethereum")
                .build();
        OverledgerTransactionRequest writeOverledgerTransactionRequest = OverledgerTransactionRequest.builder()
                .mappId("network.quant.softwarelicensechecker")
                .dltData(Collections.singletonList(writeDltTransactionRequest))
                .build();

        try {
            System.out.println(this.overledgerManager.writeTransaction(writeOverledgerTransactionRequest));
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
        new OverledgerWriteEthereumTransactionToBpi();
    }

}
