package network.quant.essential;

import network.quant.OverledgerContext;
import network.quant.api.Client;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.exception.ClientResponseException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.UUID;

/**
 * Basic implementation of client
 */
public final class OverledgerClient<T extends OverledgerTransactionRequest, S extends OverledgerTransactionResponse> implements Client<T, S> {

    private static Client I;
    private static final String BEARER = "Bearer";
    private WebClient webClient;

    private OverledgerClient() {
        this.webClient = WebClient.builder()
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        String.format("%s %s:%s", BEARER, OverledgerContext.MAPP_ID, OverledgerContext.BPI_KEY)
                )
                .build();
    }

    private Mono<ClientResponseException> getClientResponse(ClientResponse clientResponse) {
        return clientResponse
                .bodyToMono(ByteArrayResource.class)
                .map(ByteArrayResource::getByteArray)
                .map(String::new)
                .map(ClientResponseException::new);
    }

    @Override
    public S postTransaction(T ovlTransaction, Class<T> requestClass, Class<S> responseClass) {
        return this.webClient
                .post()
                .uri(OverledgerContext.WRITE_TRANSACTIONS)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(ovlTransaction), requestClass)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                .bodyToMono(responseClass)
                .block();
    }

    @Override
    public S getTransaction(UUID overledgerTransactionID, Class<S> responseClass) {
        return this.webClient
                .get()
                .uri(OverledgerContext.READ_TRANSACTIONS_BY_TRANSACTION_ID, overledgerTransactionID)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                .bodyToMono(responseClass)
                .block();
    }

    @Override
    public List<S> getTransactions(String mappId, Class<S> responseClass) {
        return this.webClient
                .get()
                .uri(OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID, mappId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                .bodyToFlux(responseClass)
                .collectList()
                .block();
    }

    @Override
    public S getTransaction(String dlt, String transactionHash, Class<S> responseClass) {
        return this.webClient
                .get()
                .uri(OverledgerContext.READ_TRANSACTIONS_BY_TRANSACTION_HASH, dlt, transactionHash)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                .bodyToMono(responseClass)
                .block();
    }

    static Client getInstance() {
        if (null == I) {
            I = new OverledgerClient();
        }
        return I;
    }

}
