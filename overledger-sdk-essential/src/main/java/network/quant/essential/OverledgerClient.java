package network.quant.essential;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import network.quant.OverledgerContext;
import network.quant.api.Client;
import network.quant.essential.dto.OverledgerTransactionRequest;
import network.quant.essential.dto.OverledgerTransactionResponse;
import network.quant.exception.ClientResponseException;
import network.quant.exception.RedirectException;
import network.quant.util.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Basic implementation of client
 */
@Slf4j
public final class OverledgerClient<T extends OverledgerTransactionRequest, S extends OverledgerTransactionResponse> implements Client<T, S> {

    private static Client I;
    private static final String BEARER = "Bearer";
    private static final String HEADER_LOCATION = "Location";
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
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.WRITE_TRANSACTIONS)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(Mono.just(ovlTransaction), requestClass)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(Mono.just(ovlTransaction), requestClass)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public S getTransaction(UUID overledgerTransactionID, Class<S> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.READ_TRANSACTIONS_BY_TRANSACTION_ID, overledgerTransactionID)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public List<S> getTransactions(String mappId, Class<S> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID, mappId)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(String.class)
                    .map(s -> {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            return (List<S>)objectMapper.readValue(s, new TypeReference<List>() {});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    })
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(String.class)
                    .map(s -> {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            return (List<S>)objectMapper.readValue(s, new TypeReference<List<S>>() {});
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        return null;
                    })
                    .block();
        }
    }

    @Override
    public PagedResult<S> getTransactions(String mappId, Page page, Class<PagedResult<S>> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.READ_TRANSACTIONS_BY_MAPP_ID_BY_PAGE, mappId, page.getPageNumber(), page.getPageSize())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public S getTransaction(String dlt, String transactionHash, Class<S> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.READ_TRANSACTIONS_BY_TRANSACTION_HASH, dlt, transactionHash)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public List<BalanceResponse> postBalances(List<BalanceRequest> balanceRequests) {
        try {
            return this.webClient
                    .post()
                    .uri(OverledgerContext.BALANCES_CHECK)
                    .body(BodyInserters.fromObject(balanceRequests))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(String.class)
                    .map(s -> {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            return (List<BalanceResponse>)objectMapper.readValue(s, new TypeReference<List<BalanceResponse>>() {});
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        return null;
                    })
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .post()
                    .uri(e.getUrl())
                    .body(BodyInserters.fromObject(balanceRequests))
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(String.class)
                    .map(s -> {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            return (List<BalanceResponse>)objectMapper.readValue(s, new TypeReference<List<BalanceResponse>>() {});
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        return null;
                    })
                    .block();
        }
    }

    @Override
    public Transaction searchTransaction(String transactionHash, Class<Transaction> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.SEARCH_TRANSACTIONS, transactionHash)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public Address searchAddress(String address, Class<Address> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.SEARCH_ADDRESSES, address)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    @Override
    public Block searchBlock(String dlt, String blockhash, Class<Block> responseClass) {
        try {
            return this.webClient
                    .get()
                    .uri(OverledgerContext.SEARCH_CHAIN_BLOCKS, dlt, blockhash)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .onStatus(HttpStatus::is3xxRedirection, clientResponse -> Mono.error(new RedirectException(clientResponse.headers().header(HEADER_LOCATION).get(0))))
                    .bodyToMono(responseClass)
                    .block();
        } catch (RedirectException e) {
            return this.webClient
                    .get()
                    .uri(e.getUrl())
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, this::getClientResponse)
                    .onStatus(HttpStatus::is5xxServerError, this::getClientResponse)
                    .bodyToMono(responseClass)
                    .block();
        }
    }

    static Client getInstance() {
        if (null == I) {
            I = new OverledgerClient();
        }
        return I;
    }

}
