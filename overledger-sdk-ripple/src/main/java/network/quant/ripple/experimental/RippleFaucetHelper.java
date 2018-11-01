package network.quant.ripple.experimental;

import network.quant.exception.ClientResponseException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;

/**
 * Experimental class for helping developer use Overleder Ripple faucet
 */
public class RippleFaucetHelper {

    private static RippleFaucetHelper I;
    private WebClient webClient;
    private String url;

    private RippleFaucetHelper() {
        this.webClient = WebClient.create();
    }

    /**
     * Fund Ripple address with given amount in XRP
     * @param address String containing ripple address
     * @param amount BigDecimal containing amount in XRP
     */
    public void fundAccount(String address, BigDecimal amount) {
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
                .bodyToMono(String.class)
                .doOnSuccess(System.out::println)
                .block();
    }

    public static RippleFaucetHelper getInstance(String url) {
        if (null == I) {
            I = new RippleFaucetHelper();
        }
        I.url = url;
        return I;
    }

}
