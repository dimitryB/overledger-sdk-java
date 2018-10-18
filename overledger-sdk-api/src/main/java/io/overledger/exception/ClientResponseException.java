package io.overledger.exception;

/**
 * Throw this exception when client returning 4xx or 5xx status
 */
public class ClientResponseException extends Exception {

    private String responseBody;

    public ClientResponseException(String body) {
        this.responseBody = body;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

}