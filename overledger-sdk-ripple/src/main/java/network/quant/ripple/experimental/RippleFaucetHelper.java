package network.quant.ripple.experimental;

import network.quant.OverledgerContext;
import network.quant.exception.ClientResponseException;
import network.quant.exception.RedirectException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Experimental class for helping developer use Overleder Ripple faucet
 */
public class RippleFaucetHelper {

    private static final String BEARER = "Bearer";
    private static RippleFaucetHelper I;
    private WebClient webClient;
    private String url;

    private RippleFaucetHelper() {
        this.webClient = WebClient.builder()
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        String.format("%s %s:%s", BEARER, OverledgerContext.MAPP_ID, OverledgerContext.BPI_KEY)
                )
                .build();
    }

    private void redirectPost(String address, String uri) {
        this.webClient
                .post()
                .uri(uri)
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
                .bodyToMono(String.class)
                .doOnSuccess(System.out::println)
                .doOnError(RuntimeException::new)
                .block();
    }

    /**
     * Fund Ripple address with given amount in XRP
     * @param address String containing ripple address
     * @param amount BigDecimal containing amount in XRP
     */
    public void fundAccount(String address, BigDecimal amount) {
        try {
            this.webClient
                    .post()
                    .uri(this.url, address, amount)
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
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header("Location").get(0))))
                    .bodyToMono(String.class)
                    .doOnSuccess(System.out::println)
                    .block();
        } catch (RedirectException e) {
            this.redirectPost(address, e.getUrl());
        }
    }

    public static RippleFaucetHelper getInstance(String url) {
        if (null == I) {
            I = new RippleFaucetHelper();
        }
        I.url = url;
        return I;
    }

}
