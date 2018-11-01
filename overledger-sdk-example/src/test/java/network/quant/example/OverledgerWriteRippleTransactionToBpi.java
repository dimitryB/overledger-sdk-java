package network.quant.example;

import network.quant.api.Account;
import network.quant.essential.dto.DltTransactionRequest;
import network.quant.ripple.RippleAccount;
import network.quant.ripple.experimental.RippleFaucetHelper;
import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
public class OverledgerWriteRippleTransactionToBpi {

    public static void main(String[] args) {

        DltTransactionRequest writeDltTransactionRequest = DltTransactionRequest
                .builder()
                .fromAddress("rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh")
                .toAddress("rMfoffJtVzKM71f1J2Fw1hsanQZD3eEGP2")
                .amount(BigInteger.valueOf(205L*1000000000L))
                .fee(BigInteger.valueOf(12000L))
                .sequence(4L)
                .message("Test me")
                .build();
        Account rippleAccount = RippleAccount.getInstance("snoPBrXtMeMyMHUVTgbuqAfg1SUTb");
        rippleAccount.sign(
                "rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh",
                "rMfoffJtVzKM71f1J2Fw1hsanQZD3eEGP2",
                "Testme",
                writeDltTransactionRequest
        );

        // Account rippleAccount1 = RippleAccount.getInstance("snwjAjEK2cFPCDKLAXnUsjBFrcXpd");
        RippleFaucetHelper
                .getInstance("http://faucet.ripple.devnet.overledger.io/{address}/{amount}")
                .fundAccount("raJUMreeE7FrCvAHT6uXVYJY6kKmei3pwq", BigDecimal.valueOf(210));
    }

}
