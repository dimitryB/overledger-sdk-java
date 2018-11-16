package network.quant.ethereum.experimental;

import network.quant.OverledgerContext;
import network.quant.ethereum.EthereumAccount;
import network.quant.ethereum.experimental.dto.FaucetResponseDto;
import network.quant.exception.ClientResponseException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.web3j.crypto.Credentials;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Experimental class for helping developer use Overleder Ethereum faucet
 */
public class EthereumFaucetHelper {

    private static final String BEARER = "Bearer";
    private static EthereumFaucetHelper I;
    private WebClient webClient;
    private String url;

    private EthereumFaucetHelper() {
        this.webClient = WebClient.builder()
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        String.format("%s %s:%s", BEARER, OverledgerContext.MAPP_ID, OverledgerContext.BPI_KEY)
                )
                .build();
    }

    /**
     * Found given EthereumAccount with 1 ETH
     * @param ethereumAccount EthereumAccount containing the Ethereum account
     */
    public void fundAccount(EthereumAccount ethereumAccount) {
        String address = Credentials.create(ethereumAccount.getEcKeyPair()).getAddress();
        this.webClient
                .post()
                .uri(this.url, address, new BigDecimal("1000000000000000000"))
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
                .doOnSuccess(faucetResponseDto -> ethereumAccount.setNonce(BigInteger.ZERO))
                .doOnError(RuntimeException::new)
                .block();
    }

    public static EthereumFaucetHelper getInstance(String url) {
        if (null == I) {
            I = new EthereumFaucetHelper();
        }
        I.url = url;
        return I;
    }

}
