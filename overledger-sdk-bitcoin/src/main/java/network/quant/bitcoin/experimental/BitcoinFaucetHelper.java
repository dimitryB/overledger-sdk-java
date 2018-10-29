package network.quant.bitcoin.experimental;

import network.quant.bitcoin.BitcoinAccount;
import network.quant.bitcoin.experimental.Dto.FaucetResponseDto;
import network.quant.exception.ClientResponseException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;

/**
 * Experimental class for helping developers use Overledger Bitcoin faucet
 */
public class BitcoinFaucetHelper {

    private static BitcoinFaucetHelper I;
    private static final BigDecimal BTC_IN_SATOSHI = BigDecimal.valueOf(100000000L);
    private static final int ONE_BTC = 1;
    private WebClient webClient;
    private String url;

    private BitcoinFaucetHelper() {
        this.webClient = WebClient.create();
    }

    /**
     * Fund given BitcoinAccount with 1 BTC
     * @param bitcoinAccount BitcoinAccount containing the bitcoin account
     */
    public void fundAccount(BitcoinAccount bitcoinAccount) {
        this.webClient
                .post()
                .uri(
                        url,
                        bitcoinAccount.getKey().toAddress(bitcoinAccount.getNetworkParameters()).toBase58(),
                        ONE_BTC
                )
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> clientResponse
                        .bodyToMono(ByteArrayResource.class)
                        .map(ByteArrayResource::getByteArray)
                        .map(String::new)
                        .map(ClientResponseException::new)
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> clientResponse
                        .bodyToMono(ByteArrayResource.class)
                        .map(ByteArrayResource::getByteArray)
                        .map(String::new)
                        .map(ClientResponseException::new)
                )
                .bodyToMono(FaucetResponseDto.class)
                .doOnSuccess(faucetResponseDto -> bitcoinAccount.addUtxo(
                        faucetResponseDto.getTxnHash(),
                        faucetResponseDto.getVout(),
                        BTC_IN_SATOSHI.multiply(faucetResponseDto.amount.abs()).longValue(),
                        1,
                        faucetResponseDto.getAddress()))
                .doOnError(RuntimeException::new)
                .block();
    }

    public static BitcoinFaucetHelper getInstance(String url) {
        if (null == I) {
            I = new BitcoinFaucetHelper();
        }
        I.url = url;
        return I;
    }

}
